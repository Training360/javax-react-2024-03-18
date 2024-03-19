package training.empappweb;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private ReactiveRedisTemplate<Long, EmployeeResource> redisTemplate;

    public Flux<EmployeeResource> listEmployees() {
        return employeeRepository
//                .findAll()
//                .map(this::toResource);
                .findAllBy();
    }

    public Mono<EmployeeResource> findEmployeeById(long id) {
        return redisTemplate.opsForValue().get(id)
                .switchIfEmpty(
                        employeeRepository.findResourceById(id)
                                .flatMap(resource -> redisTemplate.opsForValue().set(id, resource).thenReturn(resource))
                );

//        return employeeRepository
//                .findById(id)
//                .map(this::toResource);
//                .findResourceById(id);
    }

    @Transactional
    public Mono<EmployeeResource> createEmployee(EmployeeResource employeeResource) {
        return Mono.just(employeeResource)
//                .flatMap(r -> validate(r).thenReturn(r))
                .map(this::toEntity)
                .flatMap(employeeRepository::save)
                .map(this::toResource)
//                .handle((r, sink) -> sink.error(new IllegalStateException("rollback")))
                ;
    }

//    public void validate(EmployeeResource employeeResource) {
//        if (employeeResource.getName().startsWith("xxx")) {
//            throw new InvalidEmployeeException("Can not start with xxx");
//        }
//    }

    public Mono<Void> validate(EmployeeResource employeeResource) {
        return employeeRepository.existsByName(employeeResource.getName())
                .handle((alreadyExists, sink) -> {
                   if (alreadyExists) {
                       sink.error(new InvalidEmployeeException("Name already exists: %s".formatted(employeeResource.getName()),
                               employeeResource.getName()));
                   }
                });
    }

    public Mono<EmployeeResource> updateEmployee(EmployeeResource employeeResource) {
//        return Mono.just(employeeResource)
//                .map(this::toEntity)
//                .flatMap(employeeRepository::save)
//                .map(this::toResource);

        // Mi van akkor, ha az employeeresource NEM tartalmazza az ÖSSZES attribútum értéket
        // Ha lenne több attribútum, mondjuk 5, és csak a name értékét szeretnénk update-elni

        return Mono.just(employeeResource)
                .flatMap(r -> employeeRepository.findById(employeeResource.getId()))
                .doOnNext(e -> e.setName(employeeResource.getName()))
                .flatMap(e -> employeeRepository.save(e))
                .map(this::toResource)
                .flatMap(r -> redisTemplate.opsForValue().set(r.getId(), r).thenReturn(r));
    }

    public Mono<Void> deleteEmployee(long id) {
        return employeeRepository.deleteById(id);
    }

    // MapStruct
    public EmployeeResource toResource(Employee employee) {
        return new EmployeeResource(employee.getId(), employee.getName());
    }

    public Employee toEntity(EmployeeResource employeeResource) {
        return new Employee(employeeResource.getId(), employeeResource.getName());
    }
}
