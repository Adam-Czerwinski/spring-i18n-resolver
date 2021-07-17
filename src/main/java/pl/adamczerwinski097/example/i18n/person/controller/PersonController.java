package pl.adamczerwinski097.example.i18n.person.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adamczerwinski097.example.i18n.person.model.PersonCreateRequest;
import pl.adamczerwinski097.example.i18n.person.model.PersonResponse;
import pl.adamczerwinski097.example.i18n.person.service.PersonViewService;

import javax.validation.Valid;

@RequestMapping("/persons")
@RestController
public class PersonController {
    private final PersonViewService personViewService;

    public PersonController(PersonViewService personViewService) {
        this.personViewService = personViewService;
    }

    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonCreateRequest personCreateRequest) {
        return ResponseEntity.ok(personViewService.create(personCreateRequest));
    }
}
