trainingApp.factory('UserService', ['$http', '$q', function($http, $q){
                return {
                    getUserInfo: function () {
                        var deferred = $q.defer();

                        $http.get($contextPath + '/user')
                                .then(function (response) {
                                    if (response.status == 200) {
                                        deferred.resolve(response.data);
                                    } else {
                                        deferred.reject('Error retrieving user info');
                                    }
                                });

                        return deferred.promise;
                    },
                    logout: function () {
                        $http({
                            method: 'POST',
                            url: $contextPath + '/logout'
                        })
                                .then(function (response) {
                                    if (response.status == 200) {
                                        window.location.reload();
                                    } else {
                                        console.log("Logout failed!");
                                    }
                                });
                    },
                    fetchAllCountries: function () {
                        return $http.get($contextPath + '/countries/')
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while fetching countries');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    getStatesByCountry: function (countryId) {
                        return $http.get($contextPath + '/states/' + countryId)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while fetching states');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    getCitiesByState: function (stateId) {
                        return $http.get($contextPath + '/cities/' + stateId)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while fetching cities');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    getUserById: function (id) {
                        return $http.get($contextPath + '/user/'+id)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while fetching users');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    getImageProfile: function(userId){
                          return $http.get($contextPath + '/getImageProfile/'+userId)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while fetching image profile');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    createUser: function (user) {
                        user.birthDate = user.birthDate.getDate() + '/' + (user.birthDate.getMonth() + 1) + '/' + user.birthDate.getFullYear();
                        return $http.post($contextPath + '/user/', user)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while creating user');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    authenticateUser: function (login, password) {

                        return $http.post($contextPath + '/login/', {login: login, password: password})
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while athenticate user');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    updateUser: function (user, id) {
                        //user.birthDate = user.birthDate.getDate()+'/'+(user.birthDate.getMonth() + 1)+'/'+user.birthDate.getFullYear();
                        return $http.put($contextPath + '/user/' + id, user)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while updating user');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    deleteUser: function (id) {
                        return $http.delete($contextPath + '/user/' + id)
                                .then(
                                        function (response) {
                                            return response.data;
                                        },
                                        function (errResponse) {
                                            console.error('Error while deleting user');
                                            return $q.reject(errResponse);
                                        }
                                );
                    },
                    uploadFileToUrl: function (file, userId) {
                        if (file != null && userId != null) {
                            var fd = new FormData();
                            fd.append('file', file);
                            fd.append('userId', userId);
                            $http.post($contextPath + '/uploadFile/' + userId, fd, {
                                transformRequest: angular.identity,
                                headers: {'Content-Type': undefined}
                            })
                                    .success(function () {
                                    })
                                    .error(function () {
                                    });
                        }
                    }
                };
            }]);