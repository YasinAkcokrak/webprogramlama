(function () {
    "use strict";

    angular.module("webuygulama").config(welcomeRoute);

    function welcomeRoute($routeProvider) {
        $routeProvider.when("/", {
            templateUrl: 'html-parts/welcome/welcome.html',
            controller: "WelcomeCtrl as welcomeCtrl"
        });
    }
}());