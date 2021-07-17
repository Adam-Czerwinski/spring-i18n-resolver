package pl.adamczerwinski097.example.i18n.person.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adamczerwinski097.example.i18n.person.exceptions.PersonExistsException;
import pl.adamczerwinski097.example.i18n.person.model.Person;
import pl.adamczerwinski097.example.i18n.person.repository.PersonRepository;
import pl.adamczerwinski097.example.i18n.person.service.PersonService;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    @Override
    public Person save(Person person) {
        if (isNew(person.getId())) {
            return createPerson(person);
        } else {
            return updatePerson(person);
        }
    }

    private Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    private Person createPerson(Person person) {
        if (personRepository.existsByPesel(person.getPesel())) {
            throw new PersonExistsException(person.getPesel());
        } else {
            return personRepository.save(person);
        }
    }

    private boolean isNew(Long id) {
        if (id == null || id <= 0L) {
            return true;
        } else {
            return personRepository.existsById(id);
        }
    }
}
