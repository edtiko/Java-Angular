'use strict';
trainingApp.service('UserService', ['$http', '$q', function ($http, $q) {
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
            getPaginate: function(query, res){
                    return $http.post($contextPath+'user/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
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
            fetchAllUsers: function () {
                return $http.get($contextPath +'user/get/all')
                        .then(
                                function (response) {
                                    return response.data.entity.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching users');
                                    return $q.reject(errResponse);
                                }
                        );
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
                return $http.get($contextPath + '/user/' + id)
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
            getImageProfile: function (userId) {
                return $http.get($contextPath + '/getImageProfile/' + userId)
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
            createInternalUser: function (user) {
                return $http.post($contextPath + 'user/create/internal/', user)
                        .then(
                                function (response) {
                                    return response.data.entity;
                                },
                                function (errResponse) {
                                    console.error('Error while creating internal user');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeInternalUser: function (user) {
                return $http.post($contextPath + 'user/merge/internal/', user)
                        .then(
                                function (response) {
                                    return response.data.entity;
                                },
                                function (errResponse) {
                                    console.error('Error while merging internal user');
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
                 var date = user.birthDate.split("/");
                 var dateFormat = new Date(date[2], date[1] - 1, date[0]);
                 var dto = user;
                dto.birthDate = ("0" + (dateFormat.getDate()+1)).slice(-2)+"/"+("0" + (dateFormat.getMonth() + 1)).slice(-2)+"/"+dateFormat.getFullYear();
                return $http.put($contextPath + '/user/' + id, dto)
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

                var fd = new FormData();
                fd.append('file', file);
                fd.append('userId', userId);
                return $http.post($contextPath + '/uploadFile/' + userId, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                })
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while upload image');
                                    return $q.reject(errResponse);
                                }
                        );

            },
            getUserDisciplineById: function (id) {
                return $http.get($contextPath + '/user/getDiscipline/by/' + id)
                        .then(
                                function (response) {
                                    return response.data.entity;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching users');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            isImage: function (src) {

                var deferred = $q.defer();
                var res = false;
                var image = new Image();
                image.onerror = function () {
                    deferred.resolve(false);
                };
                image.onload = function () {
                    deferred.resolve(true);
                };
                image.src = src;

                return deferred.promise;
            }
        };
    }]);