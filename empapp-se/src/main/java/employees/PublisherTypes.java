package employees;

import reactor.core.publisher.Mono;

public class PublisherTypes {

    public static int getInitialValue() {
        System.out.println("getInitialValue");
        return 5;
    }

    public static void main(String[] args) {
        var values = Mono.just(getInitialValue());
        values.subscribe(System.out::println);
        values.subscribe(System.out::println);

        // COLD
//        var values = Mono.defer(() -> Mono.just(getInitialValue()));

//        values.subscribe(System.out::println);
//        values.subscribe(System.out::println);

    }
}
