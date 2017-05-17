trainingApp.controller('CalendarAsesorController', function ($scope, CalendarService, SportService,
        $window, $mdDialog) {
    $scope.activityList = [];
    $scope.trainingPow = 0;
    $scope.replaceActivityId = null;
    $scope.labelTrainingPow = 'Entrenamiento por potencia';
    $scope.loading = true;
    $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
    $scope.planSelected =  JSON.parse($window.sessionStorage.getItem("planSelected"));
    var self = this;


    $scope.getManualActivities = function () {
        CalendarService.getManualActivityList($scope.planSelected.athleteUserId.userId).then(
                function (data) {
                    $scope.manualActivities = data.output;
                },
                function (error) {
                    console.error(error);
                }


        );
    };

    $scope.getManualActivities();


    $scope.showCreateManualActivity = function (ev) {

        $scope.selectedDay = null;
        $scope.selectedId = "";
        $scope.manualActivityTitle = "Crear Actividad Manual";
        if (ev != undefined) {
            var date = $(ev.target).attr("data-event-date");
            if (date != null) {
                $scope.selectedDay = date;
            }
        }
        $mdDialog.show({
            controller: ManualActivityController,
            scope: $scope.$new(),
            templateUrl: 'static/views/calendar/manualActivity.html',
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

    $scope.showModalActivity = function (sesion, week, manualActivityId) {
        $scope.selectedSesion = sesion;
        $scope.selectedWeek = week;
        
        if (manualActivityId != 0) {
            $scope.manualActivityTitle = "Editar Actividad Manual";
            $scope.selectedId = manualActivityId;
            $mdDialog.show({
                controller: ManualActivityController,
                scope: $scope.$new(),
                templateUrl: 'static/views/calendar/manualActivity.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen
            });
        } else {
            $mdDialog.show({
                controller: ActivityController,
                scope: $scope.$new(),
                templateUrl: 'static/views/calendar/serie.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen
            });
        }


    };


    function ActivityController($scope, $mdDialog) {

        //$scope.activity = {id: '', activityId: '', modality: '', executedTime:'', executedDistance:'', title: '', indStrava:'', lastUpdateStrava:'', activityDescription: '', workoutDate: '', userId: $scope.userId};
        $scope.getActivity = function () {
            //Consulta type zona igual a PPM por defecto
            $scope.trainingPow = 1;
            CalendarService.getSerie($scope.selectedSesion, $scope.selectedWeek, $scope.planSelected.athleteUserId.userId).then(
                    function (data) {
                        $scope.series = angular.copy(data.output);
                        $scope.series.forEach(function (v) {
                            $scope.totalTimeSesion =+ (v.timeSerie + v.restTime + v.warmUpTime + v.pullDownTime);
                        });
                    },
                    function (error) {

                    }
            );
        };

        $scope.getActivity();


        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };
    }
    function ManualActivityController($scope, $mdDialog) {

        $scope.manualActivity = {id: '', sportId: '', name: '', description: '', workoutDate: '', userId: $scope.planSelected.athleteUserId.userId};

        $scope.getActivity = function () {
            CalendarService.getActivity($scope.selectedId).then(
                    function (data) {
                        $scope.manualActivity.id = data.output.activityId;
                        $scope.manualActivity.sportId = data.output.sportId;
                        $scope.manualActivity.name = data.output.title;
                        $scope.manualActivity.description = data.output.activityDescription;
                        $scope.manualActivity.workoutDate = data.output.workoutDate;
                    },
                    function (error) {

                    }
            );
        };

        if ($scope.selectedId != "") {
            $scope.getActivity();
        }
        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };
        $scope.saveActivity = function () {
            CalendarService.createManualActivity($scope.manualActivity).then(
                    function (data) {
                        if(data.status == 'success'){
                        $scope.getManualActivities();
                        if ($scope.selectedDay != null && $scope.selectedId == "") {
                            var objActivity = {'userId': $scope.planSelected.athleteUserId.userId, 'manualActivityId': data.output, 'activityDate': $scope.selectedDay};
                            createActivity(objActivity);
                        }
                        if ($scope.selectedActivityId != "") {
                            initCalendar();
                        }
                        $scope.cancel();
                    }else{
                        $scope.showMessage(data.output,"Error");
                    }
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


    $scope.showEliminarActividad = function (id, name) {
        var confirm = $mdDialog.confirm()
                .title('Confirmar')
                .textContent('\u00BFEsta seguro de borrar la actividad: ' + name + ' ?')
                .ariaLabel('Lucky day')
                .ok('Si')
                .cancel('Cancelar');

        $mdDialog.show(confirm).then(function () {
            self.deleteManualActivity(id);
        }, function () {

        });
    };

    self.deleteManualActivity = function (id) {
        CalendarService.deleteManualActivity(id).then(
                function (res) {
                    if (res.status == 'success') {
                        $scope.getManualActivities();
                        initCalendar();
                    } else {
                        $scope.showMessage(res.output, "error");
                    }
                },
                function (error) {
                    console.error(error);
                }

        );
    };


});