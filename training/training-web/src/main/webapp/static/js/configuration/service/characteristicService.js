'use strict';
trainingApp.service('CharacteristicService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'characteristic/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getPlanCharacteristicPaginate: function(characteristicId, res){
                    return $http.get($contextPath+'characteristic/trainingPlanCharact/get/all/'+characteristicId)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getCharacteristicById: function (id) {
                return $http.get($contextPath + '/get/characteristic/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching characteristics');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createCharacteristic: function (characteristic) {
                return $http.post($contextPath + '/characteristic/create', characteristic)
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
            
            mergeCharacteristic: function (characteristic) {
                return $http.post($contextPath + '/characteristic/update', characteristic)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating characteristic');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteCharacteristic: function (characteristic) {
                return $http.post($contextPath + '/characteristic/delete',characteristic)
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
            }
        };
    }]);