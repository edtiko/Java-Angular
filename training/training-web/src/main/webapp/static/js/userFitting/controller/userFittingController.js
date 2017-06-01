trainingApp.controller("UserFittingController", ['$scope', 'UserFittingService', '$window', '$mdDialog',
    function ($scope, UserFittingService, $window, $mdDialog) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.videoFitting = {};
        $scope.fittingCargado = false;
        $scope.attachedVideo = null;
 
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
        
        $scope.attachVideo = function(file){
           var file = file.files[0];
           $scope.attachedVideo = file.name; 
        };

        $scope.uploadFile = function (file) {

            if (file !== undefined && self.isVideo(file.type)) {
                $scope.showMessage("Debe seleccionar un video valido.", "error");
            } else if ($scope.userSession.userFittingId != "" && file != null) {
                UserFittingService.uploadVideo(file, $scope.userSession.userFittingId)
                        .then(
                                function (msg) {
                                    $scope.showMessage("Video cargado correctamente.");
                                    self.getUserFittingVideo();
                                },
                                function (error) {
                                    console.error(error);
                                }
                        );
            } else {
                $scope.showMessage("Debe seleccionar un video.", "error");
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
                        
                        var src = $contextPath + "userFitting/files/" + $scope.videoFitting.videoName;
                       var videoDiv = angular.element("#recordedVideo");
                       var htmlVideo = '<video src="'+src+'" type="video/*" style="background-color: #000;border: 2px solid #fff;width: 90%;height: 90%;"  controls="controls"></video>';
                       videoDiv.html(htmlVideo);
                       }
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