trainingApp.service('ScriptService', ['$http', '$q', function ($http, $q) {
        return {
            getPlanVideoStarByCoach: function (userId) {

                return $http.get($contextPath + '/script/get/getScriptVideoStarByCoach/' + userId)
                        .then(
                                function (response) {
                                    return response.data.entity.output;
                                },
                                function (errResponse) {
                                    console.error(userProfile);
                                    console.error('Error while getting');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getScriptVideoStarByCoach: function (userId) {

                return $http.get($contextPath + '/script/get/getScriptVideoStarByStar/' + userId)
                        .then(
                                function (response) {
                                    return response.data.entity.output;
                                },
                                function (errResponse) {
                                    console.error(userProfile);
                                    console.error('Error while getting');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            receivedScripts: function (planId) {
                return $http.get($contextPath + '/script/get/by/plan/' + planId)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error('Error while getting');
                                    return $q.reject(errResponse);
                                }
                        );

            }
        };
    }]);