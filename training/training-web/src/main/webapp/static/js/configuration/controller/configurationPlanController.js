trainingApp.controller('ConfigurationPlanController', ['$scope', 'ConfigurationPlanService',
    '$window', '$mdDialog', '$routeParams',
    function ($scope, ConfigurationPlanService, $window, $mdDialog, $routeParams) {
        $scope.configurationPlan = {configurationPlanId: null,
            trainingPlanId: {trainingPlanId: null, name: ''},
            communicationRoleId: {communicationRoleId: null, name: ''},
            audioDuration: '',
            audioEmergency: '',
            audioCount: '',
            emailEmergency: '',
            emailCount: '',
            messageEmergency: '',
            messageCount: '',
            videoDuration: '',
            videoEmergency: '',
            videoCount: '',
            athletesCount: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.configurationPlanList = [];
        $scope.trainingPlanList = [];
        $scope.communicationRoleList = [];
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
            order: 'trainingPlanId.name',
            limit: 10,
            page: 1
        };
        $scope.planId = $window.sessionStorage.getItem("trainingIdConfiguration");        
        if($scope.planId == '') {
            $scope.planId = '0';
        }
        
        $scope.volverPlan = function() {
            $window.sessionStorage.setItem("trainingIdConfiguration", '');
            $window.location.href = "#create-plan/"+$routeParams.typePlan;
        };
        function success(response) {
            if (response.data.status == 'fail') {
                $scope.showMessage(response.data.output);
            } else {
                return response.data.output;
            }

            return null;
        }

        $scope.getConfigurationPlanPaginate = function () {
            $scope.promise = ConfigurationPlanService.getPaginate($scope.query, $scope.planId, function (response) {
                $scope.configurationPlanList = success(response);
                if ($scope.configurationPlanList.length > 0) {                    
                    $scope.count = $scope.configurationPlanList[0].count;
                }
            }).$promise;
        };
        
        $scope.trainingPlanList = [];
        $scope.getTrainingPlanList = function () {
            ConfigurationPlanService.getTrainingPlan(function (response) {
                $scope.trainingPlanList = success(response);
            });
        };
        $scope.communicationRoleList = [];
        $scope.getCommunicationRoleList = function () {
            ConfigurationPlanService.getCommunicationRole(function (response) {
                $scope.communicationRoleList = success(response);
            });
        };
        
        $scope.membershipList = [];
        $scope.getMemberShipList = function(){
            MembershipService.getMemberShipList(function (response) {
                $scope.trainingPlanList = success(response);
            });
        };
        
        $scope.createConfigurationPlan = function (configurationPlan) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                configurationPlan.userCreate = (user.userId);
            }
            ConfigurationPlanService.createConfigurationPlan(configurationPlan)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetConfigurationPlan();
                                    $scope.getConfigurationPlanPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating ConfigurationPlan.');
                            }
                    );
        };
        $scope.updateConfigurationPlan = function (configurationPlan) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                configurationPlan.userUpdate = (user.userId);
            }

            ConfigurationPlanService.mergeConfigurationPlan(configurationPlan)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetConfigurationPlan();
                                    $scope.showMessage(d.output);
                                    $scope.getConfigurationPlanPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating ConfigurationPlan.');
                            }
                    );
        };
        $scope.deleteConfigurationPlan = function (configurationPlan) {
            var confirm = $mdDialog.confirm()
                    .title('Confirmaci\u00f3n')
                    .textContent('\u00BFDesea eliminar el registro?')
                    .ariaLabel('Lucky day')
                    .ok('Aceptar')
                    .cancel('Cancelar');
            $mdDialog.show(confirm).then(function () {

                ConfigurationPlanService.deleteConfigurationPlan(configurationPlan)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.resetConfigurationPlan();
                                        $scope.showMessage(d.output);
                                        $scope.getConfigurationPlanPaginate();
                                    } else {
                                        $scope.showMessage(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting ConfigurationPlan.');
                                }
                        );
            }, function () {
            });
        };
        $scope.submitConfigurationPlan = function (form) {
            var isValid = true;
            var message = '';
            
            if($scope.planId != '0') {
                $scope.configurationPlan.trainingPlanId.trainingPlanId = $scope.planId;
            }
            
            if ($scope.configurationPlan.trainingPlanId.trainingPlanId == '') {
                message += 'Debe seleccionar el campo Plan \n';
                isValid = false;
            }
            
            if($scope.configurationPlan.communicationRoleId.roleId == undefined) {
                message += 'Debe seleccionar el campo Role \n';
                isValid = false;
            }
            
            if (isValid) {
                if ($scope.configurationPlan.configurationPlanId === null) {
                    $scope.createConfigurationPlan($scope.configurationPlan);
                } else {
                    $scope.updateConfigurationPlan($scope.configurationPlan);
                }
            } else {
                alert(message);
//                $scope.showMessage(message, 'Aviso', true);
                form.$setSubmitted();
            }

        };
        $scope.editConfigurationPlan = function (id, ev) {
            for (var i = 0; i < $scope.configurationPlanList.length; i++) {
                if ($scope.configurationPlanList[i].configurationPlanId === id) {
                    $scope.configurationPlan = angular.copy($scope.configurationPlanList[i]);
                    break;
                }
            }
            $scope.showCreateConfigurationPlan(ev);
        };
        $scope.inactivateConfigurationPlan = function (id) {
            for (var i = 0; i < $scope.configurationPlanList.length; i++) {
                if ($scope.configurationPlanList[i].configurationPlanId === id) {
                    $scope.configurationPlan = angular.copy($scope.configurationPlanList[i]);
                    break;
                }
            }
            $scope.configurationPlan.stateId = 0;
            $scope.updateConfigurationPlan($scope.configurationPlan);
            $scope.resetConfigurationPlan();
        };
        $scope.activateConfigurationPlan = function (id) {
            for (var i = 0; i < $scope.configurationPlanList.length; i++) {
                if ($scope.configurationPlanList[i].configurationPlanId === id) {
                    $scope.configurationPlan = angular.copy($scope.configurationPlanList[i]);
                    break;
                }
            }
            $scope.configurationPlan.stateId = 1;
            $scope.updateConfigurationPlan($scope.configurationPlan);
        };
        $scope.removeConfigurationPlan = function (id) {
            if ($scope.configurationPlan.configurationPlanId === id) {
                $scope.resetConfigurationPlan();
            }
            $scope.deleteConfigurationPlan(id);
        };
        $scope.resetConfigurationPlan = function () {
            $scope.configurationPlan = {configurationPlanId: null, trainingPlanId: {trainingPlanId: null, name: ''},
                communicationRoleId: {communicationRoleId: null, name: ''},
                audioDuration: '',
                audioEmergency: '',
                audioCount: '',
                emailEmergency: '',
                emailCount: '',
                messageEmergency: '',
                messageCount: '',
                videoDuration: '',
                videoEmergency: '',
                videoCount: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
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

            $scope.getConfigurationPlanPaginate();
        });
        $scope.openConfigurationPlan = function (ev) {

            $scope.configurationPlan = {configurationPlanId: null,
                trainingPlanId: {trainingPlanId: null, name: ''},
                communicationRoleId: {communicationRoleId: null, name: ''},
                audioDuration: '',
                audioEmergency: '',
                audioCount: '',
                emailEmergency: '',
                emailCount: '',
                messageEmergency: '',
                messageCount: '',
                videoDuration: '',
                videoEmergency: '',
                videoCount: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
            $scope.showCreateConfigurationPlan(ev);
        };
        $scope.showCreateConfigurationPlan = function (ev) {

            $mdDialog.show({
                controller: ConfigurationPlanController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-configurationPlan.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    trainingPlanList: function () {
                        return $scope.trainingPlanList;
                    },
                    communicationRoleList: function () {
                        return $scope.communicationRoleList;
                    },
                    configurationPlan: function () {
                        return $scope.configurationPlan;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };
        function ConfigurationPlanController($scope, $mdDialog,
                trainingPlanList,
                communicationRoleList,
                configurationPlan) {

            $scope.configurationPlan = configurationPlan;
            $scope.trainingPlanList = trainingPlanList;
            $scope.communicationRoleList = communicationRoleList;

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };
        }

        $scope.getConfigurationPlanPaginate();
        $scope.getTrainingPlanList();
        $scope.getCommunicationRoleList();
    }]);