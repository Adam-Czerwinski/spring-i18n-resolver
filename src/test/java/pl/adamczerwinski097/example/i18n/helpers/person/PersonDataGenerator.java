package pl.adamczerwinski097.example.i18n.helpers.person;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import pl.adamczerwinski097.example.i18n.person.model.Person;

import java.util.Arrays;
import java.util.List;

public class PersonDataGenerator {
    private final static int RANDOM_PERSON_NAME_LENGTH = 30;
    private final static int RANDOM_PERSON_LAST_NAME_LENGTH = 30;
    private final static List<String> RANDOM_PESEL_LIST = Arrays.asList(
            "79042149211",
            "00291879598",
            "50091346156",
            "85081114251",
            "69112975961",
            "71020175319",
            "87042571239",
            "89071538911",
            "93042227131",
            "70061779632");

    public static Person getRandomPerson() {
        return Person.builder().name(getRandomName()).lastName(getRandomLastName())
                .pesel(getRandomPesel()).build();
    }

    public static String getRandomPesel() {
        return RANDOM_PESEL_LIST.get(RandomUtils.nextInt(0, RANDOM_PESEL_LIST.size()));
    }

    public static String getRandomLastName() {
        return RandomStringUtils.randomAlphabetic(RANDOM_PERSON_LAST_NAME_LENGTH);
    }

    public static String getRandomName() {
        return RandomStringUtils.randomAlphabetic(RANDOM_PERSON_NAME_LENGTH);
    }

    public static String getWrongPesel() {
        return "1111111111";
    }
}
