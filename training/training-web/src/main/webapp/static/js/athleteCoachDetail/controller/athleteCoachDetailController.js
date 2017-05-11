trainingApp.controller('AthleteCoachDetailController', ['$scope', 'AthleteService', 'DashboardService','MessageService','VideoService', 'AudioMessageService','MailService','$window', '$mdDialog', '$routeParams',
    function ($scope, AthleteService, DashboardService, MessageService, VideoService, AudioMessageService, MailService, $window, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athleteUserId = $routeParams.user;
        $scope.moduleSelected = 1;
        $scope.athleteCoachView = {
            profile: 'static/views/athleteCoachDetail/profile/profile.html',
            chat: 'static/views/athleteCoachDetail/message/message.html',
            mail: 'static/views/athleteCoachDetail/mail/mail.html',
            audio: 'static/views/athleteCoachDetail/audio/audio.html',
            video: 'static/views/athleteCoachDetail/video/video.html',
            calendar: 'static/views/athleteCoachDetail/calendar/calendar.html'
        };


        $scope.goAthleteCoachMenu = function (module, index) {
            if ($scope.planSelected == null) {
                $scope.showMessage("No hay un plan activo seleccionado", "Error");
                return;
            }
            $scope.moduleSelected = index;

            switch (module) {
                case 'profile':
                    $scope.athleteCoachPageSelected = $scope.athleteCoachView.profile;
                    break;
                  case 'calendar':
                    $scope.athleteCoachPageSelected = $scope.athleteCoachView.calendar;
                    break;
                case 'chat':
                    $scope.athleteCoachPageSelected = $scope.athleteCoachView.chat;
                    break;
                case 'mail':
                    $scope.athleteCoachPageSelected = $scope.athleteCoachView.mail;
                    break;
                case 'audio':
                    $scope.athleteCoachPageSelected = $scope.athleteCoachView.audio;
                    break;
                case 'video':
                    $scope.athleteCoachPageSelected = $scope.athleteCoachView.video;
                    break;

            }

        };

        $scope.getProfile = function () {
          $scope.athleteCoachPageSelected = $scope.athleteCoachView.profile;
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

                    $scope.messageReceivedCount = $scope.planSelected.coachCommunication.receivedMsg;
                    $scope.mailReceivedCount = $scope.planSelected.coachCommunication.receivedMail;
                    $scope.audioReceivedCount =  $scope.planSelected.coachCommunication.receivedAudio;
                    $scope.videoReceivedCount =  $scope.planSelected.coachCommunication.receivedVideo;

     
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