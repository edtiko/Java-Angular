'use strict';
trainingApp.service('PhysiologicalCapacityService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'physiologicalCapacity/paginated', query)
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
            getPhysiologicalCapacityById: function (id) {
                return $http.get($contextPath + '/get/physiologicalCapacity/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching physiologicalCapacitys');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createPhysiologicalCapacity: function (physiologicalCapacity) {
                return $http.post($contextPath + '/physiologicalCapacity/create', physiologicalCapacity)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating physiologicalCapacity');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergePhysiologicalCapacity: function (physiologicalCapacity) {
                return $http.post($contextPath + '/physiologicalCapacity/update', physiologicalCapacity)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating physiologicalCapacity');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deletePhysiologicalCapacity: function (physiologicalCapacity) {
                return $http.post($contextPath + '/physiologicalCapacity/delete',physiologicalCapacity)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting physiologicalCapacity');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);