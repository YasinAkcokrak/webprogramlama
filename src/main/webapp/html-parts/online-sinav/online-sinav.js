(function () {
    "use strict";

    angular.module("webuygulama").controller("OnlineSinavCtrl", OnlineSinavCtrl);

    function OnlineSinavCtrl(OnlineSinavService) {
        var vm = this;
        vm.items = {};
        active();

        function active() {
            OnlineSinavService.getir().then(function (result) {
                vm.items = result;
            });
        }
    }
}());