package net.foodmanager.util.callback;

import javax.persistence.EntityManager;

/**
 * @author fort
 */
public interface DoInTransactionCallback {

    void doInTransaction(EntityManager em);

}
