trainingApp.controller('OptionController', ['$scope', 'OptionService', '$window', '$mdDialog', 
    function ($scope, OptionService, $window, $mdDialog) {
    $scope.option ={optionId:null,
    name: '',
        
    url: '',
        
    description: '',
        
    moduleId: {moduleId: null, name:''},
        
    masterOptionId: {masterOptionId: null, name:''},
        
    stateId: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.optionList = [];
            $scope.moduleList = [];   
            $scope.masterOptionList = [];   
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

    $scope.getOptionPaginate = function () {
          $scope.promise = OptionService.getPaginate($scope.query, function(response) {
            $scope.optionList = success(response);
            
            if($scope.optionList.length > 0) {
                $scope.count = $scope.optionList[0].count;
            }
        }).$promise;
    };

            $scope.moduleList = [];   
    $scope.getModuleList = function () {
        OptionService.getModule(function(response) {
            $scope.moduleList = success(response);
        });
    };
            $scope.masterOptionList = [];   
    $scope.getMasterOptionList = function () {
        OptionService.getMasterOption(function(response) {
            $scope.masterOptionList = success(response);
        });
    };


    $scope.createOption = function (option) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            option.userCreate = (user.userId);
        }
        OptionService.createOption(option)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetOption();
                                $scope.getOptionPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Option.');
                        }
                );
    };

    $scope.updateOption = function (option) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            option.userUpdate = (user.userId);
        }

        OptionService.mergeOption(option)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetOption();
                                $scope.showMessage(d.output);
                                $scope.getOptionPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Option.');
                        }
                );
    };

    $scope.deleteOption = function (option) {
            var confirm = $mdDialog.confirm()
                        .title('Confirmaci\u00f3n')
                        .textContent('\u00BFDesea eliminar el registro?')
                        .ariaLabel('Lucky day')
                        .ok('Aceptar')
                        .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {

                OptionService.deleteOption(option)
                        .then(
                                function (d) {
                                if (d.status == 'success') {
                                    $scope.resetOption();
                                    $scope.showMessage(d.output);
                                    $scope.getOptionPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                                function (errResponse) {
                                    console.error('Error while deleting Option.');
                                }
                        );
            }, function () {
                });            
        };

    $scope.submitOption = function (form) {
        if (form.$valid) {
            if ($scope.option.optionId === null) {
                $scope.createOption($scope.option);
            } else {
                $scope.updateOption($scope.option);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editOption = function (id, ev) {
            for (var i = 0; i < $scope.optionList.length; i++) {
                if ($scope.optionList[i].optionId === id) {
                    $scope.option = angular.copy($scope.optionList[i]);
                    break;
                }
            }
            $scope.showCreateOption(ev);
    };
        
    $scope.inactivateOption = function (id) {
            for (var i = 0; i < $scope.optionList.length; i++) {
                if ($scope.optionList[i].optionId === id) {
                    $scope.option = angular.copy($scope.optionList[i]);
                    break;
                }
            }
            $scope.option.stateId = 0;
            $scope.updateOption($scope.option);
            $scope.resetOption();
    };
        
    $scope.activateOption = function (id) {
            for (var i = 0; i < $scope.optionList.length; i++) {
                if ($scope.optionList[i].optionId === id) {
                    $scope.option = angular.copy($scope.optionList[i]);
                    break;
                }
            }
            $scope.option.stateId = 1;
            $scope.updateOption($scope.option);
    };

    $scope.removeOption = function (id) {
            if ($scope.option.optionId === id) {
                $scope.resetOption();
            }
            $scope.deleteOption(id);
    };

    $scope.resetOption = function () {
            $scope.option ={optionId:null,name: '',
            url: '',
            description: '',
            moduleId: {moduleId: null,  name:''},
            masterOptionId: {masterOptionId: null,  name:''},
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

        $scope.getOptionPaginate();
    });
    
    $scope.openOption = function (ev) {
        $scope.resetOption();
        $scope.showCreateOption(ev);
    };
    
    $scope.showCreateOption = function (ev) {

        $mdDialog.show({
            controller: OptionController,
            scope: $scope.$new(),
            templateUrl: 'static/views/security/create-option.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
            resolve: {
                moduleList: function () {
                    return $scope.moduleList;
                },
                masterOptionList: function () {
                    return $scope.masterOptionList;
                },
                option: function () {
                    return $scope.option;
                }
                
            }
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    };

    function OptionController($scope, $mdDialog, 
                    
                    
                    
                    moduleList,
                    masterOptionList,
                    option) {

        $scope.option = option;
        $scope.moduleList = moduleList;
        $scope.masterOptionList = masterOptionList;

        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };

    }

    $scope.getOptionPaginate();

        $scope.getModuleList();
        $scope.getMasterOptionList();

}]);