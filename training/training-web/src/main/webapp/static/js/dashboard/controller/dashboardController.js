'use strict';

trainingApp.controller('DashboardController', ['$scope', 'UserService', 'DashboardService', '$window', 'messageService', 'MailService',
    'videoService', 'ExternalCoachService', 'AudioMessageService',
    function ($scope, UserService, DashboardService, $window, messageService, MailService,
            videoService, ExternalCoachService, AudioMessageService) {

        var self = this;
        $scope.user = {userId: null, name: '', secondName: '', lastName: '', email: '', sex: '', age: '',
            weight: '', height: '', phone: '', cellphone: '', federalState: '', city: '', address: '', postalCode: '',
            birthDate: '', facebookPage: '', country: '', profilePhoto: '',
            ageSport: '', ppm: '', imc: '', power: '', sportsAchievements: '',
            aboutMe: '', indMetricSys: '', discipline: '', sport: '', shoes: '', bikes: '', potentiometer: '',
            modelPotentiometer: '', pulsometer: '', modelPulsometer: '', objective: '', modality: '', environment:'',
            availability: '', twitterPage: '', instagramPage: '', webPage: '', vo2Running: '', vo2Ciclismo: '',
            injury: '', disease: '', weather:''
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
        $scope.tabIndexStar = 0;
        $scope.planSelected = null;
        $scope.selectedIndex = null;
        $scope.selectedIndex2 = null;
        $scope.wsocket;

        $scope.views = {
            profile: {page: 'static/views/dashboard/profile.html', controller: ""},
            video: {page: 'static/views/video/video.html', controller: "VideoController"},
            message: {page: 'static/views/message/message.html', controller: "MessageController"},
            audioMessage: {page: 'static/views/audioMessage/audioMessage.html', controller: "AudioMessageController"},
            email: {page: 'static/views/mail/mail.html', controller: "MailController"},
            script: {page: 'static/views/script/script.html', controller: "ScriptController"},
            report: {page: 'static/views/reports/reports.html', controller: "ReportsController"},
            control:{page: 'static/views/dashboard/control.html'}
        };

        $scope.pageSelected = $scope.views.profile.page;
        $scope.controllerSelected = null;

        $scope.calculateIMC = function () {

            if ($scope.user.weight != null && $scope.user.height != null
                    && $scope.user.weight != "" && $scope.user.height != "") {
                $scope.user.imc = Math.round($scope.user.weight / ($scope.user.height * $scope.user.height) * 10) / 10;
            } else if ($scope.user.weight == "" || $scope.user.height == "") {
                $scope.user.imc = null;
            }
        };

        $scope.getUserById = function () {

            if ($scope.appReady) {
                var user = JSON.parse(sessionStorage.getItem("userInfo"));

                if (user == null || user == undefined) {
                    $scope.setUserSession();
                }
                
                self.getDashBoardByUser(user);
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
                $scope.messagesReceivedCount++;
            }else{
               $scope.getMessageCount(); 
            }

        });
        
        $scope.onMessageReceived = function(data){
          console.log(data);  
        };
        
        $scope.connectToChatserver = function (sessionId) {
            $scope.wsocket = new WebSocket('ws://' + window.location.host + window.location.pathname + 'chat/' + sessionId);
            $scope.wsocket.onmessage = function (data) {
                var msg = JSON.parse(data.data); 
                if ($scope.userSession.userId != msg.messageUserId.userId && msg.mobile) {
                    $scope.messagesReceivedCount++;
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
                $scope.videoReceivedCount++;
            }else{
                $scope.getVideoCount();
            }

        });

        //notificación audios recibidos
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUser.userId == $scope.userSession.userId) {
                $scope.audioReceivedCount++;
            } else {
                
               $scope.getAudioCount();
            }

        });
        

        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {
                $scope.emailReceivedCount++;
            }else{
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
                } else {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                }
                self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);

                self.getReceivedAudios($scope.planSelected.id, receivedUser, tipoPlan, $scope.roleSelected);
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
                } else {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                }
                self.getAvailableMails($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);

                self.getReceivedMails($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
            }else if(userSelected != null){
                  self.getReceivedMails(-1, userSelected.userId, $scope.userSession.userId, tipoPlan, -1);
            }
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
                } else {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                }
                self.getAvailableVideos($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);

                self.getReceivedVideos($scope.planSelected.id, receivedUser, $scope.userSession.userId, tipoPlan, $scope.roleSelected);
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
                } else {
                    receivedUser = $scope.planSelected.athleteUserId.userId;
                }
                self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected);

                self.getReceivedMessages($scope.planSelected.id, receivedUser,$scope.userSession.userId, tipoPlan, $scope.roleSelected);
            }else if(userSelected != null){
                  self.getReceivedMessages(-1, userSelected.userId, $scope.userSession.userId, tipoPlan, -1);
            }
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
            $window.sessionStorage.setItem("planSelected", JSON.stringify(planSelected));
            $scope.planSelected = angular.copy(planSelected);

            if (planSelected != "" && planSelected != null && planSelected.external) {
                $scope.roleSelected = -1;
                self.initControls(planSelected, "EXT");

            } else if (planSelected != "" && planSelected != null && $scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                $scope.initControlAthlete($scope.userSessionTypeUserCoachInterno, $scope.planSelected.athleteUserId.userId);
                self.getDashBoardByUser(planSelected.athleteUserId);
            }
        };

        self.getDashBoardByUser = function (user) {
            DashboardService.getDashboard(user).then(
                    function (d) {
                        $scope.user = d;
                        $scope.calculateIMC();
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

        $scope.selectCoach = function (user, index) {

           $scope.selectedIndex2 = index;
            $scope.tabIndexStar = 1;
            $scope.pageSelected = $scope.views.profile.page;
            $scope.showProfileImage = true;
            $window.sessionStorage.setItem("selectedUser", JSON.stringify(user));
            $window.sessionStorage.setItem("planSelected", null);

            if ($scope.userSession != null) {
                messageService.initialize(user.userId + $scope.userSession.userId);
                self.getReceivedMessages(-1, user.userId,$scope.userSession.userId, "IN", -1);
                MailService.initialize(user.userId + $scope.userSession.userId);
                self.getReceivedMails(-1, user.userId,$scope.userSession.userId, "IN", -1);
                self.getDashBoardByUser(user);
            } else {
                $scope.setUserSession();
            }
        };

        $scope.selectStar = function (user, index) {

                $scope.selectedIndex2 = index;
                $scope.selectedIndex = null;
    
            $scope.pageSelected = $scope.views.profile.page;
            $scope.showProfileImage = true;
            $window.sessionStorage.setItem("selectedUser", JSON.stringify(user));
            $window.sessionStorage.setItem("planSelected", null);
            
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
                messageService.initialize(user.userId + $scope.userSession.userId);
                self.getReceivedMessages(-1, user.userId, $scope.userSession.userId, "IN", -1);
                MailService.initialize(user.userId + $scope.userSession.userId);
                self.getReceivedMails(-1, user.userId, $scope.userSession.userId, "IN", -1);
                self.getDashBoardByUser(user);
            } else {
                $scope.setUserSession();
            }
        };

        $scope.selectAthleteOption = function (roleSelected) {
            var user = null;
   
                $scope.selectedIndex = roleSelected;
            
            
            $scope.roleSelected = roleSelected;
            if (roleSelected === $scope.userSessionTypeUserCoachEstrella) {
                user = $scope.planSelected.starUserId;
            } else if (roleSelected === $scope.userSessionTypeUserCoachInterno) {
                user = $scope.planSelected.coachUserId;
            }

            //self.getDashBoardByUser(user);
            $scope.initControlAthlete(roleSelected, $scope.planSelected.coachUserId.userId );

            //$scope.showProfileImage = true;
            $scope.pageSelected = $scope.views.profile.page;
        };
        
        
        $scope.initControlAthlete = function (roleSelected, fromUser) {

          var tipoPlan = "IN";
          $scope.roleSelected = roleSelected;
          if($scope.planSelected != null){
              $scope.pageSelected = $scope.views.profile.page;
            //mensajes 
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
            self.getReceivedMessages($scope.planSelected.id, fromUser,$scope.userSession.userId, tipoPlan, roleSelected);

            messageService.initialize($scope.planSelected.id);
            $scope.connectToChatserver($scope.planSelected.id);
            
            //videos
            self.getAvailableVideos($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
            self.getReceivedVideos($scope.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

            videoService.initialize($scope.planSelected.id);

            //audios
            self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);

            self.getReceivedAudios($scope.planSelected.id, fromUser, tipoPlan, roleSelected);

            AudioMessageService.initialize($scope.planSelected.id);

            //email
            self.getAvailableMails($scope.planSelected.id, $scope.userSession.userId, tipoPlan, roleSelected);
            self.getReceivedMails($scope.planSelected.id, fromUser, $scope.userSession.userId, tipoPlan, roleSelected);

            MailService.initialize($scope.planSelected.id);
        }
        };

     
        self.initControls = function (plan, tipoPlan) {
            //mensajes 
            self.getAvailableMessages(plan.id, $scope.userSession.userId, tipoPlan, -1);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedMessages(plan.id, plan.coachUserId.userId,$scope.userSession.userId, tipoPlan, -1);
            } else {
                self.getReceivedMessages(plan.id, plan.athleteUserId.userId,$scope.userSession.userId, tipoPlan, -1);
            }
            messageService.initialize(plan.id);

            //videos
            self.getAvailableVideos(plan.id, $scope.userSession.userId, tipoPlan, -1);
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta || $scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                self.getReceivedVideos(plan.id, plan.coachUserId.userId,$scope.userSession.userId, tipoPlan, -1);
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
                self.getReceivedMails(plan.id, plan.coachUserId.userId,$scope.userSession.userId, tipoPlan, -1);
            } else {
                self.getReceivedMails(plan.id, plan.athleteUserId.userId,$scope.userSession.userId, tipoPlan, -1);
            }
            MailService.initialize(plan.id);

            //Inicializa las notificaciones de invitaciones
            if (tipoPlan == "EXT") {
                ExternalCoachService.initialize(plan.athleteUserId.userId);
            }

            self.getDashBoardByUser(plan.athleteUserId);
        };

        //Traer el plan asociado al Usuario Atleta
        $scope.getAssignedCoach = function (roleSelected) {
            var userId = $scope.userSession.userId;
            DashboardService.getAssignedCoach(userId,roleSelected).then(
                    function (data) {
                        var res = data.entity.output;
                        $window.sessionStorage.setItem("planSelected", JSON.stringify(res));
                        $scope.planSelected = angular.copy(res);
                        if (data.entity.status == 'success') {
                            if($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && !res.external){
                              $scope.selectAthleteOption(roleSelected);  
                            }else{
                            $scope.selectAthlete(res, roleSelected);
                            }
                        } else {
                            $scope.showMessage(res, "Alerta");
                        }

                    },
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
            messageService.getMessagesReceived(planId, userId,toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.messagesReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
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
            videoService.getVideosReceived(planId, fromUserId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.videoReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
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
            AudioMessageService.getAudiosReceived(planId, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.audioReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
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
            MailService.getReceivedMails(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.emailReceivedCount = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.goMessages = function () {
            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
            var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
            var userSelected = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado
            
            if($scope.userSession != null && userSelected != null){
                $scope.pageSelected = $scope.views.message.page;
            }
            else if ($scope.userSession != null && planSelected == null) {
                if (userInfo != null && userInfo.typeUser == $scope.userSessionTypeUserAtleta) {
                    $scope.showMessage("Debe tener un plan activo");
                } else {
                    $scope.showMessage("Debe seleccionar un usuario");
                }
            } else if (userInfo.typeUser == $scope.userSessionTypeUserAtleta && !planSelected.external && $scope.roleSelected == -1) {
                $scope.showMessage("Debe seleccionar un usuario");
            } else {
                $scope.pageSelected = $scope.views.message.page;
            }
        };

        $scope.goAudioMessages = function () {
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
                $scope.pageSelected = $scope.views.audioMessage.page;
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

            var planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
                var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.userSession != null && planSelected == null) {
                if (userInfo != null && userInfo.typeUser == $scope.userSessionTypeUserAtleta) {
                    $scope.showMessage("Debe tener un plan activo");
                } else {
                    $scope.showMessage("Debe seleccionar un atleta");
                }
            }
            else if (userInfo.typeUser == $scope.userSessionTypeUserAtleta && !planSelected.external && $scope.roleSelected == -1) {
                $scope.showMessage("Debe seleccionar un usuario");
            } else {
                //window.location.href = $contextPath + "#/video";
                $scope.pageSelected = $scope.views.video.page;
            }

        };

        $scope.goEmail = function () {
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
                $scope.pageSelected = $scope.views.email.page;
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
                self.getDashBoardByUser(user);
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
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        $scope.initStarControl = function(interno){
            if(interno){
                $scope.showChat = true;
                $scope.showEmail = true;
                $scope.showScript = false;
                $scope.showAudioMessage = false;
            }else{
                $scope.showScript = true;
                $scope.showAudioMessage = true;
                $scope.showChat = false;
                $scope.showEmail = false;
            }
            
        };
        
         
     $scope.init = function(){
        $scope.getUserSession(function (res) {
            $window.sessionStorage.setItem("planSelected", null);
            $window.sessionStorage.setItem("planSelectedStar", null);
            $window.sessionStorage.setItem("selectedUser", null);
            $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));

            if ($scope.userSession != null) {
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
                        $scope.getUserById();
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
                        self.getAssignedAthletes();
                        self.getAssignedStar();
                        $scope.getUserById();
                        break;
                    case $scope.userSessionTypeUserAtleta:
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
                        $scope.getUserSessionByResponse(res);
                        $scope.getUserById();
                        $scope.getAssignedCoach($scope.userSessionTypeUserCoachInterno);
                        break;
                    case $scope.userSessionTypeUserCoachEstrella:
                        $scope.showControl = true;
                        $scope.showStarControl = true;
                        $scope.showProfileImage = true;
                        //$scope.showAudioMessage = true;
                        //$scope.showChat = true;
                        //$scope.showEmail = true;
                        //$scope.showCountEmail = false;
                        $scope.getUserById();
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
                        $scope.getUserById();
                        self.getSupervisors();
                        self.getStars();
                        break;

                }
            }

        });
     };
     
     
     $scope.init();
          
    }]);

