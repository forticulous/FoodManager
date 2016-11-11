import Ember from 'ember';

export default Ember.Route.extend({
  renderTemplate: function() {
    this.render('foodDay.foodDayItems.new', { into: 'application' });
  },
  setupController: function(controller, model) {
    var foodDay = this.modelFor('foodDay');
    controller.set('foodDay', foodDay);
    this._super(controller, model);
  }
});