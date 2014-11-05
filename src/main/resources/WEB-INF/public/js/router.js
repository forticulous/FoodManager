FoodManager.Router.map(function() {
    this.resource('foodDays', { path: '/days' });
    this.resource('foodDay', { path: '/days/:localDate' });
});

FoodManager.IndexRoute = Ember.Route.extend({
    redirect: function() {
        this.transitionTo('foodDays');
    }
})

FoodManager.FoodDaysRoute = Ember.Route.extend({
    model: function() {
        return this.store.find('foodDay');
    }
});

FoodManager.FoodDayRoute = Ember.Route.extend({
    model: function(params) {
        return {
            id: 1,
            localDate: params.localDate,
            calories: 2000
        };
    }
});