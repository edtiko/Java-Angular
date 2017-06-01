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
        },
         getAthletesStar: function (coachUserId, starUserId) {
        return $http.get($contextPath + 'get/athletes/by/' + coachUserId+'/'+starUserId).then(
                function (response) {
                return response.data;
                },
                function (errResponse) {
                console.error('Error while fetching athletes ');
                        return $q.reject(errResponse);
                }
        );
        }
                    ,
                getActivePlan: function(athleteUserId, fn){
                return $http.get($contextPath + 'get/assigned/plan/' + athleteUserId).then(
                        fn,
                        function (errResponse) {
                        console.error('Error while fetching plan ');
                                return $q.reject(errResponse);
                        }
                );
                },
                getActivePlanStarAthlete: function(athleteUserId, starUserId, fn){
                return $http.get($contextPath + 'get/assigned/plan/star/athlete/' + athleteUserId+'/'+starUserId).then(
                        fn,
                        function (errResponse) {
                        console.error('Error while fetching plan ');
                                return $q.reject(errResponse);
                        }
                );
                }

        };
        }]);