FoodManager.Router.map(function() {
    this.resource('foodDays', { path: '/days' });
    this.resource('foodDays.new', { path: '/days/new' });
    this.resource('foodDay', { path: '/days/:localDate' });
    this.resource('foodDayItems', { path: '/days/:localDate/items' });
    this.resource('foodDayItem.new', { path: '/days/:localDate/items/new' });
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
        return this.store.find('foodDay', 0, { localDate: params.localDate });
    }
});

FoodManager.FoodDayItemsRoute = Ember.Route.extend({
    model: function(params) {
        return Ember.$.getJSON('/api/days/' + params.localDate + '/items');
    },
    setupController: function(controller, model) {
        this._super(controller, model);
        var foodDay = this.modelFor('foodDay');
        controller.set('localDate', foodDay.get('localDate'));
    }
});