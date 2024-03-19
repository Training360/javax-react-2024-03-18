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

    public Mono<EmployeeResource> createEmployee(Mono<EmployeeResource> employeeResource) {
        return employeeResource
                .flatMap(r -> validate(r).thenReturn(r))
                .map(this::toEntity)
                .flatMap(employeeRepository::save)
                .map(this::toResource);
    }

//    public void validate(EmployeeResource employeeResource) {
//        if (employeeResource.getName().startsWith("xxx")) {
//            throw new InvalidEmployeeException("Can not start with xxx");
//        }
//    }

    public Mono<Void> validate(EmployeeResource employeeResource) {
        return employeeRepository.existsByName(employeeResource.getName())
                .handle((alreadyExists, sink) -> {
                   if (alreadyExists) {
                       sink.error(new InvalidEmployeeException("Name already exists: %s".formatted(employeeResource.getName()),
                               employeeResource.getName()));
                   }
                });
    }

    public Mono<EmployeeResource> updateEmployee(Mono<EmployeeResource> employeeResource) {
//        return Mono.just(employeeResource)
//                .map(this::toEntity)
//                .flatMap(employeeRepository::save)
//                .map(this::toResource);

        // Mi van akkor, ha az employeeresource NEM tartalmazza az ÖSSZES attribútum értéket
        // Ha lenne több attribútum, mondjuk 5, és csak a name értékét szeretnénk update-elni

        return employeeResource
                .flatMap(r -> employeeRepository.findById(r.getId()))
                .doOnNext(e -> e.setName(e.getName()))
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
