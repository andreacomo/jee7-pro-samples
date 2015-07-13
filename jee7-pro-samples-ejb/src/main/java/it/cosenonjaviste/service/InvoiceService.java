package it.cosenonjaviste.service;

import it.cosenonjaviste.model.Invoice;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.*;
import java.util.logging.Logger;

/**
 */
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class InvoiceService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @Inject
    private Logger logger;

    public Invoice save(Invoice invoice) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        userTransaction.begin();
        Invoice mergedInvoice = this.entityManager.merge(invoice);
        logger.info("Saving invoice " + mergedInvoice.getNumber());
        userTransaction.commit();
        return mergedInvoice;
    }

    public Invoice find(long number) {
        logger.info("Find Invoice with number: " + number);
        return this.entityManager.find(Invoice.class, number);
    }

    public Invoice findAndLock(long number, LockModeType lockMode) throws SystemException, NotSupportedException {
        userTransaction.begin();
        logger.info("Find and lock Invoice with number: " + number);
        return this.entityManager.find(Invoice.class, number, lockMode);
    }

    @Remove
    public void releaseLock(Invoice invoice) throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        logger.info("Release lock Invoice with number: " + invoice.getNumber());
        this.entityManager.lock(invoice, LockModeType.NONE);
        if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
            userTransaction.commit();
        }
    }
}
