package training.empappweb;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

    // SELECT employee.id FROM employee WHERE employee.name = $1 LIMIT 1
    Mono<Boolean> existsByName(String name);

    Flux<EmployeeResource> findAllBy();

    Mono<EmployeeResource> findResourceById(String id);
}
