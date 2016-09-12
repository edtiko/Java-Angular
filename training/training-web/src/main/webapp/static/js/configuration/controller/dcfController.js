trainingApp.controller('DcfController', ['$scope', 'DcfService', '$window', 
    function ($scope, DcfService, $window) {
    $scope.dcf ={dcfId:null,
    objectiveId: {objectiveId: null, name:''},
        
    modalityId: {modalityId: null, name:''},
        
    pattern: '',
        
    sessions: '',
        
    stateIdId: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.dcfList = [];
            $scope.objectiveList = [];   
            $scope.modalityList = [];   
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

    $scope.getDcfPaginate = function () {
          $scope.promise = DcfService.getPaginate($scope.query, function(response) {
            $scope.dcfList = success(response);
            
            if($scope.dcfList.length > 0) {
                $scope.count = $scope.dcfList[0].count;
            }
        }).$promise;
    };

            $scope.objectiveList = [];   
    $scope.getObjectiveList = function () {
        DcfService.getObjective(function(response) {
            $scope.objectiveList = success(response);
        });
    };
            $scope.modalityList = [];   
    $scope.getModalityList = function () {
        DcfService.getModality(function(response) {
            $scope.modalityList = success(response);
        });
    };


    $scope.createDcf = function (dcf) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            dcf.userCreate = (user.userId);
        }
        DcfService.createDcf(dcf)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetDcf();
                                $scope.getDcfPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Dcf.');
                        }
                );
    };

    $scope.updateDcf = function (dcf) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            dcf.userUpdate = (user.userId);
        }

        DcfService.mergeDcf(dcf)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetDcf();
                                $scope.showMessage(d.output);
                                $scope.getDcfPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Dcf.');
                        }
                );
    };

    $scope.deleteDcf = function (dcf) {
            DcfService.deleteDcf(dcf)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetDcf();
                                $scope.showMessage(d.output);
                                $scope.getDcfPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting Dcf.');
                            }
                    );
        };

    $scope.submitDcf = function (form) {
        if (form.$valid) {
            if ($scope.dcf.dcfId === null) {
                $scope.createDcf($scope.dcf);
            } else {
                $scope.updateDcf($scope.dcf);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editDcf = function (id) {
            for (var i = 0; i < $scope.dcfList.length; i++) {
                if ($scope.dcfList[i].dcfId === id) {
                    $scope.dcf = angular.copy($scope.dcfList[i]);
                    break;
                }
            }
    };
        
    $scope.inactivateDcf = function (id) {
            for (var i = 0; i < $scope.dcfList.length; i++) {
                if ($scope.dcfList[i].dcfId === id) {
                    $scope.dcf = angular.copy($scope.dcfList[i]);
                    break;
                }
            }
            $scope.dcf.stateId = 0;
            $scope.updateDcf($scope.dcf);
            $scope.resetDcf();
    };
        
    $scope.activateDcf = function (id) {
            for (var i = 0; i < $scope.dcfList.length; i++) {
                if ($scope.dcfList[i].dcfId === id) {
                    $scope.dcf = angular.copy($scope.dcfList[i]);
                    break;
                }
            }
            $scope.dcf.stateId = 1;
            $scope.updateDcf($scope.dcf);
    };

    $scope.removeDcf = function (id) {
            if ($scope.dcf.dcfId === id) {
                $scope.resetDcf();
            }
            $scope.deleteDcf(id);
    };

    $scope.resetDcf = function () {
            $scope.dcf ={dcfId:null,objectiveId: {objectiveId: null,  name:''},
            modalityId: {modalityId: null,  name:''},
            pattern: '',
            sessions: '',
            stateIdId: '',
            userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    };

    $scope.getDcfPaginate();

        $scope.getObjectiveList();
        $scope.getModalityList();

}]);