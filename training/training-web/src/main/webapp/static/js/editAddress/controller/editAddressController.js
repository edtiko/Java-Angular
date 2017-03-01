trainingApp.controller('EditAddressController', ['$scope', 'AccountService', '$window',
    function ($scope, AccountService, $window) {

        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.addressInfo = JSON.parse($window.sessionStorage.getItem("userAddress"));
        $scope.editAddress = {
            user_id: $scope.userSession.userWordpressId,
            billing_first_name: $scope.addressInfo.billing_first_name[0],
            billing_second_name: $scope.addressInfo.billing_second_name[0],
            billing_last_name: $scope.addressInfo.billing_last_name[0],
            billing_company: $scope.addressInfo.billing_company != null? $scope.addressInfo.billing_company[0]: '',
            billing_address_1: $scope.addressInfo.billing_address_1 != null? $scope.addressInfo.billing_address_1[0]: '',
            billing_country: $scope.addressInfo.billing_country != null? $scope.addressInfo.billing_country[0]: '',
            billing_state: $scope.addressInfo.billing_state != null? $scope.addressInfo.billing_state[0]: '',
            billing_city: $scope.addressInfo.billing_city != null? $scope.addressInfo.billing_city[0]: '',
            billing_postcode: $scope.addressInfo.billing_postcode != null? $scope.addressInfo.billing_postcode[0]: '',
            billing_phone: $scope.addressInfo.billing_phone[0],
            all: ''
        };

        $scope.editAddressInfo = function () {
            if ($scope.userSession.userWordpressId != null) {
                AccountService.editUserAddress(JSON.stringify($scope.editAddress)).then(
                        function (data) {
                            if (data.status == 'success') {
                                $scope.showMessage(data.message);
                            }
                        },
                        function (error) {
                            console.log(error);
                        }
                );
            } else {
                $scope.showMessage("No se encuentra un usuario asociado con wordpress", "Error");
            }
        };


    }]);