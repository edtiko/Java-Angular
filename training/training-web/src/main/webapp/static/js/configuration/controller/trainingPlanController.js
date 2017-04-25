trainingApp.controller('TrainingPlanController', ['$scope', 'TrainingPlanService', 'CharacteristicService', '$window', '$mdDialog', '$routeParams',
    function ($scope, TrainingPlanService, CharacteristicService, $window, $mdDialog, $routeParams) {
        $scope.trainingPlan = {
            trainingPlanId: null, typePlan: null,
            name: '',
            description: '',
            duration: '',
            price: '',
            supervisorUserId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.trainingPlanList = [];
        $scope.count = 0;
        $scope.supervisorUsers = [];

        var bookmark;
        $scope.selected = [];

        $scope.filter = {
            options: {
                debounce: 500
            }
        };
        $scope.typePlan = $routeParams.typePlan;
        $scope.tipoPlanText = '';

        if ($scope.typePlan == '1') {
            $scope.tipoPlanText = 'Plan de Entrenamiento';
        } else {
            $scope.tipoPlanText = 'Plan de Plataforma';
        }

        $scope.query = {
            filter: '',
            order: 'name',
            limit: 10,
            page: 1
        };

        function success(response) {
            if (response.data.status == 'fail') {
                $scope.showMessage(response.data.output);
            } else {
                return response.data.output;
            }

            return null;
        }

        $scope.getTrainingPlanPaginate = function () {
            $scope.promise = TrainingPlanService.getPaginate($scope.query, $scope.typePlan, function (response) {
                $scope.trainingPlanList = success(response);

                if ($scope.trainingPlanList.length > 0) {
                    $scope.count = $scope.trainingPlanList[0].count;
                }
            }).$promise;
        };
        $window.sessionStorage.setItem("trainingIdConfiguration", '');
        $scope.showDetail = function (id) {
            $window.sessionStorage.setItem("trainingIdConfiguration", id);
            $window.location.href = "#create-configuration-plan/" + $scope.typePlan;
        };

        $scope.createTrainingPlan = function (trainingPlan) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                trainingPlan.userCreate = (user.userId);
                trainingPlan.typePlan = $scope.typePlan;
            }
            TrainingPlanService.createTrainingPlan(trainingPlan)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    if ($scope.typePlan == '2') {
                                        $scope.createPlanWordpress(trainingPlan);
                                    }

                                    $scope.showMessage(d.output);
                                    $scope.resetTrainingPlan();
                                    $scope.getTrainingPlanPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating TrainingPlan.');
                            }
                    );
        };

        $scope.createPlanWordpress = function (plan) {
            TrainingPlanService.createPlanWordPress(plan)
                    .then(
                            function (d) {
                                var response = d.data;

                                if (response.status == 'success') {
                                    return;
                                } else {
                                    $scope.showMessage('Error al integrar plan');
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating TrainingPlan.');
                            }
                    );
        };

        $scope.updateTrainingPlan = function (trainingPlan) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                trainingPlan.userUpdate = (user.userId);
                trainingPlan.typePlan = $scope.typePlan;
            }

            TrainingPlanService.mergeTrainingPlan(trainingPlan)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetTrainingPlan();
                                    $scope.showMessage(d.output);
                                    $scope.getTrainingPlanPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating TrainingPlan.');
                            }
                    );
        };

        $scope.deleteTrainingPlan = function (trainingPlan) {

            var confirm = $mdDialog.confirm()
                    .title('Confirmaci\u00f3n')
                    .textContent('\u00BFDesea eliminar el registro?')
                    .ariaLabel('Lucky day')
                    .ok('Aceptar')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                TrainingPlanService.deleteTrainingPlan(trainingPlan)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.resetTrainingPlan();
                                        $scope.showMessage(d.output);
                                        $scope.getTrainingPlanPaginate();
                                    } else {
                                        $scope.showMessage(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting TrainingPlan.');
                                }
                        );

            }, function () {
            });


        };

        $scope.submitTrainingPlan = function (form) {
            if (form.$valid) {
                if ($scope.trainingPlan.trainingPlanId === null) {
                    $scope.createTrainingPlan($scope.trainingPlan);
                } else {
                    $scope.updateTrainingPlan($scope.trainingPlan);
                }
            } else {
                form.$setSubmitted();
            }

        };

        $scope.editTrainingPlan = function (id, ev) {
            for (var i = 0; i < $scope.trainingPlanList.length; i++) {
                if ($scope.trainingPlanList[i].trainingPlanId === id) {
                    $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                    break;
                }
            }
            $scope.showCreateTrainingPlan(ev);
        };

        $scope.inactivateTrainingPlan = function (id) {
            for (var i = 0; i < $scope.trainingPlanList.length; i++) {
                if ($scope.trainingPlanList[i].trainingPlanId === id) {
                    $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                    break;
                }
            }
            $scope.trainingPlan.stateId = 0;
            $scope.updateTrainingPlan($scope.trainingPlan);
            $scope.resetTrainingPlan();
        };

        $scope.activateTrainingPlan = function (id) {
            for (var i = 0; i < $scope.trainingPlanList.length; i++) {
                if ($scope.trainingPlanList[i].trainingPlanId === id) {
                    $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                    break;
                }
            }
            $scope.trainingPlan.stateId = 1;
            $scope.updateTrainingPlan($scope.trainingPlan);
        };

        $scope.removeTrainingPlan = function (id) {
            if ($scope.trainingPlan.trainingPlanId === id) {
                $scope.resetTrainingPlan();
            }
            $scope.deleteTrainingPlan(id);
        };

        $scope.resetTrainingPlan = function () {
            $scope.trainingPlan = {trainingPlanId: null, name: '',
                description: '',
                duration: '',
                price: '',
                supervisorUserId: '',
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

            $scope.getTrainingPlanPaginate();
        });

        $scope.openTrainingPlan = function (ev) {

            $scope.trainingPlan = {trainingPlanId: null,
                name: '',
                description: '',
                duration: '',
                price: '',
                supervisorUserId: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
            $scope.showCreateTrainingPlan(ev);
        };

        $scope.showCreateTrainingPlan = function (ev) {

            $mdDialog.show({
                controller: TrainingPlanController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-trainingPlan.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    trainingPlan: function () {
                        return $scope.trainingPlan;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        function TrainingPlanController($scope, $mdDialog,
                trainingPlan) {

            $scope.trainingPlan = trainingPlan;


            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };
        }

        $scope.showCharac = function (ev) {
            $mdDialog.show({
                controller: CharacteristicController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/add-plan-characteristic.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    trainingPlan: function () {
                        return $scope.trainingPlan;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        $scope.characteristic = {characteristicId: null,
            name: '',
            valueType: '',
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};

        $scope.planCharacteristic = {
            trainingPlanCharactId: null,
            value: '',
            characteristicId: {characteristicId: null, name: ''},
            trainingPlanId: {trainingPlanId: null, name: ''},
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''
        };



        function CharacteristicController($scope, $mdDialog,
                trainingPlan) {

            $scope.trainingPlan = trainingPlan;
            $scope.visibleCharact = false;
            $scope.characteristicList = [];

            $scope.hide = function () {
                $mdDialog.hide();
            };

            $scope.cancel = function () {
                $mdDialog.cancel();
            };

            $scope.resetPlanCharacteristic = function () {
                $scope.planCharacteristic = {
                    trainingPlanCharactId: null,
                    value: '',
                    characteristicId: {characteristicId: null, name: ''},
                    trainingPlanId: {trainingPlanId: null, name: ''}
                };
            };

            $scope.createPlanCharacteristic = function (planCharacteristic) {
                if ($scope.appReady) {
                    var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                    planCharacteristic.userCreate = (user.userId);
                }
                CharacteristicService.createPlanCharacteristic(planCharacteristic)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.showMessage(d.output);
                                        $scope.resetPlanCharacteristic();
                                    } else {
                                        $scope.showMessage(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while creating Characteristic.');
                                }
                        );
            };

            $scope.submitPlanCharacteristic = function (form) {
                if (form.$valid) {
                    $scope.createPlanCharacteristic($scope.planCharacteristic);
                } else {
                    form.$setSubmitted();
                }
            };

            $scope.getCharacteristicAll = function () {
                $scope.promise = CharacteristicService.getCharacteristicAll(function (response) {
                    $scope.characteristicList = success(response);
                    console.debug($scope.characteristicList)
                }).$promise;
            };

            $scope.validateValueTypeVisible = function () {
                if ($scope.characteristic.valueType == 1) {
                    $scope.visibleCharact = false;
                } else {
                    $scope.visibleCharact = true;
                }
            }

            $scope.getCharacteristicAll();
        }

        $scope.getTrainingPlanPaginate();


    }]);