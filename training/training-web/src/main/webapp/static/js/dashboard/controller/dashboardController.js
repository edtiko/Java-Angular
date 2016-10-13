'use strict';

trainingApp.controller('DashboardController', ['$scope', 'UserService', 'DashboardService', '$window', 'messageService','SupervStarCoachService','MailService','videoService',
    function ($scope, UserService, DashboardService, $window, messageService,SupervStarCoachService,MailService,videoService) {

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
        $scope.callReceivedCount = 0;
        $scope.videoReceivedCount = 0;
        $scope.emailReceivedCount = 0;
        $scope.messagesReceivedCount = 0;
        $scope.tabIndex  = $window.sessionStorage.getItem("tabIndex");
        $scope.tabIndex2  = $window.sessionStorage.getItem("tabIndex2");


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
        //notificación mensajes recibidos
        messageService.receive().then(null, null, function (message) {

                $scope.messagesReceivedCount++;
            
        });
        
           //notificación videos recibidos
        videoService.receive().then(null, null, function (video) {

                $scope.videoReceivedCount++;
            
        });

        $scope.selectAthlete = function (coachAssignedPlanSelected) {
            var user = coachAssignedPlanSelected.athleteUserId;
            $window.sessionStorage.setItem("coachAssignedPlanSelected", JSON.stringify(coachAssignedPlanSelected));
            $scope.coachAssignedPlan = angular.copy(coachAssignedPlanSelected);
            $scope.showControl = true;
            $scope.showChat = false;
            $scope.showVideo = false;
            self.getAvailableMessages(coachAssignedPlanSelected.id, $scope.userSession.userId);
            self.getReceivedMessages(coachAssignedPlanSelected.id, user.userId);
            messageService.initialize(coachAssignedPlanSelected.id);
            //videos
            self.getAvailableVideos(coachAssignedPlanSelected.id, $scope.userSession.userId);
            self.getReceivedVideos(coachAssignedPlanSelected.id, user.userId);
            videoService.initialize(coachAssignedPlanSelected.id);
            
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
        
        $scope.selectCoach = function (coachAssignedPlanSelected) {
            var user = coachAssignedPlanSelected.coachUserId;
            $window.sessionStorage.setItem("coachAssignedPlanSelected", JSON.stringify(coachAssignedPlanSelected));
            $scope.coachAssignedPlan = angular.copy(coachAssignedPlanSelected);
            $scope.showControl = true;
            $scope.showChat = true;
            $scope.showVideo = true;
            messageService.initialize(coachAssignedPlanSelected.id);
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
        
        $scope.selectStar = function (coachAssignedPlanSelected) {
            var user = coachAssignedPlanSelected.starUserId;
            $window.sessionStorage.setItem("coachAssignedPlanSelected", JSON.stringify(coachAssignedPlanSelected));
            $scope.coachAssignedPlan = angular.copy(coachAssignedPlanSelected);
            $scope.showControl = true;
            $scope.showChat = true;
            $scope.showVideo = true;
            messageService.initialize(coachAssignedPlanSelected.id);
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

                        if (res != "" && res != null) {
                       $window.sessionStorage.setItem("coachAssignedPlanSelected", JSON.stringify(res));

                            $scope.coachAssignedPlan = angular.copy(res);
                            $scope.getVisibleFieldsUserByUser(res.athleteUserId);
                            //mensajes
                            self.getAvailableMessages(res.id, $scope.userSession.userId);
                            self.getReceivedMessages(res.id, res.coachUserId.userId);
                            messageService.initialize(res.id);
                            //videos
                            self.getAvailableVideos(res.id, $scope.userSession.userId);
                            self.getReceivedVideos(res.id, res.coachUserId.userId);
                            videoService.initialize(res.id);
            
                            if (res.starUserId.profilePhotoBase64 != "") {
                                $scope.profileImageStar = res.starUserId.profilePhotoBase64;
                            }
                            if (res.coachUserId.profilePhotoBase64 != "") {
                                $scope.profileImageCoach = res.coachUserId.profilePhotoBase64;
                            }
                        }

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        self.getAvailableMessages = function (coachAssignedPlanId, userId) {
            messageService.getAvailableMessages(coachAssignedPlanId, userId).then(
                    function (data) {
                        $scope.availableMessage = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        self.getReceivedMessages = function (coachAssignedPlanId, userId) {
            messageService.getMessagesReceived(coachAssignedPlanId, userId).then(
                    function (data) {
                        $scope.messagesReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
          self.getAvailableVideos = function (coachAssignedPlanId, userId) {
            videoService.getAvailableVideos(coachAssignedPlanId, userId).then(
                    function (data) {
                        $scope.availableVideo = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        self.getReceivedVideos = function (coachAssignedPlanId, userId) {
            videoService.getVideosReceived(coachAssignedPlanId, userId).then(
                    function (data) {
                        $scope.videoReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        $scope.goMessages = function () {
            var planSelected = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
            if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoach 
                    && planSelected == null) {
                $scope.showMessage("Debe seleccionar un atleta");
            } else {
                $window.location.href = "#message";
            }
        };
        
        $scope.goScript = function() {
            $window.location.href = "#script";
        };
        
        $scope.goInforme = function() {
            $window.location.href = "#informe";
        };
        
        $scope.goVideos = function () {
            var planSelected = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
            if ($scope.userSession != null && ($scope.userSession.typeUser === $scope.userSessionTypeUserCoach 
                    || $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) && planSelected == null) {
                $scope.showMessage("Debe seleccionar un atleta");
            } else {
                window.location.href = $contextPath+"#/video";
            }
         };
        $scope.selectUser = function (userId) {
                $window.sessionStorage.setItem("sendingUserId",userId );
                $window.location.href = "#mail";

        };

        self.getAssignedAthletes = function () {
            DashboardService.getAssignedAthletes($scope.userSession.userId).then(
                    function (data) {
                        $scope.athletes = data.entity.output;
                        if ($scope.athletes == null) {
                            $scope.showMessage("No tiene planes asignados.");
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        self.getAssignedStarCoachBySupervisor = function () {
            DashboardService.getAssignedStarCoachBySupervisor($scope.userSession.userId).then(
                    function (data) {
                        var res = data.output;
                        
                        if (data.status == 'success') {
                            $scope.supervisorUserAssignedList = angular.copy(res);
                        }

                    },
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        self.getAssignedAtleteCoachBySupervisor = function () {
            DashboardService.getAssignedAtleteCoachBySupervisor($scope.userSession.userId).then(
                    function (data) {
                        var res = data.output;
                        
                        if (data.status == 'success') {
                            $scope.supervisorUserAssignedList = angular.copy(res);
                        }

                    },
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        
        
        $scope.selectUserBySupervisor = function (supervisorSelected, user, userType) {
            $window.sessionStorage.setItem("supervisorSelectedSelected", JSON.stringify(supervisorSelected));
            $scope.showControl = true;
            $scope.coachAssignedPlan = {athleteUserId:null};
            $scope.coachAssignedPlan.athleteUserId = user;
            self.getAvailableMessages(supervisorSelected.id, $scope.userSession.userId);
            self.getReceivedMessages(supervisorSelected.id, user.userId);
            messageService.initialize(supervisorSelected.id);
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
        
        $scope.getUserSession(function (res) {
            $window.sessionStorage.setItem("coachAssignedPlanSelected", null);
            $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));

            if ($scope.userSession != null &&($scope.userSession.typeUser === $scope.userSessionTypeUserCoach 
                    || $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno)) {              
                self.getAssignedAthletes();
                $scope.getUserById();

            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.getUserSessionByResponse(res);
                $scope.getUserById();
                self.getAssignedCoach();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserSupervisor) {
                self.getAssignedStarCoachBySupervisor();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachEstrella) {
                $scope.getAssignedAthletesByStar();
            }
            
            $scope.getSupervisorByCoachId($scope.userSession.userId);
//            $scope.getAllRecipients();
        });

        $scope.init = function() {
            var coach = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
            if(coach != null) {
                $scope.selectAthlete(coach);
            }
        };
        //$scope.init();
        
        $scope.getSupervisorByCoachId = function(coachId) {
             SupervStarCoachService.getByCoachId(coachId)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.supervisors = d.output;
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating mail communication.');
                            }
                    );
        };
        
        $scope.getAllRecipients = function () {
            MailService.getAllRecipients($scope.userSession.userId).then(
                    function (data) {
                        $scope.recipients = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        $scope.onTabChanges = function (currentTabIndex) {
            if(currentTabIndex == 0) {
                self.getAssignedStarCoachBySupervisor();
            } else if(currentTabIndex == 1) {
                self.getAssignedAtleteCoachBySupervisor();
            }
            
            $window.sessionStorage.setItem("tabIndex", currentTabIndex);
        };
        
        $scope.onTabChanges2 = function (currentTabIndex) {
            $window.sessionStorage.setItem("tabIndex2", currentTabIndex);
        };
        
        $scope.getAssignedAthletesByStar = function () {
            DashboardService.getAssignedAthletesByStar($scope.userSession.userId).then(
                    function (data) {
                        $scope.athletes = data.entity.output;
                        if ($scope.athletes == null) {
                            $scope.showMessage("No tiene planes asignados.");
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

    }]);

