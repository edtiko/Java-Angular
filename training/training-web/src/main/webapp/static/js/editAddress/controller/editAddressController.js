trainingApp.controller('EditAddressController', ['$scope', 'AccountService', '$window',
    function ($scope, AccountService, $window) {

        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.addressInfo = JSON.parse($window.sessionStorage.getItem("userAddress"));

        $scope.editAddress = function () {
            AccountService.editUserAddress($scope.addressInfo).then(
                    function (data) {

                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };
        

    }]);