'use strict';
trainingApp.service('ObjectiveService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'objective/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getDiscipline: function(res){
                    return $http.get($contextPath+'discipline/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getObjectiveById: function (id) {
                return $http.get($contextPath + '/get/objective/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching objectives');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createObjective: function (objective) {
                return $http.post($contextPath + '/objective/create', objective)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating objective');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeObjective: function (objective) {
                return $http.post($contextPath + '/objective/update', objective)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating objective');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteObjective: function (objective) {
                return $http.post($contextPath + '/objective/delete',objective)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting objective');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getObjectives: function(){
                    return $http.get($contextPath+'objective/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting objectives');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
        };
    }]);