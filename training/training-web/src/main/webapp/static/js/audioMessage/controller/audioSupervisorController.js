trainingApp.controller("AudioSupervisorController", ['$scope', 'AudioMessageService', 'UserService', '$window', '$mdDialog', '$q', function ($scope, AudioMessageService, UserService, $window, $mdDialog, $q) {

        var self = this;
        //$scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));

        $scope.audioDuration = 0;
        if ($scope.userSession != null) {

            if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoach || $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = $scope.userSession.planSelected.athleteUserId.userId;

            } else if ($scope.user != null && $scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.toUserId = $scope.userSession.planSelected.coachUserId.userId;

            }


            if ($scope.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                $scope.audioDuration = $scope.userSession.planSelected.trainingPlanId.audioCount;
            } else if ($scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                $scope.audioDuration = $scope.userSession.planSelected.trainingPlanId.audioCount;
            }

            //establece la duración del audio según la configuración del plan
            if ($scope.audioDuration != undefined && $scope.audioDuration != "") {
                $scope.timeLimit = $scope.audioDuration;
            } else {
                $scope.timeLimit = 0;
            }
        }
        $scope.selectedIndex = 0;

        self.receivedAudios = function (tipoPlan) {

            AudioMessageService.getAudiosByUser($scope.userSession.planSelected.id, $scope.userSession.userId, "to", tipoPlan, $scope.roleSelected).then(
                    function (data) {
                        $scope.receivedaudios = data.output;
                        $scope.loadingReceived = true;

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.sendedAudios = function (tipoPlan) {

            AudioMessageService.getAudiosByUser($scope.userSession.planSelected.id, $scope.userSession.userId, "from", tipoPlan, $scope.roleSelected).then(
                    function (data) {
                        $scope.sendedaudios = data.output;
                        $scope.loadingSent = true;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.getUrl = function () {
            var tipoPlan = "IN";
            var date = new Date();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var year = date.getFullYear();
            var hh = date.getHours();
            var mm = date.getMinutes();
            var ss = date.getSeconds();
            $scope.dateString = "" + day + month + year + hh + mm + ss;
            if ($scope.userSession.planSelected != null && $scope.userSession.planSelected.external) {
                tipoPlan = "EXT";
            }
            var url = $contextPath + "audio/upload/" + $scope.toUserId + "/" + $scope.userSession.userId + "/" + $scope.userSession.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + "/" + $scope.roleSelected;
            return url;
        };

        $scope.uploadRecord = function () {
            var audio = $scope.recordedInput;
            var fd = new FormData();
            var url = self.getUrl();
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
                    if (data.status == 'success') {
                        $scope.showMessage(data.message);
                        if ($scope.userSession.planSelected.external) {
                            self.sendedAudios("EXT");
                        } else {
                            self.sendedAudios("IN");
                        }
                        //$scope.getAudioCount();
                        if (data.output != null) {
                            $scope.wsAudioMobile.send(JSON.stringify(data.output));
                        }
                    } else {
                        $scope.showMessage(data.message);
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

            //$scope.showRecord = false;
            $scope.selectedIndex = 1;
            var audioPath = path;
            var audioDiv = angular.element("#recorded");
            var htmlVideo = '<audio id="myaudio" controls width="600" height="475" >';
            htmlVideo += '<source src="' + audioPath + '" type="audio/wav"/></audio>';

            audioDiv.html(htmlVideo);

            //marcar video como visto
            if (fromto == 'to') {
                AudioMessageService.readAudio(planAudioId).then(
                        function (data) {
                            $scope.getAudioCount();
                            console.log(data.entity.output);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });

            }
        };

        //lee los audios recibidos en tiempo real
        AudioMessageService.receive().then(null, null, function (audio) {
            if (audio.toUserId == $scope.userSession.userId) {
                $scope.receivedaudios.push(audio);
            }

        });


        if ($scope.userSession.planSelected.external) {
            self.receivedAudios("EXT");
            self.sendedAudios("EXT");
        } else {
            self.receivedAudios("IN");
            self.sendedAudios("IN");
        }

    }]);