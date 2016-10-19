trainingApp.controller('ModelController', ['$scope', 'ModelService', '$window', '$mdDialog', 
    function ($scope, ModelService, $window, $mdDialog) {
    $scope.model ={modelId:null,
    sportEquipmentTypeId: {sportEquipmentTypeId: null, name:''},
    name: '', stateId: '',userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.modelList = [];
    $scope.sportEquipmentTypeList = [];   
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
        if(response.data.status == 'fail') {
            $scope.showMessage(response.data.output);
        } else {
            return response.data.output;
        }

        return null;
    }

    $scope.getModelPaginate = function () {
          $scope.promise = ModelService.getPaginate($scope.query, function(response) {
            $scope.modelList = success(response);
            
            if($scope.modelList.length > 0) {
                $scope.count = $scope.modelList[0].count;
            }
        }).$promise;
    };
  
    $scope.getSportEquipmentTypeList = function () {
        ModelService.getSportEquipmentType(function(response) {
            $scope.sportEquipmentTypeList = success(response);
        });
    };


    $scope.createModel = function (model) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            model.userCreate = (user.userId);
        }
        ModelService.createModel(model)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetModel();
                                $scope.getModelPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Model.');
                        }
                );
    };

    $scope.updateModel = function (model) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            model.userUpdate = (user.userId);
        }

        ModelService.mergeModel(model)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetModel();
                                $scope.showMessage(d.output);
                                $scope.getModelPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Model.');
                        }
                );
    };

    $scope.deleteModel = function (model) {
            ModelService.deleteModel(model)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetModel();
                                $scope.showMessage(d.output);
                                $scope.getModelPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting Model.');
                            }
                    );
        };

    $scope.submitModel = function (form) {
        if (form.$valid) {
            if ($scope.model.modelId === null) {
                $scope.createModel($scope.model);
            } else {
                $scope.updateModel($scope.model);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editModel = function (id, ev) {
            for (var i = 0; i < $scope.modelList.length; i++) {
                if ($scope.modelList[i].modelId === id) {
                    $scope.model = angular.copy($scope.modelList[i]);
                    break;
                }
            }
            $scope.showCreateModel(ev);
    };
        
    $scope.inactivateModel = function (id) {
            for (var i = 0; i < $scope.modelList.length; i++) {
                if ($scope.modelList[i].modelId === id) {
                    $scope.model = angular.copy($scope.modelList[i]);
                    break;
                }
            }
            $scope.model.stateId = 0;
            $scope.updateModel($scope.model);
            $scope.resetModel();
    };
        
    $scope.activateModel = function (id) {
            for (var i = 0; i < $scope.modelList.length; i++) {
                if ($scope.modelList[i].modelId === id) {
                    $scope.model = angular.copy($scope.modelList[i]);
                    break;
                }
            }
            $scope.model.stateId = 1;
            $scope.updateModel($scope.model);
    };

    $scope.removeModel = function (id) {
            if ($scope.model.modelId === id) {
                $scope.resetModel();
            }
            $scope.deleteModel(id);
    };

    $scope.resetModel = function () {
            $scope.model ={modelId:null,sportEquipmentTypeId: {sportEquipmentTypeId: null,  name:''},
            name: '',
            stateId: '',
            userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
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

        $scope.getModelPaginate();
    });

    $scope.showCreateModel = function (ev) {

        $mdDialog.show({
            controller: ModelController,
            scope: $scope.$new(),
            templateUrl: 'static/views/configuration/create-model.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
            resolve: {
                sportEquipmentTypeList: function () {
                    return $scope.sportEquipmentTypeList;
                },
                model: function () {
                    return $scope.model;
                }
                
            }
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    };

    function ModelController($scope, $mdDialog, 
                    sportEquipmentTypeList,
                    
                    model) {

        $scope.model = model;
        $scope.sportEquipmentTypeList = sportEquipmentTypeList

        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };

    }

    $scope.getModelPaginate();
 
    $scope.getSportEquipmentTypeList();

}]);