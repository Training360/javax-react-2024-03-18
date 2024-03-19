package training.empappweb;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class EmpappWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpappWebApplication.class, args);
	}

	@PostConstruct
	public void init() {
		Hooks.enableAutomaticContextPropagation();
	}

}
