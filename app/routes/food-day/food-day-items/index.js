import Ember from 'ember';

export default Ember.Route.extend({
  renderTemplate: function() {
    this.render('foodDay.foodDayItems.index', { into: 'application' });
  },
  model: function() {
    var foodDay = this.modelFor('foodDay');
    return Ember.$.getJSON('/api/days/' + foodDay.get('localDate') + '/items');
  },
  afterModel: function(model) {
    var foodDay = this.modelFor('foodDay');
    model.set('foodDay', foodDay);
  }
});