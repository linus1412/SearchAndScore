package uk.co.smitek.matching.match.adaptor.out;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Component;
import uk.co.smitek.matching.match.domain.Match;
import uk.co.smitek.matching.match.application.port.out.SearchPersonDataPort;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchPersonDataAdaptor implements SearchPersonDataPort {

    private final InMemoryPersonRepository inMemoryPersonRepository;

    private final Soundex soundex = new Soundex();

    @Override
    public List<Match> search(String givenName, String familyName, LocalDate dateOfBirth) {

        final String givenNameSoundex = soundex.encode(givenName);
        final String familyNameSoundex = soundex.encode(familyName);

        return inMemoryPersonRepository.findByDetails(givenNameSoundex, familyNameSoundex, dateOfBirth)
            .stream()
            .map(pd -> new Match(pd.givenName(), pd.givenNameSoundex(), pd.familyName(), pd.familyNameSoundex(), pd.dateOfBirth()))
            .toList();
    }

}
