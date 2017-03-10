trainingApp.controller('AthletesController', ['$scope', 'AthleteService', '$window', '$location', '$mdDialog',
    function ($scope, AthleteService, $window, $location, $mdDialog) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athletes = [];

        self.getAthletes = function () {
            AthleteService.getAthletes($scope.userSession.userId).then(
                    function (data) {
                      $scope.athletes = data.output;
                    },
                    function (error) {
                     console.log(error);
                    }
            );
        };
        
        self.getAthletes();


    }]);