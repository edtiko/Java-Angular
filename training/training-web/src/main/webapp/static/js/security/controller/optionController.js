trainingApp.controller('OptionController', function ($scope, OptionService,
        $window) {
    $scope.option = {name: '',
        url: '',
        module: '',
        state: '',
        description: '',
    };
    $scope.optionList = [];
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

    $scope.moduleList = [];
    $scope.getMasterOptions = function () {
        OptionService.getModule(function (response) {
            $scope.moduleList = success(response);
        });
    };


    $scope.createOption = function (option) {
        OptionService.createOption(option)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.showMessage("Option registrado correctamente.");
                                $scope.resetOption();
                                $scope.getOptionPaginate();
                            } else {
                                $scope.showMessage(d.detail);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Option.');
                        }
                );
    };

    $scope.updateOption = function (option) {
        OptionService.mergeOption(option)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetOption();
                                $scope.showMessage("Option  editado correctamente.");
                                $scope.getOptionPaginate();
                            } else {
                                $scope.showMessage(d.detail);
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
                        $scope.getOptionPaginate(),
                        function (errResponse) {
                            console.error('Error while deleting Option.');
                        }
                );
    };

    $scope.submitOption = function () {
        if ($scope.option.optionId === null) {
            $scope.createOption($scope.option);
        } else {
            $scope.updateOption($scope.option);
        }
    };

    $scope.editOption = function (id) {
        for (var i = 0; i < $scope.options.length; i++) {
            if ($scope.options[i].optionId === id) {
                $scope.option = angular.copy($scope.options[i]);
                break;
            }
        }
    };

    $scope.inactivateOption = function (id) {
        for (var i = 0; i < $scope.options.length; i++) {
            if ($scope.options[i].optionId === id) {
                $scope.option = angular.copy($scope.options[i]);
                break;
            }
        }
        $scope.option.stateId = 0;
        $scope.updateOption($scope.option);
        $scope.resetOption();
    };

    $scope.activateOption = function (id) {
        for (var i = 0; i < $scope.options.length; i++) {
            if ($scope.options[i].optionId === id) {
                $scope.option = angular.copy($scope.options[i]);
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
        $scope.option = {
            name: '',
            url: '',
            module: '',
            state: '',
            description: '',
        };
        $scope.formOption.$setPristine(); //reset Form
    };

    $scope.getOptionPaginate();

});