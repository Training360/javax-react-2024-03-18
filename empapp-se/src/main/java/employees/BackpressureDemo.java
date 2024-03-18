package employees;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BackpressureDemo {

    public static void main(String[] args) {
        // Automatikus backpressure
//        Flux.range(1, Integer.MAX_VALUE)
//                .log()
//                .concatMap(x -> Mono.delay(Duration.ofMillis(100)), 1) // simulate that processing takes time
//                .blockLast();

//        Flux.interval(Duration.ofMillis(1))
//                .doOnNext(System.out::println)
//                .blockLast();
//                ;

        Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop()
                .log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100)))
                        .blockLast();
    }
}
