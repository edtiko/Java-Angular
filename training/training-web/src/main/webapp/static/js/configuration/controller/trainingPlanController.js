trainingApp.controller('TrainingPlanController', ['$scope', 'TrainingPlanService', 'ConfigurationPlanService', 'CharacteristicService', '$window', '$mdDialog', '$routeParams',
    function ($scope, TrainingPlanService, ConfigurationPlanService, CharacteristicService, $window, $mdDialog, $routeParams) {
        $scope.trainingPlan = {
            trainingPlanId: null, typePlan: null,
            name: '',
            description: '',
            duration: '',
            price: '',
            supervisorUserId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.trainingPlanList = [];
        $scope.count = 0;
        $scope.supervisorUsers = [];

        var bookmark;
        $scope.selected = [];

        $scope.filter = {
            options: {
                debounce: 500
            }
        };
        $scope.typePlan = $routeParams.typePlan;
        $scope.tipoPlanText = '';

        if ($scope.typePlan == '1') {
            $scope.tipoPlanText = 'Plan de Entrenamiento';
        } else {
            $scope.tipoPlanText = 'Plan de Plataforma';
        }

        $scope.query = {
            filter: '',
            order: 'name',
            limit: 10,
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

        $scope.getTrainingPlanPaginate = function () {
            $scope.promise = TrainingPlanService.getPaginate($scope.query, $scope.typePlan, function (response) {
                $scope.trainingPlanList = success(response);

                if ($scope.trainingPlanList.length > 0) {
                    $scope.count = $scope.trainingPlanList[0].count;
                }
            }).$promise;
        };
        $window.sessionStorage.setItem("trainingIdConfiguration", '');
        $scope.showDetail = function (id) {
            $window.sessionStorage.setItem("trainingIdConfiguration", id);
            $window.location.href = "#create-configuration-plan/" + $scope.typePlan;
        };

        $scope.createTrainingPlan = function (trainingPlan) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                trainingPlan.userCreate = (user.userId);
                trainingPlan.typePlan = $scope.typePlan;
            }
            TrainingPlanService.createTrainingPlan(trainingPlan)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetTrainingPlan();
                                    $scope.getTrainingPlanPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating TrainingPlan.');
                            }
                    );
        };

        $scope.createPlanWordpress = function (plan) {
            TrainingPlanService.createPlanWordPress(plan)
                    .then(
                            function (d) {
                                var response = d.data;

                                if (response.status == 'success') {
                                    alert('plan integrado correctamente');
                                    return;
                                } else {
                                    alert('Error al integrar plan');
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating TrainingPlan.');
                            }
                    );
        };

        $scope.updateTrainingPlan = function (trainingPlan) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                trainingPlan.userUpdate = (user.userId);
                trainingPlan.typePlan = $scope.typePlan;
            }

            TrainingPlanService.mergeTrainingPlan(trainingPlan)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetTrainingPlan();
                                    $scope.showMessage(d.output);
                                    $scope.getTrainingPlanPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating TrainingPlan.');
                            }
                    );
        };

        $scope.deleteTrainingPlan = function (trainingPlan) {

            var confirm = $mdDialog.confirm()
                    .title('Confirmaci\u00f3n')
                    .textContent('\u00BFDesea eliminar el registro?')
                    .ariaLabel('Lucky day')
                    .ok('Aceptar')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                TrainingPlanService.deleteTrainingPlan(trainingPlan)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.resetTrainingPlan();
                                        $scope.showMessage(d.output);
                                        $scope.getTrainingPlanPaginate();
                                    } else {
                                        $scope.showMessage(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting TrainingPlan.');
                                }
                        );

            }, function () {
            });


        };

        $scope.submitTrainingPlan = function (form) {
            if (form.$valid) {
                if ($scope.trainingPlan.trainingPlanId === null) {
                    $scope.createTrainingPlan($scope.trainingPlan);
                } else {
                    $scope.updateTrainingPlan($scope.trainingPlan);
                }
            } else {
                form.$setSubmitted();
            }

        };

        $scope.editTrainingPlan = function (id, ev) {
            for (var i = 0; i < $scope.trainingPlanList.length; i++) {
                if ($scope.trainingPlanList[i].trainingPlanId === id) {
                    $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                    break;
                }
            }
            $scope.showCreateTrainingPlan(ev);
        };

        $scope.inactivateTrainingPlan = function (id) {
            for (var i = 0; i < $scope.trainingPlanList.length; i++) {
                if ($scope.trainingPlanList[i].trainingPlanId === id) {
                    $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                    break;
                }
            }
            $scope.trainingPlan.stateId = 0;
            $scope.updateTrainingPlan($scope.trainingPlan);
            $scope.resetTrainingPlan();
        };

        $scope.activateTrainingPlan = function (id) {
            for (var i = 0; i < $scope.trainingPlanList.length; i++) {
                if ($scope.trainingPlanList[i].trainingPlanId === id) {
                    $scope.trainingPlan = angular.copy($scope.trainingPlanList[i]);
                    break;
                }
            }
            $scope.trainingPlan.stateId = 1;
            $scope.updateTrainingPlan($scope.trainingPlan);
        };

        $scope.removeTrainingPlan = function (id) {
            if ($scope.trainingPlan.trainingPlanId === id) {
                $scope.resetTrainingPlan();
            }
            $scope.deleteTrainingPlan(id);
        };

        $scope.resetTrainingPlan = function () {
            $scope.trainingPlan = {trainingPlanId: null, name: '',
                description: '',
                duration: '',
                price: '',
                supervisorUserId: '',
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

            $scope.getTrainingPlanPaginate();
        });

        $scope.openTrainingPlan = function (ev) {

            $scope.trainingPlan = {trainingPlanId: null,
                name: '',
                description: '',
                duration: '',
                price: '',
                supervisorUserId: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
            $scope.showCreateTrainingPlan(ev);
        };

        $scope.showCreateTrainingPlan = function (ev) {

            $mdDialog.show({
                controller: TrainingPlanController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-trainingPlan.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    trainingPlan: function () {
                        return $scope.trainingPlan;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        function TrainingPlanController($scope, $mdDialog,
                trainingPlan) {

            $scope.trainingPlan = trainingPlan;


            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };
        }

        $scope.showCharac = function (ev, trainingPlan) {
            $scope.trainingPlan = trainingPlan;
            $mdDialog.show({
                controller: CharacteristicController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/add-plan-characteristic.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    trainingPlan: function () {
                        return $scope.trainingPlan;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        $scope.characteristic = {characteristicId: null,
            name: '',
            valueType: '',
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};



        $scope.planCharacteristic = {
            trainingPlanCharactId: null,
            value: '',
            characteristicId: {characteristicId: null, name: ''},
            trainingPlanId: {trainingPlanId: null, name: ''},
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''
        };



        function CharacteristicController($scope, $mdDialog,
                trainingPlan) {
            $scope.trainingPlan = trainingPlan;
            $scope.visibleCharact = '';
            $scope.characteristicList = [];
            $scope.planCharacteristic = {
                trainingPlanCharactId: null,
                value: '',
                characteristicId: {characteristicId: null, name: '', valueType: ''},
                trainingPlanId: trainingPlan
            };

            $scope.configurationPlan = {configurationPlanId: null,
                trainingPlanId: trainingPlan,
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

            $scope.hide = function () {
                $mdDialog.hide();
            };

            $scope.cancel = function () {
                $mdDialog.cancel();
            };

            $scope.resetPlanCharacteristic = function () {
                $scope.planCharacteristic = {
                    trainingPlanCharactId: null,
                    value: '',
                    characteristicId: {characteristicId: null, name: ''},
                    trainingPlanId: trainingPlan
                };
            };

            $scope.deletePlanCharacteristic = function (planCharacteristic) {
                var confirm = $window.confirm('\u00BFDesea eliminar el registro?');

                if (!confirm) {
                    return;
                }

                TrainingPlanService.deletePlanCharacteristic(planCharacteristic)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        alert(d.output);
                                        $scope.resetPlanCharacteristic();
                                        $scope.getPlanCharacteristicPaginate(trainingPlan.trainingPlanId);
                                    } else {
                                        alert(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting Characteristic.');
                                }
                        );
            };

            $scope.createPlanCharacteristic = function (planCharacteristic) {
                if ($scope.appReady) {
                    var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                    planCharacteristic.userCreate = (user.userId);
                }
                TrainingPlanService.createPlanCharacteristic(planCharacteristic)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        alert(d.output);
                                        $scope.resetPlanCharacteristic();
                                        $scope.getPlanCharacteristicPaginate(trainingPlan.trainingPlanId);
                                    } else {
                                        alert(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while creating Characteristic.');
                                }
                        );
            };

            $scope.communicationRoleList = [];
            $scope.getCommunicationRoleList = function () {
                TrainingPlanService.getCommunicationRole(function (response) {
                    $scope.communicationRoleList = success(response);
                });
            };

            $scope.submitPlanCharacteristic = function (form) {
                if (form.$valid) {
                    var isValid = true;

                    if ($scope.configurationPlan.communicationRoleId.roleId == undefined) {
                        isValid = false;
                    }

                    if (isValid) {

                        if ($scope.configurationPlan.audioCount > 0 ||
                                $scope.configurationPlan.audioDuration > 0 ||
                                $scope.configurationPlan.audioEmergency > 0 ||
                                $scope.configurationPlan.emailCount > 0 ||
                                $scope.configurationPlan.emailEmergency > 0 ||
                                $scope.configurationPlan.messageCount > 0 ||
                                $scope.configurationPlan.messageEmergency > 0 ||
                                $scope.configurationPlan.videoCount > 0 ||
                                $scope.configurationPlan.videoDuration > 0 ||
                                $scope.configurationPlan.videoEmergency > 0 ||
                                $scope.configurationPlan.athletesCount > 0
                                ) {
                            if ($scope.configurationPlan.configurationPlanId === null) {
                                $scope.createConfigurationPlan($scope.configurationPlan);
                            } else {
                                $scope.updateConfigurationPlan($scope.configurationPlan);
                            }
                        }


                        if ($scope.planCharacteristic.characteristicId.characteristicId != null) {
                            
                            if($scope.planCharacteristic.value == '') {
                                alert('Debe ingresar el valor de la caracter\u00edstica');
                                return;
                            }
                            
                            if ($scope.configurationPlan.communicationRoleId.roleId == 1) {
                                $scope.planCharacteristic.userType = 'Atleta';
                            }

                            if ($scope.configurationPlan.communicationRoleId.roleId == 2) {
                                $scope.planCharacteristic.userType = 'Coach Externo';
                            }

                            if ($scope.configurationPlan.communicationRoleId.roleId == 5) {
                                $scope.planCharacteristic.userType = 'Coach Estrella';
                            }

                            if ($scope.configurationPlan.communicationRoleId.roleId == 4) {
                                $scope.planCharacteristic.userType = 'Asesor';
                            }

                            $scope.createPlanCharacteristic($scope.planCharacteristic);
                            $scope.getPlanCharacteristicPaginate($scope.trainingPlan.trainingPlanId);
                        }
                    }
                } else {
                    form.$setSubmitted();
                }
            };

            $scope.getCharacteristicAll = function () {
                $scope.promise = TrainingPlanService.getCharacteristicAll(function (response) {
                    $scope.characteristicList = success(response);
                }).$promise;
            };

            $scope.getPlanCharacteristicPaginate = function (planId) {
                $scope.promise = TrainingPlanService.getPlanCharacteristicPaginateByPlan(planId, function (response) {
                    $scope.planCharacteristicList = success(response);
                }).$promise;
            };

            $scope.validateValueTypeVisible = function (characteristic) {

                for (var i = 0; i < $scope.characteristicList.length; i++) {
                    if ($scope.characteristicList[i].characteristicId == characteristic.characteristicId) {
                        if ($scope.characteristicList[i].valueType == 1) {
                            $scope.visibleCharact = false;
                        } else {
                            $scope.visibleCharact = true;
                        }
                        break;
                    }
                }
            };

            $scope.queryconf = {
                filter: '',
                order: 'trainingPlanId.name',
                limit: 10,
                page: 1
            };
            $scope.configurationPlanList = [];

            $scope.getConfigurationPlanPaginate = function () {
                $scope.promise = TrainingPlanService.getPaginateConfiguration($scope.queryconf, trainingPlan.trainingPlanId, function (response) {
                    $scope.configurationPlanList = success(response);
                    if ($scope.configurationPlanList.length > 0) {
                        $scope.countconf = $scope.configurationPlanList[0].count;
                    }
                }).$promise;
            };

            $scope.createConfigurationPlan = function (configurationPlan) {
                if ($scope.appReady) {
                    var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                    configurationPlan.userCreate = (user.userId);
                }
                TrainingPlanService.createConfigurationPlan(configurationPlan)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        alert(d.output);
                                        $scope.resetConfigurationPlan();
                                        $scope.getConfigurationPlanPaginate();
                                        $scope.getPlanCharacteristicPaginate($scope.trainingPlan.trainingPlanId);
                                    } else {
                                        alert(d.output);
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

                TrainingPlanService.mergeConfigurationPlan(configurationPlan)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.resetConfigurationPlan();
                                        alert(d.output);
                                        $scope.getConfigurationPlanPaginate();
                                    } else {
                                        alert(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while updating ConfigurationPlan.');
                                }
                        );
            };

            $scope.resetConfigurationPlan = function () {
                $scope.configurationPlan = {configurationPlanId: null, trainingPlanId: trainingPlan,
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

            $scope.editConfigurationPlan = function (id) {
                for (var i = 0; i < $scope.configurationPlanList.length; i++) {
                    if ($scope.configurationPlanList[i].configurationPlanId === id) {
                        $scope.configurationPlan = angular.copy($scope.configurationPlanList[i]);
                        break;
                    }
                }
            };

            $scope.deleteConfigurationPlan = function (configurationPlan) {
                var confirm = $window.confirm('\u00BFDesea eliminar el registro?');

//                var confirm = $mdDialog.confirm()
//                        .title('Confirmaci\u00f3n')
//                        .textContent('\u00BFDesea eliminar el registro?')
//                        .ariaLabel('Lucky day')
//                        .ok('Aceptar')
//                        .cancel('Cancelar');
//                $mdDialog.show(confirm).then(function () {
                if (confirm) {
                    TrainingPlanService.deleteConfigurationPlan(configurationPlan)
                            .then(
                                    function (d) {
                                        if (d.status == 'success') {
                                            $scope.resetConfigurationPlan();
                                            alert(d.output);
                                            $scope.getConfigurationPlanPaginate();
                                            $scope.getPlanCharacteristicPaginate($scope.trainingPlan.trainingPlanId);
                                        } else {
                                            alert(d.output);
                                        }
                                    },
                                    function (errResponse) {
                                        console.error('Error while deleting ConfigurationPlan.');
                                    }
                            );
                }

//                }, function () {
//                });
            };

            $scope.sincronizar = function () {

                var characteristic = '';
                var characteristicAtleta = '';
                var characteristicCoach = '';

                for (var i = 0; i < $scope.planCharacteristicList.length; i++) {
                    
                    if($scope.planCharacteristicList[i].characteristicId.valueType == '1') {
                        characteristic += '<li>' + $scope.planCharacteristicList[i].characteristicId.name +
                            '</li>';
                    } else {
                        characteristic += '<li>' + $scope.planCharacteristicList[i].characteristicId.name +
                            ' ' + $scope.planCharacteristicList[i].value + '</li>';
                    }
                    
                }

                var plan = {
                    description: trainingPlan.description, price: trainingPlan.price,
                    name: trainingPlan.name, characteristic: characteristic,
                    role: $scope.configurationPlanList
                };

                if ($scope.configurationPlanList.length <= 0) {
                    for (var i = 0; i < $scope.planCharacteristicList.length; i++) {

                        if ($scope.configurationPlan.communicationRoleId.roleId == 1) {
                            $scope.planCharacteristic.userType = 'Atleta';
                        }

                        if ($scope.configurationPlan.communicationRoleId.roleId == 2) {
                            $scope.planCharacteristic.userType = 'Coach Externo';
                        }

                        if ($scope.configurationPlan.communicationRoleId.roleId == 5) {
                            $scope.planCharacteristic.userType = 'Coach Estrella';
                        }

                        if ($scope.configurationPlan.communicationRoleId.roleId == 4) {
                            $scope.planCharacteristic.userType = 'Asesor';
                        }

                        if ($scope.planCharacteristicList[i].userType == 'Atleta') {
                            
                            if($scope.planCharacteristicList[i].characteristicId.valueType == '1') {
                                characteristicAtleta += '<li>' + $scope.planCharacteristicList[i].characteristicId.name +
                                    '</li>';
                            } else {
                                characteristicAtleta += '<li>' + $scope.planCharacteristicList[i].characteristicId.name +
                                    ' ' + $scope.planCharacteristicList[i].value + '</li>';
                            }
                        }
                        
                        if ($scope.planCharacteristicList[i].userType == 'Coach Externo') {
                            
                            if($scope.planCharacteristicList[i].characteristicId.valueType == '1') {
                                characteristicCoach += '<li>' + $scope.planCharacteristicList[i].characteristicId.name +
                                     '</li>';
                            } else {
                                characteristicCoach += '<li>' + $scope.planCharacteristicList[i].characteristicId.name +
                                    ' ' + $scope.planCharacteristicList[i].value + '</li>';
                            }

                        }
                    }

                    if (characteristicAtleta != '') {
                        plan = {
                            description: trainingPlan.description, price: trainingPlan.price,
                            name: trainingPlan.name, characteristic: characteristicAtleta,
                            role: $scope.configurationPlanList
                        };
                        plan.role = 'atleta';
                        plan.type = $scope.typePlan;
                        $scope.createPlanWordpress(plan);
                    }
                    
                    if (characteristicCoach != '') {
                        plan = {
                            description: trainingPlan.description, price: trainingPlan.price,
                            name: trainingPlan.name, characteristic: characteristicCoach,
                            role: $scope.configurationPlanList
                        };
                        plan.role = 'coach';
                        plan.type = $scope.typePlan;
                        $scope.createPlanWordpress(plan);
                    }


                    return;
                }

                for (var i = 0; i < $scope.configurationPlanList.length; i++) {
                    //2 coach, 1 atleta
                    if ($scope.configurationPlanList[i].communicationRoleId.roleId == 4) {
                        plan.role = 'coach';
                        plan.type = $scope.typePlan;
                        $scope.createPlanWordpress(plan);
                    }
                    //5 coach, 1 atleta
                    if ($scope.configurationPlanList[i].communicationRoleId.roleId == 5) {
                        plan.role = 'atleta';
                        plan.type = $scope.typePlan;
                        $scope.createPlanWordpress(plan);
                    }

                    if ($scope.configurationPlanList[i].communicationRoleId.roleId == 1) {
                        plan.role = 'atleta';
                        plan.type = $scope.typePlan;
                        $scope.createPlanWordpress(plan);
                    }
                    //2 coach, 1 atleta
                    if ($scope.configurationPlanList[i].communicationRoleId.roleId == 2) {
                        plan.role = 'coach';
                        plan.type = $scope.typePlan;
                        $scope.createPlanWordpress(plan);
                    }
                }
            };

            $scope.getCharacteristicAll();
            $scope.getConfigurationPlanPaginate();
            $scope.getPlanCharacteristicPaginate(trainingPlan.trainingPlanId);
            $scope.getCommunicationRoleList();
        }

        $scope.getTrainingPlanPaginate();


    }]);