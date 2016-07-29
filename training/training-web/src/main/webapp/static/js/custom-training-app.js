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

trainingApp.controller('SurveyController', ['$scope', 'SurveyService', function ($scope, SurveyService) {

        var self = this;
        $scope.maxRow = 5;
        $scope.survey = {};
        self.sePaginator = {initialRow: "1", maxRow: "10"};

        self.getAllQuestionnaireQuestion = function (paginator) {
            SurveyService.getAllQuestionnaireQuestion(paginator).then(
                    function (response) {
                        angular.forEach(response.data.entity.output, function (value, key) {
                            $scope.survey[key] = value;
                        });
                        console.log($scope.survey);
                    },
                    function (errResponse) {
                        console.error('Error while fetching Currencies');
                    }
            );
        };
        self.getAllQuestionnaireQuestion(self.sePaginator);
    }]);


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
