'use strict';

trainingApp.controller('DashboardController', ['$scope', 'UserService', 'DashboardService', '$window', 'messageService', 'MailService',
    'videoService', 'ExternalCoachService', 'AudioMessageService', '$location',
    function ($scope, UserService, DashboardService, $window, messageService, MailService,
            videoService, ExternalCoachService, AudioMessageService, $location) {

        var self = this;
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
        $scope.audioReceivedStar = 0;
        $scope.videoReceivedStar = 0;
        $scope.emailReceivedStar = 0;
        $scope.messagesReceivedStar = 0;
        $scope.audioReceivedSup = 0;
        $scope.videoReceivedSup = 0;
        $scope.emailReceivedSup = 0;
        $scope.messagesReceivedSup = 0;
        $scope.tabIndex = $window.sessionStorage.getItem("tabIndex");
        $scope.tabIndex2 = $window.sessionStorage.getItem("tabIndex2");
        $scope.tabIndexStar = 0;
        $scope.planSelected = null;
        $scope.selectedIndex2 = null;
        $scope.starNotification = false;
        $scope.supNotification = false;
        $scope.internalNotification = false;
        $scope.wsocket;
        $scope.controllerSelected = null;

        $scope.$on('home', function (e) {
            $scope.pageSelected = $scope.views.summary.page;
            //$scope.$emit("pingBack", $scope.get());
        });

        $scope.$on('profile', function (e, data) {
            $scope.getImageProfile(data.userId);
        });

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
            if ($scope.userSession.userId != message.messageUserId.userId) {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && !$scope.planSelected.external) {
                    if (message.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                        $scope.messagesReceivedStar++;
                    } else if (message.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                        $scope.messagesReceivedSup++;
                    }
                } else if ($scope.planSelected != null && !$scope.planSelected.external && message.roleSelected == $scope.roleSelected) {
                    $scope.messagesReceivedCount++;
                } else if ($scope.planSelected != null && $scope.planSelected.external) {
                    $scope.messagesReceivedCount++;
                } else if (message.messageUserId.roleId == $scope.userSessionTypeUserCoachEstrella) {
                    $scope.internalNotification = true;
                } else {
                    $scope.getMessageCount();
                }
            } else {
                $scope.getMessageCount();
            }

        });

        $scope.onMessageReceived = function (data) {
            console.log(data);
        };

        $scope.connectToChatserver = function (sessionId) {
            $scope.wsocket = new WebSocket('ws://' + window.location.host + window.location.pathname + 'chat/' + sessionId);
            $scope.wsocket.onmessage = function (data) {
                var msg = JSON.parse(data.data);
                if ($scope.userSession.userId != msg.messageUserId.userId && msg.mobile) {
                    //$scope.messagesReceivedCount++;
                }
            };

            $scope.wsocket.onopen = function (event) {
                console.log('Push connection from server is working');

            };
            $scope.wsocket.onclose = function (event) {
                console.log('Error on push connection from server ');

            };
        };

        //notificación videos recibidos
        videoService.receive().then(null, null, function (video) {
            if (video.toUser.userId == $scope.userSession.userId) {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && !$scope.planSelected.external) {
                    if (video.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                        $scope.videoReceivedStar++;
                    } else if (video.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                        $scope.videoReceivedSup++;
                    }
                } else if (!$scope.planSelected.external && video.roleSelected == $scope.roleSelected) {
                    $scope.videoReceivedCount++;
                } else if ($scope.planSelected.external) {
                    $scope.videoReceivedCount++;
                } else {
                    $scope.getVideoCount();
                }

            } else {
                $scope.getVideoCount();
            }

        });

        //notificación audios recibidos
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUser.userId == $scope.userSession.userId) {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && !$scope.planSelected.external) {
                    if (audio.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                        $scope.audioReceivedStar++;
                    } else if (audio.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                        $scope.audioReceivedSup++;
                    }
                } else if (!$scope.planSelected.external && audio.roleSelected == $scope.roleSelected) {
                    $scope.audioReceivedCount++;
                } else if ($scope.planSelected.external) {
                    $scope.audioReceivedCount++;
                } else {
                    $scope.getAudioCount();
                }

            } else {

                $scope.getAudioCount();
            }

        });


        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && !$scope.planSelected.external) {
                    if (email.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                        $scope.emailReceivedStar++;
                    } else if (email.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                        $scope.emailReceivedSup++;
                    }
                } else if (!$scope.planSelected.external && email.roleSelected == $scope.roleSelected) {
                    $scope.emailReceivedCount++;
                } else if ($scope.planSelected.external) {
                    $scope.emailReceivedCount++;
                } else if (email.receivingUser.roleId == $scope.userSessionTypeUserCoachEstrella) {
                    $scope.internalNotification = true;
                } else {
                    $scope.getMailCount();
                }

            } else {
                $scope.getMailCount();
            }

        });

        $scope.getAudioCount = function () {
            var tipoPlan = "IN";
            var receivedUser = null;
            if ($scope.planSelected != null && $scope.planSelected != "") {
                if ($scope.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.planSelected.coachUserId.userId;
                    $scope.audioReceivedStar = self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.audioReceivedSup = self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                    $scope.audioReceivedCount = self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    self.getReceivedAudios($scope.planSelected.id, receivedUser, tipoPlan, $scope.roleSelected);
                    //consulta si la estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                }


            }
        };
        $scope.getMailCount = function () {
            var tipoPlan = "IN";
            var receivedUser = null;
            var userSelected = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado
            if ($scope.planSelected != null && $scope.planSelected != "") {
                if ($scope.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.planSelected.coachUserId.userId;
                    $scope.emailReceivedStar = self.getReceivedMails($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.emailReceivedSup = self.getReceivedMails($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                    self.getAvailableMails($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    $scope.emailReceivedCount = self.getReceivedMails($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    //consulta si la estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);

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
            if ($scope.planSelected != null && $scope.planSelected != "") {
                if ($scope.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.planSelected.coachUserId.userId;
                    $scope.messagesReceivedStar = self.getReceivedVideos($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.messagesReceivedSup = self.getReceivedVideos($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                    self.getAvailableVideos($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    $scope.messagesReceivedCount = self.getReceivedVideos($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);

                    //consulta si el control de estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                    self.getAssignedAthletes();
                }
            }
        };

        $scope.getMessageCount = function () {
            var tipoPlan = "IN";
            var receivedUser = null;
            var userSelected = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado
            if ($scope.planSelected != null && $scope.planSelected != "") {
                if ($scope.planSelected.external) {
                    tipoPlan = "EXT";
                }
                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                    receivedUser = $scope.planSelected.coachUserId.userId;
                    $scope.messagesReceivedStar = self.getReceivedMessages($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    $scope.messagesReceivedSup = self.getReceivedMessages($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
                } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                    self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    $scope.messagesReceivedCount = self.getReceivedMessages($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
                    //consulta si la estrella y supervisor tienen notificaciones pendientes
                    self.getNotificationStar($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella);
                    self.getNotificationSupervisor($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno);
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
            } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                $scope.showControlAthlete = false;
                $scope.showSupervisorControl = true;
                $scope.showStarControl = false;
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
            $window.sessionStorage.setItem("planSelected", JSON.stringify(planSelected));
            $scope.planSelected = angular.copy(planSelected);

            if (planSelected != "" && planSelected != null && planSelected.external) {
                $scope.roleSelected = -1;
                self.initControls(planSelected, "EXT");

            } else if (planSelected != "" && planSelected != null && $scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                $scope.initControlAthlete($scope.userSessionTypeUserCoachInterno, $scope.planSelected.athleteUserId.userId);
                $scope.getDashBoardByUser(planSelected.athleteUserId);
                //consulta si la estrella y supervisor tienen notificaciones pendientes
                self.getNotificationStar($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, "IN", $scope.userSessionTypeUserCoachEstrella);
                self.getNotificationSupervisor($scope.planSelected.id, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId, "IN", $scope.userSessionTypeUserCoachInterno);
            }
        };

        $scope.selectCoach = function (user, index) {

            $scope.selectedIndex2 = index;
            $scope.tabIndexStar = 1;
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

            $scope.pageSelected = $scope.views.summary.page;
            $scope.showProfileImage = true;
            $window.sessionStorage.setItem("planSelected", null);
            $window.sessionStorage.setItem("selectedUser", JSON.stringify(user));

            if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                $scope.showControlAthlete = true;
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
            if ($scope.planSelected != null) {
                //no llore como mujer, lo que no supo defender como hombre

                messageService.initialize($scope.planSelected.id);
                videoService.initialize($scope.planSelected.id);
                AudioMessageService.initialize($scope.planSelected.id);
                MailService.initialize($scope.planSelected.id);
                //ws movil
                $scope.connectToChatserver($scope.planSelected.id);
                
                $scope.pageSelected = $scope.views.profile.page;
                //mensajes 
                self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
                $scope.messagesReceivedCount = self.getReceivedMessages($scope.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

                //videos
                self.getAvailableVideos($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
                $scope.videoReceivedCount = self.getReceivedVideos($scope.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

                //audios
                self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);

                $scope.audioReceivedCount = self.getReceivedAudios($scope.planSelected.id, fromUser, tipoPlan, roleSelected);

                //email
                self.getAvailableMails($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
                $scope.emailReceivedCount = self.getReceivedMails($scope.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

            }
        };

        //Consulta si existen notificaciones internas pendientes por leer 
        self.getNotificationInternal = function (userSessionId) {
            UserService.notificationInternal(userSessionId).then(
                    function (d) {
                        if (d.status == 'success') {
                            var res = d.output;
                            if (res) {
                                $scope.internalNotification = true;
                            } else {
                                $scope.internalNotification = false;
                            }
                        } else {
                            $scope.showMessage(d.output);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while getting notification: ' + errResponse);
                    }
            );
        };

        self.getNotificationStar = function (communicationPlanId, athleteUserId, userId, planType, roleSelected) {
            UserService.notificationRole(communicationPlanId, athleteUserId, userId, planType, roleSelected).then(
                    function (d) {
                        if (d.status == 'success') {
                            var res = d.output;
                            if (res) {
                                $scope.starNotification = true;
                            } else {
                                $scope.starNotification = false;
                            }
                        } else {
                            $scope.showMessage(d.output);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while getting notification: ' + errResponse);
                    }
            );
        };

        self.getNotificationSupervisor = function (communicationPlanId, athleteUserId, userId, planType, roleSelected) {
            UserService.notificationRole(communicationPlanId, athleteUserId, userId, planType, roleSelected).then(
                    function (d) {
                        if (d.status == 'success') {
                            var res = d.output;
                            if (res) {
                                $scope.supNotification = true;
                            } else {
                                $scope.supNotification = false;
                            }
                        } else {
                            $scope.showMessage(d.output);
                        }
                    },
                    function (errResponse) {
                        console.error('Error while getting notification: ' + errResponse);
                    }
            );
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

        //Traer el plan asociado al Usuario Atleta
        $scope.getAssignedPlan = function () {
            var userId = $scope.userSession.userId;
            DashboardService.getAssignedPlan(userId).then(
                    function (data) {
                        var res = data.entity.output;
                        $window.sessionStorage.setItem("planSelected", JSON.stringify(res));
                        $scope.planSelected = angular.copy(res);
                        if (data.entity.status == 'success') {
                            $scope.initCommunication(res);
                        } else {
                            $scope.showMessage(res, "Alerta");
                        }

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.initCommunication = function (plan) {
            var tipoPlan = "IN";

            if (plan.external) {
                tipoPlan = "EXT";
            }
            //ATLETA
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {

                messageService.initialize(plan.id);
                videoService.initialize(plan.id);
                AudioMessageService.initialize(plan.id);
                MailService.initialize(plan.id);
                $scope.connectToChatserver(plan.id);

                $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (res) {
                    $scope.audioReceivedStar = res.receivedAudio;
                    $scope.videoReceivedStar = res.receivedMail;
                    $scope.emailReceivedStar = res.receivedAudio;
                    $scope.messagesReceivedStar = res.receivedMsg;
                });

                $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno, function (res) {
                    $scope.audioReceivedSup = res.receivedAudio;
                    $scope.videoReceivedSup = res.receivedMail;
                    $scope.emailReceivedSup = res.receivedAudio;
                    $scope.messagesReceivedSup = res.receivedMsg;
                });

            }
            //SUPERVISOR
            else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {

                $scope.connectToChatserver(plan.id);
                messageService.initialize(plan.id);
                videoService.initialize(plan.id);
                AudioMessageService.initialize(plan.id);
                MailService.initialize(plan.id);

                if ($scope.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                    $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno, function (res) {
                        $scope.audioReceivedCount = res.receivedAudio;
                        $scope.videoReceivedCount = res.receivedMail;
                        $scope.emailReceivedCount = res.receivedAudio;
                        $scope.messagesReceivedCount = res.receivedMsg;
                    });
                } else if ($scope.roleSelected == userSessionTypeUserCoachEstrella) {
                    $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (res) {
                        $scope.audioReceivedCount = res.receivedAudio;
                        $scope.videoReceivedCount = res.receivedMail;
                        $scope.emailReceivedCount = res.receivedAudio;
                        $scope.messagesReceivedCount = res.receivedMsg;
                    });
                }

            }
            //ESTRELLA
            else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                $scope.audioReceivedCount = 0;
                $scope.videoReceivedCount = 0;
                $scope.emailReceivedCount = 0;
                $scope.messagesReceivedCount = 0;
            }

        };

        $scope.getConfigurationPlanByUser = function (planId, userId, toUserId, tipoPlan, roleSelected, fn) {
            DashboardService.getConfigurationPlanByUser(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
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

        self.getReceivedMessages = function (planId, userId, toUserId, tipoPlan, roleSelected) {
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
        };

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
        self.getReceivedVideos = function (planId, fromUserId, toUserId, tipoPlan, roleSelected) {
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
        };

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
        self.getReceivedAudios = function (planId, userId, tipoPlan, roleSelected) {
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
        };

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

        self.getReceivedMails = function (planId, userId, toUserId, tipoPlan, roleSelected) {
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
        };

        $scope.goMessages = function (roleSelected, index) {
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                $scope.selectedIndex = index;
                $scope.roleSelected = roleSelected;
            }
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
            var userSelected = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado

            if ($scope.userSession != null && userSelected != null) {
                $scope.pageSelected = $scope.views.message.page;
            } else if ($scope.userSession != null && planSelected == null) {
                if (userInfo != null && userInfo.typeUser == $scope.userSessionTypeUserAtleta) {
                    $scope.showMessage("Debe tener un plan activo");
                } else {
                    $scope.showMessage("Debe seleccionar un usuario");
                }
            } else if (userInfo.typeUser == $scope.userSessionTypeUserAtleta && !planSelected.external && $scope.roleSelected == -1) {
                $scope.showMessage("Debe seleccionar un usuario");
            } else {
                $scope.pageSelected = $scope.views.message.page + '?now=' + Date.now();
            }
        };
        $scope.goAudioMessages = function (roleSelected, index) {
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                $scope.selectedIndex = index;
                $scope.roleSelected = roleSelected;
            }
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.userSession != null && planSelected == null) {
                if (userInfo != null && userInfo.typeUser == $scope.userSessionTypeUserAtleta) {
                    $scope.showMessage("Debe tener un plan activo");
                } else {
                    $scope.showMessage("Debe seleccionar un atleta");
                }
            } else if (userInfo.typeUser == $scope.userSessionTypeUserAtleta && !planSelected.external && $scope.roleSelected == -1) {
                $scope.showMessage("Debe seleccionar un usuario");
            } else {
                $scope.pageSelected = $scope.views.audioMessage.page + '?now=' + Date.now();
                ;
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


        $scope.goVideos = function (roleSelected, index) {
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                $scope.selectedIndex = index;
                $scope.roleSelected = roleSelected;
            }
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.userSession != null && planSelected == null) {
                if (userInfo != null && userInfo.typeUser == $scope.userSessionTypeUserAtleta) {
                    $scope.showMessage("Debe tener un plan activo");
                } else {
                    $scope.showMessage("Debe seleccionar un atleta");
                }
            } else if (userInfo.typeUser == $scope.userSessionTypeUserAtleta && !planSelected.external && $scope.roleSelected == -1) {
                $scope.showMessage("Debe seleccionar un usuario");
            } else {
                //window.location.href = $contextPath + "#/video";
                $scope.pageSelected = $scope.views.video.page + '?now=' + Date.now();
                ;
            }

        };

        $scope.goEmail = function (roleSelected, index) {
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                $scope.selectedIndex = index;
                $scope.roleSelected = roleSelected;
            }
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
            var userSelected = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado

            if ($scope.userSession != null && userSelected != null) {
                $scope.pageSelected = $scope.views.email.page;
            } else if ($scope.userSession != null && planSelected == null) {
                if (userInfo != null && userInfo.typeUser == $scope.userSessionTypeUserAtleta) {
                    $scope.showMessage("Debe tener un plan activo");
                } else {
                    $scope.showMessage("Debe seleccionar un usuario");
                }
            } else if (userInfo.typeUser == $scope.userSessionTypeUserAtleta && !planSelected.external && $scope.roleSelected == -1) {
                $scope.showMessage("Debe seleccionar un usuario");
            } else {
                $scope.pageSelected = $scope.views.email.page + '?now=' + Date.now();
            }

        };

        $scope.selectUser = function (user, index) {

            $scope.selectedIndex = index;

            $scope.pageSelected = $scope.views.profile.page;
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

        self.getAssignedStar = function () {
            DashboardService.getAssignedStarByCoach($scope.userSession.userId).then(
                    function (data) {
                        $scope.starsByCoach = data.entity.output;
                        //inicializa websockets con las estrellas
                        angular.forEach($scope.starsByCoach, function (value, key) {
                            messageService.initialize(value.userId + $scope.userSession.userId);
                            MailService.initialize(value.userId + $scope.userSession.userId);
                        });
                        self.getNotificationInternal($scope.userSession.userId);
                        if ($scope.starsByCoach == null) {
                            $scope.showMessage("No tiene estrellas asignados.");
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

        self.setAthleteRole = function () {
            $scope.showControl = true;
            $scope.showControlAthlete = true;
            $scope.showVideo = true;
            $scope.showCountVideo = true;
            $scope.showEmail = true;
            $scope.showCountEmail = true;
            $scope.showAudioMessage = true;
            $scope.showCountAudio = true;
            $scope.showChat = true;
            $scope.showCountChat = true;
            $scope.showScript = false;
            $scope.getAssignedPlan();
            $scope.getDashBoardByUser($scope.userSession);
            $scope.pageSelected = $scope.views.summary.page;
        };

        $scope.init = function () {
            $scope.getUserSession(function (res) {
                $window.sessionStorage.setItem("planSelected", null);
                $window.sessionStorage.setItem("planSelectedStar", null);
                $window.sessionStorage.setItem("selectedUser", null);
                $scope.planSelected = null;
                //$scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));


                if ($scope.userSession != null) {
                    $scope.getUserSessionByResponse(res);
                    //$scope.getUserById();
                    $scope.getVisibleFieldsUserByUser($scope.userSession);

                    switch ($scope.userSession.typeUser) {
                        case $scope.userSessionTypeUserCoach:
                            $scope.showControl = true;
                            $scope.showControlAthlete = true;
                            $scope.showVideo = true;
                            $scope.showCountVideo = true;
                            $scope.showEmail = true;
                            $scope.showCountEmail = true;
                            $scope.showAudioMessage = true;
                            $scope.showCountAudio = true;
                            $scope.showChat = true;
                            $scope.showCountChat = true;
                            self.getAthletesCoachExternal();
                            break;
                        case $scope.userSessionTypeUserCoachInterno:
                            $scope.showControl = true;
                            $scope.showSupervisorControl = true;
                            $scope.showVideo = true;
                            $scope.showCountVideo = true;
                            $scope.showEmail = true;
                            $scope.showCountEmail = true;
                            $scope.showAudioMessage = true;
                            $scope.showCountAudio = true;
                            $scope.showChat = true;
                            $scope.showCountChat = true;
                            $scope.pageSelected = $scope.views.profile.page;
                            self.getAssignedAthletes();
                            self.getAssignedStar();
                            break;
                        case $scope.userSessionTypeUserAtleta:
                            self.setAthleteRole();
                            break;
                        case $scope.userSessionTypeUserCoachEstrella:
                            $scope.showControl = true;
                            $scope.showStarControl = true;
                            $scope.showProfileImage = true;
                            $scope.getSupervisorsByStar();
                            break;
                        case $scope.userSessionTypeUserAdmin:
                            $scope.showControl = true;
                            $scope.showControlAthlete = true;
                            $scope.showProfileImage = true;
                            $scope.showVideo = false;
                            $scope.showCountVideo = false;
                            $scope.showEmail = true;
                            $scope.showCountEmail = false;
                            $scope.showAudioMessage = false;
                            $scope.showCountAudio = false;
                            $scope.showChat = true;
                            $scope.showCountChat = false;
                            $scope.showScript = false;
                            self.getSupervisors();
                            self.getStars();
                            break;

                    }
                }

            });
            // $("#trainingApp").removeClass("preloader");
        };


        $scope.init();

    }]);

