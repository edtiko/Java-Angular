///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////
//var $contextPath = "https://181.143.227.220:8543/training/";
var $contextPath = window.location.origin+"/training/";
//var $contextPath = "http://181.143.227.220:8086/training/";
//var $contextPath = "http://181.143.227.220:8087/training/";
$wordPressContextPath = 'http://181.143.227.220:8081/cpt/';
require.config({
    waitSeconds: 200,
    paths: {
        
        angular: 'lib/angular.min',
        angularMessages: 'lib/angular-messages.min',
        angularRoute: 'lib/angular-route.min',
        angularAnimate: 'lib/angular-animate.min',
        angularTouch: 'lib/angular-touch.min',
        angularAria: 'lib/angular-aria.min',
        angularMaterial: 'lib/angular-material',
        angularDataTable: 'lib/md-data-table.min',
        angularTranslate: 'lib/angular-translate.min',
        angularTranslateConfig: 'lib/angular-translate-loader-static-files.min',
        angularNotification: 'lib/angular-notification-icons.min',
        csrfInterceptor: 'lib/spring-security-csrf-token-interceptor.min',
        lodash: "lib/lodash.min",
        scrollGlue: 'lib/scrollglue',
        angularFilter: 'lib/angular-filter',
        accordion: 'lib/ang-accordion',
        trainingApp: "custom-training-app",
        userService: "datosPersonales/service/userService",
        userProfileService: "datosPersonales/service/userProfileService",
        disciplineService: "configuration/service/disciplineService",
        modalityService: "configuration/service/modalityService",
        objectiveService: "configuration/service/objectiveService",
        sportEquipmentService: "configuration/service/sportEquipmentService",
        sportService: "configuration/service/sportService",
        calendarService: "calendar/service/calendarService",
        authService: "login/service/authService",
        surveyService: "questionnaire/service/surveyService",
        mainController: "login/controller/mainController",
        dashboardService: "dashboard/service/dashboardService",
        visibleFieldsUserService: "datosPersonales/service/visibleFieldsUserService",
        app: "routeResolver",
        sockjs: "lib/sockjs.min",
        stompWebsocket: "lib/stomp.min",
        ngCamRecorder: "lib/vaRecorder/ngCamRecorder",
        recorder: "lib/vaRecorder/recorder",
        whammy: "lib/vaRecorder/whammy",
        viRecorder: "lib/vaRecorder/VIRecorder",
        messageService: "message/service/messageService",
        videoService: "video/service/videoService",
        utilService: "lib/utilService",
        roleService: "security/service/roleService",
        optionService: "security/service/optionService",
        moduleService: "security/service/moduleService",
        bikeTypeService: "configuration/service/bikeTypeService",
        planService: "configuration/service/trainingPlanService",
        starTeamService: "configuration/service/starTeamService",
        physiologicalCapacityService: "configuration/service/physiologicalCapacityService",
        activityService: "configuration/service/activityService",
        angularSanitize: 'lib/angular-sanitize',
        dcfService:"configuration/service/dcfService",
        characteristicService:"configuration/service/characteristicService",
	brandService:"configuration/service/brandService",
        angularPickList: 'lib/picklist',        
        mailService: "mail/service/mailService",
        supervStarCoachService: "mail/service/supervStarCoachService",
        userActivityPerformanceService: "datosPersonales/service/userActivityPerformanceService",
        adapter: "lib/adapter",
        scriptService:"script/service/scriptService",
        informeService:"informe/service/informeService"
    },
    shim: {
        angular: {
            exports: "angular"
        },
        angularRoute: {
            exports: 'ngRoute',
            deps: ['angular']
        },
        angularAnimate: {
            exports: 'ngAnimate',
            deps: ['angular']
        },
        angularTouch: {
            exports: 'angularTouch',
            deps: ['angular']
        },
        angularMessages: {
            exports: 'ngMessages',
            deps: ['angular']
        },
        angularAria: {
            exports: 'ngAria',
            deps: ['angular']
        },
        scrollGlue: {
            deps: ['angular']
        },
        angularFilter: {
            deps: ['angular']
        },
        accordion: {
            exports: 'angAccordion',
            deps: ['angular']
        },
        angularMaterial: {
            exports: 'ngMaterial',
            deps: ['angular']
        },
        angularDataTable: {
            deps: ['angular']
        },
        angularTranslate: {
            deps: ['angular']
        },
        ngCamRecorder: {
            exports: 'ngCamRecorder',
            deps: ['angular']
        },
        angularBoostrap: {
            exports: 'angularBoostrap',
            deps: ['angular']
        },
        csrfInterceptor: {
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
        calendarService: {
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
        angularTranslateConfig: {
            deps: ['angular', 'angularTranslate']
        },
        angularSanitize: {
            deps: ['angular']
        },
        angularPickList: {
            deps: ['angular']
        },
        adapter: {
            deps: ['angular']
        },
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute', 'angularAnimate','angularAria','angularMaterial',
                'sockjs', 'stompWebsocket', 'angularTranslate', 'angularDataTable', 'angularNotification','angularSanitize',
                'scrollGlue','angularFilter','ngCamRecorder','recorder', 'whammy','viRecorder',
                'angularPickList', 'adapter'
            ]
        },
        surveyService: {
            deps: ['angular', 'trainingApp']
        },
        messageService: {
            deps: ['angular', 'trainingApp']
        },
        videoService: {
            deps: ['angular', 'trainingApp']
        },
        videochatService: {
            deps: ['angular', 'trainingApp']
        },
        utilService: {
            deps: ['angular', 'trainingApp']
        },
        roleService: {
            deps: ['angular','trainingApp']
        },
        optionService: {
            deps: ['angular','trainingApp']
        },
        moduleService: {
            deps: ['angular','trainingApp']
        },
        bikeTypeService: {
            deps: ['angular','trainingApp']
        },
        planService: {
            deps: ['angular','trainingApp']
        },
        starTeamService: {
            deps: ['angular','trainingApp']
        },
        physiologicalCapacityService: {
            deps: ['angular','trainingApp']
        },
        activityService: {
            deps: ['angular','trainingApp']
        },
        dcfService: {
            deps: ['angular','trainingApp']
        },
        characteristicService: {
            deps: ['angular','trainingApp']
        },
		brandService: {
            deps: ['angular','trainingApp']
        },
        mailService: {
            deps: ['angular','trainingApp']
        },
        supervStarCoachService: {
            deps: ['angular','trainingApp']
        },
        userActivityPerformanceService: {
            deps: ['angular','trainingApp']
        },
        scriptService : {
            deps: ['angular', 'trainingApp']
        },
        informeService : {
            deps: ['angular', 'trainingApp']
        },
        app: {
            deps: ['trainingApp', 'userService', 'disciplineService',
                'modalityService', 'objectiveService', 'sportEquipmentService',
                'sportService', 'userProfileService', 'authService',
                'mainController', 'surveyService', 'calendarService',
                'visibleFieldsUserService','utilService', 'dashboardService',
                'roleService','messageService','bikeTypeService',
                'optionService', 'angularTranslateConfig', 'moduleService', 'planService', 'starTeamService',
                'physiologicalCapacityService','activityService'
                ,'videoService','dcfService', 'characteristicService','brandService',
		'mailService','supervStarCoachService','userActivityPerformanceService', 'scriptService', 'informeService'

            ] }
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

});