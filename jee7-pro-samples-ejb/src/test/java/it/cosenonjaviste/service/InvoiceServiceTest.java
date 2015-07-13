package it.cosenonjaviste.service;

import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.model.Invoice;
import it.cosenonjaviste.utils.BaseArchive;
import it.cosenonjaviste.utils.DeliveryNoteDummyFactory;
import it.cosenonjaviste.utils.InvoiceDummyFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Tests on Pessimistic Locks
 */
@RunWith(Arquillian.class)
public class InvoiceServiceTest {

    @Inject
    DeliveryNoteService deliveryNoteService;

    @Inject
    InvoiceService invoiceService1;

    @Inject
    InvoiceService invoiceService2;

    @Inject
    Logger logger;

    @Deployment
    public static Archive<?> createTestArchive() {
        return BaseArchive.create()
                .addClass(DeliveryNoteService.class)
                .addClass(DeliveryNoteDummyFactory.class)
                .addClass(InvoiceService.class)
                .addClass(InvoiceDummyFactory.class);
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=InvoiceServiceTest#shouldSaveInvoice  -DfailIfNoTests=false
     */
    @Test
    public void shouldSaveInvoice() throws Exception {
        // Return a merged list
        List<DeliveryNote> deliveryNotes = DeliveryNoteDummyFactory.createDeliveryNotes()
                .stream()
                .map(deliveryNoteService::save)
                .collect(Collectors.toList());

        Invoice invoice = InvoiceDummyFactory.createInvoice(deliveryNotes);
        assertEquals(0, invoice.getNumber());

        invoice = invoiceService1.save(invoice);

        assertNotNull(invoice);
        assertNotEquals(0, invoice.getNumber());

        int deliveryNotesDetailsCount = deliveryNotes.stream().mapToInt(deliveryNote -> deliveryNote.getDetails().size()).sum();
        assertEquals(deliveryNotesDetailsCount, invoice.getInvoiceDetails().size());
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=InvoiceServiceTest#shouldAllowTwoLockRead  -DfailIfNoTests=false
     */
    @Test
    @Ignore("not working")
    public void shouldAllowTwoLockRead() throws Exception {
        Invoice invoiceLocked1 = null;
        Invoice invoiceLocked2 = null;
        try {
            Invoice invoice = InvoiceDummyFactory.createInvoice(Collections.emptyList());
            assertEquals(0, invoice.getNumber());

            invoice = invoiceService1.save(invoice);
            assertNotNull(invoice);
            assertNotEquals(0, invoice.getNumber());

            invoiceLocked1 = invoiceService1.findAndLock(invoice.getNumber(), LockModeType.PESSIMISTIC_READ);
            invoiceLocked2 = invoiceService2.findAndLock(invoice.getNumber(), LockModeType.PESSIMISTIC_READ);

            assertEquals(invoice, invoiceLocked1);
            assertEquals(invoice, invoiceLocked2);
        } finally {
            if (invoiceLocked1 != null) invoiceService1.releaseLock(invoiceLocked1);
            if (invoiceLocked2 != null) invoiceService2.releaseLock(invoiceLocked2);
        }
    }

    /**
     * mvn clean test -Parq-wildfly-managed -Dtest=InvoiceServiceTest#shouldThrowsPessimistickLockException  -DfailIfNoTests=false
     */
    @Test
    public void shouldThrowsPessimistickLockException() throws Exception {
        Invoice invoiceLocked1 = null;
        Invoice invoiceLocked2 = null;
        try {
            Invoice invoice = InvoiceDummyFactory.createInvoice(Collections.emptyList());
            assertEquals(0, invoice.getNumber());

            invoice = invoiceService1.save(invoice);
            assertNotNull(invoice);
            assertNotEquals(0, invoice.getNumber());

            invoiceLocked1 = invoiceService1.findAndLock(invoice.getNumber(), LockModeType.PESSIMISTIC_WRITE);
            invoiceLocked2 = invoiceService2.findAndLock(invoice.getNumber(), LockModeType.PESSIMISTIC_READ);
            fail("Should not be here!");

        } catch (Exception e) {
            assertTrue(e.getCause() instanceof PessimisticLockException);
        } finally {
            if (invoiceLocked1 != null) invoiceService1.releaseLock(invoiceLocked1);
            if (invoiceLocked2 != null) invoiceService2.releaseLock(invoiceLocked2);
        }
    }
}