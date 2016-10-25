'use strict';

trainingApp.controller('DashboardController', ['$scope', 'UserService', 'DashboardService', '$window', 'messageService', 'SupervStarCoachService', 'MailService', 'videoService', 'ExternalCoachService', 'AudioMessageService',
    function ($scope, UserService, DashboardService, $window, messageService, SupervStarCoachService, MailService, videoService, ExternalCoachService, AudioMessageService) {

        var self = this;
        $scope.user = {userId: null, name: '', secondName: '', lastName: '', email: '', sex: '', age: '',
            weight: '', phone: '', cellphone: '', federalState: '', city: '', address: '', postalCode: '',
            birthDate: '', facebookPage: '', country: '', profilePhoto: '',
            ageSport: '', ppm: '', power: '', sportsAchievements: '',
            aboutMe: '', indMetricSys: '', discipline: '', sport: '', shoes: '', bikes: '', potentiometer: '',
            modelPotentiometer: '', pulsometer: '', modelPulsometer: '', objective: '', modality: '',
            availability: '', twitterPage: '', instagramPage: '', webPage: '', vo2Running: '', vo2Ciclismo: '', 
            injury: '', disease:''
        };
        $scope.profileImage = "static/img/profile-default.png";
        $scope.profileImageStar = "static/img/profile-default.png";
        $scope.profileImageCoach = "static/img/profile-default.png";
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.coachAssignedPlanId = null;
        $scope.availableMessage = 0;
        $scope.availableAudio = 0;
        $scope.availableVideo = 0;
        $scope.availableEmail = 0;
        $scope.audioReceivedCount = 0;
        $scope.videoReceivedCount = 0;
        $scope.emailReceivedCount = 0;
        $scope.messagesReceivedCount = 0;
        $scope.tabIndex = $window.sessionStorage.getItem("tabIndex");
        $scope.tabIndex2 = $window.sessionStorage.getItem("tabIndex2");
        $scope.planSelected = null;

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
            if (message.toUserId == $scope.user.userId) {
                $scope.messagesReceivedCount++;
            }

        });

        //notificación videos recibidos
        videoService.receive().then(null, null, function (video) {
            if (video.toUserId == $scope.user.userId) {
                $scope.videoReceivedCount++;
            }

        });

        //notificación audios recibidos
        AudioMessageService.receive().then(null, null, function (audio) {
       if (audio.toUserId.userId == $scope.user.userId) {
            $scope.audioReceivedCount++;
        }

        });



        //Selecciona un Atleta
        $scope.selectAthlete = function (planSelected) {
            if (planSelected != "" && planSelected != null) {
                if (planSelected.external) {
                    self.initControls(planSelected, "EXT");
                } else {
                    $scope.showControl = true;
                    $scope.showChat = false;
                    $scope.showVideo = false;
                    self.initControls(planSelected, "IN");
                }
            }
        };

        self.getDashBoardByUser = function (user) {
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
            $window.sessionStorage.setItem("planSelected", JSON.stringify(coachAssignedPlanSelected));
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
            $window.sessionStorage.setItem("planSelected", JSON.stringify(coachAssignedPlanSelected));
            $window.sessionStorage.setItem("planSelectedStar", JSON.stringify(coachAssignedPlanSelected));
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

        //Inicia controles de Atleta con Coach
        self.initControls = function (plan, tipoPlan) {

            $window.sessionStorage.setItem("planSelected", JSON.stringify(plan));
            $scope.planSelected = angular.copy(plan);
            $scope.showControl = true;

            //mensajes 
            self.getAvailableMessages(plan.id, $scope.userSession.userId, tipoPlan);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedMessages(plan.id, plan.coachUserId.userId, tipoPlan);
            } else {
                self.getReceivedMessages(plan.id, plan.athleteUserId.userId, tipoPlan);
            }
            messageService.initialize(plan.id);

            //videos
            self.getAvailableVideos(plan.id, $scope.userSession.userId, tipoPlan);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedVideos(plan.id, plan.coachUserId.userId, tipoPlan);
            } else {
                self.getReceivedVideos(plan.id, plan.athleteUserId.userId, tipoPlan);
            }
            videoService.initialize(plan.id);

            //audios
            self.getAvailableAudios(plan.id, $scope.userSession.userId, tipoPlan);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedAudios(plan.id, plan.coachUserId.userId, tipoPlan);
            } else {
                self.getReceivedAudios(plan.id, plan.athleteUserId.userId, tipoPlan);
            }
            AudioMessageService.initialize(plan.id);

            //Carga imagenes de perfil de coach y estrella, si el plan es con Coach Interno
            if (tipoPlan == "IN") {
                if (plan.starUserId.profilePhotoBase64 != "") {
                    $scope.profileImageStar = plan.starUserId.profilePhotoBase64;
                }
                if (plan.coachUserId.profilePhotoBase64 != "") {
                    $scope.profileImageCoach = plan.coachUserId.profilePhotoBase64;
                }
            }
            self.getDashBoardByUser(plan.athleteUserId);
        };

        //Traer el plan asociado al Usuario Atleta
        $scope.getAssignedCoach = function (userId) {
            DashboardService.getAssignedCoach(userId).then(
                    function (data) {
                        var res = data.entity.output;
                        if (data.entity.status == 'success') {
                            $scope.selectAthlete(res);
                        } else {
                            $scope.showMessage(res, "Error");
                        }

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        //MESSAGES COUNT
        self.getAvailableMessages = function (planId, userId, tipoPlan) {
            messageService.getAvailableMessages(planId, userId, tipoPlan).then(
                    function (data) {
                        $scope.availableMessage = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.getReceivedMessages = function (planId, userId, tipoPlan) {
            messageService.getMessagesReceived(planId, userId, tipoPlan).then(
                    function (data) {
                        $scope.messagesReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        //VIDEOS COUNT

        self.getAvailableVideos = function (planId, userId, tipoPlan) {
            videoService.getAvailableVideos(planId, userId, tipoPlan).then(
                    function (data) {
                        $scope.availableVideo = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        self.getReceivedVideos = function (planId, userId, tipoPlan) {
            videoService.getVideosReceived(planId, userId, tipoPlan).then(
                    function (data) {
                        $scope.videoReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        //AUDIOS COUNT
        self.getAvailableAudios = function (planId, userId, tipoPlan) {
            AudioMessageService.getAvailableAudios(planId, userId, tipoPlan).then(
                    function (data) {
                        $scope.availableAudio = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        self.getReceivedAudios = function (planId, userId, tipoPlan) {
            AudioMessageService.getAudiosReceived(planId, userId, tipoPlan).then(
                    function (data) {
                        $scope.audioReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        $scope.goMessages = function () {
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            if ($scope.userSession != null && planSelected == null) {
                $scope.showMessage("Debe seleccionar un atleta \u00f3 tener un plan activo");
            }
            /*else if($scope.availableMessage == 0){
             $scope.showMessage("No tiene mensajes disponibles.");
             } */ else {
                $window.location.href = "#message";
            }
        };

        $scope.goAudioMessages = function () {
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            if ($scope.userSession != null && planSelected == null) {
                $scope.showMessage("Debe seleccionar un atleta \u00f3 tener un plan activo");
            }/*else if($scope.availableMessage == 0){
             $scope.showMessage("No tiene Audio Mensajes disponibles.");
             }  */else {
                $window.location.href = "#audio-messages";
            }
        };

        
        $scope.goScript = function() {
            $window.location.href = "#script";
        };
        
        $scope.goInforme = function() {
            $window.location.href = "#informe";
        };
        

        $scope.goVideos = function () {
            
            var coachAssignedPlanSelected = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            if ($scope.userSession != null && planSelected == null && coachAssignedPlanSelected == null) {
                $scope.showMessage("Debe seleccionar un atleta \u00f3 tener un plan activo");
            }/*else if($scope.availableVideo == 0){
             $scope.showMessage("No tiene videos disponibles.");
             } */
            else {
                window.location.href = $contextPath + "#/video";
            }
        };
        $scope.selectUser = function (userId) {
            $window.sessionStorage.setItem("sendingUserId", userId);
            $window.location.href = "#mail";

        };
       //Obtener atletas de Coach Interno  
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
        //Obtener atletas de Coach Externo
        self.getAthletesCoachExternal = function () {
            ExternalCoachService.fetchAthletes($scope.userSession.trainingPlanUserId, "ACTIVE").then(
                    function (data) {
                        $scope.athletes = data;
                        if ($scope.athletes == null || $scope.athletes == "") {
                            $scope.showMessage("No tiene atletas asignados.");
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
            $scope.coachAssignedPlan = {athleteUserId: null};
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
            $window.sessionStorage.setItem("planSelected", null);
            $window.sessionStorage.setItem("planSelectedStar", null);
            $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));

            if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {
                self.getAssignedAthletes();
                $scope.getUserById();

            } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoach) {
                $scope.getUserById();
                self.getAthletesCoachExternal();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.getUserSessionByResponse(res);
                $scope.getUserById();
                $scope.getAssignedCoach($scope.userSession.userId);
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserSupervisor) {
                self.getAssignedStarCoachBySupervisor();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachEstrella) {
                $scope.getAssignedAthletesByStar();
            }

            $scope.getSupervisorByCoachId($scope.userSession.userId);
//            $scope.getAllRecipients();
        });

        $scope.init = function () {
            var coach = JSON.parse($window.sessionStorage.getItem("planSelected"));
            if (coach != null) {
                $scope.selectAthlete(coach);
            }
        };
        //$scope.init();

        $scope.getSupervisorByCoachId = function (coachId) {
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
            if (currentTabIndex == 0) {
                self.getAssignedStarCoachBySupervisor();
            } else if (currentTabIndex == 1) {
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

