'use strict';
trainingApp.service('ActivityPerformanceMetafieldService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'activityPerformanceMetafield/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getActivityPerformanceMetafieldById: function (id) {
                return $http.get($contextPath + '/get/activityPerformanceMetafield/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching activityPerformanceMetafields');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createActivityPerformanceMetafield: function (activityPerformanceMetafield) {
                return $http.post($contextPath + '/activityPerformanceMetafield/create', activityPerformanceMetafield)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating activityPerformanceMetafield');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeActivityPerformanceMetafield: function (activityPerformanceMetafield) {
                return $http.post($contextPath + '/activityPerformanceMetafield/update', activityPerformanceMetafield)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating activityPerformanceMetafield');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteActivityPerformanceMetafield: function (activityPerformanceMetafield) {
                return $http.post($contextPath + '/activityPerformanceMetafield/delete',activityPerformanceMetafield)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting activityPerformanceMetafield');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);