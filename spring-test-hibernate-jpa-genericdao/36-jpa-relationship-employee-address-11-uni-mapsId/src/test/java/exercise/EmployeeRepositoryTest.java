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
import exercise.repository.AddressRepository;

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@lombok.extern.slf4j.Slf4j
public class EmployeeRepositoryTest {

    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;

    EmployeeRepositoryTest( EmployeeRepository employeeRepository, AddressRepository addressRepository ) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }

    @Test
    @Transactional
    void test_phones_of_employee() {
        var employee = employeeRepository.findById(1L);
        var phones = employee.getPhones();

        assertThat(employee.getName()).isEqualTo("Allison");
        assertThat(phones).extracting("model").contains("Galaxy", "iPhone");
    }

    @Test
    @Transactional
    void test_projects_of_employee() {
        var employee = employeeRepository.findById(1L);
        var projects = employee.getProjects();

        assertThat(employee.getName()).isEqualTo("Allison");
        assertThat(projects).extracting("title").contains("Java Projects", "Android App Projects");
    }

    /*
    @Test
    @Transactional
    void test_address_of_employee() {
        var employee = employeeRepository.findById(1L);
        var address = employee.getAddress();

        assertThat(employee.getName()).isEqualTo("Allison");
        assertThat(address.getCity()).isEqualTo("Daejon");
    }
    */

    @Test
    @Transactional
    void test_employee_of_address() {
        var address = addressRepository.findById(1L);
        assertThat(address.getCity()).isEqualTo("Daejon");

        var employee = address.getEmployee();
        assertThat(employee.getName()).isEqualTo("Allison");
    }

}

