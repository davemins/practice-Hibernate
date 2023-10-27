package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.PersistenceException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import exercise.config.TestPersistenceConfig;
import exercise.model.Employee;
import exercise.repository.EmployeeRepository;

// Dependency Injection with SpringExtension
// https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testcontext-junit-jupiter-di

// Constructor injection for test classes must not be used
//    in conjunction with JUnit Jupiter’s @TestInstance(PER_CLASS) support
//    if @DirtiesContext is used to close the test’s ApplicationContext
//    before or after test methods.

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@lombok.extern.slf4j.Slf4j
public class EmployeeRepositoryTest {

    private final EmployeeRepository employeeRepository;

    EmployeeRepositoryTest( EmployeeRepository employeeRepository ) {
        this.employeeRepository = employeeRepository;
    }

    @Test
    void test_count() {
        var cnt = employeeRepository.count();

        assertThat(cnt).isEqualTo(11L);
    }

    @Test
    void test_findAll() {
        var cnt = employeeRepository.count();

        var employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(cnt);

        assertThat(employees)
            .extracting("id")
            .contains(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L);
    }

    @Test
    void test_findById() {
        var employee = employeeRepository.findById(1L);

        assertThat(employee.getName()).isEqualTo("Allison");
    }

}

