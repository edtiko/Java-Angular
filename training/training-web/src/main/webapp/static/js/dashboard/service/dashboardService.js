trainingApp.service('DashboardService', ['$http', '$q', function ($http, $q) {
        return {
            getDashboard: function (userId, fn) {
                return $http.get($contextPath + 'dashboard/get/by/id/'+userId)
                        .then(
                                fn,
                                function (errResponse) {
                                    console.error(userProfile);
                                    console.error('Error while getting dashboard');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getAssignedAthletesPaginate: function (query, userId, role, res) {
                return $http.post($contextPath + 'get/athtletes/' + userId+'/'+role, query)
                        .then(
                                res,
                                function (errResponse) {
                                    console.error('Error while fetching athletes');
                                    return $q.reject(errResponse);
                                }
                        );

            },
            getAssignedStarByCoach: function (coachUserId) {
                return $http.get($contextPath + 'get/star/' + coachUserId)
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
            getSupervisorsByStar: function (starUserId) {
                return $http.get($contextPath + 'get/supervisors/by/star/' + starUserId)
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
            getAssignedPlan: function (athleteUserId) {
                return $http.get($contextPath + 'get/assigned/plan/' + athleteUserId)
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
            getConfigurationPlanByUser: function (planId, userId, toUserId, planType, roleSelected) {
                return $http.get($contextPath + 'get/count/communication/' + planId + '/' + userId + '/' + toUserId + '/' + planType + '/' + roleSelected)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching coach');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getCountByPlanRole: function (userId,roleId, fn) {
                return $http.get($contextPath + 'get/count/plan/' + userId+'/'+roleId)
                        .then(
                                fn,
                                function (errResponse) {
                                    console.error('Error while fetching count plan coach');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);