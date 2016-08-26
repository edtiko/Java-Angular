'use strict';
trainingApp.service('OptionService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'option/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getModule: function(query, res){
                    return $http.get($contextPath+'module/get/all', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getOptionById: function (id) {
                return $http.get($contextPath + '/get/option/by/' + id)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching options');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createOption: function (option) {
                return $http.post($contextPath + '/option/', option)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while creating option');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeOption: function (option) {
                return $http.put($contextPath + '/option/', option)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while updating option');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteOption: function (option) {
                return $http.delete($contextPath + '/option/',option)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting option');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);