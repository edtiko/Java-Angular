'use strict';
trainingApp.service("AccountService", ['$http', '$q', function ($http, $q) {
        return{
            getSubscriptions: function (userId) {
                return $http.get($contextPath + 'account/get/subscriptions/' + userId)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching subscriptions');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            cancelSuscription: function (subscriptionId, status) {
                return $http.get($contextPath + 'account/cancel/subscription/' + subscriptionId + '/' + status)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while cancel subscription');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getInfoAddress: function (userId) {
                return $http.get($contextPath + 'account/get/address/' + userId)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching info address');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            editUserAddress: function (editAddress) {
                return $http.post($contextPath + 'account/edit/address', editAddress)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (error) {
                                    console.error('Error while edit info address');
                                    return $q.reject(error);
                                }
                        );
            },
            updateAccountUser: function (account) {
                return $http.post($contextPath + 'account/edit/user', account)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (error) {
                                    console.error('Error while edit account');
                                    return $q.reject(error);
                                }
                        );
            }

        };
    }]);