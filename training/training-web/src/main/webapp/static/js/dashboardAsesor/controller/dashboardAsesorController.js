'use strict';

trainingApp.controller('DashboardAsesorController', ['$scope', 'AthleteService', 'DashboardService', 'MessageService', 'MailService',
    'VideoService', 'AudioMessageService', '$window',
    function ($scope, AthleteService, DashboardService, MessageService, MailService,
            VideoService, AudioMessageService, $window) {

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
            $scope.promise = DashboardService.getAssignedAthletesPaginate($scope.query, $scope.userSession.userId, $scope.userSessionTypeUserCoachInterno, function (response) {
                $scope.assignedAthletesList = success(response);
                if ($scope.assignedAthletesList.length > 0) {
                    $scope.count = $scope.assignedAthletesList[0].count;
                }
            }).$promise;
        };

        self.getAssignedAthletes = function () {
            AthleteService.getAthletes($scope.userSession.userId).then(
                    function (data) {
                        data.output.forEach(function (a) {
                            MessageService.initialize(a.planId);
                            VideoService.initialize(a.planId);
                            AudioMessageService.initialize(a.planId);
                            MailService.initialize(a.planId);
                        });
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        MessageService.receive().then(null, null, function (message) {
            if ($scope.userSession.userId == message.receivingUserId.userId) {
                $scope.getUserNotification($scope.userSession.userId, -1, -1);
            }
        });

        //notificación videos recibidos
        VideoService.receive().then(null, null, function (video) {
            if (video.toUser.userId == $scope.userSession.userId) {

                $scope.getUserNotification($scope.userSession.userId, -1, -1);

            }

        });

        //notificación audios recibidos
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUserId == $scope.userSession.userId) {

                $scope.getUserNotification($scope.userSession.userId, -1, -1);

            }

        });


        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {

                $scope.getUserNotification($scope.userSession.userId, -1, -1);

            }

        });


        self.getCountByPlanAsesor = function () {
            DashboardService.getCountByPlanRole($scope.userSession.userId, $scope.userSessionTypeUserCoachInterno, function (response) {
                $scope.countPlanList = success(response);
            });
        };

        if ($scope.userSession == null) {
            $scope.$on('userSession', function (event, args) {
                $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
                self.getCountByPlanAsesor();
                self.getAssignedAthletesPaginate();
                self.getAssignedAthletes();
            });
        } else {
            self.getCountByPlanAsesor();
            self.getAssignedAthletesPaginate();
            self.getAssignedAthletes();
        }

    }]);