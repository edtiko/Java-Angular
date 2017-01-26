trainingApp.controller("AudioMessageController", ['$scope', 'AudioMessageService', 'UserService', '$window', '$mdDialog', '$q', function ($scope, AudioMessageService, UserService, $window, $mdDialog, $q) {

        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.audioDuration = 0;
        $scope.toUserId = $scope.userSession.planSelected.coachUserId.userId;
        $scope.timeLimitStar = $scope.userSession.starCommunication.audioDuration;
        $scope.timeLimitAsesor = $scope.userSession.supervisorCommunication.audioDuration;
        $scope.availableAudioStar = $scope.userSession.starCommunication.availableAudio;
        $scope.audioPlanStar = $scope.userSession.starCommunication.planAudio;
        $scope.availableAudioAsesor = $scope.userSession.supervisorCommunication.availableAudio;
        $scope.audioPlanAsesor = $scope.userSession.supervisorCommunication.planAudio;
        $scope.selectedIndex = 0;

        self.receivedAudios = function (tipoPlan, role, fn) {

            AudioMessageService.getAudiosByUser($scope.userSession.planSelected.id, $scope.userSession.userId, "to", tipoPlan, role).then(
                    fn,
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.sendedAudios = function (tipoPlan, role, fn) {

            AudioMessageService.getAudiosByUser($scope.userSession.planSelected.id, $scope.userSession.userId, "from", tipoPlan, role).then(
                    fn,
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.getUrl = function (role) {
            var tipoPlan = "IN";
            var date = new Date();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var year = date.getFullYear();
            var hh = date.getHours();
            var mm = date.getMinutes();
            var ss = date.getSeconds();
            $scope.dateString = "" + day + month + year + hh + mm + ss;
            var url = $contextPath + "audio/upload/" + $scope.toUserId + "/" + $scope.userSession.userId + "/" + $scope.userSession.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + "/" + role;
            return url;
        };

        $scope.uploadRecordStar = function (audioBlob) {
            var audio = audioBlob;
            var fd = new FormData();
            var url = self.getUrl($scope.userSessionTypeUserCoachEstrella);
            //fd.append('filename', 'test.wav');
            if (audio != undefined && audio != null) {
                fd.append('fileToUpload', audio);
                $.ajax({
                    type: 'POST',
                    url: url,
                    data: fd,
                    processData: false,
                    contentType: false
                }).done(function (data) {
                    if (data.entity.status == 'success') {
                        $scope.showMessage(data.entity.output);
                        $scope.getVideosStar();
                    } else {
                        $scope.showMessage(data.entity.output);
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

        $scope.uploadRecordAsesor = function (audioBlob) {
            var audio = audioBlob;
            var fd = new FormData();
            var url = self.getUrl($scope.userSessionTypeUserCoachInterno);
            //fd.append('filename', 'test.wav');
            if (audio != undefined && audio != null) {
                fd.append('fileToUpload', audio);
                $.ajax({
                    type: 'POST',
                    url: url,
                    data: fd,
                    processData: false,
                    contentType: false
                }).done(function (data) {
                    if (data.entity.status == 'success') {
                        $scope.showMessage(data.entity.output);
                        $scope.getVideosAsesor();
                    } else {
                        $scope.showMessage(data.entity.output);
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

        $scope.playAudioAsesor = function (path, planAudioId, fromto) {

            $scope.selectedIndex = 1;
            var audioPath = $contextPath + "audio/files/audios/" + path;
            var audioDiv = angular.element("#recordedAsesor");

            //marcar video como visto
            if (fromto == 'to') {
                audioDiv = angular.element("#recordedReceivedAsesor");
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

        $scope.playAudioStar = function (path, planAudioId, fromto) {

            $scope.selectedIndex = 1;
            var audioPath = $contextPath + "audio/files/audios/" + path;
            var audioDiv = angular.element("#recordedStar");

            //marcar video como visto
            if (fromto == 'to') {
                audioDiv = angular.element("#recordedReceivedStar");
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
                if (audio.toStar == 'true') {
                    $scope.receivedStar.push(audio);
                } else {
                    $scope.receivedAsesor.push(audio);
                }
            }

        });

        $scope.showStarRecord = function () {
            $mdDialog.show({
                controller: starRecordController,
                scope: $scope.$new(),
                templateUrl: 'static/views/audioMessage/recordStarModal.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen
            });
        };

        function starRecordController($scope, $mdDialog) {

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }

        $scope.showAsesorRecord = function () {
            $mdDialog.show({
                controller: asesorRecordController,
                scope: $scope.$new(),
                templateUrl: 'static/views/audioMessage/recordAsesorModal.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen
            });
        };

        function asesorRecordController($scope, $mdDialog) {

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

        $scope.getVideosStar = function () {
            var tipoPlan = "IN";
            self.receivedAudios(tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.receivedStar = data.entity.output;
                $scope.loadingReceivedStar = true;

            });
            self.sendedAudios(tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.sendedStar = data.entity.output;
                $scope.loadingSentStar = true;

            });

            self.getAvailableAudios($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.availableAudioStar = data.entity.output;
                    });
        };

        $scope.getVideosAsesor = function () {
            var tipoPlan = "IN";
            self.receivedAudios(tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.receivedAsesor = data.entity.output;
                $scope.loadingReceivedAsesor = true;

            });

            self.sendedAudios(tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.sendedAsesor = data.entity.output;
                $scope.loadingSentAsesor = true;

            });


            self.getAvailableAudios($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.availableAudioAsesor = data.entity.output;
                    });
        };

        self.init = function () {
            if ($scope.userSession != null) {
                $scope.getVideosStar();
                $scope.getVideosAsesor();
            }
        };

        self.init();

        /* if ($scope.userSession.planSelected.external) {
         self.receivedAudios("EXT");
         self.sendedAudios("EXT");
         } else {
         self.receivedAudios("IN");
         self.sendedAudios("IN");
         }*/

    }]);