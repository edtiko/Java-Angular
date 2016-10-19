trainingApp.controller("VideoController", ['$scope', 'videoService', function ($scope, videoService) {
        $scope.guion = '';
        $scope.isToStar = false;
        $scope.isRecord = true;
        $scope.planSelected = JSON.parse(sessionStorage.getItem("planSelected"));
        $scope.coachAssignedPlanSelected = JSON.parse(sessionStorage.getItem("coachAssignedPlanSelected"));
        if ($scope.appReady && $scope.planSelected != null) {
            $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserCoach || $scope.user.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = $scope.planSelected.athleteUserId.userId;
            } else if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.toUserId = $scope.planSelected.coachUserId.userId;
            }
            videoService.initialize($scope.planSelected.id);
        } else if ($scope.appReady && $scope.coachAssignedPlanSelected != null) {
            $scope.isToStar = true;
            $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = $scope.coachAssignedPlanSelected.starUserId.userId;
            }
            videoService.initialize($scope.coachAssignedPlanSelected.starUserId.userId);
        }
        $scope.selectedIndex = 0;

//        var configuration = {
//            init: $scope.initiateRecord,
//            recConf: {
//                recorvideodsize: 0.4,
//                webpquality: 0.7,
//                framerate: 15,
//                videoWidth: 600,
//                videoHeight: 475
//            },
//            recfuncConf: {
//                showbuton: 2000,
//                //url: $contextPath + "video/upload/" + $scope.toUserId + "/" + $scope.user.userId + "/" +$scope.planSelected.id+ "/" +$scope.dateString,
//                chunksize: 1048576,
//                recordingtime: 17,
//                requestparam: "filename",
//                videoname: "video.webm",
//                audioname: "audio.wav"
//            },
//            output: {
//                recordingthumb: null,
//                recordinguploaded: function (data) {
//                    var response = JSON.parse(data);
//                    if (response.entity.status == 'success') {
//                        $scope.showMessage("Video cargado correctamente.");
//                        var video = response.entity.output;
//                        if (video != "") {
//                            video.sesionId = $scope.planSelected.id;
//                            videoService.send(video);
//                        }
//                    } else {
//                        $scope.showMessage(response.entity.output, "error");
//                    }
//                    $scope.sendedVideos();
//                    $scope.setUrl();
//                    configuration.init();
//                }
//            },
//            recordingerror: function () {
//                alert("browser not compatible");
//            }
//        };
//        $scope.camconfiguration = configuration;

//        setTimeout(function () {
//            configuration.init();
//            $scope.setUrl();
//        }, $scope.camconfiguration.recfuncConf.showbuton);


        $scope.receivedVideos = function (tipoPlan) {
            if ($scope.isToStar) {
                videoService.getVideosByUser($scope.coachAssignedPlanSelected.id, $scope.user.userId, "to", tipoPlan).then(
                        function (data) {
                            $scope.sendedvideos = data.entity.output;
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            } else {
                videoService.getVideosByUser($scope.planSelected.id, $scope.user.userId, "to", tipoPlan).then(
                        function (data) {
                            $scope.receivedvideos = data.entity.output;
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }

        };

        $scope.inicioGrabarVideo = function () {
            $scope.isRecord = true;
            $scope.startRecordingVideo();
        };

        $scope.verVideo = function (path, planVideoId, fromto) {
            $scope.isRecord = false;
            $scope.playVideo(path);

            if (fromto == 'to') {
                videoService.readVideo(planVideoId).then(
                        function (data) {
                            console.log(data.entity.output);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });

            }
        };

        $scope.enviarVideo = function () {
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
            var url = $contextPath + "video/upload/" + $scope.toUserId + "/" + $scope.user.userId + "/";
            if ($scope.isToStar) {
                url = $contextPath + "video/uploadScript/" + $scope.toUserId + "/" +
                        $scope.user.userId + "/" + $scope.coachAssignedPlanSelected.id + "/" +
                        $scope.dateString + "/IN/" + $scope.guion;

                if ($scope.guion == '') {
                    $scope.showMessage('Debe ingresar el gui\u00f3n');
                    return;
                }
            } else {
                url += $scope.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan;
            }
            $scope.savePlanVideo(url,
                    function (response) {
                        if (response.data.entity.status == 'success') {
                            $scope.showMessage("Video cargado correctamente.");
                            var video = response.data.entity.output;
                            if (video != "") {
                                if ($scope.isToStar) {
                                    video.sesionId = $scope.coachAssignedPlanSelected.id;
                                } else {
                                    video.sesionId = $scope.planSelected.id;
                                }

                                videoService.send(video);
                            }
                        } else {
                            $scope.showMessage(response.data.entity.output, "error");
                        }
                        $scope.sendedVideos(tipoPlan);
                    }
            );
        };
//        $scope.setUrl = function () {
//            var tipoPlan = "IN";
//            var date = new Date();
//            var month = date.getMonth() + 1;
//            var day = date.getDate();
//            var year = date.getFullYear();
//            var hh = date.getHours();
//            var mm = date.getMinutes();
//            var ss = date.getSeconds();
//            $scope.dateString = "" + day + month + year + hh + mm + ss;
//            if ($scope.planSelected != null && $scope.planSelected.external) {
//                tipoPlan = "EXT";
//            }
//            var url = $contextPath + "video/upload/" + $scope.toUserId + "/" + $scope.user.userId + "/";
//            if ($scope.isToStar) {
//                url = $contextPath + "video/uploadScript/" + $scope.toUserId + "/" + $scope.user.userId + "/" + $scope.coachAssignedPlanSelected.id + "/" +
//                        $scope.dateString + "/I/" + $scope.guion;
//            } else {
//                url += $scope.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan;
//            }
//            $scope.camconfiguration.recfuncConf.url = url;
//        };

        $scope.sendedVideos = function (tipoPlan) {
            if ($scope.isToStar) {
                videoService.getVideosByUser($scope.coachAssignedPlanSelected.id, $scope.user.userId, "from", tipoPlan).then(
                        function (data) {
                            $scope.sendedvideos = data.entity.output;
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            } else {
                videoService.getVideosByUser($scope.planSelected.id, $scope.user.userId, "from", tipoPlan).then(
                        function (data) {
                            $scope.sendedvideos = data.entity.output;
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }

        };

//        $scope.playVideo = function (path, planVideoId, fromto) {
//
//            //$scope.showRecord = false;
//            $scope.selectedIndex = 1;
//            var videoPath = $contextPath + "video/files/" + path + "/video.webm";
//            var audioPath = $contextPath + "video/files/" + path + "/audio.wav";
//            var video = angular.element("#recorded");
//            var htmlVideo = '<video ng-show="!showRecord" id="myvideo" controls width="600" height="475" >';
//            htmlVideo += '<source src="' + videoPath + '" type="video/webm" />';
//            htmlVideo += '<audio id="myaudio" controls><source src="' + audioPath + '" type="audio/wav"/></audio></video>';
//
//            video.html(htmlVideo);
//            var myvideo = document.getElementById("myvideo");
//            var myaudio = document.getElementById("myaudio");
//
//            myvideo.onplay = function () {
//                myaudio.play();
//            };
//            myvideo.onpause = function () {
//                myaudio.pause();
//            };
//
//            //marcar video como visto
//            if (fromto == 'to') {
//                videoService.readVideo(planVideoId).then(
//                        function (data) {
//                            console.log(data.entity.output);
//                        },
//                        function (error) {
//                            //$scope.showMessage(error);
//                            console.error(error);
//                        });
//
//            }
//        };

        //lee los videos recibidos en tiempo real
        videoService.receive().then(null, null, function (video) {
            if (video.toUserId == $scope.user.userId) {
                $scope.receivedvideos.push(video);
            }

        });

        if ($scope.planSelected != null && $scope.planSelected.external) {
            $scope.receivedVideos("EXT");
            $scope.sendedVideos("EXT");
        } else {
            $scope.receivedVideos("IN");
            $scope.sendedVideos("IN");
        }

    }]);