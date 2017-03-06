trainingApp.controller('EditAccountController', ['$scope', 'AccountService', 'UserService', '$window', '$mdDialog',
    function ($scope, AccountService, UserService, $window, $mdDialog) {

        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.account = {
            userId: $scope.userSession.userId,
            firstName: $scope.userSession.firstName,
            secondName: $scope.userSession.secondName,
            lastName: $scope.userSession.lastName,
            password: '',
            email: $scope.userSession.email,
            newPassword: '',
            login: $scope.userSession.login
        };
        $scope.showSecurity = false;

        $scope.editConfirmation = function () {
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/views/editAccount/editConfirmation.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }
            });
        };

        $scope.errorConfirmation = function () {
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/views/editAccount/errorMessage.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }
            });
        };

        $scope.editAccountUser = function () {
            AccountService.updateAccountUser($scope.account).then(
                    function (data) {
                        if (data.status == 'success') {
                            $scope.editConfirmation();
                        } else {
                            $scope.errorMsg = data.message;
                            $scope.errorConfirmation();
                        }
                    },
                    function (error) {
                        $scope.errorMsg = error;
                        $scope.errorConfirmation();
                    }

            );
        };


    }]);