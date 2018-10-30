(function () {
    "use strict";

    var en = {
        "urunler": {
            "title": "Online Payment Conclusion"
        }
    };

    var tr = {
        "urunler": {
            "title": "Online Ödeme Sonucu"
        }
    };

    angular.module("webuygulama").config(I18NConfig);

    I18NConfig.$inject = ["$translateProvider"];

    //@ngInject
    function I18NConfig($translateProvider) {
        $translateProvider
            .translations("en", en)
            .translations("tr", tr)
            .preferredLanguage("tr");
    }
}());