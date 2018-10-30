var app = angular.module('webuygulama', ['ngRoute', 'ngCookies',
    'ui.bootstrap', 'ui.mask', 'ui.select', 'pascalprecht.translate',
    'tmh.dynamicLocale']);

app.config(function ($routeProvider, $translateProvider,
                     $provide, $httpProvider, tmhDynamicLocaleProvider,
                     $compileProvider) {
    $routeProvider
        .otherwise({
            templateUrl: 'html-parts/error/404.html',
            controller: 'ErrorCtrl'
        });

    tmhDynamicLocaleProvider.useCookieStorage();
    tmhDynamicLocaleProvider.defaultLocale('tr');
    tmhDynamicLocaleProvider.localeLocationPattern('resources/i18n/angular-locale_{{locale}}.js');

});

app.run(function ($rootScope, $location, $translate,
                  tmhDynamicLocale, $sce) {

    $rootScope.lang = 'tr';

    $rootScope.changeLang = function (lang) {
        $translate.use(lang);
        tmhDynamicLocale.set(lang);
        $rootScope.lang = lang;
    };

    // ================================
    // Init CSRF
    // ================================
    //  CsrfService.initCsrfToken();

    /*$rootScope.hasRole = function (role) {
        return AuthenticationService.hasRole(role);
    };

    var promise = AuthenticationService.initAccount();
    */


    $rootScope.trustAsHtml = function (value) {
        return $sce.trustAsHtml(value);
    };
});