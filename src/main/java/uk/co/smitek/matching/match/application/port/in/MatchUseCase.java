package uk.co.smitek.matching.match.application.port.in;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface MatchUseCase {

    MatchResponse match(MatchRequest request);

    record MatchRequest(
            String givenName,
            String familyName,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dateOfBirth) {

    }

    record MatchResponse(MatchRequest request, List<ScoredMatch> scoredMatches) {
    }

    record ScoredMatch(
        String givenName,
        Integer givenNameScore,
        String familyName,
        Integer familyNameScore,
        LocalDate dateOfBirth,
        Integer dateOfBirthScore
    ) {
    }


}
