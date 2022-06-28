package uk.co.smitek.matching.match.application.port.out;

import uk.co.smitek.matching.match.domain.Match;

import java.time.LocalDate;
import java.util.List;

public interface SearchPersonDataPort {
    List<Match> search(String givenName, String familyName, LocalDate dateOfBirth);

}
