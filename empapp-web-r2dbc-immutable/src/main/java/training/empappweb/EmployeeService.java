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
//                .findAll()
//                .map(this::toResource);
                .findAllBy();
    }

    public Mono<EmployeeResource> findEmployeeById(long id) {
        return employeeRepository
//                .findById(id)
//                .map(this::toResource);
                .findResourceById(id);
    }

    public Mono<EmployeeResource> createEmployee(EmployeeResource employeeResource) {
        return Mono.just(employeeResource)
//                .flatMap(r -> validate(r).thenReturn(r))
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
        return employeeRepository.existsByName(employeeResource.name())
                .handle((alreadyExists, sink) -> {
                   if (alreadyExists) {
                       sink.error(new InvalidEmployeeException("Name already exists: %s".formatted(employeeResource.name()),
                               employeeResource.name()));
                   }
                });
    }

    public Mono<EmployeeResource> updateEmployee(EmployeeResource employeeResource) {
//        return Mono.just(employeeResource)
//                .map(this::toEntity)
//                .flatMap(employeeRepository::save)
//                .map(this::toResource);

        // Mi van akkor, ha az employeeresource NEM tartalmazza az ÖSSZES attribútum értéket
        // Ha lenne több attribútum, mondjuk 5, és csak a name értékét szeretnénk update-elni

        return Mono.just(employeeResource)
                .flatMap(r -> employeeRepository.findById(employeeResource.id()))
                .map(e -> new Employee(e.id(), employeeResource.name()))
                .flatMap(e -> employeeRepository.save(e))
                .map(this::toResource);
    }

    public Mono<Void> deleteEmployee(long id) {
        return employeeRepository.deleteById(id);
    }

    // MapStruct
    public EmployeeResource toResource(Employee employee) {
        return new EmployeeResource(employee.id(), employee.name());
    }

    public Employee toEntity(EmployeeResource employeeResource) {
        return new Employee(employeeResource.id(), employeeResource.name());
    }
}
