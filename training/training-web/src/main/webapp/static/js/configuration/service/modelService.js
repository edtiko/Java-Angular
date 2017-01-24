'use strict';
trainingApp.service('ModelService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'model/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getSportEquipmentType: function(res){
                    return $http.get($contextPath+'sportEquipmentType/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getModelById: function (id) {
                return $http.get($contextPath + '/get/model/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching models');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createModel: function (model) {
                return $http.post($contextPath + '/model/create', model)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating model');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeModel: function (model) {
                return $http.post($contextPath + '/model/update', model)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating model');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteModel: function (model) {
                return $http.post($contextPath + '/model/delete',model)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting model');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);