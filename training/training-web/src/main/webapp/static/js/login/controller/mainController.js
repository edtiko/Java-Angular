// create the controller and inject Angular's $scope
trainingApp.controller('mainController', ['$http', '$scope', 'AuthService', 'MessageService', 'MailService',
    'VideoService', 'AudioMessageService', 'VisibleFieldsUserService',
    'ModuleService', 'ExternalCoachService', 'DashboardService', 'UserService',
    '$window', '$mdDialog', '$mdToast', '$location',
    function ($http, $scope, AuthService, MessageService, MailService, VideoService, AudioMessageService,
            VisibleFieldsUserService, ModuleService, ExternalCoachService, DashboardService, UserService, $window,
            $mdDialog, $mdToast, $location) {

        var self = this;
        $scope.successTextAlert = "";
        $scope.fields = [];
        $scope.visibleFields = [];
        $scope.appReady = true;
        $scope.userLogin = "";
        $scope.moduleList = [];
        $scope.userSessionTypeUserAtleta = "1";//Atleta
        $scope.userSessionTypeUserCoach = "2";//Coach
        $scope.userSessionTypeUserAdmin = "3";//Admin
        $scope.userSessionTypeUserCoachInterno = "4";//Asesor
        $scope.userSessionTypeUserCoachEstrella = "5";//Estrella
        $scope.userSessionTypeUserSupervisor = "6";//Supervisor
        $scope.typePlanTraining = 1;
        $scope.typePlanPlatform = 2;
        $scope.invitation = null;
        $scope.communicationStar = null;
        $scope.communicationSup = null;
        $scope.messageReceivedCount = 0;
        $scope.mailReceivedCount = 0;
        $scope.audioReceivedCount = 0;
        $scope.videoReceivedCount = 0;
        $scope.selectedIndex = 1;
        $scope.roleSelected = -1; //-1 No aplica | 5 Estrella | 4 Asesor 
        $scope.wsocket;
        $scope.wsAudioMobile;
        $scope.wsVideoMobile;
        $scope.profileImage = $window.sessionStorage.getItem("profileImage");
        $scope.starImage = $window.sessionStorage.getItem("starImage");
        $scope.asesorImage = $window.sessionStorage.getItem("asesorImage");
        $scope.views = {
            profile:          'static/views/dashboard/profile.html',
            summaryAthlete:   'static/views/dashboard/summaryAthlete.html',
            summaryAsesor:    'static/views/dashboard/summaryAsesor.html',
            video:            'static/views/video/video.html',
            message:          'static/views/message/message.html',
            audioMessage:     'static/views/audioMessage/audioMessage.html',
            email:            'static/views/mail/mail.html',
            emailSupervisor:  'static/views/mail/mailSupervisor.html',
            script:           'static/views/script/script.html',
            chart:            'static/views/chart/chart.html',
            report:           'static/views/report/reports.html',
            control:          'static/views/dashboard/control.html',
            messageSupervisor:'static/views/message/messageSupervisor.html',
            audioSupervisor:  'static/views/audioMessage/audioSupervisor.html',
            videoSupervisor:  'static/views/video/videoSupervisor.html',
            athletePanel :    'static/views/dashboard/userPanel.html',
            asesorPanel:      'static/views/dashboard/asesorPanel.html',
            starPanel:        'static/views/dashboard/starPanel.html',
            coachPanel:       'static/views/dashboard/coachPanel.html'
        };

        $scope.userDashboard = {userId: null, name: '', secondName: '', lastName: '', email: '', sex: '', age: '',
            weight: '', height: '', phone: '', cellphone: '', federalState: '', city: '', address: '', postalCode: '',
            birthDate: '', facebookPage: '', country: '', profilePhoto: '',
            ageSport: '', ppm: '', imc: '', power: '', sportsAchievements: '',
            aboutMe: '', indMetricSys: '', discipline: '', sport: '', shoes: '', bikes: '', potentiometer: '',
            modelPotentiometer: '', pulsometer: '', modelPulsometer: '', objective: '', modality: '', environment: '',
            availability: '', twitterPage: '', instagramPage: '', webPage: '', vo2Running: '', vo2Ciclismo: '',
            injury: '', disease: '', weather: ''
        };

        $scope.verModulo = function (module) {
            switch (module) {
                case "chat":
                    $scope.go('/message', 5);
                    break
                case "mail":
                    $scope.go('/mail', 6);
                    break;
                case "audio":
                    $scope.go('/audio-messages', 7);
                    break;
                case "video":
                    $scope.go('/video', 8);
                    break;

            }
        };

        $scope.go = function (path, index) {
            $scope.selectedIndex = index;
            $location.path(path);
        };

        $scope.goHome = function () {
            $scope.selectedIndex = 1;
            $scope.go('/dashboard', 1);
            $scope.$broadcast('home');
        };

        $scope.getDashBoardByUser = function (user) {
            DashboardService.getDashboard(user.userId).then(
                    function (d) {
                        $scope.userDashboard = angular.copy(d);
                        $scope.calculateIMC();
                        if ($scope.userDashboard.birthDate != null) {
                            var date = $scope.userDashboard.birthDate.split("/");
                            var birthdate = new Date(date[2], date[1] - 1, date[0]);
                            $scope.userDashboard.age = $scope.calculateAge(birthdate);
                        }
                        $scope.getVisibleFieldsUserByUser(user);
                        $scope.getImageProfile(user.userId, function (data) {
                            if (data != "") {
                                $scope.profileImage = "data:image/png;base64," + data;
                                $window.sessionStorage.setItem("profileImage", $scope.profileImage);
                            } else {
                                $scope.profileImage = "static/img/profile-default.png";
                            }
                        });
                        //$scope.$broadcast('profile', {userId: user.userId});
                    },
                    function (errResponse) {
                        console.error('Error while fetching the dashboard');
                        console.error(errResponse);
                    }
            );
        };

        $scope.getImageProfile = function (userId, fn) {
            if (userId != null) {
                UserService.getImageProfile(userId)
                        .then(
                                fn,
                                function (errResponse) {
                                    console.error('Error while fetching Image Profile');
                                    console.error(errResponse);
                                }
                        );
            }
        };

        $scope.calculateIMC = function () {

            if ($scope.userDashboard.weight != null && $scope.userDashboard.height != null
                    && $scope.userDashboard.weight != "" && $scope.userDashboard.height != "") {
                $scope.userDashboard.imc = Math.round($scope.userDashboard.weight / ($scope.userDashboard.height * $scope.userDashboard.height) * 10) / 10;
            } else if ($scope.userDashboard.weight == "" || $scope.userDashboard.height == "") {
                $scope.userDashboard.imc = null;
            }
        };

        $scope.viewInvitations = function (userId) {
            ExternalCoachService.getInvitation(userId).then(
                    function (response) {
                        if (response != null && response != "") {
                            $scope.invitation = angular.copy(response);
                        } else {
                            $scope.invitation = null;
                        }
                    },
                    function (error) {
                        console.log(error);
                    }

            );

        };

        $scope.switchBool = function (id) {
            var e = angular.element('#' + id);
            e.hide();
            //$scope[value] = !$scope[value];
        };
        $scope.showToast = function (msg) {
            // var pinTo = $scope.getToastPosition();
            $mdToast.show(
                    $mdToast.simple()
                    .textContent(msg)
                    .position("right")
                    .hideDelay(3000)
                    );
        };
        $scope.closeToast = function () {
            $mdToast.hide();
        };

        $scope.showMessage = function (msg, title, html = false) {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog

            var titleDefault = 'Confirmaci\u00f3n';
            if (title != "" && title != undefined) {
                titleDefault = title;
            }

            if (html) {
                $scope.message = msg;
                $scope.title = title;
                $mdDialog.show({
                    scope: $scope.$new(),
                    templateUrl: 'static/tmpls/dialogConfirmation.html',
                    parent: angular.element(document.querySelector('#trainingApp')),
                    clickOutsideToClose: true,
                    fullscreen: $scope.customFullscreen,
                    controller: function () {
                        $scope.cancel = function () {
                            $mdDialog.cancel();
                        }
                    }
                });
            } else {

                $mdDialog.show(
                        $mdDialog.alert()
                        .parent(angular.element(document.querySelector('#user-container')))
                        .clickOutsideToClose(true)
                        .title(titleDefault)
                        .textContent(msg)
                        .ariaLabel('Alert Dialog Demo')
                        .ok('Aceptar')
                        //.targetEvent(ev)
                        );
            }
        };

        $scope.showMessageConfirmation = function (msg, title, link) {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog

            var titleDefault = 'Confirmaci\u00f3n';
            if (title != "") {
                titleDefault = title;
            }

            $scope.message = msg;
            $scope.title = title;
            $scope.link = link;
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/tmpls/dialogConfirmationLink.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    }
                }
            });
        };

        $scope.goCalendar = function () {
            $window.sessionStorage.setItem("coachAssignedPlanSelected", null);
            $location.path("/calendar");
        };
        $scope.getUserSessionByResponse = function (res) {
            if (res.data.entity.output == null) {
                $scope.showMessage("El usuario no se encuentra logueado");
                $scope.logout();
                return res;
            }

            $scope.appReady = true;
            if (res.data.entity.output.secondName == null || res.data.entity.output.secondName == 'undefined') {
                $scope.userLogin = res.data.entity.output.firstName + " " + res.data.entity.output.lastName;
            } else {
                $scope.userLogin = res.data.entity.output.firstName + " " + res.data.entity.output.secondName + " " + res.data.entity.output.lastName;
            }
            //$window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
            //$scope.userSession = res.data.entity.output;
            return JSON.parse(sessionStorage.getItem("userInfo"));
        };
        $scope.setUserSession = function () {
            AuthService.setUserSession(function (res) {
                if (res.data.output == null) {
                    $scope.showMessage("El usuario no se encuentra logueado");
                    $scope.logout();
                    $("#trainingApp").removeClass("preloader");
                    return res;
                }

                $scope.appReady = true;
                if (res.data.output.secondName == null || res.data.output.secondName == 'undefined') {
                    $scope.userLogin = res.data.output.firstName + " " + res.data.output.lastName;
                } else {
                    $scope.userLogin = res.data.output.firstName + " " + res.data.output.secondName + " " + res.data.output.lastName;
                }
                try {
                    $scope.userSession = res.data.output;
                    $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.output));
                } catch (e) {
                    $window.sessionStorage.clear();
                    $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.output));
                }
               
               $scope.init();
            });
        };
        $scope.getMenuByUser = function () {
            $http.get($contextPath + '/user/getUserSession')
                    .then(function (res) {
                        //$scope.userSession = res.data.entity.output;
                        var id = res.data.output;
                        $scope.userId = id.userId;
                        $scope.roleName = id.roleName;
                        ModuleService.getModuleByUserId(id.userId).then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.moduleList = d.output;
                                    } else {
                                        $scope.showMessage(d.detail);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while merging the profile');
                                    console.error(errResponse);
                                });


                        $scope.viewInvitations($scope.userId);
                    }, function (errResponse) {
                        console.error('Error while getting ' + errResponse);
                    }
                    );
        };
        $scope.getUserSession = function (fn) {
            $http.get($contextPath + '/user/getUserSession')
                    .then(fn, function (errResponse) {
                        console.error('Error while getting ' + errResponse);
                    });
        };

        $scope.getVisibleFieldsUserByUser = function (user) {
            if (user != null) {
                VisibleFieldsUserService.getVisibleFieldsUserByUser(user)
                        .then(
                                function (response) {
                                    $scope.fields = response;
                                    for (var i = 0; i < $scope.fields.length; i++) {
                                        $scope[$scope.fields[i].tableName + $scope.fields[i].columnName] = true;
                                        if (!$scope.inFieldsArray({tableName: $scope.fields[i].tableName, columnName: $scope.fields[i].columnName, userId: user.userId}, $scope.visibleFields)) {

                                            $scope.visibleFields.push({tableName: $scope.fields[i].tableName, columnName: $scope.fields[i].columnName, userId: user.userId});
                                        }
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Visible Fields');
                                    console.error(errResponse);
                                }
                        );
            }
        };
        $scope.inFieldsArray = function (field, array) {
            var length = array.length;
            for (var i = 0; i < length; i++) {
                if (array[i].tableName == field.tableName && array[i].columnName == field.columnName)
                    return true;
            }
            return false;
        };
        $scope.calculateAge = function (birthday) { // birthday is a date
            if (birthday != null) {
                var ageDifMs = Date.now() - birthday.getTime();
                var ageDate = new Date(ageDifMs); // miliseconds from epoch
                return Math.abs(ageDate.getUTCFullYear() - 1970);
            }
        };
        $scope.logout = function () {
            window.location = $wordPressContextPath + 'mi-cuenta/customer-logout/';
        };

        $scope.account = function () {
            window.location = $wordPressContextPath + '/mi-cuenta/';
        };

        $scope.viewInvitationDialog = function () {
            $mdDialog.show({
                controller: InvitationController,
                scope: $scope.$new(),
                templateUrl: 'static/views/externalCoach/invitation.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen
            });
        };

        function InvitationController($scope, $mdDialog) {

            $scope.acceptInvitation = function () {

                ExternalCoachService.acceptInvitation($scope.invitation.id).then(
                        function (data) {
                            $scope.hide();
                            $scope.invitation = null;
                            $scope.viewInvitations($scope.userId);
                        },
                        function (error) {

                        }
                );
            };

            $scope.rejectInvitation = function () {

                ExternalCoachService.rejectInvitation($scope.invitation.id).then(
                        function (data) {
                            $scope.hide();
                            $scope.invitation = null;
                            $scope.viewInvitations($scope.userId);
                        },
                        function (error) {

                        }
                );
            };

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };


        }


        $scope.offPlanDialog = function (ev) {
            var confirm = $mdDialog.confirm()
                    .title('Confirmar')
                    .textContent('\u00BFEsta seguro de salir del plan con el Coach: ' + $scope.planSelected.coachUserId.fullName + '?')
                    .ariaLabel('Lucky day')
                    .targetEvent(ev)
                    .ok('Salir del plan')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                self.desactivarPlanActivo();
            }, function () {
                $scope.status = 'You decided to keep on the plan';
            });
        };

        self.desactivarPlanActivo = function () {
            if ($scope.planSelected != null) {
                ExternalCoachService.removeAthleteCoach($scope.planSelected.id)
                        .then(
                                function (response) {
                                    if (response.entity.status == 'success') {
                                        window.location.reload();
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting User.');
                                }
                        );
            }
        };


        $scope.onMessageReceived = function (data) {
            console.log(data);
        };

        $scope.connectToChatserver = function (sessionId) {
            $scope.wsocket = new WebSocket('wss://' + window.location.host + window.location.pathname + 'chat/' + sessionId);
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

        $scope.connectToAudioWsMovil = function (sessionId) {
            $scope.wsAudioMobile = new WebSocket('wss://' + window.location.host + window.location.pathname + 'audiows/' + sessionId);
            $scope.wsAudioMobile.onmessage = function (data) {
                var msg = JSON.parse(data.data);
            };

            $scope.wsAudioMobile.onopen = function (event) {
                console.log('Push connection from audio mobile server is working');

            };
            $scope.wsAudioMobile.onclose = function (event) {
                console.log('Error on push connection from audio mobile server ');

            };
        };

        $scope.connectToVideoWsMovil = function (sessionId) {
            $scope.wsVideoMobile = new WebSocket('wss://' + window.location.host + window.location.pathname + 'videows/' + sessionId);
            $scope.wsVideoMobile.onmessage = function (data) {
                var msg = JSON.parse(data.data);
            };

            $scope.wsVideoMobile.onopen = function (event) {
                console.log('Push connection from video mobile server is working');

            };
            $scope.wsVideoMobile.onclose = function (event) {
                console.log('Error on push connection from video mobile server ');

            };
        };


        $scope.setAthleteRole = function () {
        
            $scope.userPanel = $scope.views.athletePanel;
            $scope.getDashBoardByUser($scope.userSession);
            MessageService.initialize($scope.userSession.planSelected.id);
            VideoService.initialize($scope.userSession.planSelected.id);
            AudioMessageService.initialize($scope.userSession.planSelected.id);
            MailService.initialize($scope.userSession.planSelected.id);
            //$scope.connectToChatserver($scope.userSession.planSelected.id);
            //$scope.connectToAudioWsMovil($scope.userSession.planSelected.id);
            //$scope.connectToVideoWsMovil($scope.userSession.planSelected.id);
            $scope.messageReceivedCount = ($scope.userSession.starCommunication.receivedMsg + $scope.userSession.supervisorCommunication.receivedMsg);
            $scope.mailReceivedCount = ($scope.userSession.starCommunication.receivedMail + $scope.userSession.supervisorCommunication.receivedMail);
            $scope.audioReceivedCount = ($scope.userSession.starCommunication.receivedAudio + $scope.userSession.supervisorCommunication.receivedAudio);
            $scope.videoReceivedCount = ($scope.userSession.starCommunication.receivedVideo + $scope.userSession.supervisorCommunication.receivedVideo);
        
            $scope.getImageProfile($scope.userSession.planSelected.starUserId.userId, function (data) {
                if (data != "") {
                    $scope.starImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("starImage", $scope.starImage);
                } else {
                    $scope.starImage = "static/img/profile-default.png";
                }
            });
            $scope.getImageProfile($scope.userSession.planSelected.coachUserId.userId, function (data) {
                if (data != "") {
                    $scope.asesorImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("asesorImage", $scope.asesorImage);
                } else {
                    $scope.asesorImage = "static/img/profile-default.png";
                }

            });

            $scope.getUserNotification($scope.userSession.userId);
        };
        
        $scope.setAsesorRole = function () {
            
            $scope.userPanel = $scope.views.asesorPanel;
            $scope.getImageProfile($scope.userSession.userId, function (data) {
                if (data != "") {
                    $scope.profileImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("profileImage", $scope.profileImage);
                } else {
                    $scope.profileImage = "static/img/profile-default.png";
                }
            });

            $scope.getUserNotification($scope.userSession.userId);
        };
        
        $scope.setStarRole = function () {

            $scope.userPanel = $scope.views.starPanel;
            $scope.getImageProfile($scope.userSession.userId, function (data) {
                if (data != "") {
                    $scope.profileImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("profileImage", $scope.profileImage);
                } else {
                    $scope.profileImage = "static/img/profile-default.png";
                }
            });

            $scope.getUserNotification($scope.userSession.userId);
        };
        
        
        $scope.setCoachRole = function () {
            $scope.userPanel = $scope.views.coachPanel;
            $scope.getImageProfile($scope.userSession.userId, function (data) {
                if (data != "") {
                    $scope.profileImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("profileImage", $scope.profileImage);
                } else {
                    $scope.profileImage = "static/img/profile-default.png";
                }
            });

            $scope.getUserNotification($scope.userSession.userId);
        };
        

        $scope.getUserNotification = function (userId) {
            UserService.getUserNotification(userId).then(
                    function (data) {
                        $scope.listNotification = data;
                    }
            );
        };

        //notificación mensajes recibidos
        MessageService.receive().then(null, null, function (message) {
            if ($scope.userSession.userId != message.messageUserId.userId) {

                $scope.messageReceivedCount++;

            }

        });

        //notificación videos recibidos
        VideoService.receive().then(null, null, function (video) {
            if (video.toUser.userId == $scope.userSession.userId) {

                $scope.videoReceivedCount++;

            }

        });

        //notificación audios recibidos
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUserId == $scope.userSession.userId) {

            $scope.audioReceivedCount++;

            }

        });


        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {

                $scope.mailReceivedCount++;

            }

        });

        $scope.getReceivedAudios = function (planId, userId, tipoPlan, roleSelected, fn) {
            AudioMessageService.getAudiosReceived(planId, userId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getReceivedMails = function (planId, userId, toUserId, tipoPlan, roleSelected, fn) {
            MailService.getReceivedMails(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getReceivedVideos = function (planId, fromUserId, toUserId, tipoPlan, roleSelected, fn) {
            VideoService.getVideosReceived(planId, fromUserId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getReceivedMessages = function (planId, userId, toUserId, tipoPlan, roleSelected, fn) {
            MessageService.getMessagesReceived(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getReceived = function () {
            var tipoPlan = "IN";
            if ($scope.userSession != null) {
                var coachUserId = $scope.userSession.planSelected.coachUserId.userId;
                $scope.getReceivedMessages($scope.userSession.planSelected.id, coachUserId, $scope.userSession.userId, tipoPlan, -1,
                        function (data) {
                            $scope.messageReceivedCount = data.entity.output;
                        });
                $scope.getReceivedMails($scope.userSession.planSelected.id, coachUserId, $scope.userSession.userId, tipoPlan, -1,
                        function (data) {
                            $scope.mailReceivedCount = data.entity.output;
                        });
                $scope.getReceivedVideos($scope.userSession.planSelected.id, coachUserId, $scope.userSession.userId, tipoPlan, -1,
                        function (data) {
                            $scope.videoReceivedCount = data.entity.output;
                        });
                $scope.getReceivedAudios($scope.userSession.planSelected.id, coachUserId, tipoPlan, -1,
                        function (data) {
                            $scope.audioReceivedCount = data.entity.output;
                        });
                $scope.getUserNotification($scope.userSession.userId);
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


        //Obtener atletas de Coach Interno  
        self.getAssignedAthletes = function () {
            DashboardService.getAssignedAthletes($scope.userSession.userId).then(
                    function (data) {
                        $scope.assignedAthletes = data.output;
                        if ($scope.athletes == null) {
                            $scope.showMessage("No tiene planes asignados.");
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.init = function () {
            $scope.getUserSession(function (res) {
                $window.sessionStorage.setItem("planSelected", null);
                $window.sessionStorage.setItem("planSelectedStar", null);
                $window.sessionStorage.setItem("selectedUser", null);
                $scope.userSession = res.data.output;

                if ($scope.userSession != null) {
                    //$scope.getUserById();
                    switch ($scope.userSession.typeUser) {
                        case $scope.userSessionTypeUserCoach:
                             $scope.setCoachRole(); 
                            self.getAthletesCoachExternal();
                            break;
                        case $scope.userSessionTypeUserCoachInterno:
                            $scope.setAsesorRole();
                            break;
                        case $scope.userSessionTypeUserAtleta:
                            $scope.setAthleteRole();
                            break;
                        case $scope.userSessionTypeUserCoachEstrella:
                            $scope.setStarRole();
                            break;
                        case $scope.userSessionTypeUserAdmin:
                            $scope.getMenuByUser();
                            break;

                    }
                       $scope.$broadcast('userSession');
                }

            });
        };

        $scope.setUserSession();

    }]);

function getDate() {
    var d = new Date();
    var ano = d.getFullYear();
    var mes = (d.getMonth() + 1);
    var dia = d.getDate();
    if (mes < 10) {
        mes = '0' + mes;
    }

    if (dia < 10) {
        dia = '0' + dia;
    }

    var fechaRecibo = ano + '-' + mes + '-' + dia;
    return fechaRecibo;
}

/**
 * Elimina espacios
 * @param {type} cadena
 * @returns {String}
 */
function trim(cadena) {
    return cadena.replace(/^\s+|\s+$/gm, '');
}