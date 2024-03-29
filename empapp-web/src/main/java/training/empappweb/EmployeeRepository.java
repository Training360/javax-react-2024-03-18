package training.empappweb;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmployeeRepository {

    private AtomicLong sequence = new AtomicLong();

    private final List<Employee> employees = Collections.synchronizedList(new ArrayList<>(List.of(
            new Employee(sequence.incrementAndGet(), "John Doe"),
            new Employee(sequence.incrementAndGet(), "Jack Doe")
    )));


    public Flux<Employee> findAll() {
        return Flux.fromIterable(employees);
    }

    public Mono<Employee> findById(long id) {
        return Flux.fromIterable(employees)
                .filter(e -> e.getId() == id)
                .singleOrEmpty();
    }

    public Mono<Employee> save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(sequence.incrementAndGet());
            employees.add(employee);
            return Mono.just(employee);
        }
        else {
            return Flux.fromIterable(employees)
                    .filter(e -> e.getId() == employee.getId())
                    .doOnNext(e -> e.setName(employee.getName()))
                    .singleOrEmpty();

        }
    }

    public Mono<Boolean> existsByName(String name) {
        return Flux.fromIterable(employees)
                .any(e -> e.getName().equals(name));
    }

    public Mono<Void> deleteById(long id) {
        employees.removeIf(e -> e.getId() == id);
        return Mono.empty();
    }
}
