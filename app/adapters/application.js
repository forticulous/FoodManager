import DS from 'ember-data';

export default DS.RESTAdapter.extend({
  buildURL: function(type, id, snapshot, requestType, query) {
    switch(type) {
      case 'food-day':
        return '/api/days/' + query.localDate;
      case 'food-day-item':
        if (requestType === 'createRecord') {
          return '/api/days/' + snapshot.adapterOptions.localDate + '/items/new';
        }
        return null;
      default:
        console.log('Unexpected type encountered: ' + type);
        return null;
    }
  }
});
