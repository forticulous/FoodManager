import Ember from 'ember';

export default Ember.Route.extend({
  model: function(params) {
    return this.store.queryRecord('food-day', { localDate: params.local_date });
  }
});