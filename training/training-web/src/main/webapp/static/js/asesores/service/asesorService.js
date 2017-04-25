'use strict';
trainingApp.service("AsesorService", ['$http', '$q', function ($http, $q) {

        return {
            getAsesores: function (starUserId) {
               return $http.get($contextPath + 'get/asesores/by/' + starUserId).then(
                        function (response) {
                            return response.data;
                        },
                        function (errResponse) {
                            console.error('Error while fetching athletes ');
                            return $q.reject(errResponse);
                        }
                );
            },
            getActivePlan: function(athleteUserId, fn){
                return $http.get($contextPath + 'get/assigned/plan/' + athleteUserId).then(
                        fn,
                        function (errResponse) {
                            console.error('Error while fetching plan ');
                            return $q.reject(errResponse);
                        }
                ); 
            }

        };
    }]);