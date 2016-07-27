///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////
var $contextPath = "http://localhost:8085/training/";
require.config({
    paths: {
        angular: 'lib/angular.min',
        angularMessages: 'lib/angular-messages.min',
        angularRoute: 'lib/angular-route.min',
        angularAnimate: 'lib/angular-animate.min',
        angularTouch: 'lib/angular-touch.min',
        angularBoostrap: 'lib/ui-bootstrap-tpls.min',
        csrfInterceptor: 'lib/spring-security-csrf-token-interceptor.min',
        lodash: "lib/lodash.min",
        trainingApp: "custom-training-app",
        userService: "datosPersonales/service/userService",
        disciplineService: "perfil/service/disciplineService",
        modalityService: "perfil/service/modalityService",
        objectiveService: "perfil/service/objectiveService",
        sportEquipmentService: "perfil/service/sportEquipmentService",
        sportService: "perfil/service/sportService",
        userProfileService: "perfil/service/userProfileService",
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
        userService: {
            deps: ['angular', 'trainingApp']
        },
        disciplineService: {
            deps: ['angular', 'trainingApp']
        },
        modalityService: {
            deps: ['angular', 'trainingApp']
        },
        objectiveService: {
            deps: ['angular', 'trainingApp']
        },
        sportEquipmentService: {
            deps: ['angular', 'trainingApp']
        },
        sportService: {
            deps: ['angular', 'trainingApp']
        },
        userProfileService: {
            deps: ['angular', 'trainingApp']
        },
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute', 'angularBoostrap']
        },
        app: {
            deps: ['trainingApp', 'userService', 'disciplineService', 
                'modalityService', 'objectiveService', 'sportEquipmentService', 'sportService', 'userProfileService']
        }
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

});