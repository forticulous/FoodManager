import DS from 'ember-data';

// Use JsonSerializer instead of RESTSerializer because it is too opinionated
export default DS.JSONSerializer.extend({
});
