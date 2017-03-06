trainingApp.controller('EditAddressController', ['$scope', 'AccountService', 'UserService', '$window', '$mdDialog',
    function ($scope, AccountService, UserService, $window, $mdDialog) {

        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.addressInfo = JSON.parse($window.sessionStorage.getItem("userAddress"));
        $scope.countries = [];
        $scope.states = [];
        $scope.cities = [];
        $scope.editAddress = {
            user_id: $scope.userSession.userWordpressId,
            billing_first_name: $scope.addressInfo.billing_first_name[0],
            billing_second_name: $scope.addressInfo.billing_second_name[0],
            billing_last_name: $scope.addressInfo.billing_last_name[0],
            billing_company: $scope.addressInfo.billing_company != null ? $scope.addressInfo.billing_company[0] : '',
            billing_address_1: $scope.addressInfo.billing_address_1 != null ? $scope.addressInfo.billing_address_1[0] : '',
            billing_country: $scope.addressInfo.billing_country != null ? $scope.addressInfo.billing_country[0] : '',
            billing_state: $scope.addressInfo.billing_state != null ? $scope.addressInfo.billing_state[0] : '',
            billing_city: $scope.addressInfo.billing_city != null ? $scope.addressInfo.billing_city[0] : '',
            billing_postcode: $scope.addressInfo.billing_postcode != null ? $scope.addressInfo.billing_postcode[0] : '',
            billing_phone: $scope.addressInfo.billing_phone[0],
            all: ''
        };

        $scope.fetchAllCountries = function () {
            UserService.fetchAllCountries()
                    .then(
                            function (response) {
                                $scope.countries = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching Currencies');
                            }
                    );
        };

        $scope.getStatesByCountry = function (countryId, change) {
            if (change) {
                $scope.editAddress.billing_city = '';
                $scope.editAddress.billing_state = '';
            }
            if (countryId != null) {
                UserService.getStatesByCountry(countryId)
                        .then(
                                function (response) {
                                    $scope.states = response;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            }
        };
        $scope.getCitiesByState = function (stateId, change) {
            if (change) {
                $scope.editAddress.billing_city = '';
            }
            if (stateId != null) {
                UserService.getCitiesByState(stateId)
                        .then(
                                function (response) {
                                    $scope.cities = response;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            }
        };

        $scope.editConfirmation = function () {
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/views/editAddress/editConfirmation.html',
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
                templateUrl: 'static/views/editAddress/errorMessage.html',
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

        $scope.editAddressInfo = function () {
            if ($scope.userSession.userWordpressId != null) {
                AccountService.editUserAddress(JSON.stringify($scope.editAddress)).then(
                        function (data) {
                            if (data.status == 'success') {
                                $scope.editConfirmation();
                            } else {
                                $scope.errorConfirmation();
                            }
                        },
                        function (error) {
                            $scope.errorConfirmation();
                            console.log(error);
                        }
                );
            } else {
                $scope.showMessage("No se encuentra un usuario asociado con wordpress", "Error");
            }
        };


        $scope.fetchAllCountries();
        $scope.getStatesByCountry($scope.editAddress.billing_country);
        $scope.getCitiesByState($scope.editAddress.billing_state);

    }]);