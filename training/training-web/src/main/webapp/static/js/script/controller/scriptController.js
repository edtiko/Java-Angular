trainingApp.controller("ScriptController", ['$scope', 'ScriptService', '$window',
    function ($scope, ScriptService, $window) {

        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planVideoList = [];
        $scope.scriptVideoList = [];
        $scope.planVideo = {};
        $scope.isRecord = false;
        $scope.colorGrabacion = '';

        $scope.getPlanVideoStarByCoach = function () {
            ScriptService.getPlanVideoStarByCoach($scope.userSession.userId)
                    .then(
                            function (response) {
                                $scope.planVideoList = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching');
                            }
                    );
        };

        $scope.getScriptVideoStarByCoach = function () {
            ScriptService.getScriptVideoStarByCoach($scope.userSession.userId)
                    .then(
                            function (response) {
                                $scope.scriptVideoList = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching');
                            }
                    );
        };

        $scope.selectVideo = function (o) {
            $scope.planVideo = o;
            $scope.verVideo();
        };
        
        $scope.selectVideoStar = function (o) {
            $scope.planVideo = o;
            $scope.colorGrabacion = '';
            $scope.isRecord = false;
            $scope.playVideo($scope.planVideo.videoPath);
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

        $scope.verVideo = function () {
            $scope.colorGrabacion = '';
            if ($scope.planVideo != null && $scope.planVideo.planVideoId.fromUserId != null) {
                $scope.isRecord = false;
                $scope.playVideo($scope.planVideo.planVideoId.videoPath);
            } else {
                $scope.showMessage('Debe seleccionar un video');
            }
        };

        $scope.enviarVideoToCoach = function () {
            if ($scope.planVideo != null && $scope.planVideo.planVideoId.fromUserId != null) {
                var url = $contextPath + 'video/uploadVideo/'+$scope.planVideo.planVideoId.fromUserId.userId+'/'+$scope.userSession.userId;
                $scope.savePlanVideo(url,
                            function (response) {
                                $scope.showMessage(response.data.entity.output);
                                }
                        );
            } else {
                $scope.showMessage('Debe seleccionar un video');
            }
        };

        $scope.getPlanVideoStarByCoach();
        $scope.getScriptVideoStarByCoach();
    }]);