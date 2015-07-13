package it.cosenonjaviste.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
@NamedQueries({@NamedQuery(name = "Invoice.getAll", query = "select v from Invoice v")})
public class Invoice implements Serializable {

    @Id
    @SequenceGenerator(name = "invoice_sequence")
    @GeneratedValue(generator = "invoice_sequence", strategy = GenerationType.SEQUENCE)
    private long number;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @OneToMany(mappedBy = "invoice")
    private List<DeliveryNote> deliveryNotes;

    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails;

    public long getNumber() {
        return number;
    }

    public void setNumber(long mumber) {
        this.number = number;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public List<DeliveryNote> getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(List<DeliveryNote> deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        return number == invoice.number;

    }

    @Override
    public int hashCode() {
        return (int) (number ^ (number >>> 32));
    }
}
