package it.cosenonjaviste.service;

import it.cosenonjaviste.interceptors.LoggableInterceptor;
import it.cosenonjaviste.interceptors.binding.Loggable;
import it.cosenonjaviste.model.DeliveryNodeDetail;
import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.model.DeliveryNote_;
import it.cosenonjaviste.processors.DeliveryNoteProcessorObserver;
import it.cosenonjaviste.utils.BaseArchive;
import it.cosenonjaviste.utils.DeliveryNoteDummyFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Tests on Optimistic Lock
 */
@RunWith(Arquillian.class)
public class DeliveryNoteServiceTest {

    @Inject
    DeliveryNoteService deliveryNoteService;

    @Inject
    Cache cache;

    @Inject
    Logger log;

    @Deployment
    public static Archive<?> createTestArchive() {
        return BaseArchive.create()
                .addAsWebInfResource("META-INF/test-interceptors-beans.xml", "beans.xml")
                .addClass(DeliveryNoteService.class)
                .addClass(DeliveryNoteDummyFactory.class)
                .addClass(LoggableInterceptor.class)
                .addClass(Loggable.class)
                .addClass(DeliveryNote_.class)
                .addClass(DeliveryNoteProcessorObserver.class);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldSave  -DfailIfNoTests=false
     */
    @Test
    public void shouldSave() {
        List<DeliveryNote> all = deliveryNoteService.getAll();

        int countBeforeSave = all.size();
        log.info("Delivery note count before save: " + countBeforeSave);

        deliveryNoteService.save(DeliveryNoteDummyFactory.createDeliveryNote());

        all = deliveryNoteService.getAll();

        assertEquals(countBeforeSave + 1, all.size());
        log.info("Delivery note count after save: " + all.size());
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldThrowOptimisticLockException  -DfailIfNoTests=false
     */
    @Test
    public void shouldThrowOptimisticLockException() {
        try {
            // User1 save a dn
            DeliveryNote deliveryNote = DeliveryNoteDummyFactory.createDeliveryNote();
            deliveryNote = deliveryNoteService.save(deliveryNote);

            // User2 load dn from db
            DeliveryNote reloadedDeliveryNode = deliveryNoteService.getAllWithDetails().get(0);

            // User1 update quantity and dave
            DeliveryNodeDetail detail = deliveryNote.getDetails().get(0);
            detail.setQuantity(detail.getQuantity() + 1);
            deliveryNoteService.save(deliveryNote);

            // User2 update same quantity and save: exception
            detail = reloadedDeliveryNode.getDetails().get(0);
            detail.setQuantity(detail.getQuantity() - 100);
            deliveryNoteService.save(reloadedDeliveryNode);
            fail("Should'nt be here! OptimisticLockException should be thrown");
        } catch (Exception e) {
            log.severe(e.getMessage());
            assertTrue(e.getCause() instanceof OptimisticLockException);
        }
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeCached  -DfailIfNoTests=false
     */
    @Test
    public void shouldBeCached() {
        DeliveryNote deliveryNote = DeliveryNoteDummyFactory.createDeliveryNote();
        deliveryNote = deliveryNoteService.save(deliveryNote);

        log.info("Loading delivery note: " + deliveryNote.getNumber());
        deliveryNote = deliveryNoteService.findByNumber(deliveryNote.getNumber());

        log.info("Load all delivery notes");
        List<DeliveryNote> all = deliveryNoteService.getAll();

        log.info("Load all delivery notes again");
        deliveryNoteService.getAll(); // Test visivo, non devono partire altre query

        assertTrue(cache.contains(DeliveryNote.class, deliveryNote.getNumber()));

        cache.evict(DeliveryNote.class, deliveryNote.getNumber());
        assertFalse(cache.contains(DeliveryNote.class, deliveryNote.getNumber()));
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeObjectArray  -DfailIfNoTests=false
     */
    @Test
    public void shouldBeObjectArray() {
        DeliveryNote deliveryNote = DeliveryNoteDummyFactory.createDeliveryNote();
        deliveryNoteService.save(deliveryNote);

        List<Object[]> all = deliveryNoteService.getAllAsObjectArray();
        assertTrue(all.get(0)[0] instanceof Long);
        assertTrue(all.get(0)[1] instanceof String);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeTuple  -DfailIfNoTests=false
     */
    @Test
    public void shouldBeTuple() {
        DeliveryNote deliveryNote = DeliveryNoteDummyFactory.createDeliveryNote();
        deliveryNoteService.save(deliveryNote);

        List<Tuple> all = deliveryNoteService.getAllAsTuple();
        assertTrue(all.get(0).get("number") instanceof Long);
        assertTrue(all.get(0).get(1) instanceof String);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeDTO  -DfailIfNoTests=false
     */
    @Test
    public void shouldBeDTO() {
        DeliveryNote deliveryNote = DeliveryNoteDummyFactory.createDeliveryNote();
        deliveryNoteService.save(deliveryNote);

        List<DeliveryNoteService.DeliveryNodeDTO> all = deliveryNoteService.getAllAsDTO();
        assertNotNull(all.get(0).getNumber());
        assertNotNull(all.get(0).getCompany());
    }

}