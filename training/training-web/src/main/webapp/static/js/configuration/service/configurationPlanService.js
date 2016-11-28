'use strict';
trainingApp.service('ConfigurationPlanService', ['$http', '$q', function ($http, $q) {
        return {
            getPaginate: function (query, planId, res) {
                return $http.post($contextPath + 'configurationPlan/paginated/'+planId, query)
                        .then(
                                res,
                                function (errResponse) {
                                    console.error('Error while getting service ' + errResponse);
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getTrainingPlan: function (res) {
                return $http.get($contextPath + 'trainingPlan/get/all')
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
            getConfigurationPlanById: function (id) {
                return $http.get($contextPath + '/get/configurationPlan/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching configurationPlans');
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
            }
        };
    }]);