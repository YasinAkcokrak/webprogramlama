(function () {

    "use strict";

    angular.module("webuygulama").factory("ProductsService", ProductsService);

    function ProductsService($http) {

        var productsService = {
            getir: getir
        };

        return productsService;

        function getir() {
            return $http.get("/products/all").then(function (response) {
                return response.data;
            });
        }
    }

}());