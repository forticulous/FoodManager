import Ember from 'ember';

export default Ember.TextField.extend({
    didInsertElement: function() {
        this.$().datepicker({
            dateFormat: 'yy-mm-dd',
            dayNamesMin: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
        });
    }
});
