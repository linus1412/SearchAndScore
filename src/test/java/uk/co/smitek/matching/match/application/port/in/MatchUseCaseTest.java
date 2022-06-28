package uk.co.smitek.matching.match.application.port.in;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.smitek.matching.match.adaptor.out.InMemoryPersonRepository;
import uk.co.smitek.matching.match.adaptor.out.PersonData;
import uk.co.smitek.matching.match.application.port.in.MatchUseCase.MatchRequest;
import uk.co.smitek.matching.match.application.port.in.MatchUseCase.MatchResponse;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MatchUseCaseTest {

    @Autowired
    MatchUseCase matchUseCase;

    @Autowired
    InMemoryPersonRepository inMemoryPersonRepository;

    @Test
    void matchData() {

        inMemoryPersonRepository.insert(new PersonData("Martin", "M635", "Smith",
            "S530", LocalDate.of(1975, Month.DECEMBER, 14)));
        inMemoryPersonRepository.insert(new PersonData("Iwona", "I500", "Derezinska",
            "D625", LocalDate.of(1975, Month.JULY, 14)));

        final String searchGivenName = "Iwona";
        final String searchFamilyName = "Derezinski";
        final LocalDate searchDateOfBirth = LocalDate.of(1975, Month.DECEMBER, 14);

        final MatchResponse matchResponse = matchUseCase.match(new MatchRequest(searchGivenName, searchFamilyName, searchDateOfBirth));

        assertThat(matchResponse.scoredMatches()).hasSize(1);

    }

}
