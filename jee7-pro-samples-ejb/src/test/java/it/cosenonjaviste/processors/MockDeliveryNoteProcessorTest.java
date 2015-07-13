package it.cosenonjaviste.processors;

import it.cosenonjaviste.processors.qualifiers.Mock2;
import it.cosenonjaviste.utils.BaseArchive;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static junit.framework.TestCase.assertTrue;

/**
 * Tests on Alternatives
 */
@RunWith(Arquillian.class)
public class MockDeliveryNoteProcessorTest {

    @Inject
    DeliveryNoteProcessor deliveryNoteProcessor;

    @Deployment
    public static Archive<?> createTestArchive() {
        return BaseArchive.create()
                .addAsWebInfResource("META-INF/test-alternatives-beans.xml", "beans.xml")
                .addClass(DeliveryNoteProcessor.class)
                .addClass(SaleDeliveryNoteProcessor.class)
                .addClass(PurchaseDeliveryNoteProcessor.class)
                .addClass(MockDeliveryNoteProcessor.class)
                .addClass(Mock2DeliveryNoteProcessor.class)
                .addClass(Mock2.class);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=MockDeliveryNoteProcessorTest#shouldBeAlternative  -DfailIfNoTests=false
     */
    @Test
    public void shouldBeAlternative() {

        assertTrue(deliveryNoteProcessor instanceof MockDeliveryNoteProcessor);
    }

}