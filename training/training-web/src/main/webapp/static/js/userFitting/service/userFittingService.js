trainingApp.service('UserFittingService', ['$http', '$q', function ($http, $q) {
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
            uploadVideo: function (file, userId) {

                var fd = new FormData();
                fd.append('file', file);
                fd.append('userId', userId);
                return $http.post($contextPath + 'userFitting/upload/' + userId, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(
                        function (response) {
                            return response.data;
                        },
                        function (errResponse) {
                            console.error('Error while upload video');
                            return $q.reject(errResponse);
                        }
                );
            }

        };
    }]);