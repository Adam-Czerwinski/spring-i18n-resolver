package pl.adamczerwinski097.example.i18n.person.exceptions;

import lombok.Getter;
import pl.adamczerwinski097.example.i18n.core.configuration.exception.BusinessLogicException;

public class PersonExistsException extends BusinessLogicException {
    @Getter
    private final String pesel;

    public PersonExistsException(String pesel) {
        super(String.format("Person exists by pesel: %s", pesel), new Object[]{pesel});
        this.pesel = pesel;
    }
}
