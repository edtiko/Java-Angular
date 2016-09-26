trainingApp.controller("VideoController", ['$scope', 'videoService', '$sce', function ($scope, videoService, $sce) {

        var planSelected = JSON.parse(sessionStorage.getItem("coachAssignedPlanSelected"));
        if ($scope.appReady && planSelected != null) {
            $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserCoach || $scope.user.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = planSelected.athleteUserId.userId;
                //$scope.toUserId = 94;

            } else if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.toUserId = planSelected.coachUserId.userId;
                // $scope.toUserId = 94;

            }
            videoService.initialize(planSelected.id);
        }
        var date = new Date();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var year = date.getFullYear();
        var hh = date.getHours();
        var mm = date.getMinutes();
        var ss = date.getSeconds();
        $scope.dateString = "" + day + month + year + hh + mm + ss;
        $scope.selectedIndex = 0;

        var configuration = {
            init: $scope.initiateRecord,
            recConf: {
                recorvideodsize: 0.4,
                webpquality: 0.7,
                framerate: 15,
                videoWidth: 600,
                videoHeight: 475
            },
            recfuncConf: {
                showbuton: 2000,
                url: $contextPath + "video/upload/" + $scope.toUserId + "/" + $scope.user.userId + "/" + $scope.dateString,
                chunksize: 1048576,
                recordingtime: 17,
                requestparam: "filename",
                videoname: "video.webm",
                audioname: "audio.wav"
            },
            output: {
                recordingthumb: null,
                recordinguploaded: function (data) {
                    var response = JSON.parse(data);
                    if (response.entity.status == 'success') {
                        $scope.showMessage("Video cargado correctamente.");
                        var video = response.entity.output;
                        if (video != "") {
                            video.sesionId = planSelected.id;
                            videoService.send(video);
                        }
                    } else {
                        $scope.showMessage(response.entity.output, "error");
                    }
                    $scope.sendedVideos();
                }
            },
            recordingerror: function () {
                alert("browser not compatible");
            }
        };
        $scope.camconfiguration = configuration;

        setTimeout(function () {
            configuration.init();
        }, $scope.camconfiguration.recfuncConf.showbuton);


        $scope.receivedVideos = function () {

            videoService.getVideosByUser($scope.user.userId, "to").then(
                    function (data) {
                        $scope.receivedvideos = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.sendedVideos = function () {

            videoService.getVideosByUser($scope.user.userId, "from").then(
                    function (data) {
                        $scope.sendedvideos = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.playVideo = function (path) {

            //$scope.showRecord = false;
            $scope.selectedIndex = 1;
            var videoPath = $contextPath + "video/files/" + path + "/video.webm";
            var audioPath = $contextPath + "video/files/" + path + "/audio.wav";
            var video = angular.element("#recorded");
            var htmlVideo = '<video ng-show="!showRecord" id="myvideo" controls width="600" height="475" >';
            htmlVideo += '<source src="' + videoPath + '" type="video/webm" />';
            htmlVideo += '<audio id="myaudio" controls><source src="' + audioPath + '" type="audio/wav"/></audio></video>';

            video.html(htmlVideo);
            var myvideo = document.getElementById("myvideo");
            var myaudio = document.getElementById("myaudio");

            myvideo.onplay = function () {
                myaudio.play();
            };
            myvideo.onpause = function () {
                myaudio.pause();
            };
        };

        //lee los videos recibidos en tiempo real
        videoService.receive().then(null, null, function (video) {
            if (video.toUserId == $scope.user.userId) {
                $scope.receivedvideos.push(video);
            }

        });

        $scope.receivedVideos();
        $scope.sendedVideos();
    }]);