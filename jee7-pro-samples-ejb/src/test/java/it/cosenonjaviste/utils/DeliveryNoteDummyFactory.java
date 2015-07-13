package it.cosenonjaviste.utils;

import it.cosenonjaviste.model.DeliveryNodeDetail;
import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.model.DeliveryNoteType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliveryNoteDummyFactory {

    public static DeliveryNote createDeliveryNote() {
        return createDeliveryNote("Test Company", DeliveryNoteType.PURCHASE);
    }

    public static DeliveryNote createDeliveryNote(String company, DeliveryNoteType type) {
        DeliveryNote dn = new DeliveryNote();
        dn.setCompany(company);
        dn.setIssueDate(new Date());
        dn.setType(type);
        dn.setDetails(new ArrayList<>());
        dn.setDelivered(false);
        dn.getDetails().add(new DeliveryNodeDetail("article1", 232, dn));
        dn.getDetails().add(new DeliveryNodeDetail("article2", 43, dn));
        dn.getDetails().add(new DeliveryNodeDetail("article3", 543, dn));
        dn.getDetails().add(new DeliveryNodeDetail("article4", 3, dn));
        dn.getDetails().add(new DeliveryNodeDetail("article5", 54, dn));
        return dn;
    }

    public static List<DeliveryNote> createDeliveryNotes() {
        List<DeliveryNote> deliveryNotes = new ArrayList<>();
        deliveryNotes.add(DeliveryNoteDummyFactory.createDeliveryNote("The second company", DeliveryNoteType.PURCHASE));
        deliveryNotes.add(DeliveryNoteDummyFactory.createDeliveryNote("The third company", DeliveryNoteType.PURCHASE));
        deliveryNotes.add(DeliveryNoteDummyFactory.createDeliveryNote("The fourth company", DeliveryNoteType.PURCHASE));

        return deliveryNotes;
    }
}
