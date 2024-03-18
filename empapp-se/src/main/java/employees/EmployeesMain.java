package employees;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

public class EmployeesMain {

    public static void main(String[] args) {
        // Funkcionális reaktív programozás
        // Project Reactor
        // Stream:
        //   Mono: 0-1 elemet tartalmaz (Optional)
        //   Flux: 0-n elemet tartalmaz (Stream)

        Mono
                .just(1)
//                .subscribe(i -> System.out.println(i));
                .subscribe(System.out::println);

        Mono
                .empty()
                .subscribe(System.out::println);

        Flux
                .just(1, 2, 3)
                .subscribe(System.out::println);

        var names = List.of("Jane", "Jack", "John");
        Flux
                .fromIterable(names)
                .subscribe(System.out::println);

        Flux.fromIterable(names)
                .filter(s -> s.charAt(1) == 'a')
                .map(s -> s.charAt(s.length() - 1))
                .log()
                .subscribe(System.out::println);

        //                 .take(2) - limit() párja

        var selectedNames = Flux.just(
                new Employee("John Doe", 1980),
                new Employee("Jane Doe", 1990),
                new Employee("Jack Smith", 2000)
        )
                .filter(e -> e.getYearOfBirth() >= 1990)
                .map(Employee::getName)
                .collectList()
                .block();

        System.out.println(selectedNames);

        System.out.println("***");

//        Stream.of(
//                        new Employee("John Doe", 1980),
//                        new Employee("Jane Doe", 1990),
//                        new Employee("Jack Smith", 2000)
//                )
//                .peek(System.out::println) // a peek() metódusnak felel meg
//                .map(e -> e.getAgeIn(2020));
//

        Flux.just(
                new Employee("John Doe", 1980),
                new Employee("Jack Smith", 2000),
                new Employee("Jane Doe", 1990)
        )
                .doOnNext(System.out::println) // a peek() metódusnak felel meg
                .map(e -> e.getAgeIn(1995))
                .onErrorReturn(-1)
                .subscribe(System.out::println);



    }
}
