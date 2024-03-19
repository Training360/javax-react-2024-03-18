package training.empappweb;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class EmployeesExceptionHandler {

    @ExceptionHandler
    public Mono<ProblemDetail> handle(WebExchangeBindException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, e.getMessage()
        );
        return Mono.just(problemDetail);
    }
}
