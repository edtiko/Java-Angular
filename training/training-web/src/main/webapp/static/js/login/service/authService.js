trainingApp.factory('AuthService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return {
            setUserSession: function ($scope) {
                var deferred = $q.defer();
                $http.get($contextPath + '/user/getUserSession')
                        .then(function (res) {
                            if (res.data.entity.output != null) {
                                $scope.appReady = true;
                                $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
                                $scope.getVisibleFieldsUserByUser();
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