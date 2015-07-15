package it.cosenonjaviste.processors;

import it.cosenonjaviste.interceptors.binding.Loggable;
import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.model.DeliveryNoteType;
import it.cosenonjaviste.processors.qualifiers.Processing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
@Processing(DeliveryNoteType.PURCHASE)
@Loggable
public class PurchaseDeliveryNoteProcessor implements DeliveryNoteProcessor {

    @Inject
    private Logger logger;

    @Override
    public void process(DeliveryNote deliveryNote) {
        logger.info("Processing purchase delievery note " + deliveryNote.getNumber());
    }
}
