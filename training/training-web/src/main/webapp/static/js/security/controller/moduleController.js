trainingApp.controller('ModuleController', function ($scope, ModuleService,
        $window) {
    $scope.module = {moduleId: null, name: '',
        description: '',
        stateId: '', userCreate: '', userUpdate: '',
        userCreateName: '', userUpdateName: ''
    };
    $scope.moduleList = [];
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

    $scope.getModulePaginate = function () {
        $scope.promise = ModuleService.getPaginate($scope.query, function (response) {
            $scope.moduleList = success(response);

            if ($scope.moduleList.length > 0) {
                $scope.count = $scope.moduleList[0].count;
            }
        }).$promise;
    };



    $scope.createModule = function (module) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            module.userCreate = (user.userId);
        }
        ModuleService.createModule(module)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetModule();
                                $scope.getModulePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Module.');
                        }
                );
    };

    $scope.updateModule = function (module) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            module.userUpdate = (user.userId);
        }
        ModuleService.mergeModule(module)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetModule();
                                $scope.showMessage(d.output);
                                $scope.getModulePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Module.');
                        }
                );
    };

    $scope.deleteModule = function (module) {
        ModuleService.deleteModule(module)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetModule();
                                $scope.showMessage(d.output);
                                $scope.getModulePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while deleting Module.');
                        }
                );
    };

    $scope.submitModule = function (form) {
        if (form.$valid) {
            if ($scope.module.moduleId === null) {
                $scope.createModule($scope.module);
            } else {
                $scope.updateModule($scope.module);
            }
        } else {
            form.$setSubmitted();
        }
    };

    $scope.editModule = function (id) {
        for (var i = 0; i < $scope.moduleList.length; i++) {
            if ($scope.moduleList[i].moduleId === id) {
                $scope.module = angular.copy($scope.moduleList[i]);
                break;
            }
        }
    };

    $scope.inactivateModule = function (id) {
        for (var i = 0; i < $scope.moduleList.length; i++) {
            if ($scope.moduleList[i].moduleId === id) {
                $scope.module = angular.copy($scope.moduleList[i]);
                break;
            }
        }
        $scope.module.stateId = 0;
        $scope.updateModule($scope.module);
        $scope.resetModule();
    };

    $scope.activateModule = function (id) {
        for (var i = 0; i < $scope.moduleList.length; i++) {
            if ($scope.moduleList[i].moduleId === id) {
                $scope.module = angular.copy($scope.moduleList[i]);
                break;
            }
        }
        $scope.module.stateId = 1;
        $scope.updateModule($scope.module);
    };

    $scope.removeModule = function (id) {
        if ($scope.module.moduleId === id) {
            $scope.resetModule();
        }
        $scope.deleteModule(id);
    };

    $scope.resetModule = function () {
        $scope.module = {moduleId: null, name: '',
            description: '',
            stateId: '', userCreate: '', userUpdate: '',
            userCreateName: '', userUpdateName: ''
        };
    };

    $scope.getModulePaginate();


});