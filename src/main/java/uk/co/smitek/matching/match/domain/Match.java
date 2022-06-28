package uk.co.smitek.matching.match.domain;

import java.time.LocalDate;

public record Match(
    String givenNameResult,
    String givenNameSoundexResult,
    String familyNameResult,
    String familyNameSoundexResult,
    LocalDate dateOfBirthResult) {

}
