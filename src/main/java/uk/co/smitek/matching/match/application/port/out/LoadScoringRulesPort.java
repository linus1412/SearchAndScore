package uk.co.smitek.matching.match.application.port.out;

import uk.co.smitek.matching.match.domain.ScoringRules;

public interface LoadScoringRulesPort {
    ScoringRules load(String matching);



}
