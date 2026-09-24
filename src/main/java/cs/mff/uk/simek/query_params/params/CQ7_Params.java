package cs.mff.uk.simek.query_params.params;

import java.time.LocalDate;

public record CQ7_Params(
        Character FirstNameStartingLetter,
        Character LastNameStartingLetter,
        String CityName,
        LocalDate HireDate,
        LocalDate BirthDate
) {}
