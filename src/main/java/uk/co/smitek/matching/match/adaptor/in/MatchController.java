package uk.co.smitek.matching.match.adaptor.in;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.smitek.matching.match.application.port.in.MatchUseCase;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matching")
public class MatchController {

    private final MatchUseCase matchUseCase;

    @GetMapping("/match")
    public MatchResponseJson match(
        @RequestParam String givenName,
        @RequestParam String familyName,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth) {

        final MatchUseCase.MatchResponse response = matchUseCase.match(new MatchUseCase.MatchRequest(givenName, familyName, dateOfBirth));

        final List<MatchJson> matchJsons = response.scoredMatches().stream()
            .map(sm -> new MatchJson(sm.givenName(), sm.givenNameScore(),
                sm.familyName(), sm.familyNameScore(),
                sm.dateOfBirth(), sm.dateOfBirthScore()))
            .toList();

        return new MatchResponseJson(matchJsons);

    }

    public record MatchResponseJson(List<MatchJson> matches) implements Serializable {

    }

    public record MatchJson(
        String givenName,
        Integer givenNameScore,
        String familyName,
        Integer familyNameScore,
        LocalDate dateOfBirth,
        Integer dataOfBirthScore
    ) implements Serializable  {

    }

}
