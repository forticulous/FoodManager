FoodManager.Utils = Ember.Object.extend({
    parseIntOrDefault: function(obj, radix, def) {
        if (obj === undefined ||
            obj.length === 0) {
            return def;
        }
        return parseInt(obj, radix);
    },
    parseFloatOrDefault: function(obj, radix, def) {
        if (obj === undefined ||
            obj.length === 0) {
            return def;
        }
        return parseFloat(obj, radix);
    }
});

FoodManager.reopen({
    utils: FoodManager.Utils.create()
});