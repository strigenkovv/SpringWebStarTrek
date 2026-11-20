package be.vdab.startrek.bestellingen;

import be.vdab.startrek.werknemers.WerknemerRepository;
import be.vdab.startrek.werknemers.WerknemerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class BestellingService {
    private final BestellingRepository bestellingRepository;
    private final WerknemerRepository werknemerRepository;
    private final WerknemerService werknemerService;

    BestellingService(BestellingRepository bestellingRepository,
                      WerknemerRepository werknemerRepository,
                      WerknemerService werknemerService) {
        this.bestellingRepository = bestellingRepository;
        this.werknemerRepository = werknemerRepository;
        this.werknemerService = werknemerService;
    }

    List<Bestelling> findByWerknemerId(long werknemerId) {
        return bestellingRepository.findByWerknemerId(werknemerId);
    }

    @Transactional
    long create(long werknemerId, NieuweBestelling nieuweBestelling) {
        int aantal = werknemerRepository.verminderBudget(
                werknemerId, nieuweBestelling.bedrag());

        if (aantal == 0) {
            werknemerService.findById(werknemerId);
            throw new OnvoldoendeBudgetException();
        }

        return bestellingRepository.create(werknemerId, nieuweBestelling);
    }
}
