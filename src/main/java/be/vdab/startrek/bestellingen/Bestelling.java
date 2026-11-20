package be.vdab.startrek.bestellingen;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record Bestelling(
        long id,
        long werknemerId,
        String omschrijving,
        BigDecimal bedrag,
        LocalDateTime moment
) {
}
