'use strict';
 
//var App = angular.module('myApp',[]);
 
// create the module and name it trainingApp
var trainingApp = angular.module('trainingApp', ['routeResolverServices', 'ngRoute', 'ui.bootstrap'])
        .config(function ($routeProvider, routeResolverProvider, $controllerProvider, $provide) {
 
            var route = routeResolverProvider.route;
 
            trainingApp.register = {
                controller: $controllerProvider.register,
                service: $provide.service
            };
 
            trainingApp.controller = function (name, constructor) {
                $controllerProvider.register(name, constructor);
            };
 
 
            $routeProvider
 
                    .when('/profile', route.resolve('user-profile', 'perfil/'))
 
                    .when('/data-person', route.resolve('user', 'datosPersonales/'))
            
                    .when('/encuesta', route.resolve('survey', 'questionnaire/'))
 
                    // route for the about page
                    .when('/about', {
                        templateUrl: 'static/views/about.html',
                        controller: 'aboutController'
                    })
 
                    .when('/calendar', route.resolve('calendar', 'calendar/'))
 
                    // route for the contact page
                    .when('/contact', {
                        templateUrl: 'static/views/contact.html',
                        controller: 'contactController'
                    });
 
        });
 
// create the controller and inject Angular's $scope
trainingApp.controller('mainController', function ($scope) { 
    $scope.clear = function () {
        $scope.dt = null;
    };
 
    $scope.dateOptions = {
        //dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        //minDate: new Date(),
        startingDay: 1
    };
 
    // Disable weekend selection
    function disabled(data) {
        var date = data.date,
                mode = data.mode;
        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }
 
    $scope.open = function () {
        $scope.popup.opened = true;
    };
 
    $scope.setDate = function (year, month, day) {
        $scope.dt = new Date(year, month, day);
    };
 
    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = 'dd/MM/yyyy';
    $scope.altInputFormats = ['M!/d!/yyyy'];
 
    $scope.popup = {
        opened: false
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
