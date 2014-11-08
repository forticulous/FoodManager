FoodManager.FoodDaysNewController = Ember.ArrayController.extend({
    actions: {
        createFoodDay: function() {
            var localDate = this.get('newLocalDate');
            if (localDate === null || localDate === undefined) {
                return false;
            }
            if (localDate.match(/^\d{4}-\d{2}-\d{2}$/) === null) {
                return false;
            }

            var foodDay = this.store.createRecord('foodDay', {
                localDate: localDate,
                calories: 0
            });

            this.set('newLocalDate', '');

            foodDay.save();
        }
    }
});

FoodManager.FoodDayItemNewController = Ember.ObjectController.extend({
    actions: {
        createFoodDayItem: function() {
            var desc = this.get('newDescription');
            var meal = this.get('newMeal');
            var cals = this.get('newCalories');
            if (desc === null || desc === undefined ||
                meal === null || meal === undefined ||
                cals === null || cals === undefined) {
                return false;
            }
            if (desc.length === 0 ||
                meal.length === 0 ||
                cals.length === 0) {
                return false;
            }

            var foodDayItem = this.store.createRecord('foodDayItem', {
                foodDescription: desc,
                meal: meal,
                calories: cals
            });

            this.set('newDescription', '');
            this.set('newMeal', '');
            this.set('newCalories', '');

            foodDayItem.set('localDate', this.get('localDate'));

            foodDayItem.save();
        }
    }
});