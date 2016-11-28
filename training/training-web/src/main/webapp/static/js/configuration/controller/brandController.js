trainingApp.controller('BrandController', ['$scope', 'BrandService', '$window', '$mdDialog',
    function ($scope, BrandService, $window, $mdDialog) {
        $scope.brand = {brandId: null,
            name: '',
            stateId: '',
            userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        $scope.brandList = [];
        $scope.count = 0;

        var bookmark;
        $scope.selected = [];

        $scope.filter = {
            options: {
                debounce: 500
            }
        };

        $scope.query = {
            filter: '',
            order: 'name',
            limit: 10,
            page: 1
        };

        function success(response) {
            if (response.data.status == 'fail') {
                $scope.showMessage(response.data.output);
            } else {
                return response.data.output;
            }

            return null;
        }

        $scope.getBrandPaginate = function () {
            $scope.promise = BrandService.getPaginate($scope.query, function (response) {
                $scope.brandList = success(response);

                if ($scope.brandList.length > 0) {
                    $scope.count = $scope.brandList[0].count;
                }
            }).$promise;
        };



        $scope.createBrand = function (brand) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                brand.userCreate = (user.userId);
            }
            BrandService.createBrand(brand)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetBrand();
                                    $scope.getBrandPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating Brand.');
                            }
                    );
        };

        $scope.updateBrand = function (brand) {
            if ($scope.appReady) {
                var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
                brand.userUpdate = (user.userId);
            }

            BrandService.mergeBrand(brand)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetBrand();
                                    $scope.showMessage(d.output);
                                    $scope.getBrandPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating Brand.');
                            }
                    );
        };

        $scope.deleteBrand = function (brand) {
            BrandService.deleteBrand(brand)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.resetBrand();
                                    $scope.showMessage(d.output);
                                    $scope.getBrandPaginate();
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while deleting Brand.');
                            }
                    );
        };

        $scope.submitBrand = function (form) {
            if (form.$valid) {
                if ($scope.brand.brandId === null) {
                    $scope.createBrand($scope.brand);
                } else {
                    $scope.updateBrand($scope.brand);
                }
            } else {
                form.$setSubmitted();
            }

        };

        $scope.editBrand = function (id, ev) {
            for (var i = 0; i < $scope.brandList.length; i++) {
                if ($scope.brandList[i].brandId === id) {
                    $scope.brand = angular.copy($scope.brandList[i]);
                    break;
                }
            }
            $scope.showCreateBrand(ev);
        };
        
        $scope.openBrand = function (ev) {
            $scope.resetBrand();
            $scope.showCreateBrand(ev);
        };

        $scope.inactivateBrand = function (id) {
            for (var i = 0; i < $scope.brandList.length; i++) {
                if ($scope.brandList[i].brandId === id) {
                    $scope.brand = angular.copy($scope.brandList[i]);
                    break;
                }
            }
            $scope.brand.stateId = 0;
            $scope.updateBrand($scope.brand);
            $scope.resetBrand();
        };

        $scope.activateBrand = function (id) {
            for (var i = 0; i < $scope.brandList.length; i++) {
                if ($scope.brandList[i].brandId === id) {
                    $scope.brand = angular.copy($scope.brandList[i]);
                    break;
                }
            }
            $scope.brand.stateId = 1;
            $scope.updateBrand($scope.brand);
        };

        $scope.removeBrand = function (id) {
            if ($scope.brand.brandId === id) {
                $scope.resetBrand();
            }
            $scope.deleteBrand(id);
        };

        $scope.resetBrand = function () {
            $scope.brand = {brandId: null, name: '',
                stateId: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
        };

        $scope.removeFilter = function () {
            $scope.filter.show = false;
            $scope.query.filter = '';

            if ($scope.filter.form.$dirty) {
                $scope.filter.form.$setPristine();
            }
        };

        $scope.$watch('query.filter', function (newValue, oldValue) {
            if (!oldValue) {
                bookmark = $scope.query.page;
            }

            if (newValue !== oldValue) {
                $scope.query.page = 1;
            }

            if (!newValue) {
                $scope.query.page = bookmark;
            }

            $scope.getBrandPaginate();
        });

        $scope.showCreateBrand = function (ev) {

            $mdDialog.show({
                controller: BrandController,
                scope: $scope.$new(),
                templateUrl: 'static/views/configuration/create-brand.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    brand: function () {
                        return $scope.brand;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                    });
        };

        function BrandController($scope, $mdDialog,
                brand) {

            $scope.brand = brand;

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }

        $scope.getBrandPaginate();


    }]);