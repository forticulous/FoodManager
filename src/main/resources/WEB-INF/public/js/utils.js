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
    },
    compileTemplate: function(path, name) {
        Ember.$.ajax(path, {
            async: false,
            success: function(templateText) {
                var template = Ember.Handlebars.compile(templateText);
                Ember.TEMPLATES[name] = template;
            }
        });
    },
    compileManyTemplates: function(paths, names) {
        for (var i = 0; i < paths.length; i++) {
            this.compileTemplate(paths[i], names[i]);
        }
    }
});

FoodManager.reopen({
    utils: FoodManager.Utils.create()
});

// Load templates
(function() {
    var paths = [
        'templates/foodDayIndex.handlebars',
        'templates/foodDayItemsIndex.handlebars',
        'templates/foodDayItemsNew.handlebars',
        'templates/foodDaysIndex.handlebars',
        'templates/foodDaysNew.handlebars'
    ];
    var names = [
        'foodDay/index',
        'foodDayItems/index',
        'foodDayItems/new',
        'foodDays/index',
        'foodDays/new'
    ];
    FoodManager.utils.compileManyTemplates(paths, names);
})();