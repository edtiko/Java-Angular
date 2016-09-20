trainingApp.controller("VideoController", ['$scope', 'videoService', function ($scope, videoService) {

        if ($scope.appReady) {
          $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
          var planSelected = JSON.parse(sessionStorage.getItem("coachAssignedPlanSelected"));
            if ($scope.user != null && $scope.user.typeUser === 'Coach' || $scope.user.typeUser === 'Coach-Interno') {              
                       $scope.toUserId = planSelected.athleteUserId.userId;

            } else if ($scope.user != null && $scope.user.typeUser === 'Atleta') {
                      $scope.toUserId = planSelected.coachUserId.userId;

            }
        }
        var date = new Date();
        var month = date.getMonth()+1;
        var day = date.getDate();
        var year = date.getFullYear();
        var hh = date.getHours();
        var mm = date.getMinutes();
        var ss = date.getSeconds();
        $scope.dateString = ""+day+month+year+hh+mm+ss;
        
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
                url: $contextPath + "video/upload/" +$scope.toUserId+"/"+$scope.user.userId+"/"+$scope.dateString,
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
        
        
         $scope.receivedVideos = function () {

            videoService.getVideosByUser($scope.user.userId).then(
                    function (data) {
                        $scope.receivedvideos = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        $scope.playVideo = function(videoPath){
             videoService.getVideoByPath(videoPath).then(
                    function (data) {
                        $scope.videoPath = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        $scope.receivedVideos();
    }]);