package training.empappweb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping
    public Flux<EmployeeResource> listEmployees() {
        return employeeService
                .listEmployees();
    }

    @GetMapping("/{id}")
    public Mono<EmployeeResource> findEmployeeById(@PathVariable("id") long id) {
        return employeeService.findEmployeeById(id)
                .doOnNext(e -> log.debug("Find employee by id: {}, employee: {}", id, e));
    }
}
