package uk.co.smitek.matching.match.adaptor.out;

import org.springframework.stereotype.Component;
import uk.co.smitek.matching.match.application.port.out.LoadScoringRulesPort;
import uk.co.smitek.matching.match.domain.ScoringRules;

@Component
public class LoadScoringRulesAdaptor implements LoadScoringRulesPort {
    @Override
    public ScoringRules load(String matching) {
        return new ScoringRules();
    }
}
