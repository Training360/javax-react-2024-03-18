package training.empappweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class EmployeesGateway {

    @Bean
    public Consumer<Flux<EmployeeResource>> handleEvent() {
        return employeeResourceFlux -> employeeResourceFlux
                .subscribe(r ->log.info("Event has come: {}", r));
    }
}
