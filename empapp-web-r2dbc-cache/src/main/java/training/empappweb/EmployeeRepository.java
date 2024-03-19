package training.empappweb;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends R2dbcRepository<Employee, Long> {

    // SELECT employee.id FROM employee WHERE employee.name = $1 LIMIT 1
    Mono<Boolean> existsByName(String name);

    Flux<EmployeeResource> findAllBy();

    Mono<EmployeeResource> findResourceById(long id);
}
