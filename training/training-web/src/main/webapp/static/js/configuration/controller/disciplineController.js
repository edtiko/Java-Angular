trainingApp.controller('DisciplineController', function ($scope, DisciplineService,
        $window) {
    $scope.discipline ={disciplineId:null,
    name: '',
        
    description: '',
        
    stateId: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.disciplineList = [];
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

    $scope.getDisciplinePaginate = function () {
          $scope.promise = DisciplineService.getPaginate($scope.query, function(response) {
            $scope.disciplineList = success(response);
            
            if($scope.disciplineList.length > 0) {
                $scope.count = $scope.disciplineList[0].count;
            }
        }).$promise;
    };



    $scope.createDiscipline = function (discipline) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            discipline.userCreate = (user.userId);
        }
        DisciplineService.createDiscipline(discipline)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetDiscipline();
                                $scope.getDisciplinePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Discipline.');
                        }
                );
    };

    $scope.updateDiscipline = function (discipline) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            discipline.userUpdate = (user.userId);
        }

        DisciplineService.mergeDiscipline(discipline)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetDiscipline();
                                $scope.showMessage(d.output);
                                $scope.getDisciplinePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Discipline.');
                        }
                );
    };

    $scope.deleteDiscipline = function (discipline) {
            DisciplineService.deleteDiscipline(discipline)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetDiscipline();
                                $scope.showMessage(d.output);
                                $scope.getDisciplinePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting Discipline.');
                            }
                    );
        };

    $scope.submitDiscipline = function (form) {
        if (form.$valid) {
            if ($scope.discipline.disciplineId === null) {
                $scope.createDiscipline($scope.discipline);
            } else {
                $scope.updateDiscipline($scope.discipline);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editDiscipline = function (id) {
            for (var i = 0; i < $scope.disciplineList.length; i++) {
                if ($scope.disciplineList[i].disciplineId === id) {
                    $scope.discipline = angular.copy($scope.disciplineList[i]);
                    break;
                }
            }
    };
        
    $scope.inactivateDiscipline = function (id) {
            for (var i = 0; i < $scope.disciplineList.length; i++) {
                if ($scope.disciplineList[i].disciplineId === id) {
                    $scope.discipline = angular.copy($scope.disciplineList[i]);
                    break;
                }
            }
            $scope.discipline.stateId = 0;
            $scope.updateDiscipline($scope.discipline);
            $scope.resetDiscipline();
    };
        
    $scope.activateDiscipline = function (id) {
            for (var i = 0; i < $scope.disciplineList.length; i++) {
                if ($scope.disciplineList[i].disciplineId === id) {
                    $scope.discipline = angular.copy($scope.disciplineList[i]);
                    break;
                }
            }
            $scope.discipline.stateId = 1;
            $scope.updateDiscipline($scope.discipline);
    };

    $scope.removeDiscipline = function (id) {
            if ($scope.discipline.disciplineId === id) {
                $scope.resetDiscipline();
            }
            $scope.deleteDiscipline(id);
    };

    $scope.resetDiscipline = function () {
            $scope.discipline ={disciplineId:null,name: '',
            description: '',
            stateId: '',
            userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    };

    $scope.getDisciplinePaginate();


});