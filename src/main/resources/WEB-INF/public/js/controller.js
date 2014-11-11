FoodManager.FoodDaysNewController = Ember.ArrayController.extend({
    actions: {
        createFoodDay: function() {
            var localDate = this.get('newLocalDate');
            if (FoodManager.utils.isUnset(localDate)) {
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

            foodDay.save().then(function() {
                this.transitionToRoute('foodDays');
            }.bind(this));
        }
    }
});

FoodManager.FoodDayItemsNewController = Ember.ObjectController.extend({
    actions: {
        createFoodDayItem: function() {
            var desc = this.get('newDescription');
            var meal = this.get('newMeal');
            var cals = this.get('newCalories');
            if (FoodManager.utils.isUnset(desc) ||
                FoodManager.utils.isUnset(meal) ||
                FoodManager.utils.isUnset(cals)) {
                return false;
            }

            var foodDayItem = this.store.createRecord('foodDayItem', {
                foodDescription: desc,
                meal: meal,
                calories: cals
            });

            foodDayItem.set('localDate', this.model.get('localDate'));

            this.set('newDescription', '');
            this.set('newMeal', '');
            this.set('newCalories', '');

            foodDayItem.save().then(function() {
                this.transitionToRoute('foodDayItems');
            }.bind(this));
        },
        calsHelper: function() {
            var cals = this.get('newCalories');
            var calsPerServing = this.get('calsPerServingVal');
            var servings = this.get('servingsVal');

            var cals = FoodManager.utils.parseIntOrDefault(cals, 10, 0);
            var calsPerServing = FoodManager.utils.parseIntOrDefault(calsPerServing, 10, 0);
            var servings = FoodManager.utils.parseFloatOrDefault(servings, 10, 1.0);
            cals += Math.trunc(calsPerServing * servings);

            this.set('newCalories', cals);
            this.set('calsPerServingVal', '');
            this.set('servingsVal', '');
        }
    }
});