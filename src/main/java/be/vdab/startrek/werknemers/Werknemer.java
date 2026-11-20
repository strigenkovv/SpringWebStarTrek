package be.vdab.startrek.werknemers;

import java.math.BigDecimal;

record Werknemer(
        long id,
        String voornaam,
        String familienaam,
        BigDecimal budget
) {
}
