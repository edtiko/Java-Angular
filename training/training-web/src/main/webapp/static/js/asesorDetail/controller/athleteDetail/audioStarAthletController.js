trainingApp.controller("AudioStarAthleteController", ['$scope', 'AudioMessageService', '$window', '$mdDialog', '$q', function ($scope, AudioMessageService, $window, $mdDialog, $q) {

        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.audioDuration = 0;
        $scope.toUserId = $scope.planSelected.athleteUserId.userId;
        $scope.timeLimitStar = $scope.planSelected.starCommunication.audioDuration;
        $scope.timeLimitAsesor = $scope.planSelected.asesorCommunication.audioDuration;
        $scope.availableAudioStar = $scope.planSelected.starCommunication.availableAudio;
        $scope.audioPlanStar = $scope.planSelected.starCommunication.planAudio;
        $scope.availableAudioAsesor = $scope.planSelected.asesorCommunication.availableAudio;
        $scope.audioPlanAsesor = $scope.planSelected.asesorCommunication.planAudio;
        $scope.selectedIndex = 0;
        $scope.starImage = $window.sessionStorage.getItem("starImage");
        $scope.asesorImage = $window.sessionStorage.getItem("asesorImage");
        $scope.query = {
            filter: '',
            limit: 2,
            page: 1
        };

        self.receivedAudios = function (tipoPlan, role, fn) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, $scope.userSession.userId, "to", tipoPlan, role).then(
                    fn,
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.sendedAudios = function (tipoPlan, role, fn) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, $scope.userSession.userId, "from", tipoPlan, role).then(
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
            var url = $contextPath + "audio/upload/" + $scope.toUserId + "/" + $scope.userSession.userId + "/" + $scope.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + "/" + role;
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
                    if (data.status == 'success') {
                        $scope.showMessage(data.message);
                        self.getAudiosStar();
                        if (data.output != null) {
                            $scope.wsAudioMobile.send(JSON.stringify(data.output));
                        }
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
                    if (data.status == 'success') {
                        $scope.showMessage(data.message);
                        self.getAudiosAsesor();
                        if (data.output != null) {
                            $scope.wsAudioMobile.send(data.output);
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

        $scope.playAudioAsesor = function (path, planAudioId, fromto) {

            $scope.selectedIndex = 1;
            var audioPath = path;
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
            var audioPath = path;
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

        self.getAudiosStar = function () {
            var tipoPlan = "IN";
            self.receivedAudios(tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.receivedStar = data.output;
                $scope.loadingReceivedStar = true;

            });
            self.sendedAudios(tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.sendedStar = data.output;
                $scope.loadingSentStar = true;

            });

            self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.availableAudioStar = data.entity.output;
                    });
        };

        self.getAudiosAsesor = function () {
            var tipoPlan = "IN";
            self.receivedAudios(tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.receivedAsesor = data.output;
                $scope.loadingReceivedAsesor = true;

            });

            self.sendedAudios(tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.sendedAsesor = data.output;
                $scope.loadingSentAsesor = true;

            });


            self.getAvailableAudios($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.availableAudioAsesor = data.entity.output;
                    });
        };

        self.init = function () {
            if ($scope.userSession != null) {
                self.getAudiosStar();
                self.getAudiosAsesor();
            }
        };

        self.init();


    }]);