trainingApp.service("ExternalCoachService", ['$http', '$q', function ($http, $q) {
        
        return{
             createAthlete: function (obj) {
                 
                return $http.post($contextPath + 'externalCoach/create/athlete', obj)
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
             fetchAthletes: function (trainingPlanUserId, state) {
                return $http.get($contextPath + 'externalCoach/get/athletes/' + trainingPlanUserId+"/"+state)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching athletes');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            removeAthleteCoach: function (coachExtAthleteId) {
                return $http.get($contextPath + 'externalCoach/delete/athlete/' + coachExtAthleteId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting athlete');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            loadAthletes: function () {
                return $http.get($contextPath + 'externalCoach/get/user/athletes')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching athletes');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
        
}]);