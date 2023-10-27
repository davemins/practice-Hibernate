package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;
import exercise.model.Employee;

@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class JpaApiEntityGraph {

    @PersistenceContext
    private EntityManager entityManager;

    // Default
    @Test
    void whenFindAllDepartment_thenFetchEmployeeLazily() {

        var qry = "SELECT d FROM Department d";
        var departments = entityManager.createQuery(qry, Department.class).getResultList();
        // -->
        // SELECT * FROM Department

        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");

        log.trace("{}", departments);
        // -->
        //[ Department(id=1, name=Development),
        //  Department(id=2, name=Management),
        //  Department(id=3, name=Marketing),
        //  Department(id=4, name=Personnel)
        //]

        log.trace("-----------------------------------------");
        log.trace("For each department, print its employees:");
        log.trace("-----------------------------------------");

        for (Department department: departments) {
            log.trace("{} <--- {}", department, department.getEmployees());
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 1
            // Department(id=1, name=Development) <--- [Employee(id=5, name=Allison), Employee(id=6, name=Lois), Employee(id=7, name=Ramon), Employee(id=8, name=Derek)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 2
            // Department(id=2, name=Management) <--- [Employee(id=9, name=Maria), Employee(id=10, name=Rosemary), Employee(id=11, name=Emma)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 3
            // Department(id=3, name=Marketing) <--- [Employee(id=12, name=Gabriel), Employee(id=13, name=Frances)]
            // -->
            // SELECT * FROM Employee e WHERE e.department_id = 4
            // Department(id=4, name=Personnel) <--- [Employee(id=14, name=Elaine)]

            // Notice: Employee(id=15, name=Bonnie) doesn't display!
        }
    }

    // Using FETCH JOIN
    @Test
    void whenFindAllDepartment_withJoinFetch_thenFetchEmployeeByTheSameQuery() {

        var qry = "SELECT d FROM Department d LEFT JOIN FETCH d.employees";
        var departments = entityManager.createQuery(qry, Department.class).getResultList();
        // -->
        // SELECT d.*, e.*
        // FROM Department d
        // LEFT JOIN Employee e ON e.department_id = d.id

        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");

        log.trace("{}", departments);

        log.trace("-----------------------------------------");
        log.trace("For each department, print its employees:");
        log.trace("-----------------------------------------");

        for (Department department: departments) {
            log.trace("{} <--- {}", department, department.getEmployees());
        }
    }

    // Using Entity Graph
    @Test
    void whenFindAllDepartment_usingEntityGraph_thenFetchDepartmentAndEmployee() {

        var entityGraph = entityManager.getEntityGraph("department-with-employees");

        var qry = "SELECT d FROM Department d";
        var departments = entityManager.createQuery(qry, Department.class)
            .setHint("jakarta.persistence.loadgraph", entityGraph)
            .getResultList();
        // -->
        // SELECT d.*, e.*
        // FROM Department d
        // LEFT JOIN Employee e ON e.department_id = d.id

        assertThat(departments).extracting("name").contains("Development", "Management", "Marketing", "Personnel");

        for (Department department: departments) {
            log.trace("{} ---> {}", department, department.getEmployees());
        }
    }

}

