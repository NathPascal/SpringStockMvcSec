package fr.fms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany
    private List<Article> articles;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
