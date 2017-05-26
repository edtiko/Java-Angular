trainingApp.service('UserFittingService', ['$http', '$q', function ($http, $q) {
        return {
            getUserFittingVideo: function (userFittingId) {

                return $http.get($contextPath + 'userFitting/get/video/' + userFittingId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while getting');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            uploadVideo: function (file, userFittingId) {

                var fd = new FormData();
                fd.append('file', file);
                fd.append('userFittingId', userFittingId);
                return $http.post($contextPath + 'userFitting/upload/' + userFittingId, fd, {
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