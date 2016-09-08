trainingApp.controller('CharacteristicController', ['$scope', 'CharacteristicService', '$window',
    function ($scope, CharacteristicService, $window) {
        $scope.characteristic = {characteristicId: null,
            name: '',
            valueType: '',
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.characteristicList = [];
        $scope.count = 0;

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

        $scope.editCharacteristic = function (id, ev) {
            for (var i = 0; i < $scope.characteristicList.length; i++) {
                if ($scope.characteristicList[i].characteristicId === id) {
                    $scope.characteristic = angular.copy($scope.characteristicList[i]);
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

        $scope.getCharacteristicPaginate();


    }]);