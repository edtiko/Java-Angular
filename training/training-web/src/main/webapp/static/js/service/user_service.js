'use strict';
 
trainingApp.factory('UserService', ['$http', '$q', function($http, $q){
 
    return {
        
          fetchAllCountries: function() {
                    return $http.get('http://localhost:8080/training-web/countries/')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching countries');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             getStatesByCountry: function(countryId) {
                    return $http.get('http://localhost:8080/training-web/states/'+countryId)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching states');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             getCitiesByState: function(stateId) {
                    return $http.get('http://localhost:8080/training-web/cities/'+stateId)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching cities');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
         
            fetchAllUsers: function() {
                    return $http.get('http://localhost:8080/training-web/user/')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching users');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            createUser: function(user){
                user.birthDate = user.birthDate.getDate()+'/'+(user.birthDate.getMonth() + 1)+'/'+user.birthDate.getFullYear();
                    return $http.post('http://localhost:8080/training-web/user/', user)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            updateUser: function(user, id){
                user.birthDate = user.birthDate.getDate()+'/'+(user.birthDate.getMonth() + 1)+'/'+user.birthDate.getFullYear();
                    return $http.put('http://localhost:8080/training-web/user/'+id, user)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            deleteUser: function(id){
                    return $http.delete('http://localhost:8080/training-web/user/'+id)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while deleting user');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
    };
 
}]);