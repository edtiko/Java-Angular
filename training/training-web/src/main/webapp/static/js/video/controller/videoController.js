trainingApp.controller("VideoController", ['$scope', 'videoService', 'UserService', '$timeout', '$mdSidenav',
    function ($scope, videoService, UserService, $timeout, $mdSidenav) {
        $scope.guion = '';
        $scope.isToStar = false;
        $scope.showGuion = false;
        $scope.isRecord = true;
        $scope.isVisibleSendVideo = true;
        $scope.isVisibleDeleteVideo = true;
        $scope.isSendToStar = false;
        $scope.isVisibleToRefuseAtlethe = false;
        $scope.isSendToAtlethe = false;
        $scope.planVideoSelected = null;
        $scope.colorGrabacion = '';
        $scope.counterRecord = 0;
        $scope.counterRecordInitial = 0;
        $scope.planSelected = JSON.parse(sessionStorage.getItem("planSelected"));
        $scope.planSelectedStar = JSON.parse(sessionStorage.getItem("planSelectedStar"));
        $scope.toggleLeft = buildToggler('left');
        $scope.toggleRight = buildToggler('right');

        function buildToggler(componentId) {
            return function () {
                $mdSidenav(componentId).toggle();
            };
        }

        $scope.isOpenRight = function () {
            return $mdSidenav('right').isOpen();
        };


        if ($scope.planSelected != null && $scope.planSelectedStar != null) {
            $scope.coachAssignedPlanSelected = $scope.planSelected;
            $scope.planSelected = null;
        }

        if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
            $scope.colorTime = '';
            $scope.counterRecord = $scope.counterRecordInitial;
            $scope.initCounterRecord();
        }

        if ($scope.appReady && $scope.planSelected != null) {
            $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserCoach || $scope.user.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = $scope.planSelected.athleteUserId.userId;
            } else if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.toUserId = $scope.planSelected.coachUserId.userId;
                $scope.counterRecordInitial = $scope.planSelected.trainingPlanId.videoDuration;
                $scope.counterRecord = $scope.counterRecordInitial;
            }
            videoService.initialize($scope.planSelected.id);
        } else if ($scope.appReady && $scope.coachAssignedPlanSelected != null) {
            $scope.isToStar = true;
            $scope.showGuion = true;
            $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = $scope.coachAssignedPlanSelected.starUserId.userId;
            }
            videoService.initialize($scope.coachAssignedPlanSelected.starUserId.userId);
        }
        $scope.selectedIndex = 0;

        $scope.receivedVideos = function (tipoPlan) {
            if ($scope.isToStar) {
                videoService.getVideosByUser(-1, $scope.user.userId, "to", tipoPlan).then(
                        function (data) {
                            $scope.receivedvideos = data.entity.output;
                        },
                        function (error) {
                            console.error(error);
                        });
            } else {
                videoService.getVideosByUser($scope.planSelected.id, $scope.user.userId, "to", tipoPlan).then(
                        function (data) {
                            $scope.receivedvideos = data.entity.output;
                        },
                        function (error) {
                            console.error(error);
                        });
            }

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
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.colorTime = '';
                $scope.counterRecord = $scope.counterRecordInitial;
                $scope.initCounterRecord();
            }

            $scope.isVisibleSendVideo = true;
            $scope.isVisibleDeleteVideo = true;
            $scope.isSendToStar = false;
            $scope.isVisibleToRefuseAtlethe = false;
            $scope.isSendToAtlethe = false;

            $scope.colorGrabacion = 'color:red';
            $scope.isRecord = true;
            $scope.startRecordingVideo();
        };

        $scope.stopVideo = function () {
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
                if (typeof $scope.stop === "function") {
                    $scope.stop();
                }
            }

            $scope.colorGrabacion = '';
            $scope.stopRecordingVideo();
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
            $scope.isRecord = true;
            $scope.cleanVideo();
            $scope.colorGrabacion = '';
        };

        $scope.verVideo = function (path, planVideoId, fromto) {
            $scope.isRecord = false;
            $scope.planVideoSelected = planVideoId;
            if (planVideoId.fromUser != undefined) {
                UserService.getUserById(planVideoId.fromUser.userId)
                        .then(
                                function (d) {
                                    if (d.typeUser === $scope.userSessionTypeUserAtleta) {
                                        $scope.showGuion = true;
                                        $scope.isVisibleSendVideo = false;
                                        $scope.isVisibleDeleteVideo = false;
                                        $scope.isSendToStar = true;
                                        $scope.isVisibleToRefuseAtlethe = true;
                                    }

                                    if (d.typeUser === $scope.userSessionTypeUserCoachEstrella) {
                                        $scope.showGuion = true;
                                        $scope.isVisibleSendVideo = false;
                                        $scope.isVisibleDeleteVideo = false;
                                        $scope.isVisibleToRefuseAtlethe = true;
                                        $scope.isSendToStar = false;
                                        $scope.isSendToAtlethe = true;
                                    }
                                    $scope.playVideo(path);
                                    if (fromto == 'to') {
                                        videoService.readVideo(planVideoId.id).then(
                                                function (data) {
                                                    console.log(data.entity.output);
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
                $scope.playVideo(path);
            }

        };

        $scope.refuseVideo = function () {
            $scope.isRecord = false;
            var planVideoId = $scope.planVideoSelected;

            videoService.rejectedVideo(planVideoId.id)
                    .then(
                            function (d) {

                                if (planVideoId.fromPlanVideoId != null) {
                                    videoService.rejectedVideo(planVideoId.fromPlanVideoId.id)
                                            .then(
                                                    function (d) {
                                                        $scope.showMessage(d.output);
                                                    },
                                                    function (errResponse) {
                                                        console.error('Error while fetching');
                                                    }
                                            );
                                } else {
                                    $scope.showMessage(d.output);
                                }

                            },
                            function (errResponse) {
                                console.error('Error while fetching');
                            }
                    );
        };

        $scope.sendVideoToStar = function () {
            var planVideoId = $scope.planVideoSelected;
            $scope.isRecord = false;
            if ($scope.guion == '') {
                $scope.showMessage('Debe ingresar el gui\u00f3n');
                return;
            }

            videoService.sendVideoAtletheToStar($scope.user.userId, planVideoId.id, $scope.guion)
                    .then(
                            function (d) {
                                $scope.showMessage(d.output);
                            },
                            function (errResponse) {
                                console.error('Error while fetching');
                            }
                    );
        };

        $scope.sendVideoToAtlethe = function () {
            var planVideoId = $scope.planVideoSelected;
            $scope.isRecord = false;

            videoService.sendVideoToAtlethe($scope.user.userId, planVideoId.id)
                    .then(
                            function (d) {
                                $scope.showMessage(d.output);
                            },
                            function (errResponse) {
                                console.error('Error while fetching');
                            }
                    );
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
                url += $scope.planSelected.id + "/" + $scope.dateString + "/" + tipoPlan + '?filename=1';
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
                            $scope.colorTime = '';
                            $scope.counterRecord = $scope.counterRecordInitial;
                        } else {
                            $scope.showMessage(response.data.entity.output, "error");
                        }
                        $scope.sendedVideos(tipoPlan);
                    }
            );
        };

        $scope.showStatusVideo = function (indRejected) {
            if (indRejected == 0) {
                return 'Aceptado';
            } else if (indRejected == 1) {
                return 'Rechazado';
            } else {
                return 'Pendiente';
            }
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