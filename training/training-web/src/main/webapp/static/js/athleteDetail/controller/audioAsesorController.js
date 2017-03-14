trainingApp.controller('AudioAsesorController', ['$scope', 'AthleteService', 'DashboardService', '$window', '$location', '$mdDialog', '$routeParams',
    function ($scope, AthleteService, DashboardService, $window, $location, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        
        
    }]);