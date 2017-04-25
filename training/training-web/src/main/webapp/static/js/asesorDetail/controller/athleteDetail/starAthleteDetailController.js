trainingApp.controller('StarAthleteDetailController', ['$scope', 'AthleteService', 'DashboardService','MessageService','VideoService', 'AudioMessageService','MailService','$window', '$mdDialog',
    function ($scope, AthleteService, DashboardService, MessageService, VideoService, AudioMessageService, MailService, $window, $mdDialog) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athleteUserId = $scope.starAthleteUserId;
        $scope.moduleSelected = 1;
        $scope.athleteView = {
            profile: 'static/views/asesorDetail/athleteDetail/profile/profile.html',
            chat: 'static/views/asesorDetail/athleteDetail/message/chat.html',
            mail: 'static/views/asesorDetail/athleteDetail/mail/mail.html',
            audio: 'static/views/asesorDetail/athleteDetail/audio/audio.html',
            video: 'static/views/asesorDetail/athleteDetail/video/video.html'
        };
        $scope.currentNavItem = 1;

        $scope.goStarAthleteMenu = function (module, index) {
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
                case 'audio':
                    $scope.athletePageSelected = $scope.athleteView.audio;
                    break;
                case 'video':
                    $scope.athletePageSelected = $scope.athleteView.video;
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
            AthleteService.getActivePlanStarAthlete($scope.athleteUserId, $scope.userSession.userId, function (res) {
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

                    $scope.messageReceivedCount = ($scope.planSelected.starCommunication.receivedMsg);
                    $scope.mailReceivedCount = ($scope.planSelected.starCommunication.receivedMail);
                    $scope.audioReceivedCount = ($scope.planSelected.starCommunication.receivedAudio);
                    $scope.videoReceivedCount = ($scope.planSelected.starCommunication.receivedVideo);

       
                } else {
                    $scope.showMessage("No hay un plan activo seleccionado", "Error");
                    return;
                }
                 //$scope.showLoading(false);

            });
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