trainingApp.controller("VideoStarAthleteController", ['$scope', 'VideoService', 'ScriptService', 'UserService', '$timeout', '$mdDialog', '$window','$http',
    function ($scope, VideoService, ScriptService, UserService, $timeout, $mdDialog, $window, $http) {
        $scope.isRecordStar = false;
        $scope.isVisibleSendVideo = true;
        $scope.isVisibleDeleteVideo = true;
        $scope.planVideoSelected = null;
        $scope.colorGrabacionStar = '';
        $scope.counterRecordStar = 0;
        $scope.counterRecordInitialStar = 0;
        $scope.isRecordableStar = true;
        $scope.selectedIndex = 0;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.videoDurationStar = $scope.planSelected.starCommunication.videoDuration;
        $scope.availableVideoStar = $scope.planSelected.starCommunication.availableVideo;
        $scope.videoPlanStar = $scope.planSelected.starCommunication.planVideo;
        var self = this;
        var mediaSource = new MediaSource();
        mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
        var recordedBlobsStar;
        var sourceBuffer;
        $scope.mediaModelStar = null;
        $scope.starImage = $window.sessionStorage.getItem("starImage");
        $scope.asesorImage = $window.sessionStorage.getItem("asesorImage");
        $scope.query = {
            filter: '',
            limit: 2,
            page: 1
        };

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

        function handleStop(event) {
            console.log('Recorder stopped: ', event);
        }

        $scope.recordedVideoStar = '';
        $scope.gumVideoStar = '';
        $scope.mediaRecorderStar = '';

        $scope.initVideoStar = function (recordedVideo, gumVideo) {
            if (gumVideo != undefined && gumVideo != '') {
                $scope.gumVideoStar = document.querySelector('video#' + gumVideo);
            }

            $scope.recordedVideoStar = document.querySelector('video#' + recordedVideo);
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccessStar).catch(handleError);

            $scope.recordedVideoStar.addEventListener('error', function (ev) {
                console.error('MediaRecording.recordedMedia.error()');
                alert('Error al reproducir video');
                console.error('Your browser can not play\n\n' + $scope.recordedVideoStar.src
                        + '\n\n media clip. event: ' + JSON.stringify(ev));
            }, true);
        };

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

        self.stopRecordingVideoStar = function () {
            if ($scope.mediaRecorderStar.state != 'inactive' && $scope.mediaRecorderStar.state != undefined) {
                $scope.gumVideoStar.controls = false;
                $scope.mediaRecorderStar.stop();
            } else {
                $scope.recordedVideoStar.pause();
            }
        };


        $scope.cleanVideoStar = function () {
            $scope.gumVideoStar = document.querySelector('video#gumVideoStar');
            document.getElementById('gumVideoStar').currentTime = 0;
            //$scope.gumVideo.controls = false;
            $scope.gumVideoStar.src = '';
            window.stream = null;
            $scope.mediaModelStar = null;
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccessStar).catch(handleError);

        };

        $scope.playVideoModal = function () {
            var video = document.querySelector('video#recordedVideo');
            var source = document.createElement('source');
            var src = $contextPath + "video/files/" + $scope.path;
            
             video.pause();
            source.setAttribute('src', src);

            video.appendChild(source);
            video.load();
            video.play();

           /* $scope.recordedVideo = document.querySelector('video#recordedVideo');
            $scope.recordedVideo.controls = true;
            $scope.recordedVideo.src = $contextPath + "video/files/" + path;*/
        };

        $scope.playVideoStar = function (path) {
            $scope.recordedVideoStar = document.querySelector('video#recordedVideo');
            $scope.recordedVideoStar.controls = true;
            $scope.recordedVideoStar.src = $contextPath + "video/files/" + path;
        };

        $scope.playVideoLocalStar = function () {
            var superBuffer = new Blob(recordedBlobsStar, {type: 'video/webm'});
            $scope.recordedVideoStar.controls = true;
            $scope.recordedVideoStar.src = window.URL.createObjectURL(superBuffer);
        };


        $scope.playVideoRecordedStar = function () {
            $scope.recordedVideoStar.play();
        };


        $scope.savePlanVideoStar = function (url, fnResponse) {
            $scope.gumVideoStar.controls = false;
            if ($scope.mediaRecorderStar.state != 'inactive') {
                $scope.mediaRecorderStar.stop();
            }

            var blob = new Blob(recordedBlobsStar, {type: 'video/webm'});
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
        self.receivedVideos = function (tipoPlan, role, userId, toUserId, fn) {
            VideoService.getVideosByUser($scope.planSelected.id, userId, toUserId,  "to", tipoPlan, role).then(
                    fn,
                    function (error) {
                        console.error(error);
                    });

        };

        //Obtiene los videos envíados
        self.sendedVideos = function (tipoPlan, role, userId, toUserId, fn) {
            VideoService.getVideosByUser($scope.planSelected.id, userId, toUserId, "from", tipoPlan, role).then(
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

        $scope.inicioGrabarVideoStar = function () {
            $scope.colorTimeStar = '';
            $scope.counterRecordStar = $scope.counterRecordInitialStar;
            $scope.initCounterRecordStar();

            //$scope.stopVideoStar();
            $scope.isVisibleSendVideoStar = true;
            $scope.isVisibleDeleteVideoStar = true;

            $scope.colorGrabacionStar = 'color:red';
            $scope.isRecordStar = true;
            self.startRecordingVideoStar();
        };

        $scope.stopVideoStar = function () {
            if (typeof $scope.stop === "function") {
                $scope.stop();
            }
            $scope.isRecordStar = false;
            $scope.colorGrabacionStar = '';
            self.stopRecordingVideoStar();
        };

        $scope.verVideoGrabadoStar = function () {
            $scope.stopVideoStar();
            $scope.colorGrabacionStar = '';
            $scope.isRecordStar = false;
            $scope.playVideoLocalStar();
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

        $scope.verVideoStar = function (path, planVideoId, fromto, index) {
            $scope.path = path;
            $scope.numVideo = index;
            $scope.isRecordStar = false;
            $scope.planVideoSelected = planVideoId;
            $scope.isVisibleSendVideo = true;
            $scope.isVisibleDeleteVideo = false;
            $scope.showVideo();
            if (fromto == 'to') {
                VideoService.readVideo(planVideoId).then(
                        function (data) {
                            // $scope.getReceived();
                        },
                        function (error) {
                            console.error(error);
                        });

            }


        };

        $scope.enviarVideoStar = function () {    
                var url = $contextPath + "video/uploadVideo/star/to/coach/" +$scope.toUserId  + "/" +  $scope.userSession.userId+ "/"+$scope.planVideo.id;
            

            $scope.savePlanVideoStar(url,
                    function (response) {
                        if (response.data.status == 'success') {
                            $scope.showMessage(response.data.output);
                            $scope.counterRecordStar = $scope.counterRecordInitialStar;
                        } else {
                            $scope.showMessage(response.data.output, "error");
                        }
                        self.getVideosStar();
                    }
            );
        };


        //lee los videos recibidos en tiempo real
        VideoService.receive().then(null, null, function (video) {
            if (video.toUserId == $scope.userSession.userId) {
                if (video.toStar == 'true') {
                    $scope.receivedStar.push(video);
                } else {
                    $scope.receivedAsesor.push(video);
                }
            }

        });

        $scope.showStarRecord = function (video) {
            $scope.planVideo = video;
            $mdDialog.show({
                controller: starRecordController,
                scope: $scope.$new(),
                templateUrl: 'static/views/asesorDetail/athleteDetail/video/recordModal.html',
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

        $scope.showVideo = function () {
            $mdDialog.show({
                controller: showVideoController,
                scope: $scope.$new(),
                templateUrl: 'static/views/asesorDetail/athleteDetail/video/showVideoModal.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen
            });
        };
        
        function showVideoController($scope, $mdDialog) {
            
            //$scope.playVideoModal();

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }
        

        self.getAvailableVideos = function (planId, userId, toUserId, tipoPlan, roleSelected, fn) {
            VideoService.getAvailableVideos(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        console.error(error);
                    });
        };

        self.receivedScripts = function (planId, fn) {
            ScriptService.receivedScripts(planId).then(
                    fn,
                    function (error) {
                        console.error(error);
                    }
            );
        };

        self.getVideosStar = function () {
            var tipoPlan = "IN";
            var userId = $scope.userSession.userId;
            var toUserId = $scope.planSelected.athleteUserId.userId;
            self.receivedScripts($scope.planSelected.id, function (data) {
                $scope.receivedStar = data;
                $scope.loadingReceivedStar = true;

            });
            
            self.sendedVideos(tipoPlan, $scope.userSessionTypeUserCoachEstrella, userId, toUserId, function (data) {
                $scope.sendedStar = data.output;
                $scope.loadingSentStar = true;

            });

            self.getAvailableVideos($scope.planSelected.id, $scope.userSession.userId, toUserId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.availableVideoStar = data.output;
                    });
        };



        if ($scope.userSession != null) {
            $scope.colorTimeStar = '';
            $scope.toUserId = $scope.planSelected.coachUserId.userId;
            $scope.showCounterStar = true;
            $scope.counterRecordInitialStar = $scope.videoDurationStar;
            $scope.counterRecordStar = $scope.counterRecordInitialStar;

        }

        self.init = function () {

            self.getVideosStar();

        };

        self.init();

    }]);