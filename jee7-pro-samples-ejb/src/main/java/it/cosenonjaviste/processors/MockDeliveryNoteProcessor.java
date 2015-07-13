package it.cosenonjaviste.processors;

import it.cosenonjaviste.model.DeliveryNote;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
@Alternative
public class MockDeliveryNoteProcessor implements DeliveryNoteProcessor {

    @Inject
    private Logger logger;

    @Override
    public void process(DeliveryNote deliveryNote) {
        logger.info("Processing alternative delievery note " + deliveryNote.getNumber());
    }
}
