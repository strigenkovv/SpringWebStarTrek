package be.vdab.startrek.bestellingen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
class OnvoldoendeBudgetException extends RuntimeException {
    OnvoldoendeBudgetException() {
        super("Onvoldoende budget.");
    }
}
