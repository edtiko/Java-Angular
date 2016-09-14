'use strict';
trainingApp.service('MailService', ['$http', '$q', function ($http, $q) {
        return {
            getMails: function (userId) {
                return $http.get($contextPath + '/get/mails/by/user/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching mails');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getMailsByReceivingUserFromSendingUser: function (receivingUserId, sendingUserId) {
                return $http.get($contextPath + '/get/mails/by/receivingUser/from/sendingUser/' + receivingUserId + '/' + sendingUserId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching mails');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createMailCommunication: function (mail) {
                return $http.post($contextPath + '/mailCommunication/create', mail)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating mail communication');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            updateMailCommunication: function (mail) {
                return $http.post($contextPath + '/mailCommunication/update', mail)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating mail communication');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);