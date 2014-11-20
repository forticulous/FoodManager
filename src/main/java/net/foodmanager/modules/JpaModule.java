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
        boolean local = Boolean.TRUE.toString().equals(System.getProperty("localDev", Boolean.FALSE.toString()));
        Map<String, String> properties = local ? localProps(st) : hostedProps(st);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
        return emf;
    }

    private Map<String, String> hostedProps(StringTokenizer st) {
        st.nextToken(); // provider
        String userName = st.nextToken();
        String password = st.nextToken();
        String host = st.nextToken();
        String port = st.nextToken();
        String databaseName = st.nextToken();
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName);

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", jdbcUrl);
        properties.put("javax.persistence.jdbc.user", userName);
        properties.put("javax.persistence.jdbc.password", password);
        return properties;
    }

    private Map<String, String> localProps(StringTokenizer st) {
        st.nextToken(); // provider
        String userName = st.nextToken();
        String password = st.nextToken();
        String host = st.nextToken();
        String databaseName = st.nextToken();
        String jdbcUrl = String.format("jdbc:postgresql://%s/%s", host, databaseName);

        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", jdbcUrl);
        properties.put("javax.persistence.jdbc.user", userName);
        properties.put("javax.persistence.jdbc.password", password);
        return properties;
    }

}
