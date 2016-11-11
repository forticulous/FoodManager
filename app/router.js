import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('foodDays', { path: '/days', resetnamespace: true }, function() {
    this.route('new');
    this.route('foodDay', { path: '/:localDate', resetnamespace: true }, function() {
      this.route('foodDayItems', { path: '/items', resetnamespace: true }, function() {
        this.route('new');
      });
    });
  });
});

export default Router;
