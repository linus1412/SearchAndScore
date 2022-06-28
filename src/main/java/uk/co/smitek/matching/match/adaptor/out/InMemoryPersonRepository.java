package uk.co.smitek.matching.match.adaptor.out;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@Slf4j
public class InMemoryPersonRepository {

    private List<PersonData> peopleDate = new ArrayList<>();

    private final Soundex soundex = new Soundex();

    @PostConstruct
    public void post() {
        log.info("Starting faker");
        Faker faker = new Faker();
        IntStream.range(0, 1_000_000).parallel().forEach((value -> {
            final String givenName = faker.name().firstName();
            final String familyName = faker.name().lastName();
            peopleDate.add(new PersonData(
                givenName,
                soundex.encode(givenName),
                familyName,
                soundex.encode(familyName),
                LocalDate.ofInstant(
                    faker.date().birthday(16, 110).toInstant(),
                    ZoneId.systemDefault())
            ));
        }));
        log.info("Finished faker");


    }

    public void insert(PersonData personData) {
        peopleDate.add(personData);
    }

    public List<PersonData> findByDetails(String givenNameSoundex, String familyNameSoundex, LocalDate dateOfBirth) {
        return peopleDate.stream()
            .filter(data -> {
                return
                    data.givenNameSoundex().equals(givenNameSoundex) ||
                        data.familyNameSoundex().equals(familyNameSoundex) ||
                        data.dateOfBirth().equals(dateOfBirth);
            })
            .toList();
    }

}
