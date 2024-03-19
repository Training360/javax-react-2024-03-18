package training.empappweb;

import lombok.Getter;

public class InvalidEmployeeException extends Exception {

    @Getter
    private String name;

    public InvalidEmployeeException(String message, String name) {
        super(message);
        this.name = name;
    }
}
