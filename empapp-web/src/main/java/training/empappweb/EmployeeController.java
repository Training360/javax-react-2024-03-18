package training.empappweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @GetMapping
    public Flux<EmployeeResource> listEmployees() {
        return Flux.just(
                new EmployeeResource(1L, "John Doe"),
                new EmployeeResource(2L, "Jane Doe")
        );
    }

    @GetMapping("/{id}")
    public Mono<EmployeeResource> findEmployeeById(@PathVariable("id") long id) {
        return Mono.just(new EmployeeResource(id, "John Doe"));
    }
}
