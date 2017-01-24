trainingApp.controller('BikeTypeController', ['$scope', 'BikeTypeService', '$window', '$mdDialog', 
    function ($scope, BikeTypeService, $window, $mdDialog) {
    $scope.bikeType ={bikeTypeId:null,
    name: '',
        
    stateId: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.bikeTypeList = [];
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

    $scope.getBikeTypePaginate = function () {
          $scope.promise = BikeTypeService.getPaginate($scope.query, function(response) {
            $scope.bikeTypeList = success(response);
            
            if($scope.bikeTypeList.length > 0) {
                $scope.count = $scope.bikeTypeList[0].count;
            }
        }).$promise;
    };



    $scope.createBikeType = function (bikeType) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            bikeType.userCreate = (user.userId);
        }
        BikeTypeService.createBikeType(bikeType)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetBikeType();
                                $scope.getBikeTypePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating BikeType.');
                        }
                );
    };

    $scope.updateBikeType = function (bikeType) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            bikeType.userUpdate = (user.userId);
        }

        BikeTypeService.mergeBikeType(bikeType)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetBikeType();
                                $scope.showMessage(d.output);
                                $scope.getBikeTypePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating BikeType.');
                        }
                );
    };

    $scope.deleteBikeType = function (bikeType) {
            BikeTypeService.deleteBikeType(bikeType)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetBikeType();
                                $scope.showMessage(d.output);
                                $scope.getBikeTypePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting BikeType.');
                            }
                    );
        };

    $scope.submitBikeType = function (form) {
        if (form.$valid) {
            if ($scope.bikeType.bikeTypeId === null) {
                $scope.createBikeType($scope.bikeType);
            } else {
                $scope.updateBikeType($scope.bikeType);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editBikeType = function (id, ev) {
            for (var i = 0; i < $scope.bikeTypeList.length; i++) {
                if ($scope.bikeTypeList[i].bikeTypeId === id) {
                    $scope.bikeType = angular.copy($scope.bikeTypeList[i]);
                    break;
                }
            }
            $scope.showCreateBikeType(ev);
    };
        
    $scope.inactivateBikeType = function (id) {
            for (var i = 0; i < $scope.bikeTypeList.length; i++) {
                if ($scope.bikeTypeList[i].bikeTypeId === id) {
                    $scope.bikeType = angular.copy($scope.bikeTypeList[i]);
                    break;
                }
            }
            $scope.bikeType.stateId = 0;
            $scope.updateBikeType($scope.bikeType);
            $scope.resetBikeType();
    };
        
    $scope.activateBikeType = function (id) {
            for (var i = 0; i < $scope.bikeTypeList.length; i++) {
                if ($scope.bikeTypeList[i].bikeTypeId === id) {
                    $scope.bikeType = angular.copy($scope.bikeTypeList[i]);
                    break;
                }
            }
            $scope.bikeType.stateId = 1;
            $scope.updateBikeType($scope.bikeType);
    };

    $scope.removeBikeType = function (id) {
            if ($scope.bikeType.bikeTypeId === id) {
                $scope.resetBikeType();
            }
            $scope.deleteBikeType(id);
    };

    $scope.resetBikeType = function () {
            $scope.bikeType ={bikeTypeId:null,name: '',
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

        $scope.getBikeTypePaginate();
    });

    $scope.showCreateBikeType = function (ev) {

        $mdDialog.show({
            controller: BikeTypeController,
            scope: $scope.$new(),
            templateUrl: 'static/views/configuration/create-bikeType.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
            resolve: {
                bikeType: function () {
                    return $scope.bikeType;
                }
                
            }
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    };

    function BikeTypeController($scope, $mdDialog, 
                    
                    bikeType) {

        $scope.bikeType = bikeType;
        

        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };

    }

    $scope.getBikeTypePaginate();


}]);