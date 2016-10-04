'use strict';
trainingApp.service('BikeTypeService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'bikeType/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getBikeTypeById: function (id) {
                return $http.get($contextPath + '/get/bikeType/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching bikeTypes');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createBikeType: function (bikeType) {
                return $http.post($contextPath + '/bikeType/create', bikeType)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating bikeType');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeBikeType: function (bikeType) {
                return $http.post($contextPath + '/bikeType/update', bikeType)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating bikeType');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteBikeType: function (bikeType) {
                return $http.post($contextPath + '/bikeType/delete',bikeType)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting bikeType');
                                    return $q.reject(errResponse);
                                }
                        );
            }, 
            getBikeTypes: function(){
                    return $http.get($contextPath+'bikeType/get/all')
                            .then(
                                    function(response){
                                        return response.data.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting bike types');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        
        };
    }]);