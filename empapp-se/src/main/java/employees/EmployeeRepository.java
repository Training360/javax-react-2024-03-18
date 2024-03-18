package employees;

import java.util.List;
import java.util.Optional;

public class EmployeeRepository {


    public static Optional<Employee> findEmployeeById(long id) {
        if (id > 0) {
            return Optional.of(new Employee("John Doe " + id, 1980));
        }
        else {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        var ids = List.of(1, -1, 2, -2, 3, -3);
//        var employees = ids.stream()
//                .map(i -> findEmployeeById(i))
//                .filter(o -> o.isPresent())
//                .map(o -> o.get())
//                .toList();
        var employees = ids.stream()
                        .map(EmployeeRepository::findEmployeeById)
                                .flatMap(Optional::stream)
                .toList()
                ;
        System.out.println(employees);
    }
}
