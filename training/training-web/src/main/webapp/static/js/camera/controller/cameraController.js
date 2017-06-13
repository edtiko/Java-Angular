
trainingApp.controller('CameraController', ['$scope',
    function ($scope) {


        $scope.$watch('media', function (media) {
            if (media != null) {
                $scope.media = media;
                console.log("lista para subir a bd");
            }
             $scope.media = media;
        });



    }]);