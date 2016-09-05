'use strict';
trainingApp.service('StarTeamService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'starTeam/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getStarUser: function(res){
                    return $http.get($contextPath+'starUser/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getCoachUser: function(res){
                    return $http.get($contextPath+'coachUser/get/all')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getStarTeamById: function (id) {
                return $http.get($contextPath + '/get/starTeam/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching startTeams');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createStarTeam: function (startTeam) {
                return $http.post($contextPath + '/starTeam/create', startTeam)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating startTeam');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createStarWordPress: function (user) { 
                
                return $http({
                    url: $wordPressContextPath + 'category_products.php',
                    data : user,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    method: 'POST'
                })
                        .then(
                                function (response) {
                                    return response;
                                },
                                function (errResponse) {
                                    console.error('Error while creating startTeam');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeStarTeam: function (startTeam) {
                return $http.post($contextPath + '/starTeam/update', startTeam)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating startTeam');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteStarTeam: function (startTeam) {
                return $http.post($contextPath + '/starTeam/delete',startTeam)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting startTeam');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);