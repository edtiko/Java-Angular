trainingApp.controller('ModalityController', ['$scope', 'ModalityService', '$window', 'DisciplineService',
    function ($scope, ModalityService, $window,DisciplineService) {
    $scope.modality ={modalityId:null,
    name: '',
    disciplineId: {disciplineId: null, name:''},
    stateId: '',
    userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.modalityList = [];
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

    $scope.getModalityPaginate = function () {
          $scope.promise = ModalityService.getPaginate($scope.query, function(response) {
            $scope.modalityList = success(response);
            
            if($scope.modalityList.length > 0) {
                $scope.count = $scope.modalityList[0].count;
            }
        }).$promise;
    };
  
    $scope.getSportDisciplines = function () {
            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        $scope.disciplineList = d;
                    },
                    function (errResponse) {
                        console.error('Error while disciplines');
                        console.error(errResponse);
                    }
            );
        };


    $scope.createModality = function (modality) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            modality.userCreate = (user.userId);
        }
        ModalityService.createModality(modality)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetModality();
                                $scope.getModalityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Modality.');
                        }
                );
    };

    $scope.updateModality = function (modality) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            modality.userUpdate = (user.userId);
        }

        ModalityService.mergeModality(modality)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetModality();
                                $scope.showMessage(d.output);
                                $scope.getModalityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Modality.');
                        }
                );
    };

    $scope.deleteModality = function (modality) {
            ModalityService.deleteModality(modality)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetModality();
                                $scope.showMessage(d.output);
                                $scope.getModalityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting Modality.');
                            }
                    );
        };

    $scope.submitModality = function (form) {
        if (form.$valid) {
            if ($scope.modality.modalityId === null) {
                $scope.createModality($scope.modality);
            } else {
                $scope.updateModality($scope.modality);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editModality = function (id) {
            for (var i = 0; i < $scope.modalityList.length; i++) {
                if ($scope.modalityList[i].modalityId === id) {
                    $scope.modality = angular.copy($scope.modalityList[i]);
                    break;
                }
            }
    };
        
    $scope.inactivateModality = function (id) {
            for (var i = 0; i < $scope.modalityList.length; i++) {
                if ($scope.modalityList[i].modalityId === id) {
                    $scope.modality = angular.copy($scope.modalityList[i]);
                    break;
                }
            }
            $scope.modality.stateId = 0;
            $scope.updateModality($scope.modality);
            $scope.resetModality();
    };
        
    $scope.activateModality = function (id) {
            for (var i = 0; i < $scope.modalityList.length; i++) {
                if ($scope.modalityList[i].modalityId === id) {
                    $scope.modality = angular.copy($scope.modalityList[i]);
                    break;
                }
            }
            $scope.modality.stateId = 1;
            $scope.updateModality($scope.modality);
    };

    $scope.removeModality = function (id) {
            if ($scope.modality.modalityId === id) {
                $scope.resetModality();
            }
            $scope.deleteModality(id);
    };

    $scope.resetModality = function () {
            $scope.modality ={modalityId:null,name: '',
            disciplineId: {disciplineId: null,  name:''},
            stateId: '',
            userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
        $scope.formModality.$setPristine();
    };

    $scope.getModalityPaginate();
    $scope.getSportDisciplines();

}]);