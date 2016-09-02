trainingApp.controller('CalendarController', function ($scope, CalendarService,ModalityService,
        $window, $mdDialog) {
    $scope.activityList = [];
    $scope.trainingPow = 0;
    $scope.labelTrainingPow = 'Entrenamiento por potencia';
    $scope.loading = true;
    $scope.userId = null;
    $scope.getActivityByUser = function () {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            var planAthleteSelected = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
            if(planAthleteSelected != null){
                $scope.userId = planAthleteSelected.athleteUserId.userId;
            }else{
                $scope.userId = user.userId;
            }
            CalendarService.getActivityByDisciplineUser($scope.userId)
                    .then(
                            function (response) {
                                if (response.status == 'success') {
                                    $scope.activityList = response.output;
                                    $scope.loading = false;
                                } else {
                                    $scope.showMessage(response.output, "error");
                                }
                            },
                            function (errResponse) {
                                console.error('Error consultando actividades');
                            }
                    );
             $scope.getManualActivities();
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
    
    $scope.createActivity = function (ev) {
        $mdDialog.show({
            controller: ActivityController,
            templateUrl: 'static/views/calendar/createActivity.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    };

 $scope.getManualActivities = function(){
       CalendarService.getManualActivityList($scope.userId).then(
                    function (data) {
                      $scope.manualActivities = data.output;
                    },
                    function (error) {
                      console.error(error);
                    }


            );
 };
 
     $scope.getModalitiesByDisciplineId = function (id) {
            ModalityService.getModalitiesByDisciplineId(id).then(
                    function (d) {
                        $scope.modalities = d;
                    },
                    function (errResponse) {
                        console.error('Error while modalities');
                        console.error(errResponse);
                    }
            );
        };

    function ActivityController($scope, $mdDialog) {
        $scope.activity = {manualActivityId:'', modalityId:'', name:'', description:''};
        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };
        $scope.answer = function (answer) {
            $mdDialog.hide(answer);
        };
        $scope.saveActivity = function () {
            CalendarService.createManualActivity(activity).then(
                    function (data) {
                      $scope.getManualActivities();
                    },
                    function (error) {

                    }


            );
        };

    }

});