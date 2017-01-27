// create the controller and inject Angular's $scope
trainingApp.controller('mainController', ['$http', '$scope', 'AuthService', 'messageService', 'MailService',
    'videoService', 'AudioMessageService', 'VisibleFieldsUserService',
    'ModuleService', 'ExternalCoachService', 'DashboardService',
    '$window', '$mdDialog', '$mdToast', '$location',
    function ($http, $scope, AuthService, messageService, MailService, videoService, AudioMessageService,
            VisibleFieldsUserService, ModuleService, ExternalCoachService, DashboardService, $window,
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
        $scope.userSessionTypeUserCoachInterno = "4";//CoachInterno
        $scope.userSessionTypeUserCoachEstrella = "5";//CoachEstrella
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
        //$scope.selectedIndex = 1;
        $scope.roleSelected = -1; //-1 No aplica | 5 CoachEstrella | 4 CoachInterno 
        $scope.views = {
            profile: {page: 'static/views/dashboard/profile.html', controller: ""},
            summary: {page: 'static/views/dashboard/summary.html'},
            video: {page: 'static/views/video/video.html', controller: "VideoController"},
            message: {page: 'static/views/message/message.html', controller: "MessageController"},
            audioMessage: {page: 'static/views/audioMessage/audioMessage.html', controller: "AudioMessageController"},
            email: {page: 'static/views/mail/mail.html', controller: "MailController"},
            emailSupervisor: {page: 'static/views/mail/mailSupervisor.html', controller: "MailController"},
            script: {page: 'static/views/script/script.html', controller: "ScriptController"},
            chart: {page: 'static/views/chart/chart.html', controller: "ChartController"},
            report: {page: 'static/views/report/reports.html', controller: "ReportController"},
            control: {page: 'static/views/dashboard/control.html'},
            messageSupervisor: {page: 'static/views/message/messageSupervisor.html'},
            audioSupervisor: {page: 'static/views/audioMessage/audioSupervisor.html'},
            videoSupervisor: {page: 'static/views/video/audioSupervisor.html'}
        };
        //$scope.pageSelected = $scope.views.summary.page;
        $scope.userDashboard = {userId: null, name: '', secondName: '', lastName: '', email: '', sex: '', age: '',
            weight: '', height: '', phone: '', cellphone: '', federalState: '', city: '', address: '', postalCode: '',
            birthDate: '', facebookPage: '', country: '', profilePhoto: '',
            ageSport: '', ppm: '', imc: '', power: '', sportsAchievements: '',
            aboutMe: '', indMetricSys: '', discipline: '', sport: '', shoes: '', bikes: '', potentiometer: '',
            modelPotentiometer: '', pulsometer: '', modelPulsometer: '', objective: '', modality: '', environment: '',
            availability: '', twitterPage: '', instagramPage: '', webPage: '', vo2Running: '', vo2Ciclismo: '',
            injury: '', disease: '', weather: ''
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
            DashboardService.getDashboard(user).then(
                    function (d) {
                        $scope.userDashboard = angular.copy(d);
                        $scope.calculateIMC();
                        if ($scope.userDashboard.birthDate != null) {
                            var date = $scope.userDashboard.birthDate.split("/");
                            var birthdate = new Date(date[2], date[1] - 1, date[0]);
                            $scope.userDashboard.age = $scope.calculateAge(birthdate);
                        }
                        $scope.getVisibleFieldsUserByUser(user);
                        $scope.$broadcast('profile', {userId: user.userId});
                    },
                    function (errResponse) {
                        console.error('Error while fetching the dashboard');
                        console.error(errResponse);
                    }
            );
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
            if (title != "") {
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
        }
        ;

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
            $scope.getDashBoardByUser($scope.userSession);
            return JSON.parse(sessionStorage.getItem("userInfo"));
        };
        $scope.setUserSession = function () {
            AuthService.setUserSession($scope).then(
                    function (user) {
                        $scope.$broadcast('userSession', {userSession: user});
                    },
                    function (errResponse) {
                        console.error('Error while merging the profile');
                        console.error(errResponse);
                    }
            );
        };
        $scope.getMenuByUser = function () {
            $http.get($contextPath + '/user/getUserSession')
                    .then(function (res) {
                        //$scope.userSession = res.data.entity.output;
                        var id = res.data.entity.output;
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
        //$scope.userSession = null;
        //$window.sessionStorage.setItem("userInfo", null);
        $scope.setUserSession();
        $scope.getMenuByUser();

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



        var mediaSource = new MediaSource();
        mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
        var mediaRecorder;
        var recordedBlobs;
        var sourceBuffer;
        $scope.mediaModel = null;

        var constraints = {
            audio: true,
            video: true
        };

        function handleSuccess(stream) {
            console.log('getUserMedia() got stream: ', stream);
            window.stream = stream;
            if (window.URL) {
                $scope.gumVideo.src = window.URL.createObjectURL(stream);
            } else {
                $scope.gumVideo.src = stream;
            }
        }

        function handleError(error) {
            console.log('navigator.getUserMedia error: ', error);
        }

        function handleSourceOpen(event) {
            console.log('MediaSource opened');
            sourceBuffer = mediaSource.addSourceBuffer('video/webm; codecs="vp8"');
            console.log('Source buffer: ', sourceBuffer);
        }



        function handleDataAvailable(event) {
            if (event.data && event.data.size > 0) {
                recordedBlobs.push(event.data);
                $scope.mediaModel = event.data;
            }
        }

        function handleStop(event) {
            console.log('Recorder stopped: ', event);
        }

        $scope.recordedVideo = '';
        $scope.gumVideo = '';
        $scope.mediaRecorder = '';

        $scope.initVideo = function (recordedVideo, gumVideo) {
            if (gumVideo != undefined && gumVideo != '') {
                $scope.gumVideo = document.querySelector('video#' + gumVideo);
            }

            $scope.recordedVideo = document.querySelector('video#' + recordedVideo);
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccess).catch(handleError);

            $scope.recordedVideo.addEventListener('error', function (ev) {
                console.error('MediaRecording.recordedMedia.error()');
                alert('Error al reproducir video');
                console.error('Your browser can not play\n\n' + $scope.recordedVideo.src
                        + '\n\n media clip. event: ' + JSON.stringify(ev));
            }, true);
        };
        // The nested try blocks will be simplified when Chrome 47 moves to Stable
        $scope.startRecordingVideo = function () {
            $scope.mediaRecorder = '';
            if ($scope.mediaRecorder.state == undefined) {
                $scope.gumVideo.controls = false;
                recordedBlobs = [];
                var options = {mimeType: 'video/webm;codecs=vp9'};
                if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                    console.log(options.mimeType + ' is not Supported');
                    options = {mimeType: 'video/webm;codecs=vp8'};
                    if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                        console.log(options.mimeType + ' is not Supported');
                        options = {mimeType: 'video/webm'};
                        if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                            console.log(options.mimeType + ' is not Supported');
                            options = {mimeType: ''};
                        }
                    }
                }
                try {
                    $scope.mediaRecorder = new MediaRecorder(window.stream, options);
                } catch (e) {
                    console.error('Exception while creating MediaRecorder: ' + e);
                    alert('Exception while creating MediaRecorder: '
                            + e + '. mimeType: ' + options.mimeType);
                    return;
                }
                $scope.mediaRecorder.currentTime = 0;
                $scope.mediaRecorder.onstop = handleStop;
                $scope.mediaRecorder.ondataavailable = handleDataAvailable;
                $scope.mediaRecorder.start(0); // collect 10ms of data
            } else {
                $scope.mediaRecorder.start();
            }

        };

        $scope.stopRecordingVideo = function () {
            if ($scope.mediaRecorder.state != 'inactive' && $scope.mediaRecorder.state != undefined) {
                $scope.gumVideo.controls = false;
                $scope.mediaRecorder.stop();
            } else {
                $scope.recordedVideo.pause();
            }
        };

        $scope.cleanVideo = function () {
            $scope.gumVideo = document.querySelector('video#gumVideo');
            document.getElementById('gumVideo').currentTime = 0;
            //$scope.gumVideo.controls = false;
            $scope.gumVideo.src = '';
            window.stream = null;
            $scope.mediaModel = null;
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccess).catch(handleError);

        };

        $scope.playVideo = function (path) {
            $scope.recordedVideo.controls = true;
            $scope.recordedVideo.src = $contextPath + "video/files/" + path;
        };

        $scope.playVideoLocal = function () {
            var superBuffer = new Blob(recordedBlobs, {type: 'video/webm'});
            $scope.recordedVideo.controls = true;
            $scope.recordedVideo.src = window.URL.createObjectURL(superBuffer);
        };

        $scope.playVideoRecorded = function () {
            $scope.recordedVideo.play();
        };

        $scope.savePlanVideo = function (url, fnResponse) {
            $scope.gumVideo.controls = false;
            if ($scope.mediaRecorder.state != 'inactive') {
                $scope.mediaRecorder.stop();
            }

            var blob = new Blob(recordedBlobs, {type: 'video/webm'});
            var fd = new FormData();
            fd.append("fileToUpload", blob);

            $http.post(url, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                    .then(
                            fnResponse,
                            function (errResponse) {
                                console.error('Error while getting ' + errResponse);
                            }
                    );
        };

        //Traer el plan asociado al Usuario Atleta
        $scope.getAssignedPlan = function () {
            var userId = $scope.userSession.userId;
            DashboardService.getAssignedPlan(userId).then(
                    function (data) {
                        var res = data.entity.output;
                        $window.sessionStorage.setItem("planSelected", JSON.stringify(res));
                        $scope.planSelected = res;
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

        $scope.getConfigurationPlanByUser = function (planId, userId, toUserId, tipoPlan, roleSelected, fn) {
            DashboardService.getConfigurationPlanByUser(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    fn,
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

                //messageService.initialize(plan.id);
                //videoService.initialize(plan.id);
                //AudioMessageService.initialize(plan.id);
                //MailService.initialize(plan.id);
                //$scope.connectToChatserver(plan.id);

                $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (res) {
                    $scope.communicationStar = angular.copy(res);
//                    $scope.audioReceivedStar = res.receivedAudio;
//                    $scope.videoReceivedStar = res.receivedMail;
//                    $scope.emailReceivedStar = res.receivedAudio;
//                    $scope.messagesReceivedStar = res.receivedMsg;
//                    $scope.videoDurationStar = res.videoDuration;
//                    $scope.audioDurationStar = res.audioDuration;
                });

                $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno, function (res) {
                    $scope.communicationSup = angular.copy(res);
//                    $scope.audioReceivedSup = res.receivedAudio;
//                    $scope.videoReceivedSup = res.receivedMail;
//                    $scope.emailReceivedSup = res.receivedAudio;
//                    $scope.messagesReceivedSup = res.receivedMsg;
//                    $scope.videoDurationSup = res.videoDuration;
//                    $scope.audioDurationSup = res.audioDuration;
                });

            }
            //SUPERVISOR
            else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {

                //$scope.connectToChatserver(plan.id);
                //messageService.initialize(plan.id);
                //videoService.initialize(plan.id);
                //AudioMessageService.initialize(plan.id);
                //MailService.initialize(plan.id);

                if ($scope.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                    $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno, function (res) {
                        $scope.audioReceivedCount = res.receivedAudio;
                        $scope.videoReceivedCount = res.receivedMail;
                        $scope.emailReceivedCount = res.receivedAudio;
                        $scope.messagesReceivedCount = res.receivedMsg;
                        $scope.videoDurationSup = res.videoDuration;
                        $scope.audioDurationSup = res.audioDuration;
                    });
                } else if ($scope.roleSelected == userSessionTypeUserCoachEstrella) {
                    $scope.getConfigurationPlanByUser(plan.id, $scope.userSession.userId, plan.coachUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (res) {
                        $scope.audioReceivedCount = res.receivedAudio;
                        $scope.videoReceivedCount = res.receivedMail;
                        $scope.emailReceivedCount = res.receivedAudio;
                        $scope.messagesReceivedCount = res.receivedMsg;
                        $scope.videoDurationStar = res.videoDuration;
                        $scope.audioDurationStar = res.audioDuration;
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

            $window.sessionStorage.setItem("planSelected", null);
            $window.sessionStorage.setItem("planSelected", JSON.stringify($scope.planSelected));

        };

        $scope.setAthleteRole = function () {

            messageService.initialize($scope.userSession.planSelected.id);
            videoService.initialize($scope.userSession.planSelected.id);
            AudioMessageService.initialize($scope.userSession.planSelected.id);
            MailService.initialize($scope.userSession.planSelected.id);
            //$scope.connectToChatserver($scope.userSession.planSelected.id);
            $scope.messageReceivedCount = ($scope.userSession.starCommunication.receivedMsg + $scope.userSession.supervisorCommunication.receivedMsg);
            $scope.mailReceivedCount = ($scope.userSession.starCommunication.receivedMail + $scope.userSession.supervisorCommunication.receivedMail);
            $scope.audioReceivedCount = ($scope.userSession.starCommunication.receivedAudio + $scope.userSession.supervisorCommunication.receivedAudio);
            $scope.videoReceivedCount = ($scope.userSession.starCommunication.receivedVideo + $scope.userSession.supervisorCommunication.receivedVideo);
            $scope.pageSelected = $scope.views.summary.page;
        };
        
         //notificación mensajes recibidos
        messageService.receive().then(null, null, function (message) {
            if ($scope.userSession.userId != message.messageUserId.userId) {

                $scope.messageReceivedCount++;

            }

        });

        //notificación videos recibidos
        videoService.receive().then(null, null, function (video) {
            if (video.toUser.userId == $scope.userSession.userId) {

                $scope.videoReceivedCount++;

            }

        });

        //notificación audios recibidos
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUser.userId == $scope.userSession.userId) {
    
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

        $scope.getReceivedMails = function (planId, userId, toUserId, tipoPlan, roleSelected,fn) {
            MailService.getReceivedMails(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getReceivedVideos = function (planId, fromUserId, toUserId, tipoPlan, roleSelected,fn) {
            videoService.getVideosReceived(planId, fromUserId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getReceivedMessages = function (planId, userId, toUserId, tipoPlan, roleSelected, fn) {
            messageService.getMessagesReceived(planId, userId, toUserId, tipoPlan, roleSelected).then(
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
            }

        };

        //$scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));

    }]);
trainingApp.directive('stringToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (value) {
                return '' + value;
            });
            ngModel.$formatters.push(function (value) {
                return parseFloat(value);
            });
        }
    };
});
trainingApp.directive('schrollBottom', function () {
    return {
        scope: {
            schrollBottom: "="
        },
        link: function (scope, element) {
            scope.$watchCollection('schrollBottom', function (newValue) {
                if (newValue)
                {
                    $(element).scrollTop($(element)[0].scrollHeight);
                }
            });
        }
    };
});
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