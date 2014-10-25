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
        Main main = new Main();
        main.run(args);
    }

    public void run(String[] args) {
        System.out.println("FoodManager started");

        Injector injector = Guice.createInjector(new JpaModule(), new SqlModule());
        //insertFoodDay(args, injector);
        //insertFoodDayItem(args, injector);
        printDailyCalorieTotal(args, injector);

        System.exit(0);
    }

    private void insertFoodDayItem(String[] args, Injector injector) {
        if (args.length < 4) {
            throw new IllegalArgumentException("Missing required number of arguments");
        }
        String localDate = args[0];
        String foodDesc = args[1];
        String meal = args[2];
        long calories = Long.valueOf(args[3]);

        String insertFoodDayItemSql = injector.getInstance(Key.get(String.class, Names.named(SqlModule.INSERT_FOOD_DAY_ITEM)));

        JpaUtil.doInTransaction(em -> {
            em.createNativeQuery(insertFoodDayItemSql)
                    .setParameter("localDate", localDate)
                    .setParameter("foodDesc", foodDesc)
                    .setParameter("meal", meal)
                    .setParameter("calories", calories)
                    .executeUpdate();
        });
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

    private void printDailyCalorieTotal(String[] args, Injector injector) {
        String localDate = Arrays.stream(args)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing required argument localDate"));
        String totalDayCaloriesSql = injector.getInstance(Key.get(String.class, Names.named(SqlModule.DAILY_CALORIE_TOTAL)));

        Optional<BigInteger> option = JpaUtil.<Optional<BigInteger>> returnFromTransaction(em -> {
            Query query = em.createNativeQuery(totalDayCaloriesSql);
            query.setParameter("localDate", localDate);
            BigInteger singleResult = (BigInteger) query.getSingleResult();
            return Optional.ofNullable(singleResult);
        });
        int totalCals = option
                .map(BigInteger::intValue)
                .orElse(0);

        System.out.println(String.format("Total calories for %s: %s", localDate, totalCals));
    }

}
