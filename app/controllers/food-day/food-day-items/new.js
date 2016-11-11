import Ember from 'ember';

export default Ember.Controller.extend({
    actions: {
        createFoodDayItem: function() {
            let desc = this.get('newDescription');
            let meal = this.get('newMeal');
            let cals = this.get('newCalories');
            if (Em.isBlank(desc) ||
                Em.isBlank(meal) ||
                Em.isBlank(cals)) {
                return false;
            }

            let foodDayItem = this.store.createRecord('foodDayItem', {
                foodDescription: desc,
                meal: meal,
                calories: cals
            });

            let foodDay = this.model.get('foodDay');
            foodDayItem.set('localDate', foodDay.get('localDate'));

            this.set('newDescription', '');
            this.set('newMeal', '');
            this.set('newCalories', '');

            foodDayItem.save().then(function(item) {
                // Also update food day
                let itemCals = item.get('calories');
                let foodDayCals = foodDay.get('calories');
                foodDay.set('calories', foodDayCals + itemCals);
                this.store.push('foodDay', foodDay.get('data'));

                // Refresh food day items and food day models
                this.get('foodDayItemsRoute').refresh();
                this.transitionToRoute('foodDayItems');
            }.bind(this));
        },
        calsHelper: function() {
            let cals = this.get('newCalories');
            let calsPerServing = this.get('calsPerServingVal');
            let servings = this.get('servingsVal');

            cals = Em.isEmpty(cals) ? 0 : parseInt(cals, 10);
            calsPerServing = Em.isEmpty(calsPerServing) ? 0 : parseInt(calsPerServing, 10);
            servings = Em.isEmpty(servings) ? 1.0 : parseFloat(servings, 10);
            cals += Math.trunc(calsPerServing * servings);

            this.set('newCalories', cals);
            this.set('calsPerServingVal', '');
            this.set('servingsVal', '');
        }
    }
});