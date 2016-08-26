trainingApp.factory('AuthService', ['$http', '$q', '$window', function ($http, $q, $window) {
        return {
            setUserSession: function ($scope) {
                var deferred = $q.defer();
                $http.get($contextPath + '/user/getUserSession')
                        .then(function (res) {
                            if (res.data.entity.output == null) {
                                $scope.showMessage("El usuario no se encuentra logueado");
                                $scope.logout();
                                return res;
                            }

                            $scope.appReady = true;
                            if(res.data.entity.output.secondName == null || res.data.entity.output.secondName == 'undefined') {
                                $scope.userLogin = res.data.entity.output.firstName +" "+ res.data.entity.output.lastName;
                            } else {
                                $scope.userLogin = res.data.entity.output.firstName+" "+ res.data.entity.output.secondName +" "+ res.data.entity.output.lastName;
                            }
                            $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
                            var user = JSON.stringify(res.data.entity.output);
                            $scope.getVisibleFieldsUserByUser(user);
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