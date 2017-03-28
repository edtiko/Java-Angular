trainingApp.factory('AuthService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return {
            setUserSession: function (fn) {
                var deferred = $q.defer();
                $http.get($contextPath + '/user/getUserSession')
                        .then(fn, function (errResponse) {
                            console.error('Error while getting');
                            return $q.reject(errResponse);
                        });

                return deferred.promise;
            }
        };
    }]);