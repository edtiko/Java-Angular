'use strict';
trainingApp.service('CalendarService', ['$http', '$q', function ($http, $q) {
        return {
            getActivityByDisciplineUser: function (userId) {
                return $http.get($contextPath + '/activity/by/discipline/user/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching users');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);