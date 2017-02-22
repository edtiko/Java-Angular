trainingApp.controller('SuscriptionController', ['$scope', 'AccountService', '$window',
    function ($scope, AccountService, $window) {

        $scope.suscriptionList = [];
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
        
        $scope.getSubscriptions = function () {
            AccountService.getSubscriptions($scope.userSession.userId).then(
                    function (data) {
                        $scope.suscriptionList = JSON.parse(data);
                    },
                    function(error){
                         console.log(error);
                    }
            );
        };

        
        $scope.getSubscriptions();

    }]);