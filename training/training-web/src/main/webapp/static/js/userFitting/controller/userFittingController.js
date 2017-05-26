trainingApp.controller("UserFittingController", ['$scope', 'UserFittingService', '$window', '$mdDialog',
    function ($scope, UserFittingService, $window, $mdDialog) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));

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

        $scope.isVideo = function (type) {
            if (type.indexOf("video") !== -1) {
                return false;
            }
            return true;
        };

        $scope.uploadFile = function (file) {

            if (file.files[0] !== undefined) {
                var file = file.files[0];
            }
            if (file !== undefined && $scope.isVideo(file.type)) {
                $scope.showMessage("Debe seleccionar un video valido.", "error");
            } else if ($scope.userSession.userId != "" && file != null) {
                UserFittingService.uploadVideo(file, $scope.user.userId)
                        .then(
                                function (msg) {
                                    $scope.showMessage("Imagen cargada correctamente.");
                                },
                                function (error) {
                                    console.error(error);
                                }
                        );
            } else {
                $scope.showMessage("Debe seleccionar una video.", "error");
            }
        };


    }]);