window.FoodManager = Ember.Application.create();

FoodManager.ApplicationAdapter = DS.RESTAdapter.extend({
    buildURL: function(type, id, record) {
        switch(type) {
            case 'foodDay':
                return '/api/days/' + record.get('localDate');
            case 'foodDayItem':
                return '/api/days/' + record.get('localDate') + '/items/new';
            default:
                return null;
        }
    }
});