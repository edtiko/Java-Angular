trainingApp.controller("UserFittingController", ['$scope', 'UserFittingService', '$window', '$mdDialog',
    function ($scope, UserFittingService, $window, $mdDialog) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.videoFitting = {};
        $scope.fittingCargado = false;

        $scope.showUploadFitting = function () {
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/views/userFitting/uploadVideoModal.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }
            });
        };

        self.isVideo = function (type) {
            if (type.indexOf("video") !== -1) {
                return false;
            }
            return true;
        };

        $scope.uploadFile = function (file) {

            if (file !== undefined && self.isVideo(file.type)) {
                $scope.showMessage("Debe seleccionar un video valido.", "error");
            } else if ($scope.userSession.userFittingId != "" && file != null) {
                UserFittingService.uploadVideo(file, $scope.userSession.userFittingId)
                        .then(
                                function (msg) {
                                    $scope.showMessage("Video cargado correctamente.");
                                },
                                function (error) {
                                    console.error(error);
                                }
                        );
            } else {
                $scope.showMessage("Debe seleccionar una video.", "error");
            }
        };

        self.getUserFittingVideo = function () {
            UserFittingService.getUserFittingVideo($scope.userSession.userFittingId).then(
                    function (data) {
                        $scope.videoFitting = data.output;

                        if ($scope.videoFitting != null) {
                            if ($scope.videoFitting.stateId === 2 || $scope.videoFitting.stateId === 5) { //PENDIENTE Ó APROBADO
                                $scope.fittingCargado = true;
                            }
                        }
                        var video = document.querySelector('video#recordedVideo');
                        var source = document.createElement('source');
                        var src = $contextPath + "userFitting/files/" + $scope.videoFitting.videoName;
                        video.pause();
                        source.setAttribute('src', src);

                        video.appendChild(source);
                        video.load();
                        //video.play();
                    },
                    function (error) {
                        console.log(error);
                    }

            );
        };

        if ($scope.userSession != null) {
            self.getUserFittingVideo();
        }



    }]);