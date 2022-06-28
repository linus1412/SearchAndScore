package uk.co.smitek.matching.match.adaptor.out;

import java.time.LocalDate;

public record PersonData (String givenName, String givenNameSoundex, String familyName, String familyNameSoundex, LocalDate dateOfBirth) {}
