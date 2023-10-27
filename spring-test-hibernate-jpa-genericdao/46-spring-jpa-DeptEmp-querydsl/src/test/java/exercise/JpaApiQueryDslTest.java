package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import jakarta.persistence.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.core.Tuple;

import exercise.config.TestPersistenceConfig;
import exercise.model.Department;
import exercise.model.DepartmentValue;
import exercise.model.Employee;
import exercise.model.EmployeeValue;
import exercise.model.QDepartment;
import exercise.model.QEmployee;


@SpringJUnitConfig(TestPersistenceConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Transactional
@lombok.extern.slf4j.Slf4j
public class JpaApiQueryDslTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void test_queryDsl_department_findAll() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var department = QDepartment.department;

        // SELECT d FROM Department d
        List<Department> depts = queryFactory
            .selectFrom(department)
            .fetch();

        // System.out.println(depts);

        assertThat(depts.size()).isEqualTo(4);
        assertThat(depts).extracting("name").containsOnly("Development", "Management", "Marketing", "Personnel");
    }

    @Test
    void test_queryDsl_employee_findAll() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var employee = QEmployee.employee;

        // SELECT e FROM Employee e
        List<Employee> emps = queryFactory
            .selectFrom(employee)
            .fetch();

        //System.out.println(emps);

        assertThat(emps.size()).isEqualTo(11);
        assertThat(emps).extracting("name")
            .containsOnly(
                    "Allison", "Lois", "Ramon", "Derek", "Maria",
                    "Rosemary", "Emma", "Gabriel", "Frances", "Elaine",
                    "Bonnie"
                    );
    }

    @Test
    void test_queryDsl_department_findByName() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var department = QDepartment.department;

        // SELECT d FROM Department d where d.name = 'Development'
        Department dept = queryFactory
            .selectFrom(department)
            .where(department.name.eq("Development"))
            .fetchOne();

        assertEquals(dept.getName(), "Development");
    }

    @Test
    void test_queryDsl_department_findLikeByName() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var department = QDepartment.department;

        // SELECT d FROM Department d where d.name like 'M%'
        List<Department> depts = queryFactory
            .selectFrom(department)
            .where(department.name.like("M%"))
            .fetch();

        assertThat(depts.size()).isEqualTo(2);
        assertThat(depts).extracting("name").containsOnly("Management", "Marketing");
    }

    @Test
    void test_queryDsl_department_findAll_orderby() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var department = QDepartment.department;

        // SELECT d FROM Department d ORDER BY d.name
        List<Department> depts = queryFactory
            .selectFrom(department)
            //.orderBy(department.name.asc())
            .orderBy(department.name.desc())
            .fetch();

        assertThat(depts.size()).isEqualTo(4);
        assertThat(depts).extracting("name").containsExactly("Personnel", "Marketing", "Management", "Development");
    }

    @Test
    void test_queryDsl_department_findAll_projection_string() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var department = QDepartment.department;

        // SELECT d.name FROM Department d
        List<String> names = queryFactory
            .select(department.name)
            .from(department)
            .fetch();

        // System.out.println(names);

        assertThat(names.size()).isEqualTo(4);
        assertThat(names).containsOnly("Development", "Management", "Marketing", "Personnel");
    }

    @Test
    void test_queryDsl_department_findAll_projection_return_tuple() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var department = QDepartment.department;


        // SELECT d.id, d.name FROM Department d
        List<Tuple> tuples = queryFactory
            .select(department.id, department.name)
            .from(department)
            .fetch();

        //System.out.println(tuples);
        // ---> [[1, Development], [2, Management], [3, Marketing], [4, Personnel]]

        tuples.forEach(tuple ->
            System.out.printf("Department(id=%d, name=%s)\n", tuple.get(department.id), tuple.get(department.name))
        );

        assertThat(tuples.size()).isEqualTo(4);
    }

    @Test
    void test_queryDsl_department_join() {

        var queryFactory = new JPAQueryFactory(entityManager);
        var department = QDepartment.department;
        var employee = QEmployee.employee;

        // SELECT d FROM Department d LEFT JOIN Employee e ON e.department_id = d.id
        List<Department> depts = queryFactory
            .selectFrom(department)
            .leftJoin(department.employees, employee)
            .fetch();

        //System.out.println(depts);
        // Hibernate 5:
        // ---> [Department(id=1, name=Development),
        //       Department(id=1, name=Development),
        //       Department(id=1, name=Development),
        //       Department(id=1, name=Development),
        //       Department(id=2, name=Management),
        //       Department(id=2, name=Management),
        //       Department(id=2, name=Management),
        //       Department(id=3, name=Marketing),
        //       Department(id=3, name=Marketing),
        //       Department(id=4, name=Personnel)]
        // Hibernate 6:
        // ---> [Department(id=1, name=Development),
        //       Department(id=2, name=Management),
        //       Department(id=3, name=Marketing),
        //       Department(id=4, name=Personnel)]

        assertThat(depts.size()).isEqualTo(4).isNotEqualTo(10);
    }

}


