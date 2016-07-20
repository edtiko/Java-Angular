'use strict';

//var App = angular.module('myApp',[]);

// create the module and name it trainingApp
var trainingApp = angular.module('trainingApp', ['ngRoute']);

// configure our routes
trainingApp.config(function ($routeProvider) {
    $routeProvider

            // route for the home page
            .when('/data-person', {
                templateUrl: 'static/views/datosPersonales/user.html',
                controller: 'UserController'
            })

            // route for the about page
            .when('/about', {
                templateUrl: 'static/views/about.html',
                controller: 'aboutController'
            })

            // route for the contact page
            .when('/contact', {
                templateUrl: 'static/views/contact.html',
                controller: 'contactController'
            })

            .when('/profile', {
                templateUrl: 'static/views/perfil/user-profile.html',
                controller: 'UserProfileController'
            });
});

// create the controller and inject Angular's $scope
trainingApp.controller('mainController', function ($scope) {
    // create a message to display in our view
    $scope.message = 'Everyone come and see how good I look!';
});

trainingApp.controller('aboutController', function ($scope) {
    $scope.message = 'Look! I am an about page.';
});

trainingApp.controller('contactController', function ($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});