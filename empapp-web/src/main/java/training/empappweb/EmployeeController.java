package training.empappweb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public Mono<EmployeeResource> createEmployee(@RequestBody EmployeeResource employeeResource) {
        return employeeService.createEmployee(employeeResource);
    }

    @PutMapping("/{id}")
    public Mono<EmployeeResource> updateEmployee(@PathVariable("id") long id, @RequestBody EmployeeResource employeeResource) {
        if (employeeResource.getId() != id) {
            throw new IllegalArgumentException("Ids not the same");
        }
        return employeeService.updateEmployee(employeeResource);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteEmployee(@PathVariable long id) {
        return employeeService.deleteEmployee(id);
    }
}
