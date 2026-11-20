package be.vdab.startrek.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class BestellingService {
    private final BestellingRepository bestellingRepository;

    BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }

    List<Bestelling> findByWerknemerId(long werknemerId) {
        return bestellingRepository.findByWerknemerId(werknemerId);
    }
}
