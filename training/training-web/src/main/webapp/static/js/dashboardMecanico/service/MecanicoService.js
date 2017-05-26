trainingApp.service('MecanicoService', ['$http', '$q', function ($http, $q) {
        return {

            getAssignedAthletesPaginate: function (query, userId, fn) {
                return $http.post($contextPath + 'mechanic/get/athletes/paginate/' + userId, query)
                        .then(
                                fn,
                                function (errResponse) {
                                    console.error('Error while fetching athletes');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getAthletes: function(){
                   return $http.get($contextPath + 'mechanic/get/athletes')
                        .then(
                                function(response){
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching athletes');
                                    return $q.reject(errResponse);
                                }
                        );
            
            }
        };
    }]);