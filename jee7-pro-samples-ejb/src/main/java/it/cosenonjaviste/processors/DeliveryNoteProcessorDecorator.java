package it.cosenonjaviste.processors;

import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.model.DeliveryNoteType;
import it.cosenonjaviste.processors.qualifiers.Processing;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.logging.Logger;

@Decorator
public abstract class DeliveryNoteProcessorDecorator implements DeliveryNoteProcessor {

    @Inject
    private Logger logger;

    @Inject
    @Delegate
    @Any
    private DeliveryNoteProcessor deliveryNoteProcessor;

    @Inject
    private Event<DeliveryNote> event;

    @Override
    public void process(DeliveryNote deliveryNote) {
        if (deliveryNote.getNumber() != 0) {
            deliveryNoteProcessor.process(deliveryNote);

            logger.info("Elaborazione completata, lancio l'evento");
            event.fire(deliveryNote);
        } else {
            throw new RuntimeException("Delivery Note non persisted");
        }
    }
}
