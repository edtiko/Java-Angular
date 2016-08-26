trainingApp.controller('ModuleController', function ($scope, ModuleService,
        $window) {
    $scope.module ={name: '',
    description: '',
    state: '',
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
        if(response.data.status == 'fail') {
            $scope.showMessage(response.data.output);
        } else {
            return response.data.output;
        }

        return null;
    }

    $scope.getModulePaginate = function () {
          $scope.promise = ModuleService.getPaginate($scope.query, function(response) {
            $scope.moduleList = success(response);
            
            if($scope.moduleList.length > 0) {
                $scope.count = $scope.moduleList[0].count;
            }
        }).$promise;
    };



    $scope.createModule = function (module) {
            ModuleService.createModule(module)
                    .then(
                            function (d) {
                                if(d.status == 'success') {
                                    $scope.showMessage("Module registrado correctamente.");
                                    $scope.resetModule();
                                    $scope.getModulePaginate();
                                } else {
                                    $scope.showMessage(d.detail);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating Module.');
                            }
                    );
    };

    $scope.updateModule = function (module) {
            ModuleService.mergeModule(module)
                    .then(
                            function (d) {
                                if(d.status == 'success') {
                                    $scope.resetModule();
                                    $scope.showMessage("Module  editado correctamente.");
                                    $scope.getModulePaginate();
                                } else {
                                    $scope.showMessage(d.detail);
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
                            $scope.getModulePaginate(),
                            function (errResponse) {
                                console.error('Error while deleting Module.');
                            }
                    );
        };

    $scope.submitModule = function () {
            if ($scope.module.moduleId === null) {
                $scope.createModule($scope.module);
            } else {
                $scope.updateModule($scope.module);
            }
        };

    $scope.editModule = function (id) {
            for (var i = 0; i < $scope.modules.length; i++) {
                if ($scope.modules[i].moduleId === id) {
                    $scope.module = angular.copy($scope.modules[i]);
                    break;
                }
            }
    };
        
    $scope.inactivateModule = function (id) {
            for (var i = 0; i < $scope.modules.length; i++) {
                if ($scope.modules[i].moduleId === id) {
                    $scope.module = angular.copy($scope.modules[i]);
                    break;
                }
            }
            $scope.module.stateId = 0;
            $scope.updateModule($scope.module);
            $scope.resetModule();
    };
        
    $scope.activateModule = function (id) {
            for (var i = 0; i < $scope.modules.length; i++) {
                if ($scope.modules[i].moduleId === id) {
                    $scope.module = angular.copy($scope.modules[i]);
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
            $scope.module ={ 
                        name: '',
                                        description: '',
                                        state: '',
                };
            $scope.formModule.$setPristine(); //reset Form
    };

    $scope.getModulePaginate();


});