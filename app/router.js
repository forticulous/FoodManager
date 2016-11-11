import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('foodDays', { path: '/days' }, function() {
    this.route('index', { path: '/' });
    this.route('new');
  });
  this.route('foodDay', { path: '/days/:local_date' }, function() {
    this.route('foodDayItems', { path: '/items' }, function() {
      this.route('index', { path: '/' });
      this.route('new');
    });
  });
});

export default Router;
