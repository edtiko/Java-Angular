trainingApp.service('MecanicoService', ['$http', '$q', function ($http, $q) {
        return {

            getAssignedAthletesPaginate: function (query, userId, fn) {
                return $http.post($contextPath + 'mechanic/get/athtletes/paginate/' + userId, query)
                        .then(
                                fn,
                                function (errResponse) {
                                    console.error('Error while fetching athletes');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);