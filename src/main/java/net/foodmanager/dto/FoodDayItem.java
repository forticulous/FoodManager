package net.foodmanager.dto;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

/**
 * @author fort
 */
@Entity
@Table(name = "food_day_item")
public class FoodDayItem {

    private UUID id;
    private String foodDescription;
    private String meal;
    private int calories;
    private FoodDay foodDay;

    @Id
    @Column(name = "food_day_item_id")
    @Type(type = "pg-uuid")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "food_desc")
    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_day_id")
    public FoodDay getFoodDay() {
        return foodDay;
    }

    public void setFoodDay(FoodDay foodDay) {
        this.foodDay = foodDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodDayItem other = (FoodDayItem) o;

        return Objects.equals(id, other.id) &&
                Objects.equals(foodDescription, other.foodDescription) &&
                Objects.equals(meal, other.meal) &&
                calories == other.calories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodDescription, meal, calories);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("foodDescription", foodDescription)
                .add("meal", meal)
                .add("calories", calories)
                .toString();
    }
}
