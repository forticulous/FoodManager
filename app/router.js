import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.resource('foodDays', { path: '/days' }, function() {
    this.route('new');
    this.resource('foodDay', { path: '/:localDate' }, function() {
      this.resource('foodDayItems', { path: '/items' }, function() {
        this.route('new');
      });
    });
  });
});

export default Router;
