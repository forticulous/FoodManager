FoodManager.FoodDay = DS.Model.extend({
    localDate: DS.attr('string'),
    calories: DS.attr('int')
});

FoodManager.FoodDay.FIXTURES = [
    {
        id: 1,
        localDate: '2014-10-23',
        calories: 2000
    },
    {
        id: 2,
        localDate: '2014-10-24',
        calories: 2900
    }
];