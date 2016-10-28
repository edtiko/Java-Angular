'use strict';

//var App = angular.module('myApp',[]);

// create the module and name it trainingApp
var trainingApp = angular.module('trainingApp', ['routeResolverServices', 'ngRoute',
    'ngMessages', 'ngMaterial', 'pascalprecht.translate', 'angular-notification-icons', 'md.data.table', 'ngSanitize', 'luegg.directives', 'angular.filter', 'ngCamRecorder', 'angularAudioRecorder'])
        .config(function ($routeProvider, routeResolverProvider, $controllerProvider, $provide,
                $translateProvider) {

            var route = routeResolverProvider.route;

            trainingApp.register = {
                controller: $controllerProvider.register,
                service: $provide.service
            };

            trainingApp.controller = function (name, constructor) {
                $controllerProvider.register(name, constructor);
            };


            $routeProvider

                    .when('/dashboard', route.resolve('dashboard', 'dashboard/'))

                    .when('/data-person', route.resolve('user', 'datosPersonales/'))

                    .when('/encuesta', route.resolve('survey', 'questionnaire/'))

                    .when('/create-user', route.resolve('registerUser', 'security/'))

                    .when('/message', route.resolve('message', 'message/'))

                    .when('/calendar', route.resolve('calendar', 'calendar/'))

                    .when('/create-module', route.resolve('module', 'security/'))

                    .when('/create-option', route.resolve('option', 'security/'))

                    .when('/create-role', route.resolve('role', 'security/'))

                    .when('/create-plan', route.resolve('trainingPlan', 'configuration/'))

                    .when('/create-discipline', route.resolve('discipline', 'configuration/'))

                    .when('/create-objective', route.resolve('objective', 'configuration/'))

                    .when('/create-starTeam', route.resolve('starTeam', 'configuration/'))

                    .when('/create-activity', route.resolve('activity', 'configuration/'))

                    .when('/create-modality', route.resolve('modality', 'configuration/'))

                    .when('/video', route.resolve('video', 'video/'))

                    .when('/create-characteristic', route.resolve('characteristic', 'configuration/'))

                    .when('/create-bikeType', route.resolve('bikeType', 'configuration/'))

                    .when('/create-brand', route.resolve('brand', 'configuration/'))

                    .when('/mail', route.resolve('mail', 'mail/'))

                    .when('/reports', route.resolve('reports', 'reports/'))

                    .when('/external-coach', route.resolve('externalCoach', 'externalCoach/'))

                    .when('/audio-messages', route.resolve('audioMessage', 'audioMessage/'))

                    .when('/script', route.resolve('script', 'script/'))

                    .when('/informe', route.resolve('informe', 'informe/'));
            $translateProvider.useStaticFilesLoader({
                prefix: 'static/languages/',
                suffix: '.json'
            });

            $translateProvider.preferredLanguage('es');

$translateProvider.useSanitizeValueStrategy('sanitize');

        });

trainingApp.directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);

function putObject(path, object, value) {
    var modelPath = path.split(".");

    function fill(object, elements, depth, value) {
        var hasNext = ((depth + 1) < elements.length);
        if (depth < elements.length && hasNext) {
            if (!object.hasOwnProperty(modelPath[depth])) {
                object[modelPath[depth]] = {};
            }
            fill(object[modelPath[depth]], elements, ++depth, value);
        } else {
            object[modelPath[depth]] = value;
        }
    }
    fill(object, modelPath, 0, value);
}

trainingApp.directive('jqdatepicker', function ($parse) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModelCtrl) {
              var ngModel = $parse(attrs.ngModel);
            element.datepicker({
                dateFormat: 'dd/mm/yy',
                showOn: "both",
                buttonImage: "static/img/calendar.gif",
                buttonImageOnly: true,
                changeMonth: true,
                changeYear: true,
                yearRange: '-90:-18',
                onSelect: function (date) {
                 //scope.date = date;
                    scope.showAge(date);
                    scope.$apply(function(scope){
                        // Change binded variable
                        ngModel.assign(scope, date);
                    });
                }
            });
        }
    };
});
/**
 * 
 * @param {type} element
 * @returns 
 */
function compileAngularElement(element) {
    var $div = $(element);
    // The parent of the new element
    var $target = $("[ng-view]");
    angular.element($target).injector().invoke(['$compile', function ($compile) {
            var $scope = angular.element($div).scope();
            $compile($div)($scope);
            // Finally, refresh the watch expressions in the new element
            $scope.$apply();
        }]);
}

trainingApp.config(function ($mdAriaProvider) {
    // Globally disables all ARIA warnings.
    $mdAriaProvider.disableWarnings();
});
