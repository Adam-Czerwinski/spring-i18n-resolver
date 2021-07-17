package pl.adamczerwinski097.example.i18n.person.service.impl;

import org.springframework.stereotype.Service;
import pl.adamczerwinski097.example.i18n.person.model.Person;
import pl.adamczerwinski097.example.i18n.person.model.PersonCreateRequest;
import pl.adamczerwinski097.example.i18n.person.model.PersonMapper;
import pl.adamczerwinski097.example.i18n.person.model.PersonResponse;
import pl.adamczerwinski097.example.i18n.person.service.PersonService;
import pl.adamczerwinski097.example.i18n.person.service.PersonViewService;

@Service
public class PersonViewServiceImpl implements PersonViewService {
    private final PersonService personService;

    public PersonViewServiceImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public PersonResponse create(PersonCreateRequest personCreateRequest) {
        Person person = PersonMapper.mapToEntity(personCreateRequest);
        person = personService.save(person);
        return PersonMapper.mapToResponse(person);
    }
}
