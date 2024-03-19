package training.empappweb;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

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
    public Mono<ResponseEntity<EmployeeResource>> findEmployeeById(@PathVariable("id") String id) {
        return employeeService.findEmployeeById(id)
                .doOnNext(e -> log.debug("Find employee by id: {}, employee: {}", id, e))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                ;
    }

    @PostMapping
    public Mono<ResponseEntity<EmployeeResource>> createEmployee(@Valid @RequestBody EmployeeResource employeeResource) {
        return employeeService
                .createEmployee(employeeResource)
                .map(r -> ResponseEntity.created(URI.create("/api/employees/%s".formatted(r.getId()))).body(r));
    }

    @PutMapping("/{id}")
    public Mono<EmployeeResource> updateEmployee(@PathVariable("id") String id, @Valid @RequestBody EmployeeResource employeeResource) {
        if (employeeResource.getId().equals(id)) {
            throw new IllegalArgumentException("Ids not the same");
        }
        return employeeService.updateEmployee(employeeResource);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable String id) {
        return employeeService.deleteEmployee(id);
    }
}
