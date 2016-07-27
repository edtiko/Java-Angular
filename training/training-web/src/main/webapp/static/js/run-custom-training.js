///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////
var $contextPath = "http://localhost:8080/training/";
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
        trainingApp: "custom-training-app",
        userService: "datosPersonales/service/userService",
        disciplineService: "perfil/service/disciplineService",
        modalityService: "perfil/service/modalityService",
        objectiveService: "perfil/service/objectiveService",
        sportEquipmentService: "perfil/service/sportEquipmentService",
        sportService: "perfil/service/sportService",
        userProfileService: "perfil/service/userProfileService",
        surveyService: "questionnaire/service/surveyService",
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
        surveyService: {
            deps: ['angular', 'trainingApp']
        },
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute', 'angularBoostrap']
        },
        app: {
            deps: ['trainingApp', 'userService', 'disciplineService', 
                'modalityService', 'objectiveService', 'sportEquipmentService', 'sportService', 'userProfileService','surveyService']
        }
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

});