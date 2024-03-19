package training.empappweb;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@ControllerAdvice
public class EmployeesExceptionHandler {

    @ExceptionHandler
    public Mono<ProblemDetail> handle(WebExchangeBindException exception) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, exception.getMessage()
        );
        problemDetail.setType(URI.create("employees/validation-error"));
        problemDetail.setTitle("Validation error");
        problemDetail.setProperty("id", UUID.randomUUID().toString());

        var items = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ValidationItem(e.getField(), e.getDefaultMessage()))
                .toList();

        problemDetail.setProperty("validationErrors", items);

        return Mono.just(problemDetail);
    }

    @ExceptionHandler
    public Mono<ProblemDetail> handle(InvalidEmployeeException exception) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, exception.getMessage()
        );
        problemDetail.setType(URI.create("employees/service-validation-error"));
        problemDetail.setTitle("Service validation error");

        problemDetail.setProperty("name", exception.getName());

        return Mono.just(problemDetail);
    }
}
