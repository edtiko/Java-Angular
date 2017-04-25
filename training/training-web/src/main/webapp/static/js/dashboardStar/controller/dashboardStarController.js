'use strict';

trainingApp.controller('DashboardStarController', ['$scope', 'UserService', 'DashboardService', '$window',
    function ($scope, UserService, DashboardService, $window) {

        var self = this;
        $scope.profileImage = "static/img/profile-default.png";
        $scope.profileImageStar = "static/img/profile-default.png";
        $scope.profileImageCoach = "static/img/profile-default.png";
        $scope.assignedAthletesList = [];
        $scope.countPlanList = [];
        $scope.count = 0;
        $scope.query = {
            filter: '',
            order: "concat(m.trainingPlanUserId.userId.name, m.trainingPlanUserId.userId.secondName,m.trainingPlanUserId.userId.lastName)",
            limit: 10,
            page: 1
        };

        function success(response) {
            if (response.data.status == 'fail') {
                $scope.showMessage(response.message);
            } else {
                return response.data.output;
            }

            return null;
        }

        //Obtener atletas asignados 
        self.getAssignedAthletesPaginate = function () {
            $scope.promise = DashboardService.getAssignedAthletesPaginate($scope.query, $scope.userSession.userId, $scope.userSessionTypeUserCoachEstrella, function (response) {
                $scope.assignedAthletesList = success(response);
                if ($scope.assignedAthletesList.length > 0) {
                    $scope.count = $scope.assignedAthletesList[0].count;
                }
            }).$promise;
        };

        self.getCountByPlanRole = function () {
            DashboardService.getCountByPlanRole($scope.userSession.userId, $scope.userSessionTypeUserCoachEstrella, function (response) {
                $scope.countPlanList = success(response);
            });
        };

        if ($scope.userSession == null) {
            $scope.$on('userSession', function (event, args) {
                $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
                self.getCountByPlanRole();
                self.getAssignedAthletesPaginate();
            });
        } else {
            self.getCountByPlanRole();
            self.getAssignedAthletesPaginate();
        }

    }]);