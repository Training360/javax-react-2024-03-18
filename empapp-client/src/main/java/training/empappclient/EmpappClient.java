package training.empappclient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

//@Component
@Slf4j
@AllArgsConstructor
public class EmpappClient implements CommandLineRunner {

    private WebClient.Builder webClientBuilder;

    @Override
    public void run(String... args) throws Exception {
      log.info("Running application");

      var client = webClientBuilder.baseUrl("http://localhost:8080").build();

//      var response = client.get().uri("/api/employees").retrieve().bodyToMono(String.class).block();
      var response = client.get().uri("/api/employees").retrieve().bodyToFlux(Employee.class).collectList().block();
      log.info("Response: {}", response);
    }
}
