'use strict';

trainingApp.controller('DashboardAthleteController', ['$scope', 'UserService', 'DashboardService','ActivityService', '$window','$location',
    function ($scope, UserService, DashboardService, ActivityService, $window, $location) {


        $scope.weekActivities = [];
        
        $scope.getActivitiesByWeek = function () {
            ActivityService.getActivitiesByWeek($scope.userSession.userId).then(
                    function (data) {
                        $scope.weekActivities = data;
                    },
                    function (error) {
                        console.log(error);
                    }
            );

        };

        $scope.$on('userSession', function (event, args) {
            $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
            $scope.getVisibleFieldsUserByUser($scope.userSession);
            $scope.getActivitiesByWeek();
        });


    }]);