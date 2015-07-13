package it.cosenonjaviste.utils;

import it.cosenonjaviste.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDummyFactory {

    public static Invoice createInvoice(List<DeliveryNote> deliveryNotes) {
        Invoice invoice = new Invoice();
        invoice.setIssueDate(new Date());
        invoice.setType(InvoiceType.PURCHASE);
        invoice.setDeliveryNotes(deliveryNotes);
        invoice.setInvoiceDetails(new ArrayList<>());
        invoice.getDeliveryNotes().forEach(deliveryNote -> {
            deliveryNote.getDetails().forEach(deliveryNodeDetail -> {
                invoice.getInvoiceDetails().add(new InvoiceDetail(BigDecimal.valueOf(1000), deliveryNodeDetail, invoice));
            });
        });

        return invoice;
    }
}
