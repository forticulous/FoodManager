package net.foodmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import net.foodmanager.jpa.LocalDateStringConverter;
import net.foodmanager.json.LocalDateDeserializer;
import net.foodmanager.json.LocalDateSerializer;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converts;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author fort
 */
@Entity
@Table(name = "food_day")
@Converts( {
        @Convert(attributeName = "localDate", converter = LocalDateStringConverter.class)
})
public class FoodDay {

    private UUID id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate localDate;
    @JsonIgnore
    private List<FoodDayItem> foodDayItems;

    @Id
    @Column(name = "food_day_id")
    @Type(type = "pg-uuid")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @OneToMany(mappedBy = "foodDay", fetch = FetchType.EAGER)
    public List<FoodDayItem> getFoodDayItems() {
        return foodDayItems;
    }

    public void setFoodDayItems(List<FoodDayItem> foodDayItems) {
        this.foodDayItems = foodDayItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodDay other = (FoodDay) o;

        return Objects.equals(id, other.id) &&
                Objects.equals(localDate, other.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("localDate", localDate)
                .toString();
    }
}
