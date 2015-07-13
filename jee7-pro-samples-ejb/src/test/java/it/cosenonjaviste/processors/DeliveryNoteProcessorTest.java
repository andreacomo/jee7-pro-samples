package it.cosenonjaviste.processors;

import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.model.DeliveryNoteType;
import it.cosenonjaviste.processors.qualifiers.Processing;
import it.cosenonjaviste.utils.BaseArchive;
import it.cosenonjaviste.utils.DeliveryNoteDummyFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import static junit.framework.TestCase.assertTrue;

/**
 * Tests on CDI programmatic lookup
 */
@RunWith(Arquillian.class)
public class DeliveryNoteProcessorTest {

    @Inject @Any
    Instance<DeliveryNoteProcessor> deliveryNoteProcessorDispatcher;

    @Deployment
    public static Archive<?> createTestArchive() {
        return BaseArchive.create()
                .addAsWebInfResource("META-INF/test-decorators-beans.xml", "beans.xml")
                .addClass(DeliveryNoteProcessor.class)
                .addClass(SaleDeliveryNoteProcessor.class)
                .addClass(PurchaseDeliveryNoteProcessor.class)
                .addClass(Processing.class)
                .addClass(DeliveryNoteDummyFactory.class)
                .addClass(DeliveryNoteProcessorDecorator.class)
                .addClass(DeliveryNoteProcessorObserver.class);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteProcessorTest#shouldBePurchaseProcessing  -DfailIfNoTests=false
     */
    @Test
    public void shouldBePurchaseProcessing() {

        DeliveryNoteProcessor deliveryNoteProcessor = this.deliveryNoteProcessorDispatcher.select(new PurchaseProcessing()).get();

        assertTrue(deliveryNoteProcessor instanceof PurchaseDeliveryNoteProcessor);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteProcessorTest#shouldBeSaleProcessing  -DfailIfNoTests=false
     */
    @Test
    public void shouldBeSaleProcessing() {

        DeliveryNoteProcessor deliveryNoteProcessor = this.deliveryNoteProcessorDispatcher.select(new SaleProcessing()).get();

        assertTrue(deliveryNoteProcessor instanceof SaleDeliveryNoteProcessor);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteProcessorTest#shouldBeSaleProcessingByGenericQualifier  -DfailIfNoTests=false
     */
    @Test
    public void shouldBeSaleProcessingByGenericQualifier() {

        DeliveryNoteProcessor deliveryNoteProcessor = this.deliveryNoteProcessorDispatcher
                                                            .select(new ProcessingQualifier(DeliveryNoteType.SALE))
                                                            .get();

        assertTrue(deliveryNoteProcessor instanceof SaleDeliveryNoteProcessor);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteProcessorTest#shouldThrowRuntimeException  -DfailIfNoTests=false
     */
    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeException() {

        DeliveryNoteProcessor deliveryNoteProcessor = this.deliveryNoteProcessorDispatcher
                .select(new ProcessingQualifier(DeliveryNoteType.SALE))
                .get();

        deliveryNoteProcessor.process(DeliveryNoteDummyFactory.createDeliveryNote());
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteProcessorTest#shouldProcessSuccessful  -DfailIfNoTests=false
     */
    @Test
    public void shouldProcessSuccessful() {

        DeliveryNoteProcessor deliveryNoteProcessor = this.deliveryNoteProcessorDispatcher
                .select(new ProcessingQualifier(DeliveryNoteType.PURCHASE))
                .get();

        DeliveryNote deliveryNote = DeliveryNoteDummyFactory.createDeliveryNote();
        deliveryNote.setNumber(100);
        deliveryNoteProcessor.process(deliveryNote);
    }

    static class PurchaseProcessing extends AnnotationLiteral<Processing> implements Processing {

        @Override
        public DeliveryNoteType value() {
            return DeliveryNoteType.PURCHASE;
        }

        @Override
        public String comment() {
            return "";
        }
    }

    static class SaleProcessing extends AnnotationLiteral<Processing> implements Processing {

        @Override
        public DeliveryNoteType value() {
            return DeliveryNoteType.SALE;
        }

        @Override
        public String comment() {
            return "";
        }
    }

    static class ProcessingQualifier extends AnnotationLiteral<Processing> implements Processing {

        private DeliveryNoteType type;

        public ProcessingQualifier(DeliveryNoteType type) {
            this.type = type;
        }

        @Override
        public DeliveryNoteType value() {
            return type;
        }

        @Override
        public String comment() {
            return "";
        }
    }
}