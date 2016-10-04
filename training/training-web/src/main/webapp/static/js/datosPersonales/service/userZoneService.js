'use strict';
trainingApp.service('UserZoneService', ['$http', '$q', function ($http, $q) {
        return {
            
            fetchAllUserZones: function () {
                return $http.get($contextPath +'userZone/get/all')
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching userZones');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getUserZoneById: function (id) {
                return $http.get($contextPath + '/get/userZone/by/' + id)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching userZones');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createUserZone: function (userZone) {
                return $http.post($contextPath + '/userZone/', userZone)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while creating userZone');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            mergeUserZone: function (userZone) {
                return $http.post($contextPath + '/userZone/', userZone)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while updating userZone');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteUserZone: function (userZone) {
                return $http.post($contextPath + '/userZone/',userZone)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting userZone');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);