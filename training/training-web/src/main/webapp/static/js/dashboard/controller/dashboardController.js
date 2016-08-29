'use strict';

trainingApp.controller('DashboardController', ['$scope', 'UserService', 'DashboardService','$window',
    function ($scope, UserService, DashboardService,$window) {
        var self = this;
        $scope.user = {userId: null, name: '', secondName: '', lastName: '', email: '', sex: '', age: '',
            weight: '', phone: '', cellphone: '', federalState: '', city: '', address: '', postalCode: '',
            birthDate: '', facebookPage: '', country: '', profilePhoto: '',
            ageSport: '', ppm: '', power: '', sportsAchievements: '',
            aboutMe: '', indMetricSys: '', discipline: '', sport: '', shoes: '', bikes: '', potentiometer: '',
            modelPotentiometer: '', pulsometer: '', modelPulsometer: '', objective: '', modality: '',
            availability: '', twitterPage: '', instagramPage: '', webPage: '', vo2Running: '', vo2Ciclismo: ''
        };
        $scope.profileImage = "static/img/profile-default.png";
        $scope.profileImageStar = "static/img/profile-default.png";
        $scope.profileImageCoach = "static/img/profile-default.png";
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.coachAssignedPlanId = null;
        $scope.availableMessage = 0;
        $scope.availableCall = 0;
        $scope.availableVideo = 0;
        $scope.availableEmail = 0;
         

        $scope.getUserById = function () {

            if ($scope.appReady) {
                var user = JSON.parse(sessionStorage.getItem("userInfo"));

                if (user == null || user == undefined) {
                    $scope.setUserSession();
                }

                DashboardService.getDashboard(user).then(
                        function (d) {
                            $scope.user = d;

                            if ($scope.user.birthDate != null) {
                                var date = $scope.user.birthDate.split("/");
                                var birthdate = new Date(date[2], date[1] - 1, date[0]);
                                $scope.user.age = $scope.calculateAge(birthdate);
                            }
                            $scope.getImageProfile(user.userId);
                        },
                        function (errResponse) {
                            console.error('Error while fetching the dashboard');
                            console.error(errResponse);
                        }
                );
            } else {
                $scope.showMessage("El usuario no se encuentra logueado.", "error");
            }
        };

        $scope.getImageProfile = function (userId) {
            if (userId != null) {
                UserService.getImageProfile(userId)
                        .then(
                                function (response) {
                                    if (response != "") {
                                        $scope.profileImage = "data:image/png;base64," + response;
                                    } else {
                                        $scope.profileImage = "static/img/profile-default.png";
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Image Profile');
                                    console.error(errResponse);
                                }
                        );
            }
        };

        $scope.visibleField = function (tableName, columnName) {
            if (columnName != null) {
                for (var i = 0; i < $scope.fields.length; i++) {
                    if ($scope.fields[i].tableName == tableName && $scope.fields[i].columnName == columnName) {
                        return true;
                    }
                }
            } else {
                for (var i = 0; i < $scope.fields.length; i++) {
                    if ($scope.fields[i].tableName == tableName) {
                        return true;
                    }
                }
            }
            return false;
        };

        $scope.selectAthlete = function(coachAssignedPlanSelected){
            var user = coachAssignedPlanSelected.athleteUserId;
            $window.sessionStorage.setItem("coachAssignedPlanSelected", JSON.stringify(coachAssignedPlanSelected));
             $scope.coachAssignedPlan = angular.copy(coachAssignedPlanSelected);
             self.getAvailableMessages(coachAssignedPlanSelected.id);
             DashboardService.getDashboard(user).then(
                        function (d) {
                            $scope.user = d;

                            if ($scope.user.birthDate != null) {
                                var date = $scope.user.birthDate.split("/");
                                var birthdate = new Date(date[2], date[1] - 1, date[0]);
                                $scope.user.age = $scope.calculateAge(birthdate);
                            }
                            $scope.getVisibleFieldsUserByUser(user);
                            $scope.getImageProfile(user.userId);
                        },
                        function (errResponse) {
                            console.error('Error while fetching the dashboard');
                            console.error(errResponse);
                        }
                );
        };
        self.getAssignedCoach = function () {
            DashboardService.getAssignedCoach($scope.userSession.userId).then(
                    function (data) {
                        var res = data.entity.output;
                        $scope.coachAssignedPlan = angular.copy(res);
                        self.getAvailableMessages(res.id);
                        if (res.starUserId.profilePhotoBase64 != "") {
                            $scope.profileImageStar = res.starUserId.profilePhotoBase64;
                        }
                        if (res.coachUserId.profilePhotoBase64 != "") {
                            $scope.profileImageCoach = res.coachUserId.profilePhotoBase64;
                        }

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        self.getAvailableMessages = function(coachAssignedPlanId){
              DashboardService.getAvailableMessages(coachAssignedPlanId).then(
                    function (data) {
                   $scope.availableMessage = data.entity.output;

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        $scope.goMessages = function(){
              var planSelected = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
            if ($scope.userSession != null && $scope.userSession.typeUser === 'Coach' && planSelected == null) {
              $scope.showMessage("Debe seleccionar un atleta");
            } else{
                $window.location.href = "#message";
            }
        };
        
          self.getAssignedAthletes = function () {
            DashboardService.getAssignedAthletes($scope.userSession.userId).then(
                    function (data) {
                        $scope.athletes = data.entity.output;
                        if($scope.athletes == null){
                            $scope.showMessage("No tiene planes asignados.");
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        if ($scope.userSession != null && $scope.userSession.typeUser === 'Coach') {
            self.getAssignedAthletes();
            $window.sessionStorage.setItem("coachAssignedPlanSelected", null);
            
        } else if ($scope.userSession != null && $scope.userSession.typeUser === 'Atleta') {
            $scope.getUserSession(function (res) {
                $scope.getUserSessionByResponse(res);
                $scope.getUserById();
                self.getAssignedCoach();
            });
        }

    }]);

