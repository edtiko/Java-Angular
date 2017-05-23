trainingApp.controller('AthleteDetailController', ['$scope', 'AthleteService', 'DashboardService','MessageService','VideoService', 'AudioMessageService','MailService','$window', '$mdDialog', '$routeParams',
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
            DashboardService.getDashboard($scope.athleteUserId, function(res){
                  $scope.userProfile = angular.copy(res.data.output);
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
                    $scope.athleteReceivedCount = ($scope.messageReceivedCount + $scope.mailReceivedCount +  $scope.audioReceivedCount + $scope.videoReceivedCount);

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

        MessageService.receive().then(null, null, function (message) {
            if (message.id != "" && $scope.userSession != null && $scope.userSession.userId != message.messageUserId.userId) {
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