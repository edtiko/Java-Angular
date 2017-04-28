'use strict';
trainingApp.service('TrainingPlanService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, typePlan, res){
                    return $http.post($contextPath+'trainingPlan/paginated/'+typePlan, query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getTrainingPlanById: function (id) {
                return $http.get($contextPath + '/get/trainingPlan/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching trainingPlans');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createTrainingPlan: function (trainingPlan) {
                return $http.post($contextPath + '/trainingPlan/create', trainingPlan)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating trainingPlan');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeTrainingPlan: function (trainingPlan) {
                return $http.post($contextPath + '/trainingPlan/update', trainingPlan)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating trainingPlan');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteTrainingPlan: function (trainingPlan) {
                return $http.post($contextPath + '/trainingPlan/delete',trainingPlan)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting trainingPlan');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getDefaultSupervisors: function (id) {
                return $http.get($contextPath + 'trainingPlan/get/default/supervisors/')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching supervisors');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getPlanTypes: function () {
                return $http.get($contextPath + 'trainingPlan/get/all')
                        .then(
                                function (response) {
                                return response.data.output;
                                },
                                function (errResponse) {
                                console.error('Error while fetching plans');
                                        return $q.reject(errResponse);
                                }
                        );
                },
                
            createPlanWordPress: function (training) { 
                
                return $http({
                    url: $wordPressContextPathHttps + 'add-plataforma-entrenamiento.php?name=' +
                            training.name + '&description='+training.description+ '&price='+training.price +
                            '&characteristic=' + training.characteristic + 
                            '&role='+training.role+'&type='+training.type ,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
                    method: 'POST'
                })
                        .then(
                                function (response) {
                                    return response;
                                },
                                function (errResponse) {
                                    console.error('Error while creating createPlanWordPress');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getAll: function(){
                    return $http.get($contextPath+'/trainingPlan/get/all')
                            .then(
                                    function (response) {
                                    return response;
                                },
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getPlanCharacteristicPaginateByPlan: function(planId, res){
                    return $http.get($contextPath+'characteristic/trainingPlanCharact/get/by/plan/'+planId)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            createPlanCharacteristic: function (characteristic) {
                return $http.post($contextPath + '/characteristic/trainingPlanCharact/create', characteristic)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating characteristic');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getCharacteristicAll: function (response) {
                return $http.get($contextPath + '/characteristic/get/all')
                        .then(
                                response,
                                function (errResponse) {
                                    console.error('Error while fetching characteristics');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createConfigurationPlan: function (configurationPlan) {
                return $http.post($contextPath + '/configurationPlan/create', configurationPlan)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating configurationPlan');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeConfigurationPlan: function (configurationPlan) {
                return $http.post($contextPath + '/configurationPlan/update', configurationPlan)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating configurationPlan');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteConfigurationPlan: function (configurationPlan) {
                return $http.post($contextPath + '/configurationPlan/delete', configurationPlan)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting configurationPlan');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getPaginateConfiguration :function (query, planId, res) {
                return $http.post($contextPath + 'configurationPlan/paginated/'+planId, query)
                        .then(
                                res,
                                function (errResponse) {
                                    console.error('Error while getting service ' + errResponse);
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getCommunicationRole: function (res) {
                return $http.get($contextPath + 'role/get/all')
                        .then(
                                res,
                                function (errResponse) {
                                    console.error('Error while getting service ' + errResponse);
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deletePlanCharacteristic: function (characteristic) {
                return $http.post($contextPath + '/characteristic/trainingPlanCharact/delete',characteristic)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting characteristic');
                                    return $q.reject(errResponse);
                                }
                        );
            },
        };
    }]);