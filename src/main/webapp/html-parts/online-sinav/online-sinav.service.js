(function () {
    "use strict";
    angular.module("webuygulama").factory("OnlineSinavService", OnlineSinavService);

    function OnlineSinavService($http) {
        var onlineSinavService = {
            getir: getir
        };
        return onlineSinavService;

        function getir() {
            return $http.get("/online-sinav/ales-sozel").then(function (response) {
                return response.data;
            });
        }
    }
}());