import DS from 'ember-data';

export default DS.Model.extend({
  localDate: DS.attr('string'),
  calories: DS.attr('number')
});
