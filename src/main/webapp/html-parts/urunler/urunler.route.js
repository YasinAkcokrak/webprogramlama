(function () {
    "use strict";

    angular.module("webuygulama").config(urunlerRoute);

    function urunlerRoute($routeProvider) {
        $routeProvider.when("/items", {
            templateUrl: 'html-parts/urunler/urunler.html',
            controller: "UrunlerCtrl as urunlerCtrl"
        });
    }
}());