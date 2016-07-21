angular.module('frontendServices', [])

    .service('UserService', ['$http','$q', function($http, $q) {
        return {
             getUserInfo: function() {
                var deferred = $q.defer();

                $http.get('http://localhost:8080/training/user')
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Error retrieving user info');
                        }
                });

                return deferred.promise;
            },
            logout: function () {
                $http({
                    method: 'POST',
                    url: 'http://localhost:8080/training/logout'
                })
                .then(function (response) {
                    if (response.status == 200) {
                    window.location.reload();
                    }
                    else {
                        console.log("Logout failed!");
                    }
                });
            },
            fetchAllCountries: function() {
                    return $http.get('http://localhost:8080/training/countries/')
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
                    return $http.get('http://localhost:8080/training/states/'+countryId)
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
                    return $http.get('http://localhost:8080/training/cities/'+stateId)
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
                    return $http.get('http://localhost:8080/training/user/')
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
                    return $http.post('http://localhost:8080/training/user/', user)
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
              authenticateUser: function(login, password){
                
                    return $http.post('http://localhost:8080/training/login/', {login: login, password:password})
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while athenticate user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            updateUser: function(user, id){
                user.birthDate = user.birthDate.getDate()+'/'+(user.birthDate.getMonth() + 1)+'/'+user.birthDate.getFullYear();
                    return $http.put('http://localhost:8080/training/user/'+id, user)
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
                    return $http.delete('http://localhost:8080/training/user/'+id)
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