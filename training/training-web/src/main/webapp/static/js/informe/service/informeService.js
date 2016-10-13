trainingApp.service('InformeService', ['$http', '$q', function ($http, $q) {
        return {
            getReportRendimiento: function (userId) {

                return $http.get($contextPath + '/video/planVideo/get/response/count/video/'+94)
                        .then(
                                function (response) {
                                    return response.data.entity;
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