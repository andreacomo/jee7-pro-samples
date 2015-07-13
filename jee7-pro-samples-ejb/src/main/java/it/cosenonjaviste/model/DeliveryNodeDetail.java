package it.cosenonjaviste.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "delivery_note_details")
public class DeliveryNodeDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String articleName;

    private int quantity;

    @Version
    private long version;

    public DeliveryNodeDetail() {
    }

    public DeliveryNodeDetail(String articleName, int quantity, DeliveryNote deliveryNote) {
        this.articleName = articleName;
        this.quantity = quantity;
        this.deliveryNote = deliveryNote;
    }

    @ManyToOne
    @JoinColumn(name = "delivery_note")
    private DeliveryNote deliveryNote;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryNodeDetail)) return false;

        DeliveryNodeDetail that = (DeliveryNodeDetail) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
