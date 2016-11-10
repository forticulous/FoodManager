package net.foodmanager.modules;

import com.google.inject.AbstractModule;
import net.foodmanager.util.JpaUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

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
        String connectionString = System.getProperty("connectionString");
        String[] st = connectionString.split("[:@/]");
        Map<String, String> properties = databaseProps(st);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
        return emf;
    }

    private Map<String, String> databaseProps(String[] st) {
        String userName = st[0];
        String password = st[1];
        String host = st[2];
        String port = st[3];
        String databaseName = st[4];
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName);

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", jdbcUrl);
        properties.put("javax.persistence.jdbc.user", userName);
        properties.put("javax.persistence.jdbc.password", password);
        return properties;
    }

}
