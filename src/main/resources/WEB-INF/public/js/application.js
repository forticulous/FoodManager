window.FoodManager = Ember.Application.create();

FoodManager.ApplicationAdapter = DS.RESTAdapter.extend({
    buildURL: function(type, id, record) {
        switch(type) {
            case 'foodDay':
                return '/api/days/' + record.get('localDate');
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

FoodManager.DatePickerView = Ember.TextField.extend({
    didInsertElement: function() {
        this.$().datepicker({
            dateFormat: 'yy-mm-dd'
        });
    }
})

// Use JsonSerializer instead of RESTSerializer because it is too opinionated
FoodManager.ApplicationSerializer = DS.JSONSerializer.extend();