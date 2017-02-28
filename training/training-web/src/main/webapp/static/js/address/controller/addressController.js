trainingApp.controller('AddressController', ['$scope', 'AccountService', '$window',
    function ($scope, AccountService, $window) {

        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));

        $scope.getInfoAddress = function () {
            $window.sessionStorage.setItem("userAddress", null);
            AccountService.getInfoAddress($scope.userSession.userId).then(
                    function (data) {
                        $scope.addressInfo = JSON.parse(JSON.parse(data));
                        $window.sessionStorage.setItem("userAddress", JSON.stringify($scope.addressInfo));
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };
        
        $scope.getInfoAddress();

    }]);