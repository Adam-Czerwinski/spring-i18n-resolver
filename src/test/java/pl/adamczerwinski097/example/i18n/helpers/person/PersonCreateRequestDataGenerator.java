package pl.adamczerwinski097.example.i18n.helpers.person;

import pl.adamczerwinski097.example.i18n.person.model.PersonCreateRequest;

public class PersonCreateRequestDataGenerator {
    public static PersonCreateRequest getRandomPersonCreateRequest() {
        return PersonCreateRequest.builder().name(PersonDataGenerator.getRandomName()).lastName(PersonDataGenerator.getRandomLastName())
                .pesel(PersonDataGenerator.getRandomPesel()).build();
    }
}
