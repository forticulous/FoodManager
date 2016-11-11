import DS from 'ember-data';

export default DS.Model.extend({
  foodDescription: DS.attr('string'),
  meal: DS.attr('string'),
  calories: DS.attr('number')
});