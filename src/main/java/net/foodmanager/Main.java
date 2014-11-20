package net.foodmanager;

import com.google.common.io.Resources;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.foodmanager.jaxrs.GsonReaderWriter;
import net.foodmanager.jaxrs.LocalDateParamConverterProvider;
import net.foodmanager.jaxrs.UUIDParamConverterProvider;
import net.foodmanager.modules.JpaModule;
import net.foodmanager.modules.ResourceModule;
import net.foodmanager.modules.SqlModule;
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

/**
 * @author fort
 */
public class Main {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run();
    }

    public void run() throws Exception {
        System.out.println("FoodManager started");

        Injector injector = Guice.createInjector(new JpaModule(), new SqlModule(), new ResourceModule());

        Server server = new Server(getPort());
        server.setHandler(buildHandlers(injector));
        server.start();
        server.join();

        System.exit(0);
    }

    private int getPort() {
        boolean local = Boolean.TRUE.toString().equals(System.getProperty("localDev", Boolean.FALSE.toString()));
        return local ? DEFAULT_PORT : Integer.valueOf(System.getenv("PORT"));
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
        resourceConfig.register(UUIDParamConverterProvider.class);

        ServletContainer apiServlet = new ServletContainer(resourceConfig);

        ServletHolder apiHolder = new ServletHolder(apiServlet);

        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(apiHolder, "/api/*");
        return handler;
    }

}
