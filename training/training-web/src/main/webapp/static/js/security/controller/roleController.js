trainingApp.controller('RoleController', function ($scope, RoleService,
        $window) {
    $scope.role = {roleId: null,
        name: '',
        stateId: '',
        userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    $scope.roleList = [];
    $scope.count = 0;

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

    $scope.resetRole = function () {
        $scope.role = {roleId: null, name: '',
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    };

    $scope.getRolePaginate();


});