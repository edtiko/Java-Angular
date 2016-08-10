///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////
var $contextPath = "http://localhost:8080/training/";
//var $contextPath = "http://181.143.227.220:8086/training/";
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
        checklistModel: "lib/checklist-model",
        trainingApp: "custom-training-app",
        userService: "datosPersonales/service/userService",
        disciplineService: "perfil/service/disciplineService",
        modalityService: "perfil/service/modalityService",
        objectiveService: "perfil/service/objectiveService",
        sportEquipmentService: "perfil/service/sportEquipmentService",
        sportService: "perfil/service/sportService",
        userProfileService: "perfil/service/userProfileService",
        authService: "login/service/authService",
        mainController: "login/controller/mainController",
        app: "routeResolver",
        sockjs: "lib/sockjs.min",
        stompWebsocket: "lib/stomp.min",
        ngCamRecorder: "lib/vaRecorder/ngCamRecorder",
        recorder: "lib/vaRecorder/recorder",
        whammy: "lib/vaRecorder/whammy",
        viRecorder: "lib/vaRecorder/VIRecorder",
        surveyService: "questionnaire/service/surveyService",
        chatService: "chat/service/chatService",
        videoService: "video/service/videoService",
        videochatService: "videochat/service/videochatService",
        opentok: "lib/opentok.min",
        opentokAngular: "lib/opentok-angular",
        opentokLayout: "lib/opentok-layout.min"
    },
    shim: {
        angular: {
            exports: "angular"
        },
        angularRoute: {
            exports: 'ngRoute',
            deps: ['angular']
        },
        ngCamRecorder: {
            exports: 'ngCamRecorder',
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
        angularBoostrap: {
            exports: 'angularBoostrap',
            deps: ['angular']
        },
        checklistModel: {
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
        mainController: {
            deps: ['angular', 'authService']
        },
        trainingApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'angularRoute', 'angularBoostrap', 'checklistModel', 'angularAnimate', 'sockjs', 'stompWebsocket','ngCamRecorder','recorder','whammy','viRecorder','opentok','opentokAngular','opentokLayout']
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
        app: {
            deps: ['trainingApp', 'userService', 'disciplineService',
                'modalityService', 'objectiveService', 'sportEquipmentService',
                'sportService', 'userProfileService', 'authService', 'mainController', 'surveyService', 'chatService','videoService','videochatService']
        }
    }
});

require(['app'], function () {

    angular.bootstrap(document.getElementById('trainingApp'), ['trainingApp']);

});