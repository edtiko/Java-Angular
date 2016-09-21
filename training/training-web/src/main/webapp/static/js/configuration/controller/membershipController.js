trainingApp.controller('MembershipController', ['$scope', 'MembershipService', '$window', '$mdDialog', 
    function ($scope, MembershipService, $window, $mdDialog) {
    $scope.membership ={membershipId:null,
    name: '',
        
    description: '',
        
    stateId: '',
        userCreate : '', userUpdate:'',userCreateName: '', userUpdateName: ''};
    $scope.membershipList = [];
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

    $scope.getMembershipPaginate = function () {
          $scope.promise = MembershipService.getPaginate($scope.query, function(response) {
            $scope.membershipList = success(response);
            
            if($scope.membershipList.length > 0) {
                $scope.count = $scope.membershipList[0].count;
            }
        }).$promise;
    };



    $scope.createMembership = function (membership) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            membership.userCreate = (user.userId);
        }
        MembershipService.createMembership(membership)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.showMessage(d.output);
                                $scope.resetMembership();
                                $scope.getMembershipPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while creating Membership.');
                        }
                );
    };

    $scope.updateMembership = function (membership) {
        if ($scope.appReady) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            membership.userUpdate = (user.userId);
        }

        MembershipService.mergeMembership(membership)
                .then(
                        function (d) {
                            if(d.status == 'success') {
                                $scope.resetMembership();
                                $scope.showMessage(d.output);
                                $scope.getMembershipPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                        function (errResponse) {
                            console.error('Error while updating Membership.');
                        }
                );
    };

    $scope.deleteMembership = function (membership) {
            MembershipService.deleteMembership(membership)
                    .then(
                            function (d) {
                            if (d.status == 'success') {
                                $scope.resetMembership();
                                $scope.showMessage(d.output);
                                $scope.getMembershipPaginate();
                            } else {
                                $scope.showMessage(d.output);
                            }
                        },
                            function (errResponse) {
                                console.error('Error while deleting Membership.');
                            }
                    );
        };

    $scope.submitMembership = function (form) {
        if (form.$valid) {
            if ($scope.membership.membershipId === null) {
                $scope.createMembership($scope.membership);
            } else {
                $scope.updateMembership($scope.membership);
            }
        } else {
            form.$setSubmitted();
        }
        
    };

    $scope.editMembership = function (id, ev) {
            for (var i = 0; i < $scope.membershipList.length; i++) {
                if ($scope.membershipList[i].membershipId === id) {
                    $scope.membership = angular.copy($scope.membershipList[i]);
                    break;
                }
            }
            $scope.showCreateMembership(ev);
    };
        
    $scope.inactivateMembership = function (id) {
            for (var i = 0; i < $scope.membershipList.length; i++) {
                if ($scope.membershipList[i].membershipId === id) {
                    $scope.membership = angular.copy($scope.membershipList[i]);
                    break;
                }
            }
            $scope.membership.stateId = 0;
            $scope.updateMembership($scope.membership);
            $scope.resetMembership();
    };
        
    $scope.activateMembership = function (id) {
            for (var i = 0; i < $scope.membershipList.length; i++) {
                if ($scope.membershipList[i].membershipId === id) {
                    $scope.membership = angular.copy($scope.membershipList[i]);
                    break;
                }
            }
            $scope.membership.stateId = 1;
            $scope.updateMembership($scope.membership);
    };

    $scope.removeMembership = function (id) {
            if ($scope.membership.membershipId === id) {
                $scope.resetMembership();
            }
            $scope.deleteMembership(id);
    };

    $scope.resetMembership = function () {
            $scope.membership ={membershipId:null,name: '',
            description: '',
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

        $scope.getMembershipPaginate();
    });

    $scope.showCreateMembership = function (ev) {

        $mdDialog.show({
            controller: MembershipController,
            scope: $scope.$new(),
            templateUrl: 'static/views/configuration/create-membership.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
            resolve: {
                membership: function () {
                    return $scope.membership;
                }
                
            }
        })
                .then(function (answer) {
                    $scope.status = 'You said the information was "' + answer + '".';
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
    };

    function MembershipController($scope, $mdDialog, 
                    
                    
                    membership) {

        $scope.membership = membership;
        

        $scope.hide = function () {
            $mdDialog.hide();
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        };

    }

    $scope.getMembershipPaginate();


}]);