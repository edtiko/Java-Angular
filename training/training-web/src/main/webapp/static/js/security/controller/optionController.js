trainingApp.controller('OptionController', function ($scope, OptionService, ModuleService,
        $window) {
    $scope.option = {optionId: null,
        name: '',
        url: '',
        description: '',
        masterOptionId: {optionId:null}, masterOptionName: '',
        stateId: '',
        moduleId: {moduleId:null}, moduleName: '',
        userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    $scope.optionList = [];
    $scope.masterOptionList = [];
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

    $scope.getOptionPaginate = function () {
        $scope.promise = OptionService.getPaginate($scope.query, function (response) {
            $scope.optionList = success(response);

            if ($scope.optionList.length > 0) {
                $scope.count = $scope.optionList[0].count;
            }
        }).$promise;
    };

    $scope.masterOptionList = [];
    $scope.getMasterOptionList = function () {
        OptionService.getMasterOption(function (response) {
            $scope.masterOptionList = success(response);
            
        });
    };
    $scope.moduleList = [];
    $scope.getModuleList = function () {
        OptionService.getModule(function (response) {
            $scope.moduleList = success(response);
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
                            if (d.status == 'success') {
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
                            if (d.status == 'success') {
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

    $scope.editOption = function (id) {
        for (var i = 0; i < $scope.optionList.length; i++) {
            if ($scope.optionList[i].optionId === id) {
                $scope.option = angular.copy($scope.optionList[i]);
                break;
            }
        }
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
        $scope.option = {optionId: null, name: '',
            url: '',
            description: '',
            masterOptionId: {optionId:null}, masterOptionName: '',
            stateId: '',
            moduleId: {moduleId:null}, moduleName: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    };

    $scope.getOptionPaginate();

    $scope.getMasterOptionList();
    $scope.getModuleList();

});