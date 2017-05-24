trainingApp.controller('OrderController', ['$scope', 'AccountService', '$window','$mdDialog',
    function ($scope, AccountService, $window, $mdDialog) {

        $scope.orderList = [];
        $scope.count = 0;
        $scope.selected = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.count = 0;
        $scope.query = {
            filter: '',
            limit: 3,
            page: 1
        };

        $scope.getOrders = function () {
            $scope.loading = true;
            $scope.promise = AccountService.getSubscriptions($scope.userSession.userId).then(
                    function (data) {
                        $scope.orderList = JSON.parse(JSON.parse(data));
                        angular.forEach($scope.orderList, function (v, k) {

                            switch (v['state']) {
                                case 'on-hold':
                                    v['state_user'] = 'En espera';
                                    break;
                                case 'pending':
                                    v['state_user'] = 'Pendiente';
                                    break;
                                case 'active':
                                    v['state_user'] = 'Activa';
                                    break;
                                case 'expired':
                                    v['state_user'] = 'Expirada';
                                    break;
                                case 'cancelled':
                                    v['state_user'] = 'Cancelada';
                                    break;
                                case 'pending-cancel':
                                    v['state_user'] = 'Pendiente por cancelar';
                                    break;
                                case 'switched':
                                    v['state_user'] = 'Cambiada';
                                    break;
                                default:
                                    v['state_user'] = 'Sin estado';
                                    break;

                            }
                        });

                        $scope.orderList = $scope.orderList.filter(function (e) {
                            return  (e['products'][0].plan == '' && e['products'][0].name.indexOf("Membresia") == -1);
                        });

                        $scope.loading = false;
                    },
                    function (error) {
                        console.log(error);
                        $scope.loading = false;
                    }
            ).$promise;
        };
        
        $scope.verFactura = function (order) {
            $scope.selected = order;
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/views/order/receipt.html',
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

        $scope.getOrders();
    }]);