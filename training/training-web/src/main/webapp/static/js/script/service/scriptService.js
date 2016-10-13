trainingApp.service('ScriptService', ['$http', '$q', function ($http, $q) {
        return {
            getPlanVideoStarByCoach: function (userId) {

                return $http.post($contextPath + '/video/planVideo/get/planVideoStarByCoach/', userId)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error(userProfile);
                                    console.error('Error while getting');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
            getScriptVideoStarByCoach: function (userId) {

                return $http.post($contextPath + '/script/get/getScriptVideoStarByCoach/', userId)
                        .then(
                                function (response) {
                                    return response.data.output;
                                },
                                function (errResponse) {
                                    console.error(userProfile);
                                    console.error('Error while getting');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);