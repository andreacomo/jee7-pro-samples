package it.cosenonjaviste.processors;

import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.processors.qualifiers.Mock2;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.util.logging.Logger;

@Mock2
public class Mock2DeliveryNoteProcessor implements DeliveryNoteProcessor {

    @Inject
    private transient Logger logger;

    @Override
    public void process(DeliveryNote deliveryNote) {
        logger.info("Processing alternative delievery note " + deliveryNote.getNumber());
    }
}
