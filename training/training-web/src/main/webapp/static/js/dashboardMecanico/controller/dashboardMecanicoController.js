'use strict';

trainingApp.controller('DashboardMecanicoController', ['$scope','MecanicoService', '$window',
    function ($scope, MecanicoService, $window) {

        var self = this;
        $scope.profileImage = "static/img/profile-default.png";
        $scope.profileImageStar = "static/img/profile-default.png";
        $scope.profileImageCoach = "static/img/profile-default.png";
        $scope.assignedAthletesList = [];
        $scope.countPlanList = [];
        $scope.count = 0;
        $scope.query = {
            filter: '',
            order: "concat(m.userTrainingId.name, m.userTrainingId.secondName, m.userTrainingId.lastName)",
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
            $scope.promise = MecanicoService.getAssignedAthletesPaginate($scope.query, $scope.userSession.userId, function (response) {
                $scope.assignedAthletesList = success(response);
                if ($scope.assignedAthletesList.length > 0) {
                    $scope.count = $scope.assignedAthletesList[0].count;
                }
            }).$promise;
        };


        if ($scope.userSession == null) {
            $scope.$on('userSession', function (event, args) {
                $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
                self.getAssignedAthletesPaginate();
            });
        } else {
            self.getAssignedAthletesPaginate();
        }

    }]);