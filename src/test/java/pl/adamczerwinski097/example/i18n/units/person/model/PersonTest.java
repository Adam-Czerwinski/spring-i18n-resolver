package pl.adamczerwinski097.example.i18n.units.person.model;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.adamczerwinski097.example.i18n.helpers.person.PersonDataGenerator;
import pl.adamczerwinski097.example.i18n.person.model.Person;
import pl.adamczerwinski097.example.i18n.utils.common.BeanValidationUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonTest {

    @Autowired
    private Validator validator2;

    public PersonTest() {
    }

    @Nested
    @DisplayName("validation")
    class PersonValidation {

        @Test
        @DisplayName("validate pesel - polish locale")
        void validatePesel_pl() {
            Validator validator = BeanValidationUtils.getValidator(new Locale("pl"));
            Person person = getPersonBadPesel();
            final Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
            final Optional<ConstraintViolation<Person>> optionalPersonConstraintViolation = getPersonConstraintViolation(constraintViolations, "pesel");
            Assertions.assertTrue(optionalPersonConstraintViolation.isPresent());
            Assertions.assertEquals("własny domyślny pesel error", optionalPersonConstraintViolation.get().getMessage());
        }

        private Person getPersonBadPesel() {
            Person person = PersonDataGenerator.getRandomPerson();
            person.setPesel(PersonDataGenerator.getWrongPesel());
            return person;
        }

        private Optional<ConstraintViolation<Person>> getPersonConstraintViolation(Set<ConstraintViolation<Person>> constraintViolations, String path) {
            return constraintViolations.stream().filter(constraintViolation -> constraintViolation.getPropertyPath().toString().equals(path)).findAny();
        }
    }
}
