package it.cosenonjaviste.service;

import it.cosenonjaviste.interceptors.binding.Loggable;
import it.cosenonjaviste.model.DeliveryNote;
import it.cosenonjaviste.model.DeliveryNote_;
import org.hibernate.annotations.QueryHints;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Loggable
public class DeliveryNoteService {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Cache cache;

    @Inject
    private Logger logger;

    @Inject
    @Any
    private Event<DeliveryNote> event;

    public DeliveryNote save(DeliveryNote deliveryNote) {
        DeliveryNote merge = this.entityManager.merge(deliveryNote);
        logger.info("Saving delivery note " + merge.getNumber());
        event.fire(merge);
        //System.out.println(1/0);
        return merge;
    }

    public List<DeliveryNote> getAll() {
        // cache http://www.nailedtothex.org/roller/kyle/entry/articles-jpa-l2cache
        return entityManager.createNamedQuery("DeliveryNote.all", DeliveryNote.class).getResultList();
    }

    public List<DeliveryNote> getAllWithDetails() {
        // http://www.radcortez.com/jpa-entity-graphs/
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("DeliveryNote.details");
        return entityManager.createNamedQuery("DeliveryNote.all", DeliveryNote.class).setHint(QueryHints.LOADGRAPH, entityGraph).getResultList();
    }

    public DeliveryNote findByNumber(long number) {
        logger.info("Entity in cache: " + cache.contains(DeliveryNote.class, number));
        return entityManager.find(DeliveryNote.class, number);
    }

    public List<Object[]> getAllAsObjectArray() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<DeliveryNote> dn = query.from(DeliveryNote.class);
        query.multiselect(dn.get(DeliveryNote_.number), dn.get(DeliveryNote_.company));

        return this.entityManager.createQuery(query).getResultList();
    }

    public List<Tuple> getAllAsTuple() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();

        Root<DeliveryNote> dn = query.from(DeliveryNote.class);
        query.multiselect(dn.get(DeliveryNote_.number).alias("number"), dn.get(DeliveryNote_.company));
        //query.select(cb.tuple(dn.get(DeliveryNote_.number).alias("number"), dn.get(DeliveryNote_.company)));

        return this.entityManager.createQuery(query).getResultList();
    }

    public List<DeliveryNodeDTO> getAllAsDTO() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<DeliveryNodeDTO> query = cb.createQuery(DeliveryNodeDTO.class);

        Root<DeliveryNote> dn = query.from(DeliveryNote.class);
        query.multiselect(dn.get(DeliveryNote_.number), dn.get(DeliveryNote_.company));
        //query.select(cb.construct(DeliveryNodeDTO.class, dn.get(DeliveryNote_.number), dn.get(DeliveryNote_.company)));

        return this.entityManager.createQuery(query).getResultList();
    }

    public static class DeliveryNodeDTO {

        private long number;

        private String company;

        public DeliveryNodeDTO(long number, String company) {
            this.number = number;
            this.company = company;
        }

        public long getNumber() {
            return number;
        }

        public String getCompany() {
            return company;
        }
    }
}
