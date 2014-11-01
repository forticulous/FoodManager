package net.foodmanager.resources;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.foodmanager.dto.FoodDay;
import net.foodmanager.dto.FoodDayItem;
import net.foodmanager.modules.SqlModule;
import net.foodmanager.util.JpaUtil;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * @author fort
 */
@Path("/days")
@Produces(MediaType.APPLICATION_JSON)
public class FoodDayResource {

    @Inject
    @Named(SqlModule.GET_FOOD_DAY_BY_LOCAL_DATE)
    private String foodDayByLocalDateSql;

    @GET
    @Path("/{localDate}")
    public Response foodDayByLocalDate(@PathParam("localDate") String localDateString) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return Response.ok(e.getMessage()).build();
        }

        Optional<FoodDay> foodDay = getFoodDayByLocalDate(localDate);

        return foodDay.map(Response::ok)
                .orElse(Response.ok(new ObjectNode(JsonNodeFactory.instance)))
                .build();
    }

    @GET
    @Path("/{localDate}/calories")
    public Response getCaloriesForDay(@PathParam("localDate") String localDateString) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(localDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return Response.ok(e.getMessage()).build();
        }

        Optional<FoodDay> option = getFoodDayByLocalDate(localDate);

        int calories = option.<Integer> map(fd ->
            fd.getFoodDayItems().stream()
                .mapToInt(FoodDayItem::getCalories)
                .sum()
        ).orElse(0);

        ObjectNode node = new ObjectNode(JsonNodeFactory.instance);
        node.put("calories", calories);

        return Response.ok(node).build();
    }

    private Optional<FoodDay> getFoodDayByLocalDate(LocalDate localDate) {
        return JpaUtil.<Optional<FoodDay>> returnFromTransaction(em -> {
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
        });
    }

}
