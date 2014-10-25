package net.foodmanager.modules;

import com.google.inject.AbstractModule;
import net.foodmanager.util.JpaUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author fort
 */
public class JpaModule extends AbstractModule {

    private static final String PERSISTENCE_UNIT = "foodb";

    @Override
    protected void configure() {
        bind(EntityManagerFactory.class).toInstance(provideEntityManagerFactory());
        requestStaticInjection(JpaUtil.class);
    }

    private EntityManagerFactory provideEntityManagerFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        return emf;
    }

}
