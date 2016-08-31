trainingApp.controller('ObjectiveController', function ($scope, ObjectiveService,
        $window) {
    $scope.objective = {objectiveId: null,
        name: '',
        level: '',
        stateId:'',
        disciplineId: {disciplineId: null}, disciplineName: '',
        userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    $scope.objectiveList = [];
    $scope.disciplineList = [];
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

    $scope.getObjectivePaginate = function () {
        $scope.promise = ObjectiveService.getPaginate($scope.query, function (response) {
            $scope.objectiveList = success(response);

            if ($scope.objectiveList.length > 0) {
                $scope.count = $scope.objectiveList[0].count;
            }
        }).$promise;
    };

    $scope.disciplineList = [];
    $scope.getDisciplineList = function () {
        ObjectiveService.getDiscipline(function (response) {
            $scope.disciplineList = success(response);
        });
    };


    $scope.createObjective = function (objective) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            objective.userCreate = (user.userId);
        }
        ObjectiveService.createObjective(objective)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetObjective();
                                $scope.getObjectivePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Objective.');
                        }
                );
    };

    $scope.updateObjective = function (objective) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            objective.userUpdate = (user.userId);
        }

        ObjectiveService.mergeObjective(objective)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetObjective();
                                $scope.showMessage(d.output);
                                $scope.getObjectivePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Objective.');
                        }
                );
    };

    $scope.deleteObjective = function (objective) {
        ObjectiveService.deleteObjective(objective)
                .then(
                        function (d) {
                            if (d.status == 'success') {
                                $scope.resetObjective();
                                $scope.showMessage(d.output);
                                $scope.getObjectivePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while deleting Objective.');
                        }
                );
    };

    $scope.submitObjective = function (form) {
        if (form.$valid) {
            if ($scope.objective.objectiveId === null) {
                $scope.createObjective($scope.objective);
            } else {
                $scope.updateObjective($scope.objective);
            }
        } else {
            form.$setSubmitted();
        }

    };

    $scope.editObjective = function (id) {
        for (var i = 0; i < $scope.objectiveList.length; i++) {
            if ($scope.objectiveList[i].objectiveId === id) {
                $scope.objective = angular.copy($scope.objectiveList[i]);
                break;
            }
        }
    };

    $scope.inactivateObjective = function (id) {
        for (var i = 0; i < $scope.objectiveList.length; i++) {
            if ($scope.objectiveList[i].objectiveId === id) {
                $scope.objective = angular.copy($scope.objectiveList[i]);
                break;
            }
        }
        $scope.objective.stateId = 0;
        $scope.updateObjective($scope.objective);
        $scope.resetObjective();
    };

    $scope.activateObjective = function (id) {
        for (var i = 0; i < $scope.objectiveList.length; i++) {
            if ($scope.objectiveList[i].objectiveId === id) {
                $scope.objective = angular.copy($scope.objectiveList[i]);
                break;
            }
        }
        $scope.objective.stateId = 1;
        $scope.updateObjective($scope.objective);
    };

    $scope.removeObjective = function (id) {
        if ($scope.objective.objectiveId === id) {
            $scope.resetObjective();
        }
        $scope.deleteObjective(id);
    };

    $scope.resetObjective = function () {
        $scope.objective = {objectiveId: null, name: '',
            level: '',stateId:'',
            disciplineId: {disciplineId: null}, disciplineName: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
    };

    $scope.getObjectivePaginate();

    $scope.getDisciplineList();

});