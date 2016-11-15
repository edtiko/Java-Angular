trainingApp.controller("AudioMessageController", ['$scope', 'AudioMessageService', 'UserService', '$window', '$mdDialog', '$q', function ($scope, AudioMessageService, UserService, $window, $mdDialog, $q) {

        var self = this;
        $scope.planSelected = JSON.parse(sessionStorage.getItem("planSelected"));
        if ($scope.appReady && $scope.planSelected != null) {
            $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserCoach || $scope.user.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = $scope.planSelected.athleteUserId.userId;
                //$scope.toUserId = 94;

            } else if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.toUserId = $scope.planSelected.coachUserId.userId;
                // $scope.toUserId = 94;

            }
            AudioMessageService.initialize($scope.planSelected.id);
            //establece la duración del audio según la configuración del plan
            if ($scope.planSelected.trainingPlanId.audioDuration != undefined && $scope.planSelected.trainingPlanId.audioDuration != "") {
                $scope.timeLimit = $scope.planSelected.trainingPlanId.audioDuration;
            }else{
                $scope.timeLimit = 0;
            }
        }
        $scope.selectedIndex = 0;

        self.receivedAudios = function (tipoPlan) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, $scope.user.userId, "to", tipoPlan).then(
                    function (data) {
                        $scope.receivedaudios = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.sendedAudios = function (tipoPlan) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, $scope.user.userId, "from", tipoPlan).then(
                    function (data) {
                        $scope.sendedaudios = data.entity.output;
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
            if ($scope.planSelected != null && $scope.planSelected.external) {
                tipoPlan = "EXT";
            }
            var url = $contextPath + "audio/upload/" + $scope.toUserId + "/" + $scope.user.userId + "/" + $scope.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan+"/"+$scope.selectStar;
            return url;
        };

        $scope.uploadRecord = function () {
            var audio = $scope.recordedInput;
            var fd = new FormData();
            var url = self.getUrl();
            //fd.append('filename', 'test.wav');
            if(audio != undefined && audio != null){
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
                    if ($scope.planSelected.external) {
                        self.sendedAudios("EXT");
                    } else {
                        self.sendedAudios("IN");
                    }
                } else {
                    $scope.showMessage(data.entity.output);
                }
                console.log(data);
            }).error(function (error) {
                $scope.showMessage("Ha ocurrido un error.");
                console.log(error);
            });
        }else{
           $scope.showMessage("Ha ocurrido un error obteniendo el audio, comuniquese con el Administrador."); 
        }

        };

        $scope.playAudio = function (path, planAudioId, fromto) {

            //$scope.showRecord = false;
            $scope.selectedIndex = 1;
            var audioPath = $contextPath + "audio/files/audios/" + path;
            var audioDiv = angular.element("#recorded");
            var htmlVideo = '<audio id="myaudio" controls width="600" height="475" >';
            htmlVideo += '<source src="' + audioPath + '" type="audio/wav"/></audio>';

            audioDiv.html(htmlVideo);

            //marcar video como visto
            if (fromto == 'to') {
                AudioMessageService.readAudio(planAudioId).then(
                        function (data) {
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
            if (audio.toUserId == $scope.user.userId) {
                $scope.receivedaudios.push(audio);
            }

        });


        if ($scope.planSelected.external) {
            self.receivedAudios("EXT");
            self.sendedAudios("EXT");
        } else {
            self.receivedAudios("IN");
            self.sendedAudios("IN");
        }

    }]);