trainingApp.controller('CalendarController', function ($scope, CalendarService, SportService, ActivityService,
        $window, $mdDialog) {
    $scope.activityList = [];
    $scope.trainingPow = 0;
    $scope.replaceActivityId = null;
    $scope.labelTrainingPow = 'Entrenamiento por potencia';
    $scope.loading = true;
    $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
    var self = this;

    $scope.getManualActivities = function () {
        CalendarService.getManualActivityList($scope.userSession.userId).then(
                function (data) {
                    $scope.manualActivities = data.output;
                },
                function (error) {
                    console.error(error);
                }


        );
    };

    /*$scope.getActivityByUser = function () {
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
    $scope.getActivityByUser();*/
    $scope.getManualActivities();

    $scope.showCreateReplaceActivity = function (ev) {
        $scope.selectedDay = null;
        $scope.selectedId = "";
        $scope.manualActivityTitle = "Actividades de Reemplazo";
        if (ev != undefined) {
            var date = $(ev.target).attr("data-event-date");
            var id = $(ev.target).attr("data-event-id");
            if (date != null) {
                $scope.selectedDay = date;
            }

            if (id != null) {
                $scope.selectedActivity = id;
            }
        }
        $mdDialog.show({
            controller: ReplaceActivityController,
            scope: $scope.$new(),
            templateUrl: 'static/views/calendar/replaceActivity.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            escapeToClose: true,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    }

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

    $scope.showModalActivity = function (id, isManual) {
        $scope.selectedId = id;
        if (isManual) {
            $scope.manualActivityTitle = "Editar Actividad Manual";

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

    function ReplaceActivityController($scope, $mdDialog) {
        $scope.searchReplaceActivity = "";
        $scope.activityReplaceList = [];
        //Consulta type zona igual a PPM por defecto
        $scope.getActivity = function () {
            CalendarService.getActivityReplace($scope.selectedActivity).then(
                    function (data) {
                        $scope.activityReplaceList = angular.copy(data.output);
                    },
                    function (error) {
                        console.debug(error);
                    }
            );
        };

        $scope.changeActivityReplace = function (activityId) {
            var userId = JSON.parse($window.sessionStorage.getItem("userInfo"));
            var objActivity = {'userId': userId.userId,
                'activityDate': $scope.selectedDay, 'activityId': activityId};

            createActivity(objActivity);

            objActivity = {'userId': userId.userId, 'trainingPlanWorkoutId': $scope.selectedActivity,
                'activityDate': $scope.selectedDay};
            deletePlan(objActivity);
            $mdDialog.hide();
        };

        $scope.getActivity();
        $scope.hideReplaceActivity = function () {
            console.debug('hide');
            $mdDialog.hide();
        };
        $scope.cancelReplaceActivity = function () {
            console.debug('cancel');
            $mdDialog.cancel();
        };
    }

    function ActivityController($scope, $mdDialog) {

        //$scope.activity = {id: '', activityId: '', modality: '', executedTime:'', executedDistance:'', title: '', indStrava:'', lastUpdateStrava:'', activityDescription: '', workoutDate: '', userId: $scope.userId};
        $scope.getActivity = function () {
            //Consulta type zona igual a PPM por defecto
            $scope.trainingPow = 1;
            CalendarService.getSerie($scope.selectedId, $scope.userSession.userId).then(
                    function (data) {
                        $scope.serie = angular.copy(data.output);
                    },
                    function (error) {

                    }
            );
        };
        
        $scope.getActivity();
        
       /* $scope.getActivityReplace = function () {
            CalendarService.getActivityReplace($scope.selectedId).then(
                    function (data) {
                        $scope.activityReplaceList = angular.copy(data.output);
                    },
                    function (error) {
                        console.debug(error);
                    }
            );
        };
        
        $scope.changeTrainingPow = function (trainingPow) {
            $scope.trainingPow = trainingPow;
            CalendarService.getActivityPpm($scope.selectedId, trainingPow).then(
                    function (data) {
                        $scope.activity = angular.copy(data.output);
                    },
                    function (error) {

                    }
            );
        };
        if ($scope.selectedId != "") {
            $scope.getActivity();
            $scope.getActivityReplace();
        }
        

        $scope.updateActivity = function () {
            CalendarService.updateWorkout($scope.activity).then(
                    function (data) {
                        if (data.status == 'success') {
                            $scope.showMessage(data.output);
                            $mdDialog.hide();
                        } else {
                            $scope.showMessage(data.output, "Error");
                        }
                    },
                    function (error) {

                    }


            );
        };*/

        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };
    }
    function ManualActivityController($scope, $mdDialog) {

        $scope.manualActivity = {id: '', sportId: '', name: '', description: '', workoutDate: '', userId: $scope.userSession.userId};

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
                            var objActivity = {'userId': $scope.userSession.userId, 'manualActivityId': data.output, 'activityDate': $scope.selectedDay};
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

    $scope.showConfirm = function (ev) {
        // Appending dialog to document.body to cover sidenav in docs app

    };

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



    $scope.querySearch = function (query) {
        // var results = query ? $scope.activityList.filter( createFilterFor(query) ) : $scope.activityList;

        return false;

    };

    $scope.searchTextChange = function (query) {
        if ($scope.activities != undefined) {
            $scope.activityList = $scope.activities;
        }
        $scope.activities = $scope.activityList;
        var results = query ? $scope.activityList.filter(createFilterFor(query)) : $scope.activityList;

        if (results.length > 0) {
            $scope.activityList = results;
        } else {
            $scope.activityList = $scope.activities;
        }
    };



    function createFilterFor(query) {
        var lowercaseQuery = angular.lowercase(query);

        return function filterFn(item) {
            var value = removeAccents(item.name.toLowerCase());
            return (value.indexOf(lowercaseQuery) === 0);
        };

    }

    $scope.ignoreAccents = function (item) {
        if (!$scope.search)
            return true;
        var text = removeAccents(item.name.toLowerCase());
        var search = removeAccents($scope.search.toLowerCase());
        return text.indexOf(search) > -1;
    };

    function removeAccents(source) {

        var accent = [
            /[\300-\306]/g, /[\340-\346]/g, // A, a
            /[\310-\313]/g, /[\350-\353]/g, // E, e
            /[\314-\317]/g, /[\354-\357]/g, // I, i
            /[\322-\330]/g, /[\362-\370]/g, // O, o
            /[\331-\334]/g, /[\371-\374]/g, // U, u
            /[\321]/g, /[\361]/g, // N, n
            /[\307]/g, /[\347]/g, // C, c
        ],
                noaccent = ['A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'N', 'n', 'C', 'c'];

        for (var i = 0; i < accent.length; i++) {
            source = source.replace(accent[i], noaccent[i]);
        }

        return source;

    } // removeAccents


});