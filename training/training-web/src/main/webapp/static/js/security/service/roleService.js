'use strict';
trainingApp.service('RoleService', ['$http', '$q', function ($http, $q) {
        return {
            
            getPaginate: function(query, res){
                    return $http.post($contextPath+'role/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            getRoleById: function (id) {
                return $http.get($contextPath + '/get/role/by/' + id)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching roles');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createRole: function (role) {
                return $http.post($contextPath + '/role/create', role)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating role');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeRole: function (role) {
                return $http.post($contextPath + '/role/update', role)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating role');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteRole: function (role) {
                return $http.post($contextPath + '/role/delete',role)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting role');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getRoles: function(){
                    return $http.get($contextPath+'role/get/all')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting roles');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
        };
    }]);