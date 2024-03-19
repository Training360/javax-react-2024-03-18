package training.empappclient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
@Slf4j
public class EmpappHttpInterfaceClient implements CommandLineRunner {

    private EmployeesService employeesService;

    @Override
    public void run(String... args) throws Exception {
//        var result = employeesService.list().collectList().block();
//        log.info("Result: {}", result);

//        var result = employeesService.findById(14).block();
//        log.info("Result: {}", result);

        var result = Flux.just(10, 11, 12)
                .flatMap(i -> employeesService.findById(i))
                .log()
                .collectList().block();
        log.info("Result: {}", result);

    }
}
