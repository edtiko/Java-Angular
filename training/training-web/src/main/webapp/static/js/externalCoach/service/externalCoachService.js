trainingApp.service("ExternalCoachService", ['$http', '$q', function ($http, $q) {
        
        return{
             createUser: function (obj) {
                 
                return $http.post($contextPath + '/externalCoach/', obj)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating user');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
        
}]);