package net.foodmanager.util.callback;

import javax.persistence.EntityManager;

/**
 * @author fort
 */
@FunctionalInterface
public interface DoInTransactionCallback {

    void doInTransaction(EntityManager em);

}
