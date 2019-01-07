(function () {
    "use strict";

    angular.module("webuygulama").config(productsRoute);

    function productsRoute($routeProvider) {
        $routeProvider.when("/products", {
            templateUrl: 'html-parts/products/products.html',
            controller: "ProductsCtrl as productsCtrl"
        });
    }
}());