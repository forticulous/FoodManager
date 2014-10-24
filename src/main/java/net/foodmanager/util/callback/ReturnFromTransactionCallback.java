package net.foodmanager.util.callback;

import javax.persistence.EntityManager;

/**
 * @author fort
 */
public interface ReturnFromTransactionCallback<T> {

    T returnFromTransaction(EntityManager em);

}
