trainingApp.controller('CalendarController', function ($scope, CalendarService, SportService,
        $window, $mdDialog) {
    $scope.activityList = [];
    $scope.trainingPow = 0;
    $scope.labelTrainingPow = 'Entrenamiento por potencia';
    $scope.loading = true;
    $scope.userId = null;
    
        $scope.getManualActivities = function () {
        CalendarService.getManualActivityList($scope.userId).then(
                function (data) {
                    $scope.manualActivities = data.output;
                },
                function (error) {
                    console.error(error);
                }


        );
    };
    
    $scope.getActivityByUser = function () {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            var planAthleteSelected = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
            if (planAthleteSelected != null) {
                $scope.userId = planAthleteSelected.athleteUserId.userId;
            } else {
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

    $scope.showCreateManualActivity = function (ev) {
        
        $scope.selectedDay = null;
        if (ev != undefined) {
            var date = $(ev.target).attr("data-event-date");
            if (date != null) {
                $scope.selectedDay = date;
            }
        }
        $mdDialog.show({
            controller: ActivityController,
            scope: $scope.$new(),
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


    function ActivityController($scope, $mdDialog) {
        
        $scope.manualActivity = {manualActivityId: '', modalityId: '', name: '', description: '', userId: $scope.userId};
        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };
        $scope.saveActivity = function () {
            CalendarService.createManualActivity($scope.manualActivity).then(
                    function (data) {
                        $scope.getManualActivities();
                        if($scope.selectedDay != null){
                           var objActivity = {'userId' : $scope.userId, 'manualActivityId':data.output, 'activityDate' : $scope.selectedDay};
                            createActivity(objActivity);
                        }
                        $scope.cancel();
                    },
                    function (error) {

                    }


            );
        };

            $scope.getSportDisciplinesCalendar = function () {
            SportService.getSportDisciplines().then(
                    function (d) {
                        $scope.sports = d;
                    },
                    function (errResponse) {
                        console.error('Error while disciplines');
                        console.error(errResponse);
                    }
            );
        };

        $scope.getSportDisciplinesCalendar();

    }
    
     $scope.querySearch = function (query) {
      var results = query ? $scope.activityList.filter( createFilterFor(query) ) : $scope.activityList;

        return results;
      
    };
    
     $scope.searchTextChange= function (text) {
      $log.info('Text changed to ' + text);
     };

    
 
    function createFilterFor(query) {
      var lowercaseQuery = angular.lowercase(query);

      return function filterFn(activity) {
        return (activity.name.indexOf(lowercaseQuery) === 0);
      };

    }


});