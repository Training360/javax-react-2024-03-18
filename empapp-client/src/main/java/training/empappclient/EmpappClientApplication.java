package training.empappclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class EmpappClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpappClientApplication.class, args);
	}

	@Bean
	public EmployeesService employeesService(WebClient.Builder builder) {
		var client = builder.baseUrl("http://localhost:8080").build();
		var factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
		return factory.createClient(EmployeesService.class);
	}

}
