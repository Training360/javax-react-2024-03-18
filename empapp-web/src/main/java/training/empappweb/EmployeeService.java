package training.empappweb;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public Flux<EmployeeResource> listEmployees() {
        return employeeRepository
                .findAll()
                .map(this::toResource);
    }

    public Mono<EmployeeResource> findEmployeeById(long id) {
        return employeeRepository
                .findById(id)
                .map(this::toResource);
    }

    public Mono<EmployeeResource> createEmployee(EmployeeResource employeeResource) {
        return Mono.just(employeeResource)
                .map(this::toEntity)
                .flatMap(employeeRepository::save)
                .map(this::toResource);
    }

    public Mono<EmployeeResource> updateEmployee(EmployeeResource employeeResource) {
//        return Mono.just(employeeResource)
//                .map(this::toEntity)
//                .flatMap(employeeRepository::save)
//                .map(this::toResource);

        // Mi van akkor, ha az employeeresource NEM tartalmazza az ÖSSZES attribútum értéket
        // Ha lenne több attribútum, mondjuk 5, és csak a name értékét szeretnénk update-elni

        return Mono.just(employeeResource)
                .flatMap(r -> employeeRepository.findById(employeeResource.getId()))
                .doOnNext(e -> e.setName(employeeResource.getName()))
                .map(this::toResource);
    }

    public Mono<Void> deleteEmployee(long id) {
        return employeeRepository.deleteById(id);
    }

    // MapStruct
    public EmployeeResource toResource(Employee employee) {
        return new EmployeeResource(employee.getId(), employee.getName());
    }

    public Employee toEntity(EmployeeResource employeeResource) {
        return new Employee(employeeResource.getId(), employeeResource.getName());
    }
}
