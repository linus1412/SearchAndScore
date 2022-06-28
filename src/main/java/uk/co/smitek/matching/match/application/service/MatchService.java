package uk.co.smitek.matching.match.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.smitek.matching.match.application.port.in.MatchUseCase;
import uk.co.smitek.matching.match.application.port.out.LoadScoringRulesPort;
import uk.co.smitek.matching.match.domain.ScoringRules;
import uk.co.smitek.matching.match.application.port.out.SearchPersonDataPort;
import uk.co.smitek.matching.match.domain.Match;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService implements MatchUseCase {

    private final SearchPersonDataPort searchPersonDataPort;
    private final LoadScoringRulesPort loadScoringRulesPort;

    @Override
    public MatchResponse match(MatchRequest request) {

        // Get the matching data
        final List<Match> matches = searchPersonDataPort.search(request.givenName(), request.familyName(), request.dateOfBirth());

        // Get the scoring rules
        final ScoringRules scoringRules = loadScoringRulesPort.load("matching");

        List<ScoredMatch> scoredMatches = scoringRules.score(matches, request);

        return new MatchResponse(request, scoredMatches);
    }

}
