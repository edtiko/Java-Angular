'use strict';
trainingApp.service('MembershipService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'membership/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getMembershipById: function (id) {
                return $http.get($contextPath + '/get/membership/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching memberships');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createMembership: function (membership) {
                return $http.post($contextPath + '/membership/create', membership)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating membership');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeMembership: function (membership) {
                return $http.post($contextPath + '/membership/update', membership)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating membership');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteMembership: function (membership) {
                return $http.post($contextPath + '/membership/delete',membership)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting membership');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);