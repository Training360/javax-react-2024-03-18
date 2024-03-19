package training.empappweb;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
@AllArgsConstructor
public class EmployeeWebController {

    private EmployeeService employeeService;

    @GetMapping("/")
    public Mono<Rendering> listEmployees() {
        return Mono.just(
                Rendering.view("index")
                        .modelAttribute("employees", employeeService.listEmployees())
                        .build()
        );
    }
}
