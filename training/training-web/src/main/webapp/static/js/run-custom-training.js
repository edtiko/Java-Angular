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
        chatService: "chat/service/chatService",
        videoService: "video/service/videoService",
        videochatService: "videochat/service/videochatService",
        opentok: "lib/opentok.min",
        opentokAngular: "lib/opentok-angular",
        opentokLayout: "lib/opentok-layout.min",
        utilService: "lib/utilService",
        ngDialog: "lib/ngDialog.min",
        roleService: "security/service/roleService"
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
        ngDialog:{
          exports: 'ngDialog',
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
        opentokAngular: {
            exports: 'opentok',
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
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute',
                'angularBoostrap', 'angularAnimate', 'ngDialog'
//                'sockjs', 
//                'stompWebsocket','ngCamRecorder','recorder',
//                'whammy','viRecorder','opentok','opentokAngular','opentokLayout'
            ]
        },
        surveyService: {
            deps: ['angular', 'trainingApp']
        },
        chatService: {
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
        app: {
            deps: ['trainingApp', 'userService', 'disciplineService',
                'modalityService', 'objectiveService', 'sportEquipmentService',
                'sportService', 'userProfileService', 'authService',
                'mainController', 'surveyService', 'calendarService',
                ,'visibleFieldsUserService','utilService', 'dashboardService','roleService'
//                'chatService','videoService','videochatService'
            ] }
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

});