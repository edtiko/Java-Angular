trainingApp.controller('ActivityController', ['$scope', 'ActivityService',
    'ModalityService', 'ObjectiveService', 'PhysiologicalCapacityService',
    'SportService', 'DisciplineService', '$window', '$mdDialog',
    function ($scope, ActivityService,
            ModalityService, ObjectiveService, PhysiologicalCapacityService,
            SportService, DisciplineService, $window, $mdDialog) {
        $scope.activity = {activityId: null,
            name: '',
            description: '',
            physiologicalCapacityId: {physiologicalCapacityId: null, name: ''},
            modalityId: {modalityId: null, name: ''},
            objectiveId: {objectiveId: null, name: ''},
            sportId: {sportId: null, name: ''},
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.activityList = [];
        $scope.physiologicalCapacityList = [];
        $scope.modalityList = [];
        $scope.objectiveList = [];
        $scope.sportList = [];
        $scope.count = 0;
        $scope.disciplineList = [];

        var bookmark;
        $scope.selected = [];

        $scope.filter = {
            options: {
                debounce: 500
            }
        };

        $scope.query = {
            filter: '',
            order: 'name',
            limit: 10,
            page: 1
        };

        function success(response) {
            if (response.data != undefined) {
                if (response.data.status == 'fail') {
                    $scope.showMessage(response.data.output);
                } else {
                    return response.data.output;
                }
            } else {
                if (response.status == 'fail') {
                    $scope.showMessage(response.data.output);
                } else {
                    if (response.output != undefined) {
                        return response.output;
                    } else {
                        return response;
                    }
                }
            }


            return null;
        }

        $scope.getActivityPaginate = function () {
            $scope.promise = ActivityService.getPaginate($scope.query, function (response) {
                $scope.activityList = success(response);

                if ($scope.activityList.length > 0) {
                    $scope.count = $scope.activityList[0].count;
                }
            }).$promise;
        };

        $scope.getSportDisciplines = function () {
            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        $scope.modalityList = [];
                        $scope.disciplineList = d;
                    },
                    function (errResponse) {
                        console.error('Error while disciplines');
                        console.error(errResponse);
                    }
            );
        };

        $scope.physiologicalCapacityList = [];
        $scope.getPhysiologicalCapacityList = function () {
            PhysiologicalCapacityService.getPhysiologicalCapacity(function (response) {
                $scope.physiologicalCapacityList = success(response);
            });
        };
        $scope.getModalitiesByDisciplineId = function (id) {
            ModalityService.getModalitiesByDisciplineId(id).then(
                    function (d) {                        
                        $scope.modalityList = success(d);
                    },
                    function (errResponse) {
                        console.error('Error while modalities');
                        console.error(errResponse);
                    }
            );
        };
        $scope.getObjectives = function () {
            ObjectiveService.getObjectives().then(
                    function (d) {
                        $scope.objectiveList = success(d);
                    },
                    function (errResponse) {
                        console.error('Error while objectives');
                        console.error(errResponse);
                    }
            );
        };
        $scope.getSportList = function () {
            SportService.getSportDisciplines().then(function (response) {
                $scope.sportList = success(response);
            },
                    function (errResponse) {
                        console.error('Error while objectives');
                        console.error(errResponse);
                    });
        };

        $scope.createActivity = function (activity) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                activity.userCreate = (user.userId);
            }
            ActivityService.createActivity(activity)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetActivity();
                                    $scope.getActivityPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating Activity.');
                            }
                    );
        };

        $scope.updateActivity = function (activity) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                activity.userUpdate = (user.userId);
            }

            ActivityService.mergeActivity(activity)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetActivity();
                                    $scope.showMessage(d.output);
                                    $scope.getActivityPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating Activity.');
                            }
                    );
        };

        $scope.deleteActivity = function (activity) {
            var confirm = $mdDialog.confirm()
                    .title('Confirmaci\u00f3n')
                    .textContent('\u00BFDesea eliminar el registro?')
                    .ariaLabel('Lucky day')
                    .ok('Aceptar')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {

                ActivityService.deleteActivity(activity)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.resetActivity();
                                        $scope.showMessage(d.output);
                                        $scope.getActivityPaginate();
                                    } else {
                                        $scope.showMessage(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting Activity.');
                                }
                        );
            }, function () {
            });
        };

        $scope.submitActivity = function (form) {
            if (form.$valid) {
                if ($scope.activity.activityId === null) {
                    $scope.createActivity($scope.activity);
                } else {
                    $scope.updateActivity($scope.activity);
                }
            } else {
                form.$setSubmitted();
            }

        };

        $scope.editActivity = function (id, ev) {
            for (var i = 0; i < $scope.activityList.length; i++) {
                if ($scope.activityList[i].activityId === id) {
                    $scope.activity = angular.copy($scope.activityList[i]);
                    $scope.activity.discipline = $scope.activityList[i].modalityId.disciplineId.disciplineId;
                    $scope.getModalitiesByDisciplineId($scope.activity.discipline, $scope.activity);
                    break;
                }
            }
            $scope.showCreateActivity(ev);
        };

        $scope.inactivateActivity = function (id) {
            for (var i = 0; i < $scope.activityList.length; i++) {
                if ($scope.activityList[i].activityId === id) {
                    $scope.activity = angular.copy($scope.activityList[i]);
                    break;
                }
            }
            $scope.activity.stateId = 0;
            $scope.updateActivity($scope.activity);
            $scope.resetActivity();
        };

        $scope.activateActivity = function (id) {
            for (var i = 0; i < $scope.activityList.length; i++) {
                if ($scope.activityList[i].activityId === id) {
                    $scope.activity = angular.copy($scope.activityList[i]);
                    break;
                }
            }
            $scope.activity.stateId = 1;
            $scope.updateActivity($scope.activity);
        };

        $scope.removeActivity = function (id) {
            if ($scope.activity.activityId === id) {
                $scope.resetActivity();
            }
            $scope.deleteActivity(id);
        };

        $scope.resetActivity = function () {
            $scope.activity = {activityId: null, name: '',
                description: '',
                physiologicalCapacityId: {physiologicalCapacityId: null, name: ''},
                modalityId: {modalityId: null, name: ''},
                objectiveId: {objectiveId: null, name: ''},
                sportId: {sportId: null, name: ''},
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        };

        $scope.removeFilter = function () {
            $scope.filter.show = false;
            $scope.query.filter = '';

            if ($scope.filter.form.$dirty) {
                $scope.filter.form.$setPristine();
            }
        };

        $scope.$watch('query.filter', function (newValue, oldValue) {
            if (!oldValue) {
                bookmark = $scope.query.page;
            }

            if (newValue !== oldValue) {
                $scope.query.page = 1;
            }

            if (!newValue) {
                $scope.query.page = bookmark;
            }

            $scope.getActivityPaginate();
        });

        $scope.openActivity = function (ev) {
            $scope.resetActivity();
            $scope.showCreateActivity(ev);
        };

        $scope.showCreateActivity = function (ev) {

            $mdDialog.show({
                controller: ActivityController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-activity.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    physiologicalCapacityList: function () {
                        return $scope.physiologicalCapacityList;
                    },
                    modalityList: function () {
                        return $scope.modalityList;
                    },
                    objectiveList: function () {
                        return $scope.objectiveList;
                    },
                    sportList: function () {
                        return $scope.sportList;
                    },
                    activity: function () {
                        return $scope.activity;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        function ActivityController($scope, $mdDialog,
                physiologicalCapacityList,
                modalityList,
                objectiveList,
                sportList, activity) {

            $scope.activity = activity;
            $scope.physiologicalCapacityList = physiologicalCapacityList;
            $scope.modalityList = modalityList;
            $scope.objectiveList = objectiveList;
            $scope.sportList = sportList;

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }

        $scope.getActivityPaginate();
        $scope.getPhysiologicalCapacityList();
        $scope.getObjectives();
        $scope.getSportDisciplines();
        $scope.getSportList();

    }]);