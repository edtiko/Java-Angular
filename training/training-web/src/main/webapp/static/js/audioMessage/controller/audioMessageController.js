trainingApp.controller("AudioMessageController", ['$scope', 'AudioMessageService', 'UserService', '$window', '$mdDialog', '$q', function ($scope, AudioMessageService, UserService, $window, $mdDialog, $q) {
        
    var self = this;
            $scope.planSelected = JSON.parse(sessionStorage.getItem("planSelected"));
        if ($scope.appReady && $scope.planSelected != null) {
            $scope.user = JSON.parse(sessionStorage.getItem("userInfo"));
            if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserCoach || $scope.user.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.toUserId = $scope.planSelected.athleteUserId.userId;
                //$scope.toUserId = 94;

            } else if ($scope.user != null && $scope.user.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.toUserId = $scope.planSelected.coachUserId.userId;
                // $scope.toUserId = 94;

            }
            AudioMessageService.initialize($scope.planSelected.id);
        }
    
      $scope.receivedAudios= function (tipoPlan) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, $scope.user.userId, "to", tipoPlan).then(
                    function (data) {
                        $scope.receivedvideos = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
         $scope.sendedAudios = function (tipoPlan) {

            AudioMessageService.getAudiosByUser($scope.planSelected.id, $scope.user.userId, "from", tipoPlan).then(
                    function (data) {
                        $scope.sendedvideos = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
}]).config(function (recorderServiceProvider) {
    recorderServiceProvider
      .forceSwf(false)
      //.setSwfUrl('/lib/recorder.swf')
      .withMp3Conversion(true)
    ;
  });