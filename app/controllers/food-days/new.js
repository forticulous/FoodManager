import Ember from 'ember';

export default Ember.Controller.extend({
    actions: {
        createFoodDay: function() {
            var localDate = this.get('newLocalDate');
            if (Em.isBlank(localDate)) {
                return false;
            }
            if (localDate.match(/^\d{4}-\d{2}-\d{2}$/) === null) {
                return false;
            }

            var foodDay = this.store.createRecord('foodDay', {
                localDate: localDate,
                calories: 0
            });

            this.set('newLocalDate', '');

            foodDay.save().then(function() {
                // TODO: Refresh the food days model
                this.transitionToRoute('foodDays');
            }.bind(this));
        }
    }
});
