trainingApp.controller('ActivityPerformanceMetafieldController', ['$scope', 'ActivityPerformanceMetafieldService', '$window', '$mdDialog', 
    function ($scope, ActivityPerformanceMetafieldService, $window, $mdDialog) {
    $scope.activityPerformanceMetafield ={activityPerformanceMetafieldId:null,
    name: '',
        
    label: '',
        
    dataType: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.activityPerformanceMetafieldList = [];
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

    $scope.getActivityPerformanceMetafieldPaginate = function () {
          $scope.promise = ActivityPerformanceMetafieldService.getPaginate($scope.query, function(response) {
            $scope.activityPerformanceMetafieldList = success(response);
            
            if($scope.activityPerformanceMetafieldList.length > 0) {
                $scope.count = $scope.activityPerformanceMetafieldList[0].count;
            }
        }).$promise;
    };



    $scope.createActivityPerformanceMetafield = function (activityPerformanceMetafield) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            activityPerformanceMetafield.userCreate = (user.userId);
        }
        ActivityPerformanceMetafieldService.createActivityPerformanceMetafield(activityPerformanceMetafield)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetActivityPerformanceMetafield();
                                $scope.getActivityPerformanceMetafieldPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating ActivityPerformanceMetafield.');
                        }
                );
    };

    $scope.updateActivityPerformanceMetafield = function (activityPerformanceMetafield) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            activityPerformanceMetafield.userUpdate = (user.userId);
        }

        ActivityPerformanceMetafieldService.mergeActivityPerformanceMetafield(activityPerformanceMetafield)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetActivityPerformanceMetafield();
                                $scope.showMessage(d.output);
                                $scope.getActivityPerformanceMetafieldPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating ActivityPerformanceMetafield.');
                        }
                );
    };

    $scope.deleteActivityPerformanceMetafield = function (activityPerformanceMetafield) {
            ActivityPerformanceMetafieldService.deleteActivityPerformanceMetafield(activityPerformanceMetafield)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetActivityPerformanceMetafield();
                                $scope.showMessage(d.output);
                                $scope.getActivityPerformanceMetafieldPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting ActivityPerformanceMetafield.');
                            }
                    );
        };

    $scope.submitActivityPerformanceMetafield = function (form) {
        if (form.$valid) {
            if ($scope.activityPerformanceMetafield.activityPerformanceMetafieldId === null) {
                $scope.createActivityPerformanceMetafield($scope.activityPerformanceMetafield);
            } else {
                $scope.updateActivityPerformanceMetafield($scope.activityPerformanceMetafield);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editActivityPerformanceMetafield = function (id, ev) {
            for (var i = 0; i < $scope.activityPerformanceMetafieldList.length; i++) {
                if ($scope.activityPerformanceMetafieldList[i].activityPerformanceMetafieldId === id) {
                    $scope.activityPerformanceMetafield = angular.copy($scope.activityPerformanceMetafieldList[i]);
                    break;
                }
            }
            $scope.showCreateActivityPerformanceMetafield(ev);
    };
        
    $scope.inactivateActivityPerformanceMetafield = function (id) {
            for (var i = 0; i < $scope.activityPerformanceMetafieldList.length; i++) {
                if ($scope.activityPerformanceMetafieldList[i].activityPerformanceMetafieldId === id) {
                    $scope.activityPerformanceMetafield = angular.copy($scope.activityPerformanceMetafieldList[i]);
                    break;
                }
            }
            $scope.activityPerformanceMetafield.stateId = 0;
            $scope.updateActivityPerformanceMetafield($scope.activityPerformanceMetafield);
            $scope.resetActivityPerformanceMetafield();
    };
        
    $scope.activateActivityPerformanceMetafield = function (id) {
            for (var i = 0; i < $scope.activityPerformanceMetafieldList.length; i++) {
                if ($scope.activityPerformanceMetafieldList[i].activityPerformanceMetafieldId === id) {
                    $scope.activityPerformanceMetafield = angular.copy($scope.activityPerformanceMetafieldList[i]);
                    break;
                }
            }
            $scope.activityPerformanceMetafield.stateId = 1;
            $scope.updateActivityPerformanceMetafield($scope.activityPerformanceMetafield);
    };

    $scope.removeActivityPerformanceMetafield = function (id) {
            if ($scope.activityPerformanceMetafield.activityPerformanceMetafieldId === id) {
                $scope.resetActivityPerformanceMetafield();
            }
            $scope.deleteActivityPerformanceMetafield(id);
    };

    $scope.resetActivityPerformanceMetafield = function () {
            $scope.activityPerformanceMetafield ={activityPerformanceMetafieldId:null,name: '',
            label: '',
            dataType: '',
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

        $scope.getActivityPerformanceMetafieldPaginate();
    });

    $scope.showCreateActivityPerformanceMetafield = function (ev) {

        $mdDialog.show({
            controller: ActivityPerformanceMetafieldController,
            scope: $scope.$new(),
            templateUrl: 'static/views/configuration/create-activityPerformanceMetafield.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
            resolve: {
                activityPerformanceMetafield: function () {
                    return $scope.activityPerformanceMetafield;
                }
                
            }
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    };

    function ActivityPerformanceMetafieldController($scope, $mdDialog, 
                    
                    
                    activityPerformanceMetafield) {

        $scope.activityPerformanceMetafield = activityPerformanceMetafield;
        

        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };

    }

    $scope.getActivityPerformanceMetafieldPaginate();


}]);