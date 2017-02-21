trainingApp.controller('ProgressController', ['$scope', 'UserActivityPerformanceService', '$window',
    function ($scope, UserActivityPerformanceService, $window) {

        $scope.rightSelect = [];
        $scope.toRemove = [];
        $scope.leftSelect = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.filter = {
           date: '',
           activity: '',
           userId : $scope.userSession.userId
        };
        $scope.result = {};
        $scope.moveRight = function () {
            var left = $scope.leftSelect;
            for (var i = 0; i < left.length; i++) {
                var el = left[i];
                if ($scope.rightSelect.indexOf(el) < 0) {
                    $scope.rightSelect.push(el);
                }
            }
        };

        $scope.moveLeft = function () {
            var toRemove = $scope.toRemove;
            for (var i = 0; i < toRemove.length; i++) {
                var el = toRemove[i];
                var indexOf = $scope.rightSelect.indexOf(el);
                $scope.rightSelect.splice(indexOf, 1);

            }
        };
        
        $scope.getProgressReport = function(){
            UserActivityPerformanceService.getProgressReport($scope.filter.date, $scope.filter.activity, $scope.filter.userId).then(
                    function(data){
                       $scope.result = data; 
                    },
                    function(error){
                        console.log(error);
                    }                  
                    
                    );
        };


    }]);

trainingApp.directive('myClick', function ($parse, $rootScope) {
    return {
        restrict: 'A',
        compile: function ($element, attrs) {
            var fn = $parse(attrs.myClick, null, true);
            return function myClick(scope, element) {
                element.on('click', function (event) {
                    var callback = function () {
                        fn(scope, { $event: event });
                    };
                    scope.$apply(callback);
                })
            }
        }
    }
});