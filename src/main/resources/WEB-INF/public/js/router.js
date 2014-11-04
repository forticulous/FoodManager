FoodManager.Router.map(function() {
    this.resource('foodDays', { path: '/' });
});

FoodManager.FoodDaysRoute = Ember.Route.extend({
    model: function() {
        return this.store.find('foodDay');
    }
});