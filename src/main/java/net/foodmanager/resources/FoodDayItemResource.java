package net.foodmanager.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.foodmanager.dto.FoodDay;
import net.foodmanager.dto.FoodDayItem;
import net.foodmanager.modules.SqlModule;
import net.foodmanager.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * @author mgibs6
 */
@Path("/days/{localDate}/items")
@Produces(MediaType.APPLICATION_JSON)
public class FoodDayItemResource {

    @Inject
    @Named(SqlModule.GET_FOOD_DAY_BY_LOCAL_DATE)
    private String foodDayByLocalDateSql;

    @GET
    public Response findAllFoodDayItems(@QueryParam("localDate") LocalDate localDate) {
        Optional<FoodDay> option = JpaUtil.returnFromTransaction(em -> getFoodDayByLocalDate(em, localDate));

        return option.map(FoodDay::getFoodDayItems)
            .map(Response::ok)
            .orElse(Response.ok(new JsonArray()))
            .build();
    }

    private Optional<FoodDay> getFoodDayByLocalDate(EntityManager em, LocalDate localDate) {
        String sql = String.format(foodDayByLocalDateSql, FoodDay.class.getSimpleName());
        TypedQuery<FoodDay> query = em.createQuery(sql, FoodDay.class)
                .setParameter("localDate", localDate);

        FoodDay singleResult;
        try {
            singleResult = query.getSingleResult();
        } catch (NoResultException ex) {
            return Optional.empty();
        }
        return Optional.of(singleResult);
    }

    @GET
    @Path("/{itemId}")
    public Response getFoodDayItem(@QueryParam("itemId") String itemId) {
        UUID itemUUID = UUID.fromString(itemId);

        Optional<FoodDayItem> option = JpaUtil.returnFromTransaction(em -> Optional.ofNullable(em.find(FoodDayItem.class, itemUUID)));

        return option.map(Response::ok)
            .orElse(Response.ok(new JsonObject()))
            .build();
    }

}
