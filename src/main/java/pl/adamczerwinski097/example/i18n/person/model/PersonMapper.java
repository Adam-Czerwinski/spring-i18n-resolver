package pl.adamczerwinski097.example.i18n.person.model;

public class PersonMapper {
    private PersonMapper() {
    }

    public static Person mapToEntity(PersonCreateRequest personCreateRequest) {
        return Person.builder().name(personCreateRequest.getName())
                .lastName(personCreateRequest.getLastName())
                .pesel(personCreateRequest.getPesel()).build();
    }

    public static PersonResponse mapToResponse(Person person) {
        return PersonResponse.builder().name(person.getName()).lastName(person.getLastName()).pesel(person.getPesel()).id(person.getId()).build();
    }
}
