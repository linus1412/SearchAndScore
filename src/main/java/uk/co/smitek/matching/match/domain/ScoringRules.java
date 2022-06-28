package uk.co.smitek.matching.match.domain;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import uk.co.smitek.matching.match.application.port.in.MatchUseCase;
import uk.co.smitek.matching.match.application.port.in.MatchUseCase.ScoredMatch;

import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ScoringRules {

    private final SimilarityStrategy strategy = new JaroWinklerStrategy();
    private final StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
    public List<ScoredMatch> score(List<Match> matches, MatchUseCase.MatchRequest request) {

        return matches.stream()
            .map(match -> new ScoredMatch(
                match.givenNameResult(),
                scoreString(request.givenName(), match.givenNameResult()),
                match.familyNameResult(),
                scoreString(request.familyName(), match.familyNameResult()),
                match.dateOfBirthResult(),
                (int) DAYS.between(request.dateOfBirth(), match.dateOfBirthResult()))
            ).filter(score -> (score.givenNameScore() > 65 && score.familyNameScore() > 65) && Math.abs(score.dateOfBirthScore()) < 365)
            .toList();
    }

    private int scoreString(String searchValue, String matchValue) {
        return (int) (service.score(searchValue, matchValue) * 100);
    }
}
