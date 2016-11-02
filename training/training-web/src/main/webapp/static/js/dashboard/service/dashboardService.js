trainingApp.service('DashboardService', ['$http', '$q', function ($http, $q) {
        return {
            getDashboard: function (userProfile) {
                return $http.post($contextPath + 'dashboard/get/by/id', userProfile)
                        .then(
                                function (response) {
                                    return response.data.entity.output;
                                },
                                function (errResponse) {
                                    console.error(userProfile);
                                    console.error('Error while getting dashboard');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getAssignedAthletes: function (coachUserId) {
                return $http.get($contextPath + 'get/athtletes/' + coachUserId)
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
             getAssignedAthletesByStar: function (starUserId) {
                 return $http.get($contextPath + 'get/athtletes/by/star/' + starUserId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching athletes');
                                    return $q.reject(errResponse);
                                }
                        );
//                return $http.get($contextPath + 'get/athtletes/by/star/' + starUserId)
//                        .then(
//                                function (response) {
//                                    return response.data;
//                                },
//                                function (errResponse) {
//                                    console.error('Error while fetching athletes');
//                                    return $q.reject(errResponse);
//                                }
//                        );

            },
            getAssignedCoach: function (athleteUserId) {
                return $http.get($contextPath + 'get/coach/' + athleteUserId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching coach');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getAssignedStarCoachBySupervisor: function (userId) {
                return $http.get($contextPath + 'get/coach/by/supervisor/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching coach');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getAssignedAtleteCoachBySupervisor: function (userId) {
                return $http.get($contextPath + 'get/atlete/coach/by/supervisor/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching coach');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getAssignedUserBySupervisor: function (userId) {
                return $http.get($contextPath + 'get/users/by/supervisor/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching coach');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);