trainingApp.service("videoService", ['$http','$q', function ($http, $q) {
        return{
            getVideosByUser: function (userId, fromto) {
                return $http.get($contextPath + 'video/get/videos/'+userId+"/"+fromto)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching user videos ');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            
             getVideoByPath: function (videoPath) {
                return $http.get($contextPath + 'video/files/' + videoPath)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching video data ');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };

    }]);