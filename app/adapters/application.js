import DS from 'ember-data';

export default DS.RESTAdapter.extend({
  buildURL: function(type, id, record) {
    switch(type) {
      case 'food-day':
        return '/api/days/' + record.get('localDate');
      case 'food-day-item':
        if (record.get('isNew') === true) {
          return '/api/days/' + record.get('localDate') + '/items/new';
        }
        return null;
      default:
        console.log('Unexpected type encountered: ' + type);
        return null;
    }
  }
});
