package employees;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class EmployeeTest {

    @Test
    void map() {
        var names = Flux.just(
                new Employee("John Doe", 1980),
                new Employee("Jack Smith", 2000),
                new Employee("Jane Doe", 1990)
        ).map(Employee::getName);

        StepVerifier.create(names)
                .expectNext("John Doe")
                .expectNext("Jack Smith")
                .expectNext("Jane Doe")
                .verifyComplete();
    }
}
