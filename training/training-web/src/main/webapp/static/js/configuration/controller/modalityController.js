trainingApp.controller('ModalityController', ['$scope', 'ModalityService', '$window', 'DisciplineService', '$mdDialog',
    function ($scope, ModalityService, $window, DisciplineService, $mdDialog) {
        $scope.modality = {modalityId: null,
            name: '',
            disciplineId: {disciplineId: null, name: ''},
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.modalityList = [];
        $scope.disciplineList = [];
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

        $scope.getModalityPaginate = function () {
            $scope.promise = ModalityService.getPaginate($scope.query, function (response) {
                $scope.modalityList = success(response);

                if ($scope.modalityList.length > 0) {
                    $scope.count = $scope.modalityList[0].count;
                }
            }).$promise;
        };

        $scope.getSportDisciplines = function () {
            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        $scope.disciplineList = d;
                    },
                    function (errResponse) {
                        console.error('Error while disciplines');
                        console.error(errResponse);
                    }
            );
        };


        $scope.createModality = function (modality) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                modality.userCreate = (user.userId);
            }
            ModalityService.createModality(modality)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetModality();
                                    $scope.getModalityPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating Modality.');
                            }
                    );
        };

        $scope.updateModality = function (modality) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                modality.userUpdate = (user.userId);
            }

            ModalityService.mergeModality(modality)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetModality();
                                    $scope.showMessage(d.output);
                                    $scope.getModalityPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating Modality.');
                            }
                    );
        };

        $scope.deleteModality = function (modality) {
            ModalityService.deleteModality(modality)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetModality();
                                    $scope.showMessage(d.output);
                                    $scope.getModalityPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while deleting Modality.');
                            }
                    );
        };

        $scope.submitModality = function (form) {
            if (form.$valid) {
                if ($scope.modality.modalityId === null) {
                    $scope.createModality($scope.modality);
                } else {
                    $scope.updateModality($scope.modality);
                }
            } else {
                form.$setSubmitted();
            }

        };

        $scope.editModality = function (id, ev) {
            for (var i = 0; i < $scope.modalityList.length; i++) {
                if ($scope.modalityList[i].modalityId === id) {
                    $scope.modality = angular.copy($scope.modalityList[i]);
                    break;
                }
            }
            $scope.showCreateModality(ev);
        };

        $scope.inactivateModality = function (id) {
            for (var i = 0; i < $scope.modalityList.length; i++) {
                if ($scope.modalityList[i].modalityId === id) {
                    $scope.modality = angular.copy($scope.modalityList[i]);
                    break;
                }
            }
            $scope.modality.stateId = 0;
            $scope.updateModality($scope.modality);
            $scope.resetModality();
        };

        $scope.activateModality = function (id) {
            for (var i = 0; i < $scope.modalityList.length; i++) {
                if ($scope.modalityList[i].modalityId === id) {
                    $scope.modality = angular.copy($scope.modalityList[i]);
                    break;
                }
            }
            $scope.modality.stateId = 1;
            $scope.updateModality($scope.modality);
        };

        $scope.removeModality = function (id) {
            if ($scope.modality.modalityId === id) {
                $scope.resetModality();
            }
            $scope.deleteModality(id);
        };

        $scope.resetModality = function () {
            $scope.modality = {modalityId: null, name: '',
                disciplineId: {disciplineId: null, name: ''},
                stateId: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        };

        $scope.getModalityPaginate();
        $scope.getSportDisciplines();


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

            $scope.getModalityPaginate();
        });

        $scope.showCreateModality = function (ev) {

            $mdDialog.show({
                controller: ModalityController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-modality.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    modality: function () {
                        return $scope.modality;
                    },
                    disciplineList: function () {
                        return $scope.disciplineList;
                    }
                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        function ModalityController($scope, $mdDialog, modality, disciplineList) {

            $scope.modality = modality;
            $scope.disciplineList = disciplineList;

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }
    }]);