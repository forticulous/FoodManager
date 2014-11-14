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
    compileManyTemplates: function(pathNames) {
        for (var i = 0; i < pathNames.length; i++) {
            this.compileTemplate(pathNames[i][0], pathNames[i][1]);
        }
    }
});

FoodManager.reopen({
    utils: FoodManager.Utils.create()
});

// Load templates
(function() {
    var pathNames = [
        ['templates/foodDayIndex.handlebars', 'foodDay/index'],
        ['templates/foodDayItemsIndex.handlebars', 'foodDayItems/index'],
        ['templates/foodDayItemsNew.handlebars', 'foodDayItems/new'],
        ['templates/foodDaysIndex.handlebars', 'foodDays/index'],
        ['templates/foodDaysNew.handlebars', 'foodDays/new']
    ];
    FoodManager.utils.compileManyTemplates(pathNames);
})();