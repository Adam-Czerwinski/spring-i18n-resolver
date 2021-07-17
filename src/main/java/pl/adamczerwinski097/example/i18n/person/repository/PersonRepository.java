package pl.adamczerwinski097.example.i18n.person.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.adamczerwinski097.example.i18n.person.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    boolean existsByPesel(String pesel);
}
