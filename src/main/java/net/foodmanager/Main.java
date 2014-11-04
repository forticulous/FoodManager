package net.foodmanager;

import com.google.common.io.Resources;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import net.foodmanager.jaxrs.GsonReaderWriter;
import net.foodmanager.jaxrs.LocalDateParamConverterProvider;
import net.foodmanager.modules.JpaModule;
import net.foodmanager.modules.ResourceModule;
import net.foodmanager.modules.SqlModule;
import net.foodmanager.util.JpaUtil;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.URL;
import java.util.Arrays;

/**
 * @author fort
 */
public class Main {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run(args);
    }

    public void run(String[] args) throws Exception {
        System.out.println("FoodManager started");

        Injector injector = Guice.createInjector(new JpaModule(), new SqlModule(), new ResourceModule());

        Server server = new Server(PORT);
        server.setHandler(buildHandlers(injector));
        server.start();
        server.join();

        //insertFoodDay(args, injector);
        //insertFoodDayItem(args, injector);

        System.exit(0);
    }

    private Handler buildHandlers(Injector injector) {
        HandlerList handlers = new HandlerList();
        handlers.addHandler(getStaticContentHandler());
        handlers.addHandler(getServiceHandler(injector));
        return handlers;
    }

    /**
     * Create the resource handler that serves the static single-page app as /ui
     */
    private Handler getStaticContentHandler() {
        URL url = Resources.getResource("WEB-INF/public/");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(url.getPath());

        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/ui");
        contextHandler.setHandler(resourceHandler);
        return contextHandler;
    }

    /**
     * Create the servlet handler that maps the rest services to /api
     */
    private ServletContextHandler getServiceHandler(Injector injector) {
        ResourceConfig resourceConfig = injector.getInstance(ResourceConfig.class);
        resourceConfig.register(GsonReaderWriter.class);
        resourceConfig.register(LocalDateParamConverterProvider.class);

        ServletContainer apiServlet = new ServletContainer(resourceConfig);

        ServletHolder apiHolder = new ServletHolder(apiServlet);

        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(apiHolder, "/api/*");
        return handler;
    }

    private void insertFoodDayItem(String[] args, Injector injector) {
        if (args.length < 4) {
            throw new IllegalArgumentException("Missing required number of arguments");
        }
        String localDate = args[0];
        String foodDesc = args[1];
        String meal = args[2];
        long calories = Long.parseLong(args[3]);

        String insertFoodDayItemSql = injector.getInstance(Key.get(String.class, Names.named(SqlModule.INSERT_FOOD_DAY_ITEM)));

        JpaUtil.doInTransaction(em ->
            em.createNativeQuery(insertFoodDayItemSql)
                    .setParameter("localDate", localDate)
                    .setParameter("foodDesc", foodDesc)
                    .setParameter("meal", meal)
                    .setParameter("calories", calories)
                    .executeUpdate()
        );
        System.out.println("Inserted Food Day Item");
    }

    private void insertFoodDay(String[] args, Injector injector) {
        String localDate = Arrays.stream(args)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing required argument localDate"));

        String insertFoodDaySql = injector.getInstance(Key.get(String.class, Names.named(SqlModule.INSERT_FOOD_DAY)));

        JpaUtil.doInTransaction(em -> {
            em.createNativeQuery(insertFoodDaySql)
                    .setParameter("localDate", localDate)
                    .executeUpdate();
        });
        System.out.println("Inserted Food Day: " + localDate);
    }

}
