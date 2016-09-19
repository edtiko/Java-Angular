trainingApp.controller('RoleController',
        ['$scope', 'RoleService', '$window', '$mdDialog', 'OptionService', function ($scope, RoleService,
                    $window, $mdDialog, OptionService) {
                $scope.role = {roleId: null,
                    name: '',
                    stateId: '',
                    userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
                $scope.roleList = [];
                $scope.count = 0;
                $scope.tselected = [];
                $scope.toptions = [];

                $scope.selected = [];

                $scope.query = {
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

                $scope.getRolePaginate = function () {
                    $scope.promise = RoleService.getPaginate($scope.query, function (response) {
                        $scope.roleList = success(response);

                        if ($scope.roleList.length > 0) {
                            $scope.count = $scope.roleList[0].count;
                        }
                    }).$promise;
                };

                $scope.createRole = function (role) {
                    if ($scope.appReady) {
                        var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                        role.userCreate = (user.userId);
                    }
                    RoleService.createRole(role)
                            .then(
                                    function (d) {
                                        if (d.status == 'success') {
                                            $scope.showMessage(d.output);
                                            $scope.resetRole();
                                            $scope.getRolePaginate();
                                        } else {
                                            $scope.showMessage(d.output);
                                        }
                                    },
                                    function (errResponse) {
                                        console.error('Error while creating Role.');
                                    }
                            );
                };

                $scope.updateRole = function (role) {
                    if ($scope.appReady) {
                        var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                        role.userUpdate = (user.userId);
                    }

                    RoleService.mergeRole(role)
                            .then(
                                    function (d) {
                                        if (d.status == 'success') {
                                            $scope.resetRole();
                                            $scope.showMessage(d.output);
                                            $scope.getRolePaginate();
                                        } else {
                                            $scope.showMessage(d.output);
                                        }
                                    },
                                    function (errResponse) {
                                        console.error('Error while updating Role.');
                                    }
                            );
                };

                $scope.deleteRole = function (role) {
                    RoleService.deleteRole(role)
                            .then(
                                    function (d) {
                                        if (d.status == 'success') {
                                            $scope.resetRole();
                                            $scope.showMessage(d.output);
                                            $scope.getRolePaginate();
                                        } else {
                                            $scope.showMessage(d.output);
                                        }
                                    },
                                    function (errResponse) {
                                        console.error('Error while deleting Role.');
                                    }
                            );
                };

                $scope.submitRole = function (form) {
                    if (form.$valid) {
                        if ($scope.role.roleId === null) {
                            $scope.createRole($scope.role);
                        } else {
                            $scope.updateRole($scope.role);
                        }
                    } else {
                        form.$setSubmitted();
                    }

                };

                $scope.editRole = function (id) {
                    for (var i = 0; i < $scope.roleList.length; i++) {
                        if ($scope.roleList[i].roleId === id) {
                            $scope.role = angular.copy($scope.roleList[i]);
                            break;
                        }
                    }
                };

                $scope.inactivateRole = function (id) {
                    for (var i = 0; i < $scope.roleList.length; i++) {
                        if ($scope.roleList[i].roleId === id) {
                            $scope.role = angular.copy($scope.roleList[i]);
                            break;
                        }
                    }
                    $scope.role.stateId = 0;
                    $scope.updateRole($scope.role);
                    $scope.resetRole();
                };

                $scope.activateRole = function (id) {
                    for (var i = 0; i < $scope.roleList.length; i++) {
                        if ($scope.roleList[i].roleId === id) {
                            $scope.role = angular.copy($scope.roleList[i]);
                            break;
                        }
                    }
                    $scope.role.stateId = 1;
                    $scope.updateRole($scope.role);
                };

                $scope.removeRole = function (id) {
                    if ($scope.role.roleId === id) {
                        $scope.resetRole();
                    }
                    $scope.deleteRole(id);
                };
                
                $scope.getOptionList = function () {
                    OptionService.getMasterOption(function (response) {
                        $scope.toptions = success(response);
                    });
                };
                
                $scope.getRoleOptionList = function () {
                    RoleService.getRoleOption($scope.role.roleId, function (response) {
                        $scope.toptions = success(response);
                    });
                };
                
                $scope.submitRoleOption = function(form) {
                    console.debug($scope.tselected);
                };

                $scope.showCreateRoleOption = function (ev) {
                    console.debug($scope.tselected);
                    console.debug($scope.toptions);
                    $mdDialog.show({
                        controller: RoleOptionController,
                        scope: $scope.$new(),
                        templateUrl: 'static/views/security/role-option.html',
                        parent: angular.element(document.body),
                        targetEvent: ev,
                        clickOutsideToClose: true,
                        fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                        resolve: {
                            tselected: function () {
                                return $scope.tselected;
                            },
                            toptions: function () {
                                return $scope.toptions;
                            }

                        }
                    })
                            .then(function (answer) {
                                $scope.status = 'You said the information was "' + answer + '".';
                            }, function () {
                                $scope.status = 'You cancelled the dialog.';
                            });
                };

                function RoleOptionController($scope, $mdDialog, tselected, toptions) {

                    $scope.toptions = toptions;
                    $scope.tselected = tselected;

                    $scope.hide = function () {
                        $mdDialog.hide();
                    };
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };

                }

                $scope.resetRole = function () {
                    $scope.role = {roleId: null, name: '',
                        stateId: '',
                        userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
                };

                $scope.getRolePaginate();
                $scope.getOptionList();

            }]);