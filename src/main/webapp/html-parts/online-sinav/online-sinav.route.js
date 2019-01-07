(function () {
    "use strict";
    angular.module("webuygulama").config(OnlineSinavRoute);

    function OnlineSinavRoute($routeProvider) {
        $routeProvider.when("/online-sinav", {
            templateUrl: 'html-parts/online-sinav/online-sinav.html',
            controller: "OnlineSinavCtrl as onlineSinavCtrl"
        });
    }
}());