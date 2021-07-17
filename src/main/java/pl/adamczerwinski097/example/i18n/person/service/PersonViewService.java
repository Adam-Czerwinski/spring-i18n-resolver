package pl.adamczerwinski097.example.i18n.person.service;

import pl.adamczerwinski097.example.i18n.person.model.PersonCreateRequest;
import pl.adamczerwinski097.example.i18n.person.model.PersonResponse;

public interface PersonViewService {

    PersonResponse create(PersonCreateRequest personCreateRequest);
}
