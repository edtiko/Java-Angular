'use strict';
trainingApp.service("AccountService", ['$http', '$q', function ($http, $q) {
        return{
            
            getSubscriptions: function (userId) {
                return $http.get($contextPath + 'account/get/subscriptions/'+userId)
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
                return $http.get($contextPath + 'account/cancel/subscription/' + subscriptionId+'/'+status)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while cancel subscription');
                                    return $q.reject(errResponse);
                                }
                        );
            }
              
        };
    }]);