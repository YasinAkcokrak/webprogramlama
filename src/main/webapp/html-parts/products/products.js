(function () {
    "use strict";

    angular.module("webuygulama").controller("ProductsCtrl", ProductsCtrl);

    function ProductsCtrl(ProductsService) {

        var vm = this;
        vm.products = {};

        active();

        function active() {
            ProductsService.getir().then(function (result) {
                vm.products = result;
            });
        }
    }
}());