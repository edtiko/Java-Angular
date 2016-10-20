trainingApp.controller("VideoController", ['$scope', 'videoService', function ($scope, videoService) {
        $scope.guion = '';
        $scope.isToStar = false;
        $scope.isRecord = true;
        $scope.colorGrabacion = '';
        $scope.planSelected = JSON.parse(sessionStorage.getItem("planSelected"));
        
        if($scope.planSelected != null && $scope.planSelected.starUserId != null)  {
            $scope.coachAssignedPlanSelected = $scope.planSelected;
            $scope.planSelected = null;
        }
        
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

        $scope.receivedVideos = function (tipoPlan) {
            if ($scope.isToStar) {
                videoService.getVideosByUser($scope.coachAssignedPlanSelected.id, $scope.user.userId, "to", tipoPlan).then(
                        function (data) {
                            $scope.receivedvideos = data.entity.output;
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
            $scope.colorGrabacion = 'color:red';
            $scope.isRecord = true;
            $scope.startRecordingVideo();
        };
        
        $scope.stopVideo = function() {
            $scope.colorGrabacion = '';
            $scope.stopRecordingVideo();
        };
        
        $scope.verVideoGrabado = function () {
            $scope.colorGrabacion = '';
            $scope.isRecord = false;
            $scope.playVideoLocal();
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
            $scope.colorGrabacion = '';
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