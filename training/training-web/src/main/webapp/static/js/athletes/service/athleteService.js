'use strict';
trainingApp.service("AthleteService", ['$http', '$q', function ($http, $q) {

        return {
            getAthletes: function (coachUserId) {
               return $http.get($contextPath + 'get/athletes/by/' + coachUserId).then(
                        function (response) {
                            return response.data;
                        },
                        function (errResponse) {
                            console.error('Error while fetching athletes ');
                            return $q.reject(errResponse);
                        }
                );
            }

        };
    }]);