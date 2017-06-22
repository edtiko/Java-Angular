///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////
//var $contextPath = "https://181.143.227.220:8543/training/";
var $contextPath = window.location.origin + "/training/";
//var $contextPath = "http://181.143.227.220:8086/training/";
//var $contextPath = "http://181.143.227.220:8087/training/";
$wordPressContextPath = 'http://181.143.227.220:4321/cpt/';
$wordPressContextPathHttps = 'https://181.143.227.220:444/cpt/';
$urlCompraRunning = $wordPressContextPath+'estrellas-del-running/'; 
$urlCompraCiclismo = $wordPressContextPath+'estrellas-del-ciclismo/'; 
$urlCompraTriatlon = $wordPressContextPath+'estrellas-del-triatlon/';
$urlCompraFitting = $wordPressContextPath+'atletas/';

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
        angularAudioRecorder: "lib/angular-audio-recorder",
        wavesurfer: "lib/wavesurfer.min",
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
        marketingReportService: "report/service/marketingReportService",
        paymentReportService: "report/service/paymentReportService",
        saleReportService: "report/service/saleReportService",
        accountService: "account/service/accountService",
        sharedProperties: "dashboard/service/sharedProperties",
        mainController: "login/controller/mainController",
        dashboardChartController: "chart/controller/dashboardChartController",
        calendarAsesorController: "athleteDetail/controller/calendarAsesorController",
        chartAsesorController: "athleteDetail/controller/chartAsesorController",
        messageAsesorController: "athleteDetail/controller/messageAsesorController",
        mailAsesorController: "athleteDetail/controller/mailAsesorController",
        audioAsesorController: "athleteDetail/controller/audioAsesorController",
        videoAsesorController: "athleteDetail/controller/videoAsesorController",
        calendarCoachController: "athleteCoachDetail/controller/calendarCoachController",
        messageCoachController: "athleteCoachDetail/controller/messageCoachController",
        mailCoachController: "athleteCoachDetail/controller/mailCoachController",
        audioCoachController: "athleteCoachDetail/controller/audioCoachController",
        videoCoachController: "athleteCoachDetail/controller/videoCoachController",
        dashboardService: "dashboard/service/dashboardService",
        visibleFieldsUserService: "datosPersonales/service/visibleFieldsUserService",
        athleteService: "athletes/service/athleteService",
        asesorService: "asesores/service/asesorService",
        starAthleteController: "asesorDetail/controller/starAthleteController",
        mailStarController: "asesorDetail/controller/mailStarController",
        messageStarController: "asesorDetail/controller/messageStarController",
        starAthleteDetailController: "asesorDetail/controller/athleteDetail/starAthleteDetailController",
        messageStarAthleteController: "asesorDetail/controller/athleteDetail/messageStarAthleteController",
        mailStarAthleteController: "asesorDetail/controller/athleteDetail/mailStarAthleteController",
        audioStarAthleteController: "asesorDetail/controller/athleteDetail/audioStarAthleteController",
        videoStarAthleteController: "asesorDetail/controller/athleteDetail/videoStarAthleteController",
        app: "routeResolver",
        sockjs: "lib/sockjs.min",
        moment: "lib/moment.min",
        jqueryui: "lib/jquery-ui.min",
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
        userFittingService: "userFitting/service/userFittingService",
        mecanicoService:  "dashboardMecanico/service/MecanicoService",
        angularSanitize: 'lib/angular-sanitize',
        dcfService: "configuration/service/dcfService",
        characteristicService: "configuration/service/characteristicService",
        brandService: "configuration/service/brandService",
        angularPickList: 'lib/picklist',
        mailService: "mail/service/mailService",
        supervStarCoachService: "mail/service/supervStarCoachService",
        userActivityPerformanceService: "datosPersonales/service/userActivityPerformanceService",
        externalCoachService: "externalCoach/service/externalCoachService",
        audioMessageService: "audioMessage/service/audioMessageService",
        adapter: "lib/adapter",
        scriptService: "script/service/scriptService",
        informeService: "informe/service/informeService",
        configurationPlanService: "configuration/service/configurationPlanService",
        membershipService: "configuration/service/membershipService",
        multiStepForm: "lib/angular-multi-step-form",
        loader: "lib/loader",
        agGrid: "lib/ag-grid.min",
        angularCamera: "lib/angular-camera"
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
        angularAudioRecorder: {
            exports: 'angularAudioRecorder',
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
        accountService: {
            deps: ['angular', 'trainingApp']
        },
        authService: {
            deps: ['angular', 'trainingApp']
        },
        calendarService: {
            deps: ['angular', 'trainingApp']
        },
        userFittingService:{
             deps: ['angular', 'trainingApp']
        },
        mecanicoService: {
             deps: ['angular', 'trainingApp']
        },
        sharedProperties: {
            deps: ['angular', 'trainingApp']
        },
        mainController: {
            deps: ['angular', 'trainingApp']
        },
        dashboardChartController: {
            deps: ['angular', 'trainingApp']
        },
        calendarAsesorController: {
            deps: ['angular', 'trainingApp']
        },
        chartAsesorController: {
            deps: ['angular', 'trainingApp']
        },
        messageAsesorController: {
            deps: ['angular', 'trainingApp']
        },
        mailAsesorController: {
            deps: ['angular', 'trainingApp']
        },
        audioAsesorController: {
            deps: ['angular', 'trainingApp']
        },
        videoAsesorController: {
            deps: ['angular', 'trainingApp']
        },
        calendarCoachController: {
            deps: ['angular', 'trainingApp']
        },
        messageCoachController: {
            deps: ['angular', 'trainingApp']
        },
        mailCoachController: {
            deps: ['angular', 'trainingApp']
        },
        audioCoachController: {
            deps: ['angular', 'trainingApp']
        },
        videoCoachController: {
            deps: ['angular', 'trainingApp']
        },
        starAthleteController: {
            deps: ['angular', 'trainingApp']
        },
        mailStarController: {
            deps: ['angular', 'trainingApp']
        },
        messageStarController: {
            deps: ['angular', 'trainingApp']
        },
        starAthleteDetailController: {
            deps: ['angular', 'trainingApp']
        },
        messageStarAthleteController: {
            deps: ['angular', 'trainingApp']
        },
        mailStarAthleteController: {
            deps: ['angular', 'trainingApp']
        },
        audioStarAthleteController: {
            deps: ['angular', 'trainingApp']
        },
        videoStarAthleteController: {
            deps: ['angular', 'trainingApp']
        },
        dashboardService: {
            deps: ['angular', 'trainingApp']
        },
        visibleFieldsUserService: {
            deps: ['angular', 'trainingApp']
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
        multiStepForm: {
            exports: 'multiStepForm',
            deps: ['angular']
        },
        agGrid:{
            exports: 'agGrid',
            deps: ['angular']
        },
         angularCamera:{
            deps: ['angular']
        },
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute', 'angularAnimate', 'angularAria', 'angularMaterial',
                'sockjs', 'stompWebsocket', 'angularTranslate', 'angularDataTable', 'angularNotification', 'angularSanitize',
                'scrollGlue', 'angularFilter', 'ngCamRecorder', 'recorder', 'whammy', 'viRecorder', 'angularPickList', 'angularAudioRecorder',
                'wavesurfer', 'adapter', 'moment', 'jqueryui', 'multiStepForm', 'loader', 'accordion','angularCamera'

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
            deps: ['angular', 'trainingApp']
        },
        optionService: {
            deps: ['angular', 'trainingApp']
        },
        moduleService: {
            deps: ['angular', 'trainingApp']
        },
        bikeTypeService: {
            deps: ['angular', 'trainingApp']
        },
        planService: {
            deps: ['angular', 'trainingApp']
        },
        starTeamService: {
            deps: ['angular', 'trainingApp']
        },
        physiologicalCapacityService: {
            deps: ['angular', 'trainingApp']
        },
        activityService: {
            deps: ['angular', 'trainingApp']
        },
        dcfService: {
            deps: ['angular', 'trainingApp']
        },
        characteristicService: {
            deps: ['angular', 'trainingApp']
        },
        brandService: {
            deps: ['angular', 'trainingApp']
        },
        mailService: {
            deps: ['angular', 'trainingApp']
        },
        supervStarCoachService: {
            deps: ['angular', 'trainingApp']
        },
        userActivityPerformanceService: {
            deps: ['angular', 'trainingApp']
        },
        externalCoachService: {
            deps: ['angular', 'trainingApp']
        },
        audioMessageService: {
            deps: ['angular', 'trainingApp']
        },
        scriptService: {
            deps: ['angular', 'trainingApp']
        },
        informeService: {
            deps: ['angular', 'trainingApp']
        },
        configurationPlanService: {
            deps: ['angular', 'trainingApp']
        },
        membershipService: {
            deps: ['angular', 'trainingApp']
        },
        marketingReportService: {
            deps: ['angular', 'trainingApp']
        },
        paymentReportService: {
            deps: ['angular', 'trainingApp']
        },
        saleReportService: {
            deps: ['angular', 'trainingApp']
        },
        athleteService: {
            deps: ['angular', 'trainingApp']
        },
        asesorService: {
            deps: ['angular', 'trainingApp']
        },
        app: {
            deps: ['trainingApp', 'userService', 'disciplineService',
                'modalityService', 'objectiveService', 'sportEquipmentService',
                'sportService', 'userProfileService', 'authService',
                'mainController', 'surveyService', 'calendarService',
                'visibleFieldsUserService', 'utilService', 'dashboardService',
                'roleService', 'messageService', 'bikeTypeService',
                'optionService', 'angularTranslateConfig', 'moduleService', 'planService',
                'starTeamService', 'physiologicalCapacityService', 'activityService',
                'videoService', 'dcfService', 'characteristicService', 'brandService',
                'mailService', 'supervStarCoachService', 'userActivityPerformanceService',
                'externalCoachService', 'audioMessageService', 'scriptService', 'informeService',
                'dashboardChartController', 'configurationPlanService', 'membershipService',
                'marketingReportService', 'paymentReportService', 'saleReportService', 'accountService',
                'athleteService', 'calendarAsesorController', 'chartAsesorController', 'messageAsesorController',
                'mailAsesorController', 'audioAsesorController', 'videoAsesorController', 'asesorService', 'starAthleteController',
                'mailStarController', 'messageStarController', 'starAthleteDetailController','messageStarAthleteController',
                'mailStarAthleteController','audioStarAthleteController','videoStarAthleteController', 'calendarCoachController', 'messageCoachController',
                'mailCoachController', 'audioCoachController', 'videoCoachController','userFittingService','mecanicoService'

            ]}
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

    $("#trainingApp").removeClass("preloader");

});