'use strict';
trainingApp.service('DisciplineService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'discipline/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getDisciplineById: function (id) {
                return $http.get($contextPath + '/get/discipline/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching disciplines');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createDiscipline: function (discipline) {
                return $http.post($contextPath + '/discipline/create', discipline)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating discipline');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeDiscipline: function (discipline) {
                return $http.post($contextPath + '/discipline/update', discipline)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating discipline');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteDiscipline: function (discipline) {
                return $http.post($contextPath + '/discipline/delete',discipline)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting discipline');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getSportDisciplines: function() {
                    return $http.get($contextPath+'sportDiscipline/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting disciplines');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
        };
    }]);