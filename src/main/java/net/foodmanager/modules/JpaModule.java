package net.foodmanager.modules;

import com.google.inject.AbstractModule;
import net.foodmanager.util.JpaUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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
        String databaseUrl = System.getenv("DATABASE_URL");
        StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
        boolean local = "jdbc".equals(st.nextToken());
        Map<String, String> properties = local ? localProps(databaseUrl) : hostedProps(databaseUrl, st);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
        return emf;
    }

    private Map<String, String> hostedProps(String databaseUrl, StringTokenizer st) {
        String userName = st.nextToken();
        String password = st.nextToken();

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", databaseUrl);
        properties.put("javax.persistence.jdbc.user", userName);
        properties.put("javax.persistence.jdbc.password", password);
        return properties;
    }

    private Map<String, String> localProps(String databaseUrl) {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", databaseUrl);
        properties.put("javax.persistence.jdbc.user", "postgres");
        return properties;
    }

}
