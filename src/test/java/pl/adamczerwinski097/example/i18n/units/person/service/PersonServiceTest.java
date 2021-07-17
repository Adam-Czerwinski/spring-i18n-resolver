package pl.adamczerwinski097.example.i18n.units.person.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.TransactionSystemException;
import pl.adamczerwinski097.example.i18n.helpers.person.PersonDataGenerator;
import pl.adamczerwinski097.example.i18n.person.model.Person;
import pl.adamczerwinski097.example.i18n.person.repository.PersonRepository;
import pl.adamczerwinski097.example.i18n.person.service.PersonService;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
    @Nested
    @DisplayName("save")
    class SaveTest {
        @Test
        @DisplayName("random person - saved")
        void saveNewPerson_saved() {
            Assertions.assertEquals(0, personRepository.count());
            final Person personExpected = PersonDataGenerator.getRandomPerson();
            final Person personActual = personService.save(personExpected);

            Assertions.assertEquals(personExpected.getName(), personActual.getName());
            Assertions.assertEquals(personExpected.getLastName(), personActual.getLastName());
            Assertions.assertNotNull(personActual.getId());
            Assertions.assertTrue(personRepository.existsById(personActual.getId()));
        }

        @Test
        @DisplayName("random person - incorrect pesel - constraint exception")
        void saveNewPerson_badPesel() {
            final Person personExpected = PersonDataGenerator.getRandomPerson();
            personExpected.setPesel(PersonDataGenerator.getWrongPesel());
            final TransactionSystemException expectedTransactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> personService.save(personExpected)); //error occurs when flushing to database
            final Throwable expectedRollbackException = expectedTransactionSystemException.getCause();
            Assertions.assertEquals(RollbackException.class, expectedTransactionSystemException.getCause().getClass()); //rollback occurs
            final Throwable expectedConstraintViolationException = expectedRollbackException.getCause();
            Assertions.assertEquals(ConstraintViolationException.class, expectedConstraintViolationException.getClass()); //because of constraint violations
            final Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) expectedConstraintViolationException).getConstraintViolations();
            final boolean containsPeselPath = constraintViolations.stream().anyMatch(constraintViolation -> constraintViolation.getPropertyPath().toString().equals("pesel"));
            Assertions.assertTrue(containsPeselPath, "Pesel was correct but expected to not be");
        }
    }
}
