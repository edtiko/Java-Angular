trainingApp.controller("VideoCoachController", ['$scope', 'VideoService', 'UserService', '$timeout', '$mdDialog', '$window', '$http',
    function ($scope, VideoService, UserService, $timeout, $mdDialog, $window, $http) {
        $scope.isRecord = false;
        $scope.isVisibleSendVideo = true;
        $scope.isVisibleDeleteVideo = true;
        $scope.planVideoSelected = null;
        $scope.colorGrabacion = '';
        $scope.counterRecord = 0;
        $scope.counterRecordInitial = 0;
        $scope.isRecordable = true;
        $scope.selectedIndex = 0;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.videoDuration = $scope.planSelected.coachCommunication.videoDuration;
        $scope.availableVideo = $scope.planSelected.coachCommunication.availableVideo;
        $scope.videoPlan = $scope.planSelected.coachCommunication.planVideo;
        var self = this;
        var mediaSource = new MediaSource();
        mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
        var recordedBlobs;
        var sourceBuffer;
        $scope.mediaModel = null;
        $scope.query = {
            filter: '',
            limit: 2,
            page: 1
        };
        $scope.roleSelected = -1;

        var constraints = {
            audio: true,
            video: true
        };

        function handleSuccess(stream) {
            console.log('getUserMedia() got stream: ', stream);
            window.stream = stream;
            if (window.URL) {
                $scope.gumVideo.src = window.URL.createObjectURL(stream);
            } else {
                $scope.gumVideo.src = stream;
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



        function handleDataAvailable(event) {
            if (event.data && event.data.size > 0) {
                recordedBlobs.push(event.data);
                $scope.mediaModel = event.data;
            }
        }


        function handleStop(event) {
            console.log('Recorder stopped: ', event);
        }

        $scope.recordedVideo = '';
        $scope.gumVideo = '';
        $scope.mediaRecorder = '';

        $scope.initVideo = function (recordedVideo, gumVideo) {
            if (gumVideo != undefined && gumVideo != '') {
                $scope.gumVideo = document.querySelector('video#' + gumVideo);
            }

            $scope.recordedVideo = document.querySelector('video#' + recordedVideo);
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccess).catch(handleError);

            $scope.recordedVideo.addEventListener('error', function (ev) {
                console.error('MediaRecording.recordedMedia.error()');
                alert('Error al reproducir video');
                console.error('Your browser can not play\n\n' + $scope.recordedVideo.src
                        + '\n\n media clip. event: ' + JSON.stringify(ev));
            }, true);
        };


        // The nested try blocks will be simplified when Chrome 47 moves to Stable
        self.startRecordingVideo = function () {
            $scope.mediaRecorder = '';
            if ($scope.mediaRecorder.state == undefined) {
                $scope.gumVideo.controls = false;
                recordedBlobs = [];
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
                    $scope.mediaRecorder = new MediaRecorder(window.stream, options);
                } catch (e) {
                    console.error('Exception while creating MediaRecorder: ' + e);
                    alert('Exception while creating MediaRecorder: '
                            + e + '. mimeType: ' + options.mimeType);
                    return;
                }
                $scope.mediaRecorder.currentTime = 0;
                $scope.mediaRecorder.onstop = handleStop;
                $scope.mediaRecorder.ondataavailable = handleDataAvailable;
                $scope.mediaRecorder.start(0); // collect 10ms of data
            } else {
                $scope.mediaRecorder.start();
            }

        };

        self.stopRecordingVideo = function () {
            if ($scope.mediaRecorder.state != 'inactive' && $scope.mediaRecorder.state != undefined) {
                $scope.gumVideo.controls = false;
                $scope.mediaRecorder.stop();
            } else {
                $scope.recordedVideo.pause();
            }
        };


        $scope.cleanVideo = function () {
            $scope.gumVideo = document.querySelector('video#gumVideo');
            document.getElementById('gumVideo').currentTime = 0;
            //$scope.gumVideo.controls = false;
            $scope.gumVideo.src = '';
            window.stream = null;
            $scope.mediaModel = null;
            navigator.mediaDevices.getUserMedia(constraints).
                    then(handleSuccess).catch(handleError);

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

        };

        $scope.playVideo = function (path) {
            $scope.recordedVideo = document.querySelector('video#recordedVideo');
            $scope.recordedVideo.controls = true;
            $scope.recordedVideo.src = $contextPath + "video/files/" + path;
        };

        $scope.playVideoLocal = function () {
            var superBuffer = new Blob(recordedBlobs, {type: 'video/webm'});
            $scope.recordedVideo.controls = true;
            $scope.recordedVideo.src = window.URL.createObjectURL(superBuffer);
        };



        $scope.playVideoRecorded = function () {
            $scope.recordedVideo.play();
        };


        $scope.savePlanVideo = function (url, fnResponse) {
            $scope.gumVideo.controls = false;
            if ($scope.mediaRecorder.state != 'inactive') {
                $scope.mediaRecorder.stop();
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
        self.receivedVideos = function (tipoPlan, role, userId, toUserId, fn) {
            VideoService.getVideosByUser($scope.planSelected.id, userId, toUserId, "to", tipoPlan, role).then(
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

        $scope.initCounterRecord = function () {
            $scope.colorTime = 'color:red';
            $scope.onTimeout = function () {
                if ($scope.counterRecord <= 0) {
                    $scope.stop();
                    $scope.stopVideo();
                    return;
                }
                $scope.counterRecord--;
                mytimeout = $timeout($scope.onTimeout, 1000);
            };
            var mytimeout = $timeout($scope.onTimeout, 1000);

            $scope.stop = function () {
                $timeout.cancel(mytimeout);
            };
        };


        $scope.inicioGrabarVideo = function () {
            $scope.colorTime = '';
            $scope.counterRecord = $scope.counterRecordInitial;
            $scope.initCounterRecord();

            //$scope.stopVideo();
            $scope.isVisibleSendVideo = true;
            $scope.isVisibleDeleteVideo = true;

            $scope.colorGrabacion = 'color:red';
            $scope.isRecord = true;
            self.startRecordingVideo();
        };


        $scope.stopVideo = function () {
            if (typeof $scope.stop === "function") {
                $scope.stop();
            }
            $scope.isRecord = false;
            $scope.colorGrabacion = '';
            self.stopRecordingVideo();
        };

        $scope.verVideoGrabado = function () {
            $scope.stopVideo();
            $scope.colorGrabacion = '';
            $scope.isRecord = false;
            $scope.playVideoLocal();
        };

        $scope.verVideoRecibido = function () {
            $scope.playVideoRecorded();
        };

        $scope.eliminarVideoGrabado = function () {
            $scope.isRecord = false;
            $scope.counterRecord = $scope.counterRecordInitial;
            $scope.cleanVideo();
            $scope.colorGrabacion = '';
        };



        $scope.verVideo = function (path, planVideoId, fromto, index) {
            $scope.path = path;
            $scope.numVideo = index;
            $scope.isRecord = false;
            $scope.planVideoSelected = planVideoId;
            if (planVideoId.fromUserId != undefined) {
                UserService.getUserById(planVideoId.fromUserId)
                        .then(
                                function (d) {

                                    $scope.isVisibleSendVideo = true;
                                    $scope.isVisibleDeleteVideo = false;
                                    $scope.showVideo();
                                    //$scope.playVideo(path);
                                    if (fromto == 'to') {
                                        VideoService.readVideo(planVideoId.id).then(
                                                function (data) {
                                                    $scope.getReceived();
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
                $scope.showVideo();
            }

        };


        $scope.enviarVideo = function () {
            $scope.colorGrabacion = '';
            var tipoPlan = "EXT";
            var date = new Date();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var year = date.getFullYear();
            var hh = date.getHours();
            var mm = date.getMinutes();
            var ss = date.getSeconds();
            $scope.dateString = "" + day + month + year + hh + mm + ss;

            var url = $contextPath + "video/upload/" + $scope.toUserId + "/" + $scope.userSession.userId + "/";
            url += $scope.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + "/" + $scope.roleSelected;

            $scope.savePlanVideo(url,
                    function (response) {
                        if (response.data.status == 'success') {
                            $scope.showMessage(response.data.message);
                            var video = response.data.output;
                            if (video != "") {
                                video.sesionId = $scope.planSelected.id;

                            }
                            $scope.colorTime = '';
                            $scope.counterRecord = $scope.counterRecordInitial;
                        } else {
                            $scope.showMessage(response.data.message, "error");
                        }
                        self.getVideos();
                    }
            );
        };


        //lee los videos recibidos en tiempo real
        VideoService.receive().then(null, null, function (video) {
            if (video.toUser.userId == $scope.userSession.userId) {

                $scope.received.push(video);

            }

        });

        $scope.showRecord = function () {
            $mdDialog.show({
                controller: recordController,
                scope: $scope.$new(),
                templateUrl: 'static/views/athleteCoachDetail/video/recordModal.html',
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


        $scope.showVideo = function () {
            $mdDialog.show({
                controller: showVideoController,
                scope: $scope.$new(),
                templateUrl: 'static/views/athleteCoachDetail/video/showVideoModal.html',
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

        self.getVideos = function () {
            var tipoPlan = "EXT";
            var userId = $scope.userSession.userId;
            var toUserId = $scope.planSelected.athleteUserId.userId;
            self.receivedVideos(tipoPlan, $scope.roleSelected, userId, toUserId, function (data) {
                $scope.received = data.output;
                $scope.loadingReceived = true;

            });
            self.sendedVideos(tipoPlan, $scope.roleSelected, userId, toUserId, function (data) {
                $scope.sended = data.output;
                $scope.loadingSent = true;

            });

            self.getAvailableVideos($scope.planSelected.id, userId, toUserId, tipoPlan, $scope.roleSelected,
                    function (data) {
                        $scope.availableVideo = data.output;
                    });
        };



        if ($scope.userSession != null) {
            $scope.colorTime = '';
            $scope.toUserId = $scope.planSelected.athleteUserId.userId;
            $scope.showCounter = true;
            $scope.counterRecordInitial = $scope.videoDuration;
            $scope.counterRecord = $scope.counterRecordInitial;

        }

        self.init = function () {

            self.getVideos();

        };

        self.init();

    }]);