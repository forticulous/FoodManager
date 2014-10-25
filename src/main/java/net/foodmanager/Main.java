package net.foodmanager;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import net.foodmanager.modules.JpaModule;
import net.foodmanager.modules.SqlModule;
import net.foodmanager.util.JpaUtil;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author fort
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("FoodManager started");

        String localDate = Arrays.stream(args)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing required argument localDate"));

        Injector injector = Guice.createInjector(new JpaModule(), new SqlModule());
        String totalDayCaloriesSql = injector.getInstance(Key.get(String.class, Names.named(SqlModule.DAILY_CALORIE_TOTAL)));

        Optional<BigInteger> option = JpaUtil.<Optional<BigInteger>> returnFromTransaction(em -> {
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
