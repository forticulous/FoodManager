package net.foodmanager.util.callback;

import javax.persistence.EntityManager;

/**
 * @author fort
 */
@FunctionalInterface
public interface ReturnFromTransactionCallback<T> {

    T returnFromTransaction(EntityManager em);

}
