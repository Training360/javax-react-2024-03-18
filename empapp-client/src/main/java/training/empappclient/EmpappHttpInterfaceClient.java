package training.empappclient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmpappHttpInterfaceClient implements CommandLineRunner {

    private EmployeesService employeesService;

    @Override
    public void run(String... args) throws Exception {
//        var result = employeesService.list().collectList().block();
//        log.info("Result: {}", result);

        var result = employeesService.findById(14).block();
        log.info("Result: {}", result);

    }
}
