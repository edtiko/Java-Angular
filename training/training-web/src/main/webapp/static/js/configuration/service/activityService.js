'use strict';
trainingApp.service('ActivityService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'activity/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getPhysiologicalCapacity: function(res){
                    return $http.get($contextPath+'physiologicalCapacity/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getModality: function(res){
                    return $http.get($contextPath+'modality/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getObjective: function(res){
                    return $http.get($contextPath+'objective/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getSport: function(res){
                    return $http.get($contextPath+'sport/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getActivityById: function (id) {
                return $http.get($contextPath + '/get/activity/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching activitys');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createActivity: function (activity) {
                return $http.post($contextPath + '/activity/create', activity)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating activity');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeActivity: function (activity) {
                return $http.post($contextPath + '/activity/update', activity)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating activity');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteActivity: function (activity) {
                return $http.post($contextPath + '/activity/delete',activity)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting activity');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getActivitiesByWeek: function (userId) {
                return $http.get($contextPath + 'trainingPlanWorkout/get/activities/week/'+userId)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching week activities ');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);