package training.empappweb;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@Configuration
@AllArgsConstructor
public class EmployeeHandler {

    private EmployeeService employeeService;

    private Validator validator;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(RequestPredicates.GET("/api/employees"), this::employees)
                .andRoute(RequestPredicates.GET("/api/employees/{id}"), this::findEmployeeById)
                .andRoute(RequestPredicates.POST("/api/employees"), this::createEmployee)
                .andRoute(RequestPredicates.PUT("/api/employees/{id}"), this::updateEmployee)
                .andRoute(RequestPredicates.DELETE("/api/employees/{id}"), this::deleteEmployee);
    }
    public Mono<ServerResponse> employees(ServerRequest request) {
        return ServerResponse
                .ok()
                .body(employeeService.listEmployees(), EmployeeResource.class);
    }
    public Mono<ServerResponse> findEmployeeById(ServerRequest request) {
        return ServerResponse.ok()
                .body(employeeService.findEmployeeById(Long.parseLong(request.pathVariable("id"))), EmployeeResource.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createEmployee(ServerRequest request) {
        return employeeService.createEmployee(request.bodyToMono(EmployeeResource.class))
                .doOnNext(this::validate)
                .flatMap(employee ->
                        ServerResponse
                                .created(URI.create(String.format("/api/employees/%d", employee.getId())))
                                .bodyValue(employee));
    }

    public void validate(EmployeeResource employeeResource) {
        var violations = validator.validate(employeeResource);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public Mono<ServerResponse> updateEmployee(ServerRequest request) {
        return ServerResponse.ok()
                .body(employeeService.updateEmployee(
                                request.bodyToMono(EmployeeResource.class)),
                        EmployeeResource.class);
    }
    public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
        return employeeService
                .deleteEmployee(Long.parseLong(request.pathVariable("id")))
                .flatMap(v -> ServerResponse.noContent().build());
    }
}
