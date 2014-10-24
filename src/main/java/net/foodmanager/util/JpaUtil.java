package net.foodmanager.util;

import net.foodmanager.util.callback.DoInTransactionCallback;
import net.foodmanager.util.callback.ReturnFromTransactionCallback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author fort
 */
public final class JpaUtil {

    private static EntityManagerFactory emf;

    private JpaUtil() {}

    public static void setEntityManagerFactory(EntityManagerFactory emf) {
        JpaUtil.emf = emf;
    }

    public static void doInTransaction(DoInTransactionCallback callback) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        callback.doInTransaction(em);
        em.getTransaction().commit();
    }

    public static <T> T returnFromTransaction(ReturnFromTransactionCallback<T> callback) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        T returned = callback.returnFromTransaction(em);
        em.getTransaction().commit();
        return returned;
    }

}
