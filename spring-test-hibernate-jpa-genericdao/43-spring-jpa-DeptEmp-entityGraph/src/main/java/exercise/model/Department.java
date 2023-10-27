package exercise.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;


@NamedEntityGraph(
  name = "department-with-employees",
  attributeNodes = { @NamedAttributeNode("employees") }
)

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude="employees")
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<Employee>();

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

}

