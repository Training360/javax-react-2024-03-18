package training.empappweb;

import jakarta.validation.constraints.NotBlank;

public record EmployeeResource(Long id, @NotBlank String name) {

}
