trainingApp.factory('AuthService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return {
            setUserSession: function ($scope) {
                var deferred = $q.defer();
                $http.get($contextPath + '/user/getUserSession')
                        .then(function (res) {
                            if (res.data.entity.output != null) {
                                $scope.appReady = true;
                                $scope.userLogin = res.data.entity.output.login;
                                $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
                            }
                            return res;
                        }, function (errResponse) {
                            console.error('Error while getting');
                            return $q.reject(errResponse);
                        });

                return deferred.promise;
            },
               getSessionOpenTok: function ($scope) {
                var deferred = $q.defer();
                $http.get($contextPath + '/session/opentok')
                        .then(function (res) {
                            if (res.data.entity.output != null) {
                                //$scope.session = angular.copy(res.data.entity.output);
                                $window.sessionStorage.setItem("sessionCall", JSON.stringify(res.data.entity.output));
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