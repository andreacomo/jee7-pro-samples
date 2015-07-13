package it.cosenonjaviste.model;

import it.cosenonjaviste.model.converters.BooleanConverter;
import org.hibernate.annotations.QueryHints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "delivery_note")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "DeliveryNote.details", attributeNodes = @NamedAttributeNode("details"))
})
@NamedQueries({
        @NamedQuery(name = "DeliveryNote.all", query = "select d from DeliveryNote d", hints = {@QueryHint(name = QueryHints.CACHEABLE, value = "true")})
})
@Cacheable
public class DeliveryNote implements Serializable {

    @Id
    @SequenceGenerator(name = "delivery_note_sequence")
    @GeneratedValue(generator = "delivery_note_sequence", strategy = GenerationType.SEQUENCE)
    private long number;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    private String company;

    @Enumerated(EnumType.STRING)
    private DeliveryNoteType type;

    @OrderBy("id")
    @OneToMany(mappedBy = "deliveryNote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryNodeDetail> details;

    @ManyToOne
    @JoinColumn(name = "invoice_number")
    private Invoice invoice;

    private Boolean delivered;

    public long getNumber() {
        return number;
    }

    public void setNumber(long mumber) {
        this.number = mumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<DeliveryNodeDetail> getDetails() {
        return details;
    }

    public void setDetails(List<DeliveryNodeDetail> details) {
        this.details = details;
    }

    public DeliveryNoteType getType() {
        return type;
    }

    public void setType(DeliveryNoteType type) {
        this.type = type;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryNote)) return false;

        DeliveryNote that = (DeliveryNote) o;

        return number == that.number;

    }

    @Override
    public int hashCode() {
        return (int) (number ^ (number >>> 32));
    }
}
