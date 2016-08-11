///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////
var $contextPath = "http://localhost:8085/training/";
//var $contextPath = "http://181.143.227.220:8086/training/";
require.config({
    waitSeconds: 200,
    paths: {
        
        angular: 'lib/angular.min',
        angularMessages: 'lib/angular-messages.min',
        angularRoute: 'lib/angular-route.min',
        angularAnimate: 'lib/angular-animate.min',
        angularTouch: 'lib/angular-touch.min',
        angularBoostrap: 'lib/ui-bootstrap-tpls.min',
        csrfInterceptor: 'lib/spring-security-csrf-token-interceptor.min',
        lodash: "lib/lodash.min",
        checklistModel: "lib/checklist-model",
        trainingApp: "custom-training-app",
        userService: "datosPersonales/service/userService",
        userProfileService: "datosPersonales/service/userProfileService",
        disciplineService: "configuration/service/disciplineService",
        modalityService: "configuration/service/modalityService",
        objectiveService: "configuration/service/objectiveService",
        sportEquipmentService: "configuration/service/sportEquipmentService",
        sportService: "configuration/service/sportService",
        authService: "login/service/authService",
        mainController: "login/controller/mainController",
        dashboardService: "dashboard/service/dashboardService",
        visibleFieldsUserService: "datosPersonales/service/visibleFieldsUserService",
        app: "routeResolver",
		surveyService: "questionnaire/service/surveyService"
    },
    shim: {
        angular: {
            exports: "angular"
        },
        angularRoute: {
            exports: 'ngRoute', 
            deps: ['angular']
        },
        angularAnimate:{
             exports: 'ngAnimate', 
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
        checklistModel:{
           deps: ['angular'] 
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            exports: 'ngMessages', 
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
        authService: {
            deps: ['angular', 'trainingApp']
        },
        mainController: {
            deps: ['angular', 'authService']
        },
        dashboardService: {
            deps: ['angular', 'trainingApp']
        },
        visibleFieldsUserService: {
            deps: ['angular','trainingApp']
        },
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute', 'angularBoostrap','checklistModel','angularAnimate']
        },
		surveyService: {
            deps: ['angular', 'trainingApp']
        },
        app: {
            deps: ['trainingApp', 'userService', 'disciplineService', 
                'modalityService', 'objectiveService', 'sportEquipmentService', 
                'sportService', 'userProfileService', 'authService', 'mainController', 'surveyService','dashboardService','visibleFieldsUserService']
        }
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

});