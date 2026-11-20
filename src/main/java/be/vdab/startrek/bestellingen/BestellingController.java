package be.vdab.startrek.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
class BestellingController {
    private final BestellingService bestellingService;

    BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @GetMapping("werknemers/{id}/bestellingen")
    List<Bestelling> findByWerknemerId(@PathVariable long id) {
        return bestellingService.findByWerknemerId(id);
    }

    @PostMapping("werknemers/{id}/bestellingen")
    long create(@PathVariable long id,
                @RequestBody @Valid NieuweBestelling nieuweBestelling) {
        return bestellingService.create(id, nieuweBestelling);
    }
}
