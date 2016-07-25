///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////

require.config({
    paths: {
        angular: '../bower_components/angular/angular',
        angularMessages: '../bower_components/angular-messages/angular-messages',
        angularRoute: '../bower_components/angular-route/angular-route',
        angularAnimate: '../bower_components/angular-animate/angular-animate.min',
        angularTouch: '../bower_components/angular-touch/angular-touch.min',
        angularBoostrap: '../bower_components/angular-bootstrap/ui-bootstrap-tpls',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: "../bower_components/lodash/dist/lodash",
        frontendServices: 'frontend-services',
        trainingApp: "custom-training-app",
        calendarController: "calendar/controller/calendar_controller",
        app: "routeResolver"
    },
    shim: {
        angular: {
            exports: "angular"
        },
        angularRoute: {
            exports: 'ngRoute', 
            deps: ['angular']
        },
        angularTouch:{
            exports: 'angularTouch', 
            deps: ['angular']
        },
        angularBoostrap:{
            exports: 'angularBoostrap', 
            deps: ['angular']
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            deps: ['angular']
        },
        frontendServices: {
            deps: ['angular', 'lodash']
        },
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute', 
                'frontendServices','angularBoostrap']
        },
        app: {
            deps: ['trainingApp']
        }
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

});