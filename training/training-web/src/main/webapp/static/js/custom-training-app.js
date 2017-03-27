'use strict';
var urlCompraPlanEntrenamiento = $wordPressContextPath + 'plan-de-entrenamiento-deporte/';
//var App = angular.module('myApp',[]);

// create the module and name it trainingApp
var trainingApp = angular.module('trainingApp', ['routeResolverServices', 'ngRoute',
    'ngMessages', 'ngMaterial', 'pascalprecht.translate', 'angular-notification-icons', 'md.data.table', 'ngSanitize', 'luegg.directives', 'angular.filter', 'ngCamRecorder', 'angularAudioRecorder', 'multiStepForm', 'angAccordion'])
        .config(function ($routeProvider, routeResolverProvider, $controllerProvider, $provide,
                $translateProvider, $mdDateLocaleProvider) {

            var route = routeResolverProvider.route;

            trainingApp.register = {
                controller: $controllerProvider.register,
                service: $provide.service
            };

            trainingApp.controller = function (name, constructor) {
                $controllerProvider.register(name, constructor);
            };


            $routeProvider

                    .when('/dashboard-athlete', route.resolve('dashboardAthlete', 'dashboardAthlete/'))
            
                    .when('/dashboard-asesor', route.resolve('dashboardAsesor', 'dashboardAsesor/'))

                    .when('/data-person', route.resolve('user', 'datosPersonales/'))
            
                    .when('/asesor-profile', route.resolve('asesorProfile', 'asesorProfile/'))

                    .when('/encuesta', route.resolve('survey', 'questionnaire/'))

                    .when('/create-user', route.resolve('registerUser', 'security/'))

                    .when('/message', route.resolve('message', 'message/'))

                    .when('/calendar', route.resolve('calendar', 'calendar/'))

                    .when('/create-module', route.resolve('module', 'security/'))

                    .when('/create-option', route.resolve('option', 'security/'))

                    .when('/create-role', route.resolve('role', 'security/'))

                    .when('/create-plan/:typePlan', route.resolve('trainingPlan', 'configuration/'))

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

                    .when('/marketing-report', route.resolve('marketingReport', 'report/'))

                    .when('/payment-report', route.resolve('paymentReport', 'report/'))

                    .when('/sale-report', route.resolve('saleReport', 'report/'))

                    .when('/external-coach', route.resolve('externalCoach', 'externalCoach/'))

                    .when('/audio-messages', route.resolve('audioMessage', 'audioMessage/'))

                    .when('/script', route.resolve('script', 'script/'))

                    .when('/informe', route.resolve('informe', 'informe/'))

                    .when('/chart', route.resolve('chart', 'chart/'))

                    .when('/progress', route.resolve('progress', 'progress/'))

                    .when('/account', route.resolve('account', 'account/'))

                    .when('/suscription', route.resolve('suscription', 'suscription/'))

                    .when('/order', route.resolve('order', 'order/'))

                    .when('/pay-method', route.resolve('payMethod', 'payMethod/'))

                    .when('/create-pay-method', route.resolve('createPayMethod', 'createPayMethod/'))

                    .when('/address', route.resolve('address', 'address/'))
            
                    .when('/edit-address', route.resolve('editAddress', 'editAddress/'))
            
                    .when('/edit-account', route.resolve('editAccount', 'editAccount/'))

                    .when('/create-configuration-plan/:typePlan', route.resolve('configurationPlan', 'configuration/'))
                    
                    .when('/athletes', route.resolve('athletes','athletes/'))
            
                    .when('/athlete-detail/:user', route.resolve('athleteDetail', 'athleteDetail/'));
            
            $translateProvider.useStaticFilesLoader({
                prefix: 'static/languages/',
                suffix: '.json'
            });

            $translateProvider.preferredLanguage('es');

            $translateProvider.useSanitizeValueStrategy('sanitizeParameters');

            $mdDateLocaleProvider.months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
            $mdDateLocaleProvider.shortMonths = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];
            $mdDateLocaleProvider.days = ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'];
            $mdDateLocaleProvider.shortDays = ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'];
            // Can change week display to start on Monday.
            $mdDateLocaleProvider.firstDayOfWeek = 1;
            // Optional.
            //$mdDateLocaleProvider.dates = [1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15,16,17,18,19,
            //                               20,21,22,23,24,25,26,27,28,29,30,31];
            // In addition to date display, date components also need localized messages
            // for aria-labels for screen-reader users.
            $mdDateLocaleProvider.weekNumberFormatter = function (weekNumber) {
                return 'Semana ' + weekNumber;
            };
            $mdDateLocaleProvider.msgCalendar = 'Calendario';
            $mdDateLocaleProvider.msgOpenCalendar = 'Abrir calendario';
            $mdDateLocaleProvider.formatDate = function (date) {
                if (date !== "") {
                    var day = date.getDate();
                    var monthIndex = date.getMonth();
                    var year = date.getFullYear();

                    return day + '/' + (monthIndex + 1) + '/' + year;
                } else {
                    return null;
                }

            };

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
                onSelect: function (date) {
                    scope.$apply(function (scope) {
                        // Change binded variable
                        ngModel.assign(scope, date);
                    });
                }
            });
        }
    };

});

trainingApp.directive('jqdatepickerbirthday', function ($parse) {
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
                    scope.$apply(function (scope) {
                        // Change binded variable
                        ngModel.assign(scope, date);
                    });
                }
            });
        }
    };

});

trainingApp.directive('dynController', ['$compile', '$parse', function ($compile, $parse) {
        return {
            restrict: 'A',
            terminal: true,
            priority: 100000,
            link: function (scope, elem, attrs) {
                // Parse the scope variable
                var name = $parse(elem.attr('dyn-controller'))(scope);
                elem.removeAttr('dyn-controller');
                elem.attr('ng-controller', name);

                // Compile the element with the ng-controller attribute
                $compile(elem)(scope);
            }
        };

    }]);


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
