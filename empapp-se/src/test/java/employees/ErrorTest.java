package employees;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ErrorTest {

    @Test
    void expectError() {
        var error = Flux.just(1)
                .doOnNext(e -> {throw new IllegalArgumentException("error");});

        StepVerifier
                .create(error)
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}
