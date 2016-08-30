trainingApp.controller('TrainingPlanController', function ($scope, TrainingPlanService,
        $window) {
    $scope.trainingPlan = {trainingPlanId: null,
        name: '',
        description: '',
        duration: '',
        videoCount: '',
        messageCount: '',
        emailCount: '',
        callCount: '',
        endDate: '',
        stateId:'',
        userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    $scope.trainingPlanList = [];
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

    $scope.getTrainingPlanPaginate = function () {
        $scope.promise = TrainingPlanService.getPaginate($scope.query, function (response) {
            $scope.trainingPlanList = success(response);

            if ($scope.trainingPlanList.length > 0) {
                $scope.count = $scope.trainingPlanList[0].count;
            }
        }).$promise;
    };



    $scope.createTrainingPlan = function (trainingPlan) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            trainingPlan.userCreate = (user.userId);
        }
        TrainingPlanService.createTrainingPlan(trainingPlan)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetTrainingPlan();
                                $scope.getTrainingPlanPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating TrainingPlan.');
                        }
                );
    };

    $scope.updateTrainingPlan = function (trainingPlan) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            trainingPlan.userUpdate = (user.userId);
        }

        TrainingPlanService.mergeTrainingPlan(trainingPlan)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetTrainingPlan();
                                $scope.showMessage(d.output);
                                $scope.getTrainingPlanPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating TrainingPlan.');
                        }
                );
    };

    $scope.deleteTrainingPlan = function (trainingPlan) {
        TrainingPlanService.deleteTrainingPlan(trainingPlan)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetTrainingPlan();
                                $scope.showMessage(d.output);
                                $scope.getTrainingPlanPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while deleting TrainingPlan.');
                        }
                );
    };

    $scope.submitTrainingPlan = function (form) {
        if (form.$valid) {
            if ($scope.trainingPlan.trainingPlanId === null) {
                $scope.createTrainingPlan($scope.trainingPlan);
            } else {
                $scope.updateTrainingPlan($scope.trainingPlan);
            }
        } else {
            form.$setSubmitted();
        }

    };

    $scope.editTrainingPlan = function (id) {
        for (var i = 0; i < $scope.trainingPlanList.length; i++) {
            if ($scope.trainingPlanList[i].trainingPlanId === id) {
                $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                break;
            }
        }
    };

    $scope.inactivateTrainingPlan = function (id) {
        for (var i = 0; i < $scope.trainingPlanList.length; i++) {
            if ($scope.trainingPlanList[i].trainingPlanId === id) {
                $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                break;
            }
        }
        $scope.trainingPlan.stateId = 0;
        $scope.updateTrainingPlan($scope.trainingPlan);
        $scope.resetTrainingPlan();
    };

    $scope.activateTrainingPlan = function (id) {
        for (var i = 0; i < $scope.trainingPlanList.length; i++) {
            if ($scope.trainingPlanList[i].trainingPlanId === id) {
                $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                break;
            }
        }
        $scope.trainingPlan.stateId = 1;
        $scope.updateTrainingPlan($scope.trainingPlan);
    };

    $scope.removeTrainingPlan = function (id) {
        if ($scope.trainingPlan.trainingPlanId === id) {
            $scope.resetTrainingPlan();
        }
        $scope.deleteTrainingPlan(id);
    };

    $scope.resetTrainingPlan = function () {
        $scope.trainingPlan = {trainingPlanId: null, name: '',
            description: '',
            duration: '',
            videoCount: '',
            messageCount: '',
            emailCount: '',
            callCount: '',
            endDate: '',
            stateId:'',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    };

    $scope.getTrainingPlanPaginate();


});