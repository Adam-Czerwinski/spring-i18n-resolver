package pl.adamczerwinski097.example.i18n.person.model;

import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "persons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personsSequence")
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 45)
    private String name;

    @NotBlank
    @NotNull
    @Size(max = 45)
    @Column(name = "last_name")
    private String lastName;

    @PESEL
    @Column(unique = true, length = 11)
    private String pesel;
}
