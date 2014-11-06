FoodManager.FoodDayItem = DS.Model.extend({
    foodDescription: DS.attr('string'),
    meal: DS.attr('string'),
    calories: DS.attr('int')
});