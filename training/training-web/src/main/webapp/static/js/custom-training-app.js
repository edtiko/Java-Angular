'use strict';

//var App = angular.module('myApp',[]);

// create the module and name it trainingApp
var trainingApp = angular.module('trainingApp', ['routeResolverServices', 'ngRoute',
    'ngMessages', 'ngMaterial', 'ngDialog', 'pascalprecht.translate','ngMaterial', 'md.data.table'])
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

                    .when('/register-user', route.resolve('registerUser', 'security/'))
                    
                    .when('/message', route.resolve('message', 'message/'))

                    .when('/calendar', route.resolve('calendar', 'calendar/'))

                    .when('/create-module', route.resolve('module', 'security/'))
            
                    .when('/create-option', route.resolve('option', 'security/'))
            
                    .when('/create-role', route.resolve('role', 'security/'))

                    // route for the contact page
                    .when('/contact', {
                        templateUrl: 'static/views/contact.html',
                        controller: 'contactController'
                    });

            $translateProvider.useStaticFilesLoader({
                prefix: 'static/languages/',
                suffix: '.json'
            });

            $translateProvider.preferredLanguage('es');



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
