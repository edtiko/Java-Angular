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
                }
        };
    }]);