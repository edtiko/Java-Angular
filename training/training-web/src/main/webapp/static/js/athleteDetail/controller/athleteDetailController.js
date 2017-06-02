trainingApp.controller('AthleteDetailController', ['$scope', 'AthleteService', 'DashboardService', 'MessageService', 'VideoService', 'AudioMessageService', 'MailService', '$window', '$mdDialog', '$routeParams',
    function ($scope, AthleteService, DashboardService, MessageService, VideoService, AudioMessageService, MailService, $window, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athleteUserId = $routeParams.user;
        $scope.moduleSelected = 1;
        $scope.messageReceivedCount = 0;
        $scope.athleteView = {
            profile: 'static/views/athleteDetail/profile/profile.html',
            chat: 'static/views/athleteDetail/message/message.html',
            mail: 'static/views/athleteDetail/mail/mail.html',
            audio: 'static/views/athleteDetail/audio/audio.html',
            video: 'static/views/athleteDetail/video/video.html',
            calendar: 'static/views/athleteDetail/calendar/calendar.html',
            chart: 'static/views/athleteDetail/chart/chart.html'
        };
        $scope.metricSystems = [{id: 1, name: 'Metrico Decimal'}, {id: '0', name: "Anglosaj\u00f3n"}];


        $scope.goAthleteMenu = function (module, index) {
            if ($scope.planSelected == null) {
                $scope.showMessage("No hay un plan activo seleccionado", "Error");
                return;
            }
            $scope.moduleSelected = index;

            switch (module) {
                case 'profile':
                    $scope.athletePageSelected = $scope.athleteView.profile;
                    break;
                case 'chat':
                    $scope.athletePageSelected = $scope.athleteView.chat;
                    break;
                case 'mail':
                    $scope.athletePageSelected = $scope.athleteView.mail;
                    break;
                case 'calendar':
                    $scope.athletePageSelected = $scope.athleteView.calendar;
                    break;
                case 'audio':
                    $scope.athletePageSelected = $scope.athleteView.audio;
                    break;
                case 'video':
                    $scope.athletePageSelected = $scope.athleteView.video;
                    break;
                case 'chart':
                    $scope.athletePageSelected = $scope.athleteView.chart;
                    break;

            }

        };

        $scope.getProfile = function () {
            $scope.athletePageSelected = $scope.athleteView.profile;
            DashboardService.getDashboard($scope.athleteUserId, function (res) {
                $scope.userProfile = angular.copy(res.data.output);
                $scope.calculatePpm();
                $scope.calculateZone();
                $scope.calculatePaceZone();
            });

            $scope.getImageProfile($scope.athleteUserId, function (data) {
                if (data != "") {
                    $scope.athleteImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("athleteImage", $scope.athleteImage);
                } else {
                    $scope.athleteImage = "static/img/profile-default.png";
                }
            });
        };


        $scope.getActivePlan = function () {
            //$scope.showLoading(true);
            AthleteService.getActivePlan($scope.athleteUserId, function (res) {
                $scope.planSelected = res.data.output;
                if ($scope.planSelected != null) {
                    $window.sessionStorage.setItem("planSelected", JSON.stringify(res.data.output));

                    MessageService.initialize($scope.planSelected.id);
                    VideoService.initialize($scope.planSelected.id);
                    AudioMessageService.initialize($scope.planSelected.id);
                    MailService.initialize($scope.planSelected.id);
                    //$scope.connectToChatserver($scope.planSelected.id);
                    //$scope.connectToAudioWsMovil($scope.planSelected.id);
                    //$scope.connectToVideoWsMovil($scope.planSelected.id);

                    $scope.messageReceivedCount = ($scope.planSelected.starCommunication.receivedMsg + $scope.planSelected.asesorCommunication.receivedMsg);
                    $scope.mailReceivedCount = ($scope.planSelected.starCommunication.receivedMail + $scope.planSelected.asesorCommunication.receivedMail);
                    $scope.audioReceivedCount = ($scope.planSelected.starCommunication.receivedAudio + $scope.planSelected.asesorCommunication.receivedAudio);
                    $scope.videoReceivedCount = ($scope.planSelected.starCommunication.receivedVideo + $scope.planSelected.asesorCommunication.receivedVideo);
                    $scope.athleteReceivedCount = ($scope.messageReceivedCount + $scope.mailReceivedCount + $scope.audioReceivedCount + $scope.videoReceivedCount);

                    $scope.getImageProfile($scope.planSelected.starUserId.userId, function (data) {
                        if (data != "") {
                            $scope.starImage = "data:image/png;base64," + data;
                            $window.sessionStorage.setItem("starImage", $scope.starImage);
                        } else {
                            $scope.starImage = "static/img/profile-default.png";
                        }
                    });
                    $scope.getImageProfile($scope.planSelected.coachUserId.userId, function (data) {
                        if (data != "") {
                            $scope.asesorImage = "data:image/png;base64," + data;
                            $window.sessionStorage.setItem("asesorImage", $scope.asesorImage);
                        } else {
                            $scope.asesorImage = "static/img/profile-default.png";
                        }

                    });
                } else {
                    $scope.showMessage("No hay un plan activo seleccionado", "Error");
                    return;
                }
                //$scope.showLoading(false);

            });
        };


        $scope.getReceivedAthleteAsesor = function (fromUserId, planId) {
            var tipoPlan = "IN";
            if ($scope.userSession != null) {
                //var coachUserId = $scope.userSession.planSelected.coachUserId.userId;
                $scope.getReceivedMessages(planId, fromUserId, $scope.userSession.userId, tipoPlan, -1,
                        function (data) {
                            $scope.messageReceivedCount = data.output;
                        });
                $scope.getReceivedMails(planId, fromUserId, $scope.userSession.userId, tipoPlan, -1,
                        function (data) {
                            $scope.mailReceivedCount = data.output;
                        });
                $scope.getReceivedVideos(planId, fromUserId, $scope.userSession.userId, tipoPlan, -1,
                        function (data) {
                            $scope.videoReceivedCount = data.output;
                        });
                $scope.getReceivedAudios(planId, fromUserId, $scope.userSession.userId, tipoPlan, -1,
                        function (data) {
                            $scope.audioReceivedCount = data.output;
                        });
                $scope.getUserNotification($scope.userSession.userId, planId, tipoPlan);
            }

        };

        $scope.calculatePpm = function () {
            if ($scope.userProfile.ppm !== "") {
                var ppm = ($scope.userProfile.ppm * 95) / 100;

                $scope.userProfile.ppm0 = "-";
                $scope.userProfile.ppm81 = Math.round((ppm * 81) / 100);

                $scope.userProfile.ppm82 = $scope.userProfile.ppm81 + 1;
                $scope.userProfile.ppm89 = Math.round((ppm * 89) / 100);

                $scope.userProfile.ppm90 = $scope.userProfile.ppm89 + 1;
                $scope.userProfile.ppm93 = Math.round((ppm * 93) / 100);

                $scope.userProfile.ppm94 = $scope.userProfile.ppm93 + 1;
                $scope.userProfile.ppm99 = Math.round((ppm * 99) / 100);

                $scope.userProfile.ppm100 = $scope.userProfile.ppm99 + 1;
                $scope.userProfile.ppm102 = Math.round((ppm * 102) / 100);

                $scope.userProfile.ppm103 = $scope.userProfile.ppm102 + 1;
                $scope.userProfile.ppm106 = Math.round((ppm * 106) / 100);
            }
        };

        $scope.calculatePaceZone = function () {
            var pace;
            if ($scope.userProfile.testDistance !== "" && $scope.userProfile.testDistance != null
                    && $scope.userProfile.disciplineId != null) {

                if ($scope.userProfile.disciplineId == 3) {
                    pace = 20 / $scope.userProfile.testDistance;
                } else if ($scope.userProfile.disciplineId == 4) {
                    pace = 20 / ($scope.userProfile.testDistanceN / 100);
                }
                pace = (pace * 95) / 100;
                //TODO falta multiplicar la parte decimal por 6
                $scope.userProfile.ftp0 = "-";
                $scope.userProfile.ftp129 = ((pace * 129) / 100);
                var seconds = Math.round($scope.userProfile.ftp129) - $scope.userProfile.ftp129;
                seconds = Math.round(Math.abs(seconds * 60));
                $scope.userProfile.ftp129 = Math.round($scope.userProfile.ftp129) + ":" + seconds;
                $scope.userProfile.ftp114 = ((pace * 114) / 100);
                var seconds = Math.round($scope.userProfile.ftp114) - $scope.userProfile.ftp114;
                seconds = Math.round(Math.abs(seconds * 60));
                $scope.userProfile.ftp114 = Math.round($scope.userProfile.ftp114) + ":" + seconds;
                $scope.userProfile.ftp106 = ((pace * 106) / 100);
                var seconds = Math.round($scope.userProfile.ftp106) - $scope.userProfile.ftp106;
                seconds = Math.round(Math.abs(seconds * 60));
                $scope.userProfile.ftp106 = Math.round($scope.userProfile.ftp106) + ":" + seconds;
                $scope.userProfile.ftp100 = ((pace * 100) / 100);
                var seconds = Math.round($scope.userProfile.ftp100) - $scope.userProfile.ftp100;
                seconds = Math.round(Math.abs(seconds * 60));
                $scope.userProfile.ftp100 = Math.round($scope.userProfile.ftp100) + ":" + seconds;
                $scope.userProfile.ftp97 = ((pace * 97) / 100);
                var seconds = Math.round($scope.userProfile.ftp97) - $scope.userProfile.ftp97;
                seconds = Math.round(Math.abs(seconds * 60));
                $scope.userProfile.ftp97 = Math.round($scope.userProfile.ftp97) + ":" + seconds;
                $scope.userProfile.ftp90 = ((pace * 90) / 100);
                var seconds = Math.round($scope.userProfile.ftp90) - $scope.userProfile.ftp90;
                seconds = Math.round(Math.abs(seconds * 60));
                $scope.userProfile.ftp90 = Math.round($scope.userProfile.ftp90) + ":" + seconds;
            }
        };

        $scope.calculateZone = function () {
            if ($scope.userProfile.power !== "") {
                var power = ($scope.userProfile.power * 95) / 100;

                $scope.userProfile.ftp0 = "-";
                $scope.userProfile.ftp129 = Math.round((power * 129) / 100);

                $scope.userProfile.ftp114 = Math.round((power * 114) / 100);
                $scope.userProfile.ftp106 = Math.round((power * 106) / 100);

                $scope.userProfile.ftp100 = Math.round((power * 100) / 100);
                $scope.userProfile.ftp97 = Math.round((power * 97) / 100);

                $scope.userProfile.ftp90 = Math.round((power * 90) / 100);
            }
        };

        MessageService.receive().then(null, null, function (message) {
             if ($scope.userSession.userId == message.receivingUserId.userId) {
                $scope.messageReceivedCount = $scope.messageReceivedCount + 1;
            }
        });

        //notificación videos recibidos
        VideoService.receive().then(null, null, function (video) {
            if (video.toUser.userId == $scope.userSession.userId) {

                $scope.videoReceivedCount = $scope.videoReceivedCount + 1;

            }

        });

        //notificación audios recibidos
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUserId == $scope.userSession.userId) {

                $scope.audioReceivedCount = $scope.audioReceivedCount + 1;

            }

        });


        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {

                $scope.mailReceivedCount = $scope.mailReceivedCount + 1;

            }

        });

        $scope.showLoading = function (loading) {
            if (loading) {
                $mdDialog.show({
                    scope: $scope.$new(),
                    templateUrl: 'static/views/athleteDetail/loading.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: false,
                    fullscreen: $scope.customFullscreen,
                    controller: function () {
                        $mdDialog.cancel();
                    }
                });
            } else {

                $mdDialog.cancel();
            }
        };


        self.init = function () {
            $scope.getProfile();
            $scope.getActivePlan();
        };

        self.init();

    }]);