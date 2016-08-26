'use strict';
trainingApp.service('ModuleService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'module/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getModuleById: function (id) {
                return $http.get($contextPath + '/get/module/by/' + id)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching modules');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createModule: function (module) {
                return $http.post($contextPath + '/module/', module)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while creating module');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeModule: function (module) {
                return $http.put($contextPath + '/module/', module)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while updating module');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteModule: function (module) {
                return $http.delete($contextPath + '/module/',module)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting module');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);