package pl.adamczerwinski097.example.i18n.integration.person.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.adamczerwinski097.example.i18n.helpers.person.PersonCreateRequestDataGenerator;
import pl.adamczerwinski097.example.i18n.helpers.person.PersonDataGenerator;
import pl.adamczerwinski097.example.i18n.person.model.Person;
import pl.adamczerwinski097.example.i18n.person.model.PersonCreateRequest;
import pl.adamczerwinski097.example.i18n.person.service.PersonService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {
    private final ObjectMapper objectMapper;

    private final MockMvc mockMvc;

    private final PersonService personService;

    @Autowired
    PersonControllerTest(ObjectMapper objectMapper, MockMvc mockMvc, PersonService personService) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;
        this.personService = personService;
    }

    @Nested
    @DisplayName("create")
    class CreateTest {
        @Test
        @DisplayName("random person - created")
        void createNewPerson_created() throws Exception {
            PersonCreateRequest expectedPerson = PersonCreateRequestDataGenerator.getRandomPersonCreateRequest();
            final String expectedPersonString = objectMapper.writeValueAsString(expectedPerson);
            mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON).content(expectedPersonString))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(expectedPerson.getName()))
                    .andExpect(jsonPath("$.lastName").value(expectedPerson.getLastName()))
                    .andExpect(jsonPath("$.pesel").value(expectedPerson.getPesel()))
                    .andExpect(jsonPath("$.id").isNotEmpty());
        }

        @Test
        @DisplayName("person with existing pesel - 400")
        void createPersonWithExistingPesel_existsPerson() throws Exception {
            Person existingPerson = PersonDataGenerator.getRandomPerson();
            existingPerson = personService.save(existingPerson);

            PersonCreateRequest expectedPerson = PersonCreateRequestDataGenerator.getRandomPersonCreateRequest();
            expectedPerson.setPesel(existingPerson.getPesel());
            final String expectedPersonString = objectMapper.writeValueAsString(expectedPerson);
            mockMvc.perform(post("/persons")
                    .header("Accept-Language","pl")
                    .contentType(MediaType.APPLICATION_JSON).content(expectedPersonString))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(String.format("Test %s", existingPerson.getPesel())))
                    .andExpect(jsonPath("$.status").value(400));
        }

        @Test
        @DisplayName("random person - incorrect pesel - 400")
        void createNewPerson_badPesel() throws Exception {
            PersonCreateRequest expectedPerson = PersonCreateRequestDataGenerator.getRandomPersonCreateRequest();
            expectedPerson.setPesel(PersonDataGenerator.getWrongPesel());
            final String expectedPersonString = objectMapper.writeValueAsString(expectedPerson);
            mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON).content(expectedPersonString))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.message").value("własny polski MethodArgumentNotValidException"))
                    .andExpect(jsonPath(String.format("$..errors[?(@=='%s')]", "Niepoprawny pesel dla osoby")).isNotEmpty());
        }

        @Test
        @DisplayName("random person - incorrect name and last name size - 400")
        void createNewPerson_badNameAndLastName() throws Exception {
            PersonCreateRequest expectedPerson = PersonCreateRequestDataGenerator.getRandomPersonCreateRequest();
            expectedPerson.setName(RandomStringUtils.randomAlphabetic(100));
            expectedPerson.setLastName(RandomStringUtils.randomAlphabetic(100));
            final String expectedPersonString = objectMapper.writeValueAsString(expectedPerson);
            mockMvc.perform(post("/persons").characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).content(expectedPersonString))
                    .andDo(print()).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.message").value("własny polski MethodArgumentNotValidException"))
                    .andExpect(jsonPath(String.format("$..errors[?(@=='%s')]", "Niepoprawne imię dla osoby")).isNotEmpty())
                    .andExpect(jsonPath(String.format("$..errors[?(@=='%s')]", "Niepoprawne nazwisko dla osoby")).isNotEmpty());
        }
    }

}
