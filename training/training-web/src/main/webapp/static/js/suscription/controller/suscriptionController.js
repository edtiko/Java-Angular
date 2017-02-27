trainingApp.controller('SuscriptionController', ['$scope', 'AccountService', '$window','$mdDialog',
    function ($scope, AccountService, $window,$mdDialog) {

        $scope.subscriptionList = [];
        $scope.count = 0;
        $scope.selected = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.count = 0;
        $scope.filter = {
            options: {
                debounce: 500
            }
        };
        $scope.query = {
            filter: '',
            limit: 3,
            page: 1
        };

        $scope.getSuscriptionsPaginate = function () {
            $scope.promise = AccountService.getSubscriptions($scope.query, $scope.userSession.userId, function (response) {
                $scope.suscriptionList = response;
                if ($scope.suscriptionList.length > 0) {
                    $scope.count = $scope.suscriptionList[0].count;
                }
            }).$promise;
        };
        
        Date.prototype.addDays = function (days) {
            var dat = new Date(this.valueOf());
            dat.setDate(dat.getDate() + days);
            return dat;
        };
        
        function addDays(date, days) {
            var result = new Date(date);
            result.setDate(date.getDate() + days);
            return result;
        }

        $scope.getSubscriptions = function () {
            $scope.loading = true;
            $scope.promise = AccountService.getSubscriptions($scope.userSession.userId).then(
                    function (data) {
                        $scope.subscriptionList = JSON.parse(JSON.parse(data));
                        angular.forEach($scope.subscriptionList, function (v, k) {
                    
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
                        
                        $scope.subscriptionList = $scope.subscriptionList.filter(function (e) {
                            return  (e['products'][0].plan != '' || e['products'][0].name.indexOf("Membresia") !== -1);
                        });

                        $scope.loading = false;
                    },
                    function (error) {
                        console.log(error);
                        $scope.loading = false;
                    }
            ).$promise;
        };
        
        $scope.confirmationCancel = function(){
                $mdDialog.show({
                    scope: $scope.$new(),
                    templateUrl: 'static/views/suscription/cancelConfirmation.html',
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
        
        $scope.cancelSuscription = function () {
            var id = $scope.selected[0].id;
            var status = $scope.selected[0].state;
            AccountService.cancelSuscription(id, status).then(
                    function (data) {
                       if(data.status == 'success'){
                           $scope.showMessage(data.output);
                           $scope.getSubscriptions();
                       }
                    },
                    function (error) {
                        $scope.showMessage("Ocurrió un error");
                        console.log(error);
                    }
            );
        };

        $scope.showSubscription = function (item) {
            console.log(item);
        };

        $scope.onSelect = function (item) {
            console.log(item);
        };


        $scope.getSubscriptions();

    }]);