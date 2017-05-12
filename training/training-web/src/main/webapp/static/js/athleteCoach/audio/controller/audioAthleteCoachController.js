trainingApp.controller("AudioAthleteCoachController", ['$scope', 'AudioMessageService', '$window', '$mdDialog', '$q', function ($scope, AudioMessageService, $window, $mdDialog, $q) {

        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = $scope.userSession.planSelected;
        $scope.audioDuration = 0;
        $scope.toUserId = $scope.planSelected.coachUserId.userId;
        $scope.timeLimit = $scope.planSelected.coachCommunication.audioDuration;
        $scope.availableAudio = $scope.planSelected.coachCommunication.availableAudio;
        $scope.audioPlan = $scope.planSelected.coachCommunication.planAudio;
        $scope.selectedIndex = 0;
        $scope.roleSelected = -1;
        $scope.query = {
            filter: '',
            limit: 2,
            page: 1
        };

        self.receivedAudios = function (tipoPlan, role, userId ,toUserId ,fn) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, userId, toUserId, "to", tipoPlan, role).then(
                    fn,
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.sendedAudios = function (tipoPlan, role, userId, toUserId, fn) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, userId ,toUserId, "from", tipoPlan, role).then(
                    fn,
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };
        

        self.getUrl = function (role) {
            var tipoPlan = "EXT";
            var date = new Date();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var year = date.getFullYear();
            var hh = date.getHours();
            var mm = date.getMinutes();
            var ss = date.getSeconds();
            $scope.dateString = "" + day + month + year + hh + mm + ss;
            var url = $contextPath + "audio/upload/" + $scope.toUserId + "/" + $scope.userSession.userId + "/" + $scope.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + "/" + role;
            return url;
        };

        $scope.uploadRecord = function (audioBlob) {
            var audio = audioBlob;
            var fd = new FormData();
            var url = self.getUrl($scope.roleSelected);
            
            if (audio != undefined && audio != null) {
                fd.append('fileToUpload', audio);
                $.ajax({
                    type: 'POST',
                    url: url,
                    data: fd,
                    processData: false,
                    contentType: false
                }).done(function (data) {
                    if (data.status == 'success') {
                        $scope.showMessage(data.message);
                        self.getAudios();
                        /*if (data.output != null) {
                            $scope.wsAudioMobile.send(JSON.stringify(data.output));
                        }*/
                    } else {
                        $scope.showMessage(data.output);
                    }
                    console.log(data);
                }).error(function (error) {
                    $scope.showMessage("Ha ocurrido un error.");
                    console.log(error);
                });
            } else {
                $scope.showMessage("Ha ocurrido un error obteniendo el audio, comuniquese con el Administrador.");
            }

        };


        $scope.playAudio = function (path, planAudioId, fromto) {

            $scope.selectedIndex = 1;
            var audioPath = path;
            var audioDiv = angular.element("#recorded");

            //marcar video como visto
            if (fromto == 'to') {
                audioDiv = angular.element("#recordedReceived");
                AudioMessageService.readAudio(planAudioId).then(
                        function (data) {
                            $scope.getReceived();
                            console.log(data.entity.output);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });

            }
            var htmlVideo = '<audio id="myaudio" controls width="800" height="475" style="width:500px">';
            htmlVideo += '<source src="' + audioPath + '" type="audio/wav"/></audio>';

            audioDiv.html(htmlVideo);

        };


        //lee los audios recibidos en tiempo real
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUser.userId == $scope.userSession.userId) {
          
                    $scope.received.push(audio);
            
            }

        });

        $scope.showRecord = function () {
            $mdDialog.show({
                controller: recordController,
                scope: $scope.$new(),
                templateUrl: 'static/views/athleteCoach/audio/recordModal.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen
            });
        };

        function recordController($scope, $mdDialog) {

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }

        self.getAvailableAudios = function (planId, userId, tipoPlan, roleSelected, fn) {
            AudioMessageService.getAvailableAudios(planId, userId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        console.error(error);
                    });
        };

        self.getAudios = function () {
            var tipoPlan = "EXT";
            self.receivedAudios(tipoPlan, $scope.userSessionTypeUserCoachEstrella, $scope.userSession.userId, $scope.toUserId, function (data) {
                $scope.received = data.output;
                $scope.loadingReceived = true;

            });
            self.sendedAudios(tipoPlan, $scope.roleSelected, $scope.userSession.userId, $scope.toUserId, function (data) {
                $scope.sended = data.output;
                $scope.loadingSent = true;

            });

            self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected,
                    function (data) {
                        $scope.availableAudio = data.entity.output;
                    });
        };

        self.init = function () {
            if ($scope.userSession != null) {
                self.getAudios();
            }
        };

        self.init();


    }]);