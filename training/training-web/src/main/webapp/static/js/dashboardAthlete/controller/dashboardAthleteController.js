'use strict';

trainingApp.controller('DashboardAthleteController', ['$scope', 'ActivityService', '$window',
    function ($scope, ActivityService, $window) {


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

        if ($scope.userSession == null) {
            $scope.$on('userSession', function (event, args) {
                $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
                $scope.getVisibleFieldsUserByUser($scope.userSession);
                $scope.getActivitiesByWeek();
            });
        } else {
            $scope.getVisibleFieldsUserByUser($scope.userSession);
            $scope.getActivitiesByWeek();
        }


    }]);