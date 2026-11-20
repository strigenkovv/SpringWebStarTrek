package be.vdab.startrek.werknemers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class WerknemerNietGevondenException extends RuntimeException {
    WerknemerNietGevondenException(long id) {
        super("Werknemer niet gevonden. Id: " + id);
    }
}
