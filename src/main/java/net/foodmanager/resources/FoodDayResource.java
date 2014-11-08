package net.foodmanager.resources;

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
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author fort
 */
@Path("/days")
@Produces(MediaType.APPLICATION_JSON)
public class FoodDayResource {

    @Inject
    @Named(SqlModule.FIND_ALL_FOOD_DAYS)
    private String findAllFoodDays;

    @Inject
    @Named(SqlModule.GET_FOOD_DAY_BY_LOCAL_DATE)
    private String foodDayByLocalDateSql;

    @GET
    public Response findAllFoodDays(@QueryParam("offset") @DefaultValue("1") int offset,
                                    @QueryParam("limit") @DefaultValue("10") int limit) {
        Pagination pagination = Pagination.valueOf(offset, limit);

        List<FoodDay> foodDays = JpaUtil.returnFromTransaction(em -> {
            String sql = String.format(findAllFoodDays, FoodDay.class.getSimpleName());
            TypedQuery<FoodDay> query = em.createQuery(sql, FoodDay.class)
                    .setFirstResult(pagination.getFirstResult())
                    .setMaxResults(pagination.getMaxResult());

            return query.getResultList();
        });

        foodDays.forEach(fd -> {
            int cals = calculateCals(fd);
            fd.setCalories(cals);
        });

        return Response.ok(foodDays).build();
    }

    @GET
    @Path("/{localDate}")
    public Response foodDayByLocalDate(@PathParam("localDate") LocalDate localDate) {
        Optional<FoodDay> foodDay = JpaUtil.returnFromTransaction(em -> getFoodDayByLocalDate(em, localDate));

        foodDay.ifPresent(fd -> {
            int calories = calculateCals(fd);
            fd.setCalories(calories);
        });

        return foodDay.map(Response::ok)
                .orElse(Response.ok(new JsonObject()))
                .build();
    }

    @POST
    @Path("/{localDate}")
    public Response insertFoodDay(@PathParam("localDate") LocalDate localDate) throws URISyntaxException {
        final FoodDay newFoodDay = new FoodDay();
        newFoodDay.setId(UUID.randomUUID());
        newFoodDay.setLocalDate(localDate);

        JpaUtil.doInTransaction(em ->
                em.persist(newFoodDay)
        );

        // TODO: make sure this url is correct
        URI newUrl = new URI("/" + localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return Response.created(newUrl).build();
    }

    @DELETE
    @Path("/{localDate}")
    public Response deleteFoodDay(@PathParam("localDate") LocalDate localDate) {
        JpaUtil.doInTransaction(em -> {
            Optional<FoodDay> option = getFoodDayByLocalDate(em, localDate);

            option.ifPresent(em::remove);
        });

        return Response.ok().build();
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

    private int calculateCals(FoodDay fd) {
        return fd.getFoodDayItems().stream()
                .mapToInt(FoodDayItem::getCalories)
                .sum();
    }

}
