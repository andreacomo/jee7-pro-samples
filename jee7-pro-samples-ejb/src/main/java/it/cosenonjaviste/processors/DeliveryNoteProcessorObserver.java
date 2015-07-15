package it.cosenonjaviste.processors;

import it.cosenonjaviste.model.DeliveryNote;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class DeliveryNoteProcessorObserver {

    @Inject
    private Logger logger;

    void onElaborationDone(@Observes DeliveryNote deliveryNote) {
        logger.info("Delivery Note " + deliveryNote.getNumber() + " elaborated!");
        //System.out.println(1 / 0);
    }

    void onAfterElaborationSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) DeliveryNote deliveryNote) {
        logger.info("AFTER_SUCCESS " + deliveryNote.getNumber());
    }

    void onAfterElaborationFailed(@Observes(during = TransactionPhase.AFTER_FAILURE) DeliveryNote deliveryNote) {
        logger.info("AFTER_FAILURE " + deliveryNote.getNumber());
    }

    void onAfterElaborationComplete(@Observes(during = TransactionPhase.AFTER_COMPLETION) DeliveryNote deliveryNote) {
        logger.info("AFTER_COMPLETION " + deliveryNote.getNumber());
    }

    void onBeforeElaborationComplete(@Observes(during = TransactionPhase.BEFORE_COMPLETION) DeliveryNote deliveryNote) {
        logger.info("BEFORE_COMPLETION " + deliveryNote.getNumber());
    }
}
