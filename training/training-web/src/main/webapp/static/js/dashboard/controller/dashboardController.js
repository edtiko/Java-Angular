'use strict';

trainingApp.controller('DashboardController', ['$scope', 'UserService', 'DashboardService', '$window', 'messageService', 'MailService',
    'videoService', 'ExternalCoachService', 'AudioMessageService', 'ActivityService','$location',
    function ($scope, UserService, DashboardService, $window, messageService, MailService,
            videoService, ExternalCoachService, AudioMessageService,ActivityService, $location) {

        var self = this;
        $scope.profileImage = "static/img/profile-default.png";
        $scope.profileImageStar = "static/img/profile-default.png";
        $scope.profileImageCoach = "static/img/profile-default.png";
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.coachAssignedPlanId = null;
        $scope.selectedIndex2 = null;
        $scope.starNotification = false;
        $scope.supNotification = false;
        $scope.internalNotification = false;
        
        $scope.$on('home', function (e) {
            $scope.pageSelected = $scope.views.summary.page;
            //$scope.$emit("pingBack", $scope.get());
        });

        /*$scope.$on('profile', function (e, data) {
            $scope.getImageProfile(data.userId, function (data) {
                if (data != "") {
                    $scope.profileImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("profileImage", $scope.profileImage);
                } else {
                    $scope.profileImage = "static/img/profile-default.png";
                }
            });
        });*/

        $scope.$on('userSession', function (e, data) {
            $scope.userSession = data.userSession;
        });

        $scope.getUserById = function () {

            if ($scope.appReady) {
                var user = JSON.parse(sessionStorage.getItem("userInfo"));

                if (user == null || user == undefined) {
                    $scope.setUserSession();
                }

                $scope.getDashBoardByUser(user);
            } else {
                $scope.showMessage("El usuario no se encuentra logueado.", "error");
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

        
 
        $scope.getAudioCount = function () {
            var tipoPlan = "IN";
            var receivedUser = null;
            if ($scope.userSession.planSelected != null && $scope.userSession.planSelected != "") {
                if ($scope.userSession.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.audioReceivedStar = self.getAvailableAudios($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.audioReceivedSup = self.getAvailableAudios($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.userSession.planSelected.athleteUserId.userId;
                    $scope.audioReceivedCount = self.getAvailableAudios($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    self.getReceivedAudios($scope.userSession.planSelected.id, receivedUser, tipoPlan, $scope.roleSelected);
                    //consulta si la estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                }


            }
        };
        $scope.getMailCount = function () {
            var tipoPlan = "IN";
            var receivedUser = null;
            var userSelected = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado
            if ($scope.userSession.planSelected != null && $scope.userSession.planSelected != "") {
                if ($scope.userSession.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.emailReceivedStar = self.getReceivedMails($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.emailReceivedSup = self.getReceivedMails($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.userSession.planSelected.athleteUserId.userId;
                    self.getAvailableMails($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    $scope.emailReceivedCount = self.getReceivedMails($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    //consulta si la estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);

                    self.getAssignedAthletes();
                }

            } else if (userSelected != null) {
                self.getReceivedMails(-1, userSelected.userId, $scope.userSession.userId, tipoPlan, -1);
            }
            self.getNotificationInternal($scope.userSession.userId);
        };

        $scope.getVideoCount = function () {
            var tipoPlan = "IN";
            var receivedUser = null;
            if ($scope.userSession.planSelected != null && $scope.userSession.planSelected != "") {
                if ($scope.userSession.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.messagesReceivedStar = self.getReceivedVideos($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.messagesReceivedSup = self.getReceivedVideos($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.userSession.planSelected.athleteUserId.userId;
                    self.getAvailableVideos($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    $scope.messagesReceivedCount = self.getReceivedVideos($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);

                    //consulta si el control de estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                    self.getAssignedAthletes();
                }
            }
        };

        $scope.getMessageCount = function () {
            var tipoPlan = "IN";
            var receivedUser = null;
            var userSelected = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado
            if ($scope.userSession.planSelected != null && $scope.userSession.planSelected != "") {
                if ($scope.userSession.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.messagesReceivedStar = self.getReceivedMessages($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.messagesReceivedSup = self.getReceivedMessages($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.userSession.planSelected.athleteUserId.userId;
                    self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    $scope.messagesReceivedCount = self.getReceivedMessages($scope.userSession.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    //consulta si la estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                    self.getAssignedAthletes();
                }

            } else if (userSelected != null) {
                self.getReceivedMessages(-1, userSelected.userId, $scope.userSession.userId, tipoPlan, -1);
            }
            self.getNotificationInternal($scope.userSession.userId);

        };


        //notificación invitaciones recibidas
        ExternalCoachService.receive().then(null, null, function (data) {
            $scope.viewInvitations($scope.userSession.userId);
        });


        //Selecciona un Atleta
        $scope.selectAthlete = function (planSelected, index) {

            $scope.selectedIndex = index;
            $scope.selectedIndex2 = null;
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoach) {
                $scope.showSupervisorControl = false;
                $scope.showControlAthlete = true;
                $scope.showStarControl = false;
                $scope.showInternalControl = false;
                $scope.lblMailStar = false;
                $scope.lblMessageStar = false;
            } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                $scope.showInternalControl = false;
                $scope.showControlAthlete = false;
                $scope.showSupervisorControl = true;
                $scope.showStarControl = false;
                $scope.lblMailStar = false;
                $scope.lblMessageStar = false;
            }

            $scope.showVideo = true;
            $scope.showCountVideo = true;
            $scope.showEmail = true;
            $scope.showCountEmail = true;
            $scope.showAudioMessage = true;
            $scope.showCountAudio = true;
            $scope.showChat = true;
            $scope.showCountChat = true;
            $scope.showScript = false;
            $scope.pageSelected = $scope.views.profile.page;
            $window.sessionStorage.setItem("selectedUser", null);
            $scope.userSession['planSelected'] = planSelected;

            if (planSelected != "" && planSelected != null && planSelected.external) {
                $scope.roleSelected = -1;
                self.initControls(planSelected, "EXT");

            } else if (planSelected != "" && planSelected != null && $scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                $scope.roleSelected = $scope.userSessionTypeUserCoachInterno;
                $scope.initControlAthlete($scope.userSessionTypeUserCoachInterno, $scope.userSession.planSelected.athleteUserId.userId);
                $scope.getDashBoardByUser(planSelected.athleteUserId);
                //consulta si la estrella y supervisor tienen notificaciones pendientes
                self.getNotificationStar($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, "IN", $scope.userSessionTypeUserCoachEstrella);
                self.getNotificationSupervisor($scope.userSession.planSelected.id, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId, "IN", $scope.userSessionTypeUserCoachInterno);
            }
        };

        $scope.selectCoach = function (user, index) {

            $scope.selectedIndex2 = index;
            $scope.pageSelected = $scope.views.profile.page;
            $scope.showProfileImage = true;
            $window.sessionStorage.setItem("planSelected", null);
            $window.sessionStorage.setItem("selectedUser", JSON.stringify(user));

            if ($scope.userSession != null) {
                messageService.initialize(user.userId + $scope.userSession.userId);
                self.getReceivedMessages(-1, user.userId, $scope.userSession.userId, "IN", -1);
                MailService.initialize(user.userId + $scope.userSession.userId);
                self.getReceivedMails(-1, user.userId, $scope.userSession.userId, "IN", -1);
                $scope.getDashBoardByUser(user);
            } else {
                $scope.setUserSession();
            }
        };

        $scope.selectStar = function (user, index) {

            $scope.selectedIndex2 = index;
            $scope.selectedIndex = null;

            $scope.pageSelected = $scope.views.profile.page;
            $scope.showProfileImage = true;
            $window.sessionStorage.setItem("planSelected", null);
            $window.sessionStorage.setItem("selectedUser", JSON.stringify(user));

            if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                $scope.showInternalControl = true;
                $scope.showControlAthlete = false;
                $scope.showStarControl = false;
                $scope.showSupervisorControl = false;
                $scope.showControl = true;
                $scope.showVideo = false;
                $scope.showCountVideo = false;
                $scope.showEmail = true;
                $scope.showCountEmail = false;
                $scope.showAudioMessage = false;
                $scope.showCountAudio = false;
                $scope.showChat = true;
                $scope.showCountChat = false;
                $scope.initStarControl(true);
            }
            if ($scope.userSession != null) {
                messageService.initialize($scope.userSession.userId + user.userId);
                self.getReceivedMessages(-1, user.userId, $scope.userSession.userId, "IN", -1);
                MailService.initialize($scope.userSession.userId + user.userId);
                self.getReceivedMails(-1, user.userId, $scope.userSession.userId, "IN", -1);
                $scope.getDashBoardByUser(user);
            } else {
                $scope.setUserSession();
            }
        };


        $scope.initControlAthlete = function (roleSelected, fromUser) {

            $scope.audioReceivedCount = 0;
            $scope.videoReceivedCount = 0;
            $scope.emailReceivedCount = 0;
            $scope.messagesReceivedCount = 0;

            var tipoPlan = "IN";
            $scope.roleSelected = roleSelected;
            if (roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                $scope.showCountVideo = false;
            } else {
                $scope.showCountVideo = true;
            }
            if ($scope.userSession.planSelected != null) {
                //no llore como mujer, lo que no supo defender como hombre

                messageService.initialize($scope.userSession.planSelected.id);
                videoService.initialize($scope.userSession.planSelected.id);
                AudioMessageService.initialize($scope.userSession.planSelected.id);
                MailService.initialize($scope.userSession.planSelected.id);
                //ws movil
                $scope.connectToChatserver($scope.userSession.planSelected.id);
                
                //ws movil
                $scope.connectToAudioWsMovil($scope.userSession.planSelected.id);
                
                 //ws movil
                $scope.connectToVideoWsMovil($scope.userSession.planSelected.id);
                
                $scope.pageSelected = $scope.views.profile.page;
                //mensajes 
                self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
                //$scope.messagesReceivedCount = self.getReceivedMessages($scope.userSession.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

                //videos
                self.getAvailableVideos($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
                //$scope.videoReceivedCount = self.getReceivedVideos($scope.userSession.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

                //audios
                self.getAvailableAudios($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);

                //$scope.audioReceivedCount = self.getReceivedAudios($scope.userSession.planSelected.id, fromUser, tipoPlan, roleSelected);

                //email
                self.getAvailableMails($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
                //$scope.emailReceivedCount = self.getReceivedMails($scope.userSession.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

            }
        };


        self.initControls = function (plan, tipoPlan) {

            $scope.audioReceivedCount = 0;
            $scope.videoReceivedCount = 0;
            $scope.emailReceivedCount = 0;
            $scope.messagesReceivedCount = 0;
            //mensajes 
            self.getAvailableMessages(plan.id, $scope.userSession.userId, tipoPlan, -1);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedMessages(plan.id, plan.coachUserId.userId, $scope.userSession.userId, tipoPlan, -1);
            } else {
                self.getReceivedMessages(plan.id, plan.athleteUserId.userId, $scope.userSession.userId, tipoPlan, -1);
            }
            messageService.initialize(plan.id);

            //videos
            self.getAvailableVideos(plan.id, $scope.userSession.userId, tipoPlan, -1);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedVideos(plan.id, plan.coachUserId.userId, $scope.userSession.userId, tipoPlan, -1);
            } else {
                self.getReceivedVideos(plan.id, plan.athleteUserId.userId, $scope.userSession.userId, tipoPlan, -1);
            }
            videoService.initialize(plan.id);

            //audios
            self.getAvailableAudios(plan.id, $scope.userSession.userId, tipoPlan, -1);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedAudios(plan.id, plan.coachUserId.userId, tipoPlan, -1);
            } else {
                self.getReceivedAudios(plan.id, plan.athleteUserId.userId, tipoPlan, -1);
            }
            AudioMessageService.initialize(plan.id);

            //email
            self.getAvailableMails(plan.id, $scope.userSession.userId, tipoPlan, -1);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedMails(plan.id, plan.coachUserId.userId, $scope.userSession.userId, tipoPlan, -1);
            } else {
                self.getReceivedMails(plan.id, plan.athleteUserId.userId, $scope.userSession.userId, tipoPlan, -1);
            }
            MailService.initialize(plan.id);

            //Inicializa las notificaciones de invitaciones
            if (tipoPlan == "EXT") {
                ExternalCoachService.initialize(plan.athleteUserId.userId);
            }

            $scope.getDashBoardByUser(plan.athleteUserId);
        };

       

        //MESSAGES COUNT
        self.getAvailableMessages = function (planId, userId, tipoPlan, roleSelected) {
            messageService.getAvailableMessages(planId, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.availableMessage = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

       /* self.getReceivedMessages = function (planId, userId, toUserId, tipoPlan, roleSelected) {
            var res = 0;
            messageService.getMessagesReceived(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        res =  data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
                    return res;
        };*/

        //VIDEOS COUNT

        self.getAvailableVideos = function (planId, userId, tipoPlan, roleSelected) {
            videoService.getAvailableVideos(planId, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.availableVideo = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        /*self.getReceivedVideos = function (planId, fromUserId, toUserId, tipoPlan, roleSelected) {
            var res = 0;
            videoService.getVideosReceived(planId, fromUserId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        res = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
            return res;
        };*/

        //AUDIOS COUNT
        self.getAvailableAudios = function (planId, userId, tipoPlan, roleSelected) {
            AudioMessageService.getAvailableAudios(planId, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.availableAudio = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        /*self.getReceivedAudios = function (planId, userId, tipoPlan, roleSelected) {
            var res = 0;
            AudioMessageService.getAudiosReceived(planId, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        res = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
            return res;
        };*/

        //EMAIL COUNT
        self.getAvailableMails = function (planId, userId, tipoPlan, roleSelected) {
            MailService.getAvailableMails(planId, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.availableEmail = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        /*self.getReceivedMails = function (planId, userId, toUserId, tipoPlan, roleSelected) {
            var res = 0;
            MailService.getReceivedMails(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        res = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
            return res;
        };*/
       
        $scope.goMessages = function () {

            if ($scope.userSession != null && $scope.userSession.planSelected == null) {

                $scope.showMessage("Debe tener un plan activo");

            } else {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    $scope.pageSelected = $scope.views.messageSupervisor.page + '?now=' + Date.now();
                } else {
                    $scope.pageSelected = $scope.views.message.page + '?now=' + Date.now();
                }
            }
        };
        
        $scope.goAudioMessages = function () {
            if ($scope.userSession != null && $scope.userSession.planSelected == null) {

                $scope.showMessage("Debe tener un plan activo");

            } else {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    $scope.pageSelected = $scope.views.audioSupervisor.page + '?now=' + Date.now();
                } else {
                    $scope.pageSelected = $scope.views.audio.page + '?now=' + Date.now();
                }
            }
        };


        $scope.goScript = function () {
            $window.location.href = "#script";
            //$scope.pageSelected = $scope.views.script.page;
        };

        $scope.goInforme = function () {
            $window.location.href = "#informe";
            //$scope.pageSelected = $scope.views.report.page;
        };


        $scope.goVideos = function () {
            if ($scope.userSession != null && $scope.userSession.planSelected == null) {

                $scope.showMessage("Debe tener un plan activo");

            } else {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    $scope.pageSelected = $scope.views.videoSupervisor.page + '?now=' + Date.now();
                } else {
                    $scope.pageSelected = $scope.views.video.page + '?now=' + Date.now();
                }
            }

        };

        $scope.goEmail = function () {
            if ($scope.userSession != null && $scope.userSession.planSelected == null) {

                $scope.showMessage("Debe tener un plan activo");

            } else {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    $scope.pageSelected = $scope.views.emailSupervisor.page + '?now=' + Date.now();
                } else {
                    $scope.pageSelected = $scope.views.email.page + '?now=' + Date.now();
                }
            }
        };

        $scope.selectUser = function (user, index) {

            $scope.selectedIndex = index;
            $scope.pageSelected = $scope.views.profile.page;
            $window.sessionStorage.setItem("selectedUser", null);
            $window.sessionStorage.setItem("selectedUser", JSON.stringify(user));
            $window.sessionStorage.setItem("planSelected", null);

            if ($scope.userSession != null) {
                messageService.initialize(user.userId + $scope.userSession.userId);
                MailService.initialize(user.userId + $scope.userSession.userId);
                $scope.getDashBoardByUser(user);
            } else {
                $scope.setUserSession();
            }

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


        self.getSupervisors = function () {
            UserService.getCoaches()
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.coaches = d.output;
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while getting coaches');
                            }
                    );
        };

        self.getStars = function () {
            UserService.getStars()
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.stars = d.output;
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while getting stars');
                            }
                    );
        };

        $scope.getSupervisorsByStar = function () {
            DashboardService.getSupervisorsByStar($scope.userSession.userId).then(
                    function (data) {
                        $scope.supervisors = data.entity.output;
                        if ($scope.supervisors == null) {
                            $scope.showMessage("No tiene supervisores asignados.");
                        }
                        self.getNotificationInternal($scope.userSession.userId);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
       
        $scope.initStarControl = function (interno) {
            if (interno) {
                $scope.showChat = true;
                $scope.showEmail = true;
                $scope.showScript = false;
                $scope.showAudioMessage = false;
                $scope.lblVideoStar = false;
                $scope.lblMessageStar = true;
                $scope.lblMailStar = true;
            } else {
                $scope.showScript = true;
                $scope.showAudioMessage = false;
                $scope.showChat = false;
                $scope.showEmail = false;
                $scope.lblVideoStar = true;
                $scope.lblMessageStar = false;
                $scope.lblMailStar = false;
            }

        };


    }]);

