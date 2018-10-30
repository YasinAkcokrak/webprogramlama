(function () {
    "use strict";

    angular.module("webuygulama").config(welcomeRoute);

    function welcomeRoute($routeProvider) {
        $routeProvider.when("/items", {
            templateUrl: 'html-parts/urunler/urunler.html',
            controller: "UrunlerCtrl as urunlerCtrl"
        });
    }
}());