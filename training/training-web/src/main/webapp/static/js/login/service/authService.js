trainingApp.factory('AuthService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return {
            setUserSession: function () {
                var deferred = $q.defer();
                $http.get($contextPath + '/user/getUserSession')
                        .then(function (res) {
                            if (res.data.entity.output != null) {
                                $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
                            }
                            return res;
                        }, function (errResponse) {
                            console.error('Error while getting');
                            return $q.reject(errResponse);
                        });

                return deferred.promise;
            }
        };
    }]);