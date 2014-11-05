FoodManager.Router.map(function() {
    this.resource('foodDays', { path: '/days' });
    this.resource('foodDay', { path: '/days/:localDate' });
});

FoodManager.IndexRoute = Ember.Route.extend({
    redirect: function() {
        this.transitionTo('foodDays');
    }
});

FoodManager.FoodDaysRoute = Ember.Route.extend({
    model: function() {
        return Ember.$.getJSON('/api/days');
    }
});

FoodManager.FoodDayRoute = Ember.Route.extend({
    model: function(params) {
        return Ember.$.getJSON('/api/days/' + params.localDate + '/calories').then(function(resp) {
            return Ember.$.extend({}, { localDate: params.localDate }, resp);
        });
    }
});