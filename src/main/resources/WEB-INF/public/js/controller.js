FoodManager.FoodDaysNewController = Ember.Controller.extend({
    actions: {
        createFoodDay: function() {
            var localDate = this.get('newLocalDate');
            if (Em.isBlank(localDate)) {
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
                // Refresh the food days model
                this.get('foodDaysRoute').refresh();
                this.transitionToRoute('foodDays');
            }.bind(this));
        }
    }
});

FoodManager.FoodDayItemsNewController = Ember.Controller.extend({
    actions: {
        createFoodDayItem: function() {
            var desc = this.get('newDescription');
            var meal = this.get('newMeal');
            var cals = this.get('newCalories');
            if (Em.isBlank(desc) ||
                Em.isBlank(meal) ||
                Em.isBlank(cals)) {
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
                // Refresh food day items and food day models
                this.get('foodDayItemsRoute').refresh();
                this.get('foodDayRoute').refresh();
                this.transitionToRoute('foodDayItems');
            }.bind(this));
        },
        calsHelper: function() {
            var cals = this.get('newCalories');
            var calsPerServing = this.get('calsPerServingVal');
            var servings = this.get('servingsVal');

            var cals = Em.isEmpty(cals) ? 0 : parseInt(cals, 10);
            var calsPerServing = Em.isEmpty(calsPerServing) ? 0 : parseInt(calsPerServing, 10);
            var servings = Em.isEmpty(servings) ? 1.0 : parseFloat(servings, 10);
            cals += Math.trunc(calsPerServing * servings);

            this.set('newCalories', cals);
            this.set('calsPerServingVal', '');
            this.set('servingsVal', '');
        }
    }
});