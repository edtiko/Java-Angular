trainingApp.controller('CalendarController', function ($scope, CalendarService,
        $window) {
    $scope.activityList = [];

    $scope.getActivityByUser = function () {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            CalendarService.getActivityByDisciplineUser(user.userId)
                    .then(
                            function (response) {
                                if(response.status == 'success') {
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
});