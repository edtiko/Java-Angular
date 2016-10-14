trainingApp.service('InformeService', ['$http', '$q', function ($http, $q) {
        return {
            getVideoReportRendimiento: function (userId,roleId) {

                return $http.get($contextPath + '/video/planVideo/get/response/count/video/'+userId+'/'+roleId)
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
            },
            getVideoReportTimeResponse: function (userId,roleId) {

                return $http.get($contextPath + '/video/planVideo/get/timeResponse/'+userId+'/'+roleId)
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
            },
            getChatReportRendimiento: function (userId,roleId) {

                return $http.get($contextPath + '/chat/get/response/count/chat/'+userId+'/'+roleId)
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
            },
            getChatReportTimeResponse: function (userId,roleId) {

                return $http.get($contextPath + '/chat/get/timeResponse/'+userId+'/'+roleId)
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
            },
            
            getMailReportRendimiento: function (userId,roleId) {

                return $http.get($contextPath + '/mail/get/response/count/mail/'+userId+'/'+roleId)
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
            },
            getMailReportTimeResponse: function (userId,roleId) {

                return $http.get($contextPath + '/mail/get/timeResponse/'+userId+'/'+roleId)
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