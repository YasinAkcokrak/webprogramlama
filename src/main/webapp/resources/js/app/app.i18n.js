(function () {
    "use strict";

    var tr = {};

    var en = {};

    angular.module("webuygulama")
        .config(I18NConfig);

    I18NConfig.$inject = ["$translateProvider"];

    // @ngInject
    function I18NConfig($translateProvider) {
        $translateProvider
            .translations("en", en)
            .translations("tr", tr)
            .preferredLanguage("tr");
        $translateProvider.useCookieStorage();
        $translateProvider.useSanitizeValueStrategy("escaped");
        $translateProvider.directivePriority(222);
    }
}());