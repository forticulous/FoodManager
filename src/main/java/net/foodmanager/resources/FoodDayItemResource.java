package net.foodmanager.resources;

import com.google.gson.JsonObject;
import net.foodmanager.dto.FoodDayItem;
import net.foodmanager.util.JpaUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

/**
 * @author mgibs6
 */
@Path("/days/{localDate}")
@Produces(MediaType.APPLICATION_JSON)
public class FoodDayItemResource {

    @GET
    @Path("/items/{itemId}")
    public Response getFoodDayItem(@QueryParam("itemId") String itemId) {
        UUID itemUUID = UUID.fromString(itemId);

        Optional<FoodDayItem> option = JpaUtil.returnFromTransaction(em -> Optional.ofNullable(em.find(FoodDayItem.class, itemUUID)));

        return option.map(Response::ok)
            .orElse(Response.ok(new JsonObject()))
            .build();
    }

}
