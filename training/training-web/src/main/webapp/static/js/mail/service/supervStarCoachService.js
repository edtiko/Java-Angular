'use strict';
trainingApp.service('SupervStarCoachService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'supervStarCoach/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getSupervStarCoachById: function (id) {
                return $http.get($contextPath + '/get/supervStarCoach/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching supervStarCoachs');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createSupervStarCoach: function (supervStarCoach) {
                return $http.post($contextPath + '/supervStarCoach/create', supervStarCoach)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating supervStarCoach');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeSupervStarCoach: function (supervStarCoach) {
                return $http.post($contextPath + '/supervStarCoach/update', supervStarCoach)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating supervStarCoach');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteSupervStarCoach: function (supervStarCoach) {
                return $http.post($contextPath + '/supervStarCoach/delete',supervStarCoach)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting supervStarCoach');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getByCoachId: function (id) {
                return $http.get($contextPath + '/supervStarCoach/get/by/coachId/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching supervStarCoachs by coach Id');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);