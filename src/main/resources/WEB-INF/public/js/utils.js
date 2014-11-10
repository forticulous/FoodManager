FoodManager.Utils = Ember.Object.extend({
    parseIntOrDefault: function(obj, radix, def) {
        if (this.isUnset(obj)) {
            return def;
        }
        return parseInt(obj, radix);
    },
    parseFloatOrDefault: function(obj, radix, def) {
        if (this.isUnset(obj)) {
            return def;
        }
        return parseFloat(obj, radix);
    },
    isUnset: function(obj) {
        return obj === undefined || obj.length === 0;
    }
});

FoodManager.reopen({
    utils: FoodManager.Utils.create()
});