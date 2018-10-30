(function () {
    "use strict";

    var en = {
        "welcome": {
            "title": "Online Payment Conclusion",
            "success": "Payment successfully received!",
            "failed": "Your payment failed!",
            "redirect-message": "saniye sonra 'Borç Ödeme ve Kayıt Onay Durumu' sayfasina yonlendirileceksiniz!"
        }
    };

    var tr = {
        "welcome": {
            "title": "Online Ödeme Sonucu",
            "success": "Odeme islemi basarili!",
            "failed": "Odeme islemi basarisiz!",
            "redirect-message": "saniye sonra 'Borç Ödeme ve Kayıt Onay Durumu' sayfasina yonlendirileceksiniz!"
        }
    };

    angular.module("webuygulama").config(I18NConfig);

    I18NConfig.$inject = ["$translateProvider"];

    //@ngInject
    function I18NConfig($translateProvider) {
        $translateProvider
            .translations("en", en)
            .translations("tr", tr)
            .preferredLanguage("tr");
    }
}());