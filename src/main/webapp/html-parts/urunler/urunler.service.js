(function () {

    "use strict";

    angular.module("webuygulama").factory("UrunlerService", UrunlerService);

    function UrunlerService($http) {

        var urunlerService = {
            getir: getir
        };

        return urunlerService;

        function getir() {
            return $http.get("/urunler/hepsi").then(function (response) {
                return response.data;
            });
        }
    }

}());