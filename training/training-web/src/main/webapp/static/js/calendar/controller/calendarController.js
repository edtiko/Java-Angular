trainingApp.controller('CalendarController', function ($scope, CalendarService,
        $window) {
    $scope.activityList = [];
    $scope.trainingPow = 0;
    $scope.labelTrainingPow = 'Entrenamiento por potencia';
    $scope.getActivityByUser = function () {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            CalendarService.getActivityByDisciplineUser(user.userId)
                    .then(
                            function (response) {
                                if (response.status == 'success') {
                                    $scope.activityList = response.output;
                                } else {
                                    $scope.showMessage(response.output, "error");
                                }
                            },
                            function (errResponse) {
                                console.error('Error consultando actividades');
                            }
                    );
        } else {
            $scope.showMessage("El usuario no se encuentra logueado.", "error");
        }
    };
    $scope.getActivityByUser();

    $scope.changeTrainingPow = function () {
        if ($scope.trainingPow == 0) {
            $scope.trainingPow = 1;
            $scope.labelTrainingPow = 'Entrenamiento por ppm';
        } else {
            $scope.trainingPow = 0;
            $scope.labelTrainingPow = 'Entrenamiento por potencia';
        }
    };

});