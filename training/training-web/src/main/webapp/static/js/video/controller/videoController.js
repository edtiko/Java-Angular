trainingApp.controller("VideoController", ['$scope', 'videoService', function ($scope, videoService) {

        if ($scope.appReady) {
          $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
        }
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
                url: $contextPath + "video/upload/" + $scope.user.userId,
                chunksize: 1048576,
                recordingtime: 17,
                requestparam: "filename",
                videoname: "video.webm",
                audioname: "audio.wav"
            },
            output: {
                recordingthumb: null,
                recordinguploaded: function (data) {
                    //console.log(data);
                    $scope.showMessage("Video cargado correctamente.");
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
    }]);