package net.foodmanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author fort
 */
public class Main {

    private static final String PERSISTENCE_UNIT = "foodb";

    public static void main(String[] args) {
        System.out.println("FoodManager started");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emf.createEntityManager();

        System.out.println("em: " + em);

        System.exit(0);
    }

}
