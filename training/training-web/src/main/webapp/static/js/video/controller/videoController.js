trainingApp.controller("VideoController", ['$scope', 'videoService', 'UserService', '$timeout', '$mdDialog', '$window',
    function ($scope, videoService, UserService, $timeout, $mdDialog, $window) {
        $scope.guion = '';
        $scope.isToStar = false;
        $scope.showGuion = false;
        $scope.isRecord = false;
        $scope.isVisibleSendVideo = true;
        $scope.isVisibleDeleteVideo = true;
        $scope.isSendToStar = false;
        $scope.isVisibleToRefuseAtlethe = false;
        $scope.isSendToAtlethe = false;
        $scope.planVideoSelected = null;
        $scope.colorGrabacion = '';
        $scope.counterRecord = 0;
        $scope.counterRecordInitial = 0;
        $scope.isRecordable = true;
        $scope.selectedIndex = 0;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.videoDurationStar = $scope.userSession.starCommunication.videoDuration;
        $scope.videoDurationAsesor = $scope.userSession.supervisorCommunication.videoDuration;
        $scope.availableVideoStar = $scope.userSession.starCommunication.availableVideo;
        $scope.videoPlanStar = $scope.userSession.starCommunication.planVideo;
        $scope.availableVideoAsesor = $scope.userSession.supervisorCommunication.availableVideo;
        $scope.videoPlanAsesor = $scope.userSession.supervisorCommunication.planVideo;
        var self = this;
        var mediaSource = new MediaSource();
        mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
        var recordedBlobsStar;
        var recordedBlobsAsesor;
        var sourceBuffer;
        $scope.mediaModelStar = null;
        $scope.mediaModelAsesor = null;

        var constraints = {
            audio: true,
            video: true
        };

        function handleSuccessStar(stream) {
            console.log('getUserMedia() got stream: ', stream);
            window.stream = stream;
            if (window.URL) {
                $scope.gumVideoStar.src = window.URL.createObjectURL(stream);
            } else {
                $scope.gumVideoStar.src = stream;
            }
        }

        function handleSuccessAsesor(stream) {
            console.log('getUserMedia() got stream: ', stream);
            window.stream = stream;
            if (window.URL) {
                $scope.gumVideoAsesor.src = window.URL.createObjectURL(stream);
            } else {
                $scope.gumVideoAsesor.src = stream;
            }
        }

        function handleError(error) {
            console.log('navigator.getUserMedia error: ', error);
        }

        function handleSourceOpen(event) {
            console.log('MediaSource opened');
            sourceBuffer = mediaSource.addSourceBuffer('video/webm; codecs="vp8"');
            console.log('Source buffer: ', sourceBuffer);
        }



        function handleDataAvailableStar(event) {
            if (event.data && event.data.size > 0) {
                recordedBlobsStar.push(event.data);
                $scope.mediaModelStar = event.data;
            }
        }

        function handleDataAvailableAsesor(event) {
            if (event.data && event.data.size > 0) {
                recordedBlobsAsesor.push(event.data);
                $scope.mediaModelAsesor = event.data;
            }
        }

        function handleStop(event) {
            console.log('Recorder stopped: ', event);
        }

        $scope.recordedVideoStar = '';
        $scope.recordedVideoAsesor = '';
        $scope.gumVideoStar = '';
        $scope.gumVideoAsesor = '';
        $scope.mediaRecorderStar = '';
        $scope.mediaRecorderAsesor = '';

        // The nested try blocks will be simplified when Chrome 47 moves to Stable
        self.startRecordingVideoStar = function () {
            $scope.mediaRecorderStar = '';
            if ($scope.mediaRecorderStar.state == undefined) {
                $scope.gumVideoStar.controls = false;
                recordedBlobsStar = [];
                var options = {mimeType: 'video/webm;codecs=vp9'};
                if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                    console.log(options.mimeType + ' is not Supported');
                    options = {mimeType: 'video/webm;codecs=vp8'};
                    if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                        console.log(options.mimeType + ' is not Supported');
                        options = {mimeType: 'video/webm'};
                        if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                            console.log(options.mimeType + ' is not Supported');
                            options = {mimeType: ''};
                        }
                    }
                }
                try {
                    $scope.mediaRecorderStar = new MediaRecorder(window.stream, options);
                } catch (e) {
                    console.error('Exception while creating MediaRecorder: ' + e);
                    alert('Exception while creating MediaRecorder: '
                            + e + '. mimeType: ' + options.mimeType);
                    return;
                }
                $scope.mediaRecorderStar.currentTime = 0;
                $scope.mediaRecorderStar.onstop = handleStop;
                $scope.mediaRecorderStar.ondataavailable = handleDataAvailableStar;
                $scope.mediaRecorderStar.start(0); // collect 10ms of data
            } else {
                $scope.mediaRecorderStar.start();
            }

        };

        self.startRecordingVideoAsesor = function () {
            $scope.mediaRecorderAsesor = '';
            if ($scope.mediaRecorderAsesor.state == undefined) {
                $scope.gumVideoAsesor.controls = false;
                recordedBlobsAsesor = [];
                var options = {mimeType: 'video/webm;codecs=vp9'};
                if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                    console.log(options.mimeType + ' is not Supported');
                    options = {mimeType: 'video/webm;codecs=vp8'};
                    if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                        console.log(options.mimeType + ' is not Supported');
                        options = {mimeType: 'video/webm'};
                        if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                            console.log(options.mimeType + ' is not Supported');
                            options = {mimeType: ''};
                        }
                    }
                }
                try {
                    $scope.mediaRecorderStar = new MediaRecorder(window.stream, options);
                } catch (e) {
                    console.error('Exception while creating MediaRecorder: ' + e);
                    alert('Exception while creating MediaRecorder: '
                            + e + '. mimeType: ' + options.mimeType);
                    return;
                }
                $scope.mediaRecorderAsesor.currentTime = 0;
                $scope.mediaRecorderAsesor.onstop = handleStop;
                $scope.mediaRecorderAsesor.ondataavailable = handleDataAvailableAsesor;
                $scope.mediaRecorderAsesor.start(0); // collect 10ms of data
            } else {
                $scope.mediaRecorderAsesor.start();
            }

        };

        self.stopRecordingVideoStar = function () {
            if ($scope.mediaRecorderStar.state != 'inactive' && $scope.mediaRecorderStar.state != undefined) {
                $scope.gumVideoStar.controls = false;
                $scope.mediaRecorderStar.stop();
            } else {
                $scope.recordedVideoStar.pause();
            }
        };

        self.stopRecordingVideoAsesor = function () {
            if ($scope.mediaRecorderAsesor.state != 'inactive' && $scope.mediaRecorderAsesor.state != undefined) {
                $scope.gumVideoAsesor.controls = false;
                $scope.mediaRecorderAsesor.stop();
            } else {
                $scope.recordedVideoAsesor.pause();
            }
        };

        self.cleanVideoStar = function () {
            $scope.gumVideoStar = document.querySelector('video#gumVideoStar');
            document.getElementById('gumVideoStar').currentTime = 0;
            //$scope.gumVideo.controls = false;
            $scope.gumVideoStar.src = '';
            window.stream = null;
            $scope.mediaModelStar = null;
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccessStar).catch(handleError);

        };

        self.cleanVideoAsesor = function () {
            $scope.gumVideoAsesor = document.querySelector('video#gumVideoAsesor');
            document.getElementById('gumVideoAsesor').currentTime = 0;
            //$scope.gumVideo.controls = false;
            $scope.gumVideoAsesor.src = '';
            window.stream = null;
            $scope.mediaModelAsesor = null;
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccessAsesor).catch(handleError);

        };

        $scope.playVideoStar = function (path) {
            $scope.recordedVideoStar.controls = true;
            $scope.recordedVideoStar.src = $contextPath + "video/files/" + path;
        };

        $scope.playVideoAsesor = function (path) {
            $scope.recordedVideoAsesor.controls = true;
            $scope.recordedVideoAsesor.src = $contextPath + "video/files/" + path;
        };

        $scope.playVideoLocalStar = function () {
            var superBuffer = new Blob(recordedBlobsStar, {type: 'video/webm'});
            $scope.recordedVideoStar.controls = true;
            $scope.recordedVideoStar.src = window.URL.createObjectURL(superBuffer);
        };

        $scope.playVideoLocalAsesor = function () {
            var superBuffer = new Blob(recordedBlobsAsesor, {type: 'video/webm'});
            $scope.recordedVideoAsesor.controls = true;
            $scope.recordedVideoAsesor.src = window.URL.createObjectURL(superBuffer);
        };

        $scope.playVideoRecordedStar = function () {
            $scope.recordedVideoStar.play();
        };

        $scope.playVideoRecordedAsesor = function () {
            $scope.recordedVideoAsesor.play();
        };

        $scope.savePlanVideoStar = function (url, fnResponse) {
            $scope.gumVideoStar.controls = false;
            if ($scope.mediaRecorderStar.state != 'inactive') {
                $scope.mediaRecorderStar.stop();
            }

            var blob = new Blob(recordedBlobs, {type: 'video/webm'});
            var fd = new FormData();
            fd.append("fileToUpload", blob);

            $http.post(url, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                    .then(
                            fnResponse,
                            function (errResponse) {
                                console.error('Error while getting ' + errResponse);
                            }
                    );
        };

        $scope.savePlanVideoAsesor = function (url, fnResponse) {
            $scope.gumVideoAsesor.controls = false;
            if ($scope.mediaRecorderAsesor.state != 'inactive') {
                $scope.mediaRecorderAsesor.stop();
            }

            var blob = new Blob(recordedBlobs, {type: 'video/webm'});
            var fd = new FormData();
            fd.append("fileToUpload", blob);

            $http.post(url, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                    .then(
                            fnResponse,
                            function (errResponse) {
                                console.error('Error while getting ' + errResponse);
                            }
                    );
        };

        //Obtiene los videos recibidos 
        self.receivedVideos = function (tipoPlan, role, fn) {
            videoService.getVideosByUser($scope.userSession.planSelected.id, $scope.userSession.userId, "to", tipoPlan, role).then(
                    fn,
                    function (error) {
                        console.error(error);
                    });

        };

        //Obtiene los videos envíados
        self.sendedVideos = function (tipoPlan, role, fn) {
            videoService.getVideosByUser($scope.userSession.planSelected.id, $scope.userSession.userId, "from", tipoPlan, role).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });


        };

        $scope.initCounterRecordStar = function () {
            $scope.colorTimeStar = 'color:red';
            $scope.onTimeout = function () {
                if ($scope.counterRecordStar <= 0) {
                    $scope.stop();
                    $scope.stopVideoStar();
                    return;
                }
                $scope.counterRecordStar--;
                mytimeout = $timeout($scope.onTimeout, 1000);
            };
            var mytimeout = $timeout($scope.onTimeout, 1000);

            $scope.stop = function () {
                $timeout.cancel(mytimeout);
            };
        };

        $scope.initCounterRecordAsesor = function () {
            $scope.colorTimeAsesor = 'color:red';
            $scope.onTimeout = function () {
                if ($scope.counterRecordAsesor <= 0) {
                    $scope.stop();
                    $scope.stopVideoAsesor();
                    return;
                }
                $scope.counterRecordAsesor--;
                mytimeout = $timeout($scope.onTimeout, 1000);
            };
            var mytimeout = $timeout($scope.onTimeout, 1000);

            $scope.stop = function () {
                $timeout.cancel(mytimeout);
            };
        };

        $scope.inicioGrabarVideoStar = function () {
            $scope.colorTimeStar = '';
            $scope.counterRecordStar = $scope.counterRecordInitialStar;
            $scope.initCounterRecordStar();

            $scope.stopVideoStar();
            $scope.isVisibleSendVideoStar = true;
            $scope.isVisibleDeleteVideoStar = true;

            $scope.colorGrabacionStar = 'color:red';
            $scope.isRecordStar = true;
            $scope.startRecordingVideo();
        };

        $scope.inicioGrabarVideoAsesor = function () {
            $scope.colorTimeAsesor = '';
            $scope.counterRecordAsesor = $scope.counterRecordInitialAsesor;
            $scope.initCounterRecordAsesor();

            $scope.stopVideoAsesor();
            $scope.isVisibleSendVideo = true;
            $scope.isVisibleDeleteVideo = true;

            $scope.colorGrabacion = 'color:red';
            $scope.isRecordAsesor = true;
            $scope.startRecordingVideoAsesor();
        };

        $scope.stopVideoStar = function () {
            if (typeof $scope.stop === "function") {
                $scope.stop();
            }
            $scope.isRecordStar = false;
            $scope.colorGrabacionStar = '';
            self.stopRecordingVideoStar();
        };

        $scope.stopVideoAsesor = function () {
            if (typeof $scope.stop === "function") {
                $scope.stop();
            }
            $scope.isRecord = false;
            $scope.colorGrabacion = '';
            self.stopRecordingVideoAsesor();
        };

        $scope.verVideoGrabadoStar = function () {
            $scope.stopVideoStar();
            $scope.colorGrabacionStar = '';
            $scope.isRecordStar = false;
            $scope.playVideoLocalStar();
        };

        $scope.verVideoGrabadoAsesor = function () {
            $scope.stopVideoAsesor();
            $scope.colorGrabacionStar = '';
            $scope.isRecordStar = false;
            $scope.playVideoLocalAsesor();
        };

        $scope.verVideoRecibidoStar = function () {
            $scope.playVideoRecordedStar();
        };

        $scope.eliminarVideoGrabadoStar = function () {
            $scope.isRecordStar = false;
            $scope.counterRecordStar = $scope.counterRecordInitialStar;
            $scope.cleanVideoStar();
            $scope.colorGrabacionStar = '';
        };

        $scope.eliminarVideoGrabadoAsesor = function () {
            $scope.isRecordAsesor = false;
            $scope.counterRecordAsesor = $scope.counterRecordInitialAsesor;
            $scope.cleanVideoAsesor();
            $scope.colorGrabacionAsesor = '';
        };


        $scope.verVideoStar = function (path, planVideoId, fromto) {
            $scope.isRecordStar = false;
            $scope.planVideoSelected = planVideoId;
            if (planVideoId.fromUserId != undefined) {
                UserService.getUserById(planVideoId.fromUserId)
                        .then(
                                function (d) {

                                    $scope.isVisibleSendVideo = true;
                                    $scope.isVisibleDeleteVideo = false;

                                    $scope.playVideoStar(path);
                                    if (fromto == 'to') {
                                        videoService.readVideo(planVideoId.id).then(
                                                function (data) {
                                                    $scope.getVideoCount();
                                                },
                                                function (error) {
                                                    //$scope.showMessage(error);
                                                    console.error(error);
                                                });

                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            } else {
                $scope.playVideoStar(path);
            }

        };

        $scope.verVideoAsesor = function (path, planVideoId, fromto) {
            $scope.isRecordStar = false;
            $scope.planVideoSelected = planVideoId;
            if (planVideoId.fromUserId != undefined) {
                UserService.getUserById(planVideoId.fromUserId)
                        .then(
                                function (d) {

                                    $scope.isVisibleSendVideo = true;
                                    $scope.isVisibleDeleteVideo = false;

                                    $scope.playVideoAsesor(path);
                                    if (fromto == 'to') {
                                        videoService.readVideo(planVideoId.id).then(
                                                function (data) {
                                                    $scope.getVideoCount();
                                                },
                                                function (error) {
                                                    //$scope.showMessage(error);
                                                    console.error(error);
                                                });

                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            } else {
                $scope.playVideoAsesor(path);
            }

        };

        $scope.enviarVideoStar = function (role) {
            $scope.colorGrabacionStar = '';
            var tipoPlan = "IN";
            var date = new Date();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var year = date.getFullYear();
            var hh = date.getHours();
            var mm = date.getMinutes();
            var ss = date.getSeconds();
            $scope.dateString = "" + day + month + year + hh + mm + ss;

            var url = $contextPath + "video/upload/" + $scope.toUserId + "/" + $scope.userSession.userId + "/";
            url += $scope.userSession.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + "/" + role;

            $scope.savePlanVideo(url,
                    function (response) {
                        if (response.data.entity.status == 'success') {
                            $scope.showMessage("Video cargado correctamente.");
                            var video = response.data.entity.output;
                            if (video != "") {
                                video.sesionId = $scope.userSession.planSelected.id;

                            }
                            $scope.colorTimeStar = '';
                            $scope.counterRecordStar = $scope.counterRecordInitialStar;
                        } else {
                            $scope.showMessage(response.data.entity.output, "error");
                        }
                        self.getVideosStar();
                    }
            );
        };

        $scope.enviarVideoAsesor = function (role) {
            $scope.colorGrabacionAsesor = '';
            var tipoPlan = "IN";
            var date = new Date();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var year = date.getFullYear();
            var hh = date.getHours();
            var mm = date.getMinutes();
            var ss = date.getSeconds();
            $scope.dateString = "" + day + month + year + hh + mm + ss;

            var url = $contextPath + "video/upload/" + $scope.toUserId + "/" + $scope.userSession.userId + "/";
            url += $scope.userSession.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + "/" + role;

            $scope.savePlanVideo(url,
                    function (response) {
                        if (response.data.entity.status == 'success') {
                            $scope.showMessage("Video cargado correctamente.");
                            var video = response.data.entity.output;
                            if (video != "") {
                                video.sesionId = $scope.userSession.planSelected.id;
                            }
                            $scope.colorTimeAsesor = '';
                            $scope.counterRecordAsesor = $scope.counterRecordInitialAsesor;
                        } else {
                            $scope.showMessage(response.data.entity.output, "error");
                        }
                        self.getVideosAsesor();
                    }
            );
        };


        //lee los videos recibidos en tiempo real
        videoService.receive().then(null, null, function (video) {
            if (video.toUser.userId == $scope.userSession.userId) {
                $scope.receivedvideos.push(video);
            }

        });

        $scope.showStarRecord = function () {
            $mdDialog.show({
                controller: starRecordController,
                scope: $scope.$new(),
                templateUrl: 'static/views/video/recordStarModal.html',
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
                templateUrl: 'static/views/video/recordAsesorModal.html',
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

        self.getAvailableVideos = function (planId, userId, tipoPlan, roleSelected, fn) {
            videoService.getAvailableVideos(planId, userId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        console.error(error);
                    });
        };

        self.getVideosStar = function () {
            var tipoPlan = "IN";
            self.receivedVideos(tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.receivedStar = data.entity.output;
                $scope.loadingReceivedStar = true;

            });
            self.sendedVideos(tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.sendedStar = data.entity.output;
                $scope.loadingSentStar = true;

            });

            self.getAvailableVideos($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.availableVideoStar = data.entity.output;
                    });
        };

        self.getVideosAsesor = function () {
            var tipoPlan = "IN";
            self.receivedVideos(tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.receivedAsesor = data.entity.output;
                $scope.loadingReceivedAsesor = true;

            });

            self.sendedVideos(tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.sendedAsesor = data.entity.output;
                $scope.loadingSentAsesor = true;

            });


            self.getAvailableVideos($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.availableVideoAsesor = data.entity.output;
                    });
        };


        if ($scope.userSession != null) {
            $scope.colorTimeStar = '';
            $scope.colorTimeAsesor = '';
            $scope.toUserId = $scope.userSession.planSelected.coachUserId.userId;
            $scope.showCounterStar = true;
            $scope.showCounterAsesor = true;
            $scope.counterRecordInitialStar = $scope.videoDurationStar;
            $scope.counterRecordStar = $scope.counterRecordInitialStar;
            $scope.counterRecordInitialAsesor = $scope.videoDurationAsesor;
            $scope.counterRecordAsesor = $scope.counterRecordInitialAsesor;

        }

        self.init = function () {

            self.getVideosStar();
            self.getVideosAsesor();

        };

        self.init();

    }]);