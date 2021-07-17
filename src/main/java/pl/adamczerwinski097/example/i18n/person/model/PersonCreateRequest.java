package pl.adamczerwinski097.example.i18n.person.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class PersonCreateRequest {
    @NotBlank
    @NotNull
    @Size(max = 45)
    private String name;

    @NotBlank
    @NotNull
    @Size(max = 45)
    private String lastName;

    @PESEL
    private String pesel;
}
