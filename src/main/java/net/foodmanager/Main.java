package net.foodmanager;

import net.foodmanager.util.JpaUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author fort
 */
public class Main {

    private static final String PERSISTENCE_UNIT = "foodb";

    public static void main(String[] args) {
        System.out.println("FoodManager started");

        String localDate = Arrays.stream(args)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing required argument localDate"));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        JpaUtil.setEntityManagerFactory(emf);

        Optional<BigInteger> option = JpaUtil.<Optional<BigInteger>> returnFromTransaction(em -> {
            String totalDayCaloriesSql =
                    "SELECT sum(fdi.calories) " +
                    "FROM food_day fd " +
                    "INNER JOIN food_day_item fdi " +
                    "ON fd.food_day_id = fdi.food_day_id " +
                    "WHERE fd.local_date = :localDate";
            Query query = em.createNativeQuery(totalDayCaloriesSql);
            query.setParameter("localDate", localDate);
            BigInteger singleResult = (BigInteger) query.getSingleResult();
            return Optional.ofNullable(singleResult);
        });
        int totalCals = option
                .map(BigInteger::intValue)
                .orElseThrow(() -> new IllegalArgumentException("No records found"));

        System.out.println(String.format("Total calories for %s: %s", localDate, totalCals));

        System.exit(0);
    }

}
