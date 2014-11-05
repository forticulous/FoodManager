package net.foodmanager.modules;

import com.google.inject.AbstractModule;
import net.foodmanager.resources.FoodDayItemResource;
import net.foodmanager.resources.FoodDayResource;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author fort
 */
public class ResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        FoodDayResource foodDayResource = new FoodDayResource();
        bind(FoodDayResource.class).toInstance(foodDayResource);

        FoodDayItemResource foodDayItemResource = new FoodDayItemResource();
        bind(FoodDayItemResource.class).toInstance(foodDayItemResource);

        bind(ResourceConfig.class).toInstance(provideResourceConfig(foodDayResource, foodDayItemResource));
    }

    private ResourceConfig provideResourceConfig(Object... resources) {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.registerInstances(resources);
        return resourceConfig;
    }

}
