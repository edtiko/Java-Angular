trainingApp.controller('UserActivityPerformanceController', ['$scope', 'UserActivityPerformanceService', '$window', '$mdDialog', 
    function ($scope, UserActivityPerformanceService, $window, $mdDialog) {
    $scope.userActivityPerformance ={userActivityPerformanceId:null,
    userId: {userId: null, name:''},
        
    activityId: {activityId: null, name:''},
        
    value: '',
        
    activityPerformanceMetafieldId: {activityPerformanceMetafieldId: null, name:''},
        
    executedDate: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.userActivityPerformanceList = [];
            $scope.userList = [];   
            $scope.activityList = [];   
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

    $scope.getUserActivityPerformancePaginate = function () {
          $scope.promise = UserActivityPerformanceService.getPaginate($scope.query, function(response) {
            $scope.userActivityPerformanceList = success(response);
            
            if($scope.userActivityPerformanceList.length > 0) {
                $scope.count = $scope.userActivityPerformanceList[0].count;
            }
        }).$promise;
    };

            $scope.userList = [];   
    $scope.getUserList = function () {
        UserActivityPerformanceService.getUser(function(response) {
            $scope.userList = success(response);
        });
    };
            $scope.activityList = [];   
    $scope.getActivityList = function () {
        UserActivityPerformanceService.getActivity(function(response) {
            $scope.activityList = success(response);
        });
    };
            $scope.activityPerformanceMetafieldList = [];   
    $scope.getActivityPerformanceMetafieldList = function () {
        UserActivityPerformanceService.getActivityPerformanceMetafield(function(response) {
            $scope.activityPerformanceMetafieldList = success(response);
        });
    };


    $scope.createUserActivityPerformance = function (userActivityPerformance) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            userActivityPerformance.userCreate = (user.userId);
        }
        UserActivityPerformanceService.createUserActivityPerformance(userActivityPerformance)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetUserActivityPerformance();
                                $scope.getUserActivityPerformancePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating UserActivityPerformance.');
                        }
                );
    };

    $scope.updateUserActivityPerformance = function (userActivityPerformance) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            userActivityPerformance.userUpdate = (user.userId);
        }

        UserActivityPerformanceService.mergeUserActivityPerformance(userActivityPerformance)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetUserActivityPerformance();
                                $scope.showMessage(d.output);
                                $scope.getUserActivityPerformancePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating UserActivityPerformance.');
                        }
                );
    };

    $scope.deleteUserActivityPerformance = function (userActivityPerformance) {
            UserActivityPerformanceService.deleteUserActivityPerformance(userActivityPerformance)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetUserActivityPerformance();
                                $scope.showMessage(d.output);
                                $scope.getUserActivityPerformancePaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting UserActivityPerformance.');
                            }
                    );
        };

    $scope.submitUserActivityPerformance = function (form) {
        if (form.$valid) {
            if ($scope.userActivityPerformance.userActivityPerformanceId === null) {
                $scope.createUserActivityPerformance($scope.userActivityPerformance);
            } else {
                $scope.updateUserActivityPerformance($scope.userActivityPerformance);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editUserActivityPerformance = function (id, ev) {
            for (var i = 0; i < $scope.userActivityPerformanceList.length; i++) {
                if ($scope.userActivityPerformanceList[i].userActivityPerformanceId === id) {
                    $scope.userActivityPerformance = angular.copy($scope.userActivityPerformanceList[i]);
                    break;
                }
            }
            $scope.showCreateUserActivityPerformance(ev);
    };
        
    $scope.inactivateUserActivityPerformance = function (id) {
            for (var i = 0; i < $scope.userActivityPerformanceList.length; i++) {
                if ($scope.userActivityPerformanceList[i].userActivityPerformanceId === id) {
                    $scope.userActivityPerformance = angular.copy($scope.userActivityPerformanceList[i]);
                    break;
                }
            }
            $scope.userActivityPerformance.stateId = 0;
            $scope.updateUserActivityPerformance($scope.userActivityPerformance);
            $scope.resetUserActivityPerformance();
    };
        
    $scope.activateUserActivityPerformance = function (id) {
            for (var i = 0; i < $scope.userActivityPerformanceList.length; i++) {
                if ($scope.userActivityPerformanceList[i].userActivityPerformanceId === id) {
                    $scope.userActivityPerformance = angular.copy($scope.userActivityPerformanceList[i]);
                    break;
                }
            }
            $scope.userActivityPerformance.stateId = 1;
            $scope.updateUserActivityPerformance($scope.userActivityPerformance);
    };

    $scope.removeUserActivityPerformance = function (id) {
            if ($scope.userActivityPerformance.userActivityPerformanceId === id) {
                $scope.resetUserActivityPerformance();
            }
            $scope.deleteUserActivityPerformance(id);
    };

    $scope.resetUserActivityPerformance = function () {
            $scope.userActivityPerformance ={userActivityPerformanceId:null,userId: {userId: null,  name:''},
            activityId: {activityId: null,  name:''},
            value: '',
            activityPerformanceMetafieldId: {activityPerformanceMetafieldId: null,  name:''},
            executedDate: '',
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

        $scope.getUserActivityPerformancePaginate();
    });

    $scope.showCreateUserActivityPerformance = function (ev) {

        $mdDialog.show({
            controller: UserActivityPerformanceController,
            scope: $scope.$new(),
            templateUrl: 'static/views/user/create-userActivityPerformance.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
            resolve: {
                userList: function () {
                    return $scope.userList;
                },
                activityList: function () {
                    return $scope.activityList;
                },
                activityPerformanceMetafieldList: function () {
                    return $scope.activityPerformanceMetafieldList;
                },
                userActivityPerformance: function () {
                    return $scope.userActivityPerformance;
                }
                
            }
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    };

    function UserActivityPerformanceController($scope, $mdDialog, 
                    userList,
                    activityList,
                    
                    activityPerformanceMetafieldList,
                    userActivityPerformance) {

        $scope.userActivityPerformance = userActivityPerformance;
        $scope.userList = userList$scope.activityList = activityList$scope.activityPerformanceMetafieldList = activityPerformanceMetafieldList

        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };

    }

    $scope.getUserActivityPerformancePaginate();

        $scope.getUserList();
        $scope.getActivityList();
        $scope.getActivityPerformanceMetafieldList();

}]);