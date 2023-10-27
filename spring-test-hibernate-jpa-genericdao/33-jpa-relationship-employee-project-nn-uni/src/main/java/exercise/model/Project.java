package exercise.model;

import jakarta.persistence.*;

@Entity

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString//(exclude="employees")
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    public Project() {}

    public Project(String title) {
        this.title = title;
    }

}

