trainingApp.controller('SuscriptionController', ['$scope', 'UserService', '$window',
    function ($scope, UserService, $window) {

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
            $scope.promise = UserService.getSuscriptionPaginate($scope.query, $scope.userSession.userId, function (response) {
                $scope.suscriptionList = response;
                if ($scope.suscriptionList.length > 0) {
                    $scope.count = $scope.suscriptionList[0].count;
                }
            }).$promise;
        };

        
        //$scope.getSuscriptionsPaginate();

    }]);