trainingApp.controller('CharacteristicController', ['$scope', 'CharacteristicService', 'TrainingPlanService', '$window', '$mdDialog',
    function ($scope, CharacteristicService, TrainingPlanService, $window, $mdDialog) {
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

        $scope.characteristicList = [];
        $scope.planCharacteristicList = [];
        $scope.planList = [];
        $scope.count = 0;

        var bookmark;
        $scope.selected = [];
        $scope.selectedPlan = [];

        $scope.filter = {
            options: {
                debounce: 500
            }
        };

        $scope.query = {
            filter: '',
            order: 'name',
            limit: 5,
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

        $scope.getCharacteristicPaginate = function () {
            $scope.promise = CharacteristicService.getPaginate($scope.query, function (response) {
                $scope.characteristicList = success(response);

                if ($scope.characteristicList.length > 0) {
                    $scope.count = $scope.characteristicList[0].count;
                }
            }).$promise;
        };

        $scope.getPlanCharacteristicPaginate = function (characteristicId) {
            $scope.promise = CharacteristicService.getPlanCharacteristicPaginate(characteristicId, function (response) {
                $scope.planCharacteristicList = success(response);
            }).$promise;
        };

        $scope.getPlanCharacteristicList = function (characteristicId, success) {
            $scope.promise = CharacteristicService.getPlanCharacteristicPaginate(characteristicId, success).$promise;
        };

        $scope.getPlan = function (sucess) {
            TrainingPlanService.getAll().then(sucess,
                    function (errResponse) {
                        console.error('Error while plan');
                        console.error(errResponse);
                    }
            );
        };

        $scope.createCharacteristic = function (characteristic) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                characteristic.userCreate = (user.userId);
            }
            CharacteristicService.createCharacteristic(characteristic)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetCharacteristic();
                                    $scope.getCharacteristicPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating Characteristic.');
                            }
                    );
        };

        $scope.updateCharacteristic = function (characteristic) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                characteristic.userUpdate = (user.userId);
            }

            CharacteristicService.mergeCharacteristic(characteristic)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetCharacteristic();
                                    $scope.showMessage(d.output);
                                    $scope.getCharacteristicPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating Characteristic.');
                            }
                    );
        };

        $scope.deleteCharacteristic = function (characteristic) {
            CharacteristicService.deleteCharacteristic(characteristic)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetCharacteristic();
                                    $scope.showMessage(d.output);
                                    $scope.getCharacteristicPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while deleting Characteristic.');
                            }
                    );
        };
        
        $scope.deletePlanCharacteristic = function (characteristic) {
            CharacteristicService.deletePlanCharacteristic(characteristic)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetPlanCharacteristic();
                                    $scope.getPlanCharacteristicPaginate($scope.characteristic.characteristicId);
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while deleting Characteristic.');
                            }
                    );
        };

        $scope.submitCharacteristic = function (form) {
            if (form.$valid) {
                if ($scope.characteristic.characteristicId === null) {
                    $scope.createCharacteristic($scope.characteristic);
                } else {
                    $scope.updateCharacteristic($scope.characteristic);
                }
            } else {
                form.$setSubmitted();
            }

        };

        $scope.submitPlanCharacteristic = function (form) {
            if (form.$valid) {
                $scope.createPlanCharacteristic($scope.planCharacteristic);
            } else {
                form.$setSubmitted();
            }

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
                                    $scope.getPlanCharacteristicPaginate($scope.characteristic.characteristicId);
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating Characteristic.');
                            }
                    );
        };

        $scope.editCharacteristic = function (id, ev) {
            for (var i = 0; i < $scope.characteristicList.length; i++) {
                if ($scope.characteristicList[i].characteristicId === id) {
                    $scope.characteristic = angular.copy($scope.characteristicList[i]);

                    if ($scope.characteristic.valueType == 'CHECK') {
                        $scope.characteristic.valueType = 1;
                    } else if ($scope.characteristic.valueType == 'NUMERO') {
                        $scope.characteristic.valueType = 2;
                    } else {
                        $scope.characteristic.valueType = 3;
                    }
                    break;
                }
            }
            $scope.showCreateCharacteristic(ev);
        };

        $scope.inactivateCharacteristic = function (id) {
            for (var i = 0; i < $scope.characteristicList.length; i++) {
                if ($scope.characteristicList[i].characteristicId === id) {
                    $scope.characteristic = angular.copy($scope.characteristicList[i]);
                    break;
                }
            }
            $scope.characteristic.stateId = 0;
            $scope.updateCharacteristic($scope.characteristic);
            $scope.resetCharacteristic();
        };

        $scope.activateCharacteristic = function (id) {
            for (var i = 0; i < $scope.characteristicList.length; i++) {
                if ($scope.characteristicList[i].characteristicId === id) {
                    $scope.characteristic = angular.copy($scope.characteristicList[i]);
                    break;
                }
            }
            $scope.characteristic.stateId = 1;
            $scope.updateCharacteristic($scope.characteristic);
        };

        $scope.removeCharacteristic = function (id) {
            if ($scope.characteristic.characteristicId === id) {
                $scope.resetCharacteristic();
            }
            $scope.deleteCharacteristic(id);
        };

        $scope.resetCharacteristic = function () {
            $scope.characteristic = {characteristicId: null, name: '',
                valueType: '',
                stateId: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        };

        $scope.resetPlanCharacteristic = function () {
            $scope.planCharacteristic = {
                trainingPlanCharactId: null,
                value: '',
                characteristicId: {characteristicId: null, name: ''},
                trainingPlanId: {trainingPlanId: null, name: ''}
            };
        }

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

            $scope.getCharacteristicPaginate();
        });

        $scope.showCreateCharacteristic = function (ev) {

            $mdDialog.show({
                controller: CharacteristicController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-characteristic.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    characteristic: function () {
                        return $scope.characteristic;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        function CharacteristicController($scope, $mdDialog, characteristic) {

            $scope.characteristic = characteristic;



            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }

        $scope.addPlan = function (characteristicId, ev) {
            $scope.getPlanCharacteristicList(characteristicId, function (response) {
                $scope.planCharacteristicList = success(response);

                $scope.getPlan(
                        function (d) {
                            if (d.status == 'fail') {
                                $scope.showMessage(d.output);
                            } else {
                                $scope.planList = d.output;
                            }

                            for (var i = 0; i < $scope.characteristicList.length; i++) {
                                if ($scope.characteristicList[i].characteristicId === characteristicId) {
                                    $scope.characteristic = angular.copy($scope.characteristicList[i]);

                                    if ($scope.characteristic.valueType == 'CHECK') {
                                        $scope.characteristic.valueType = 1;
                                    } else if ($scope.characteristic.valueType == 'NUMERO') {
                                        $scope.characteristic.valueType = 2;
                                    } else {
                                        $scope.characteristic.valueType = 3;
                                    }
                                    break;
                                }
                            }
                            $scope.planCharacteristic.characteristicId.characteristicId = characteristicId;

                            $mdDialog.show({
                                controller: PlanCharacteristicController,
                                scope: $scope.$new(),
                                templateUrl: 'static/views/configuration/add-plan-characteristic.html',
                                parent: angular.element(document.body),
                                targetEvent: ev,
                                clickOutsideToClose: true,
                                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                                resolve: {
                                    characteristic: function () {
                                        return $scope.characteristic;
                                    },
                                    planList: function () {
                                        return $scope.planList;
                                    },
                                    planCharacteristicList: function () {
                                        return $scope.planCharacteristicList;
                                    },
                                }
                            })
                                    .then(function (answer) {
                                        $scope.status = 'You said the information was "' + answer + '".';
                                    }, function () {
                                        $scope.status = 'You cancelled the dialog.';
                                    });

                        });

            })
        };

        function PlanCharacteristicController($scope, $mdDialog, characteristic, planList, planCharacteristicList) {

            $scope.characteristic = characteristic;
            $scope.planList = planList;
            $scope.planCharacteristicList = planCharacteristicList;
            $scope.selectedPlan = [];

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }

        $scope.validateValueTypeVisible = function () {
            if ($scope.characteristic.valueType == 1) {
                return false;
            } else {
                return true;
            }
        }

        $scope.getCharacteristicPaginate();
    }]);