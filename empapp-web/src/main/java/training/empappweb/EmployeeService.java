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

    // MapStruct
    public EmployeeResource toResource(Employee employee) {
        return new EmployeeResource(employee.getId(), employee.getName());
    }
}
