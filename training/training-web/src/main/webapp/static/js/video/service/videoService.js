trainingApp.service("videoService", ['$http','$q', function ($http, $q) {
        return{
            getVideosByUser: function (userId) {
                return $http.get($contextPath + 'video/get/videos/' + userId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching user videos ');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };

    }]);