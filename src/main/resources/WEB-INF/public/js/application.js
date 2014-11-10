window.FoodManager = Ember.Application.create();

FoodManager.ApplicationAdapter = DS.RESTAdapter.extend({
    buildURL: function(type, id, record) {
        switch(type) {
            case 'foodDay':
                return '/api/days/' + id;
            case 'foodDayItem':
                if (record.get('isNew') === true) {
                    return '/api/days/' + record.get('localDate') + '/items/new';
                }
                return null;
            default:
                return null;
        }
    }
});

// Use JsonSerializer instead of RESTSerializer because it is too opinionated
FoodManager.ApplicationSerializer = DS.JSONSerializer.extend();