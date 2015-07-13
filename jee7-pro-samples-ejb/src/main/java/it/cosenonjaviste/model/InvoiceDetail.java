package it.cosenonjaviste.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "delivery_note_detail_id")
    private DeliveryNodeDetail deliveryNoteDetail;

    @ManyToOne
    @JoinColumn(name = "invoice_number")
    private Invoice invoice;

    public InvoiceDetail() {
    }

    public InvoiceDetail(BigDecimal amount, DeliveryNodeDetail deliveryNoteDetail, Invoice invoice) {
        this.amount = amount;
        this.deliveryNoteDetail = deliveryNoteDetail;
        this.invoice = invoice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public DeliveryNodeDetail getDeliveryNoteDetail() {
        return deliveryNoteDetail;
    }

    public void setDeliveryNoteDetail(DeliveryNodeDetail deliveryNoteDetail) {
        this.deliveryNoteDetail = deliveryNoteDetail;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceDetail)) return false;

        InvoiceDetail that = (InvoiceDetail) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
