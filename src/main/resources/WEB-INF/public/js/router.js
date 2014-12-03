FoodManager.Router.map(function() {
    this.resource('foodDays', { path: '/days' }, function() {
        this.route('new');
        this.resource('foodDay', { path: '/:localDate' }, function() {
            this.resource('foodDayItems', { path: '/items' }, function() {
                this.route('new');
            });
        });
    });
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
        var rec = Ember.Object.create();
        rec.set('localDate', params.localDate);
        return this.store.find('foodDay', 0, rec);
    }
});

FoodManager.FoodDayItemsRoute = Ember.Route.extend({
    model: function(params) {
        var foodDay = this.modelFor('foodDay');
        return Ember.$.getJSON('/api/days/' + foodDay.get('localDate') + '/items');
    },
    afterModel: function(model) {
        var foodDay = this.modelFor('foodDay');
        model.set('foodDay', foodDay);
    }
});

FoodManager.FoodDayItemsNewRoute = Ember.Route.extend({
    setupController: function(controller, model) {
        var foodDay = this.modelFor('foodDay');
        model.set('localDate', foodDay.get('localDate'));
        this._super(controller, model);
    }
})