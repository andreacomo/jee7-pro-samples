package it.cosenonjaviste.utils;

import it.cosenonjaviste.model.*;
import it.cosenonjaviste.util.Resources;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class BaseArchive {

    private BaseArchive() {}

    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Resources.class, DeliveryNote.class, DeliveryNodeDetail.class, Invoice.class, InvoiceDetail.class, InvoiceType.class, DeliveryNoteType.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                        // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
    }
}
