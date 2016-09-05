trainingApp.controller('PhysiologicalCapacityController', function ($scope, PhysiologicalCapacityService,
        $window) {
    $scope.physiologicalCapacity ={physiologicalCapacityId:null,
    name: '',
        
    code: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.physiologicalCapacityList = [];
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

    $scope.getPhysiologicalCapacityPaginate = function () {
          $scope.promise = PhysiologicalCapacityService.getPaginate($scope.query, function(response) {
            $scope.physiologicalCapacityList = success(response);
            
            if($scope.physiologicalCapacityList.length > 0) {
                $scope.count = $scope.physiologicalCapacityList[0].count;
            }
        }).$promise;
    };



    $scope.createPhysiologicalCapacity = function (physiologicalCapacity) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            physiologicalCapacity.userCreate = (user.userId);
        }
        PhysiologicalCapacityService.createPhysiologicalCapacity(physiologicalCapacity)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetPhysiologicalCapacity();
                                $scope.getPhysiologicalCapacityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating PhysiologicalCapacity.');
                        }
                );
    };

    $scope.updatePhysiologicalCapacity = function (physiologicalCapacity) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            physiologicalCapacity.userUpdate = (user.userId);
        }

        PhysiologicalCapacityService.mergePhysiologicalCapacity(physiologicalCapacity)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetPhysiologicalCapacity();
                                $scope.showMessage(d.output);
                                $scope.getPhysiologicalCapacityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating PhysiologicalCapacity.');
                        }
                );
    };

    $scope.deletePhysiologicalCapacity = function (physiologicalCapacity) {
            PhysiologicalCapacityService.deletePhysiologicalCapacity(physiologicalCapacity)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetPhysiologicalCapacity();
                                $scope.showMessage(d.output);
                                $scope.getPhysiologicalCapacityPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting PhysiologicalCapacity.');
                            }
                    );
        };

    $scope.submitPhysiologicalCapacity = function (form) {
        if (form.$valid) {
            if ($scope.physiologicalCapacity.physiologicalCapacityId === null) {
                $scope.createPhysiologicalCapacity($scope.physiologicalCapacity);
            } else {
                $scope.updatePhysiologicalCapacity($scope.physiologicalCapacity);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editPhysiologicalCapacity = function (id) {
            for (var i = 0; i < $scope.physiologicalCapacityList.length; i++) {
                if ($scope.physiologicalCapacityList[i].physiologicalCapacityId === id) {
                    $scope.physiologicalCapacity = angular.copy($scope.physiologicalCapacityList[i]);
                    break;
                }
            }
    };
        
    $scope.inactivatePhysiologicalCapacity = function (id) {
            for (var i = 0; i < $scope.physiologicalCapacityList.length; i++) {
                if ($scope.physiologicalCapacityList[i].physiologicalCapacityId === id) {
                    $scope.physiologicalCapacity = angular.copy($scope.physiologicalCapacityList[i]);
                    break;
                }
            }
            $scope.physiologicalCapacity.stateId = 0;
            $scope.updatePhysiologicalCapacity($scope.physiologicalCapacity);
            $scope.resetPhysiologicalCapacity();
    };
        
    $scope.activatePhysiologicalCapacity = function (id) {
            for (var i = 0; i < $scope.physiologicalCapacityList.length; i++) {
                if ($scope.physiologicalCapacityList[i].physiologicalCapacityId === id) {
                    $scope.physiologicalCapacity = angular.copy($scope.physiologicalCapacityList[i]);
                    break;
                }
            }
            $scope.physiologicalCapacity.stateId = 1;
            $scope.updatePhysiologicalCapacity($scope.physiologicalCapacity);
    };

    $scope.removePhysiologicalCapacity = function (id) {
            if ($scope.physiologicalCapacity.physiologicalCapacityId === id) {
                $scope.resetPhysiologicalCapacity();
            }
            $scope.deletePhysiologicalCapacity(id);
    };

    $scope.resetPhysiologicalCapacity = function () {
            $scope.physiologicalCapacity ={physiologicalCapacityId:null,name: '',
            code: '',
            userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    };

    $scope.getPhysiologicalCapacityPaginate();


});