trainingApp.controller('AsesorDetailController', ['$scope', 'AsesorService', 'DashboardService','MessageService','VideoService', 'AudioMessageService','MailService','$window', '$mdDialog', '$routeParams',
    function ($scope, AthleteService, DashboardService, MessageService, VideoService, AudioMessageService, MailService, $window, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.asesorUserId = $routeParams.user;
        $scope.moduleSelected = 1;
        $scope.asesorView = {
            profile: 'static/views/asesorDetail/profile/profile.html',
            chat: 'static/views/asesorDetail/message/chat.html',
            mail: 'static/views/asesorDetail/mail/mail.html',
            athletes: 'static/views/asesorDetail/athletes/athletes.html',
            athleteDetail: 'static/views/asesorDetail/athleteDetail/athleteDetail.html'
        };


        $scope.goAsesorMenu = function (module, index) {
            $scope.moduleSelected = index;

            switch (module) {
                case 'profile':
                    $scope.asesorPageSelected = $scope.asesorView.profile;
                    break;
                case 'chat':
                    $scope.asesorPageSelected = $scope.asesorView.chat;
                    break;
                case 'mail':
                    $scope.asesorPageSelected = $scope.asesorView.mail;
                    break;
                case 'athletes':
                    $scope.asesorPageSelected = $scope.asesorView.athletes;
                    break;

            }

        };
        
        $scope.goStarAthleteDetail = function(athleteUserId){
           $scope.starAthleteUserId = athleteUserId; 
           $scope.asesorPageSelected = $scope.asesorView.athleteDetail;
           
        };

        $scope.getProfile = function () {
          $scope.asesorPageSelected = $scope.asesorView.profile;
            DashboardService.getDashboard($scope.asesorUserId, function(res){
                  $scope.userProfile = angular.copy(res.data.output);
            });

            $scope.getImageProfile($scope.asesorUserId, function (data) {
                if (data != "") {
                    $scope.asesorImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("asesorImage", $scope.asesorImage);
                } else {
                    $scope.asesorImage = "static/img/profile-default.png";
                }
            });
        };
        

        $scope.getActivePlan = function () {
            //$scope.showLoading(true);
            AthleteService.getActivePlan($scope.asesorUserId, function (res) {
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

        MessageService.receive().then(null, null, function (message) {
            if (message.id != "" && $scope.userSession != null && $scope.userSession.userId != message.messageUserId.userId) {
                 $scope.messageReceivedCount++;
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
            //$scope.getActivePlan();
        };
        
        self.init();

    }]);