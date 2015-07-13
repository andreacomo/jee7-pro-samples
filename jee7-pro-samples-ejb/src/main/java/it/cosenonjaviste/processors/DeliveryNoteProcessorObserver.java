package it.cosenonjaviste.processors;

import it.cosenonjaviste.model.DeliveryNote;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class DeliveryNoteProcessorObserver {

    @Inject
    private Logger logger;

    void onElaborationDone(@Observes DeliveryNote deliveryNote) {
        logger.info("Delivery Note " + deliveryNote.getNumber() + " elaborated!");
    }
}
