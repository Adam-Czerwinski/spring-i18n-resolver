package pl.adamczerwinski097.example.i18n.person.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PersonResponse {
    private Long id;

    private String name;

    private String lastName;

    private String pesel;
}
