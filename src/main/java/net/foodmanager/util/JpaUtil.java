package net.foodmanager.util;

import com.google.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author fort
 */
public final class JpaUtil {

    @Inject
    private static EntityManagerFactory emf;

    private JpaUtil() {}

    public static void doInTransaction(Consumer<EntityManager> callback) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        callback.accept(em);
        em.getTransaction().commit();
    }

    public static <T> T returnFromTransaction(Function<EntityManager, T> callback) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        T returned = callback.apply(em);
        em.getTransaction().commit();
        return returned;
    }

}
