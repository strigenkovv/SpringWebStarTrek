package be.vdab.startrek.werknemers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class WerknemerController {
    private final WerknemerService werknemerService;

    WerknemerController(WerknemerService werknemerService) {
        this.werknemerService = werknemerService;
    }

    @GetMapping("werknemers")
    List<Werknemer> findAll() {
        return werknemerService.findAll();
    }

    @GetMapping("werknemers/{id}")
    Werknemer findById(@PathVariable long id) {
        return werknemerService.findById(id);
    }
}
