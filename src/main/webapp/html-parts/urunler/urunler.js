(function () {
    "use strict";

    angular.module("webuygulama").controller("UrunlerCtrl", UrunlerCtrl);

    function UrunlerCtrl(UrunlerService) {

        var vm = this;
        vm.items = {};

        active();

        function active() {
            UrunlerService.getir().then(function (result) {
                vm.items = result;
            });
        }

    }
}());