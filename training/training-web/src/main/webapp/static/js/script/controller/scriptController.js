trainingApp.controller("ScriptController", ['$scope', 'ScriptService', 'UserService', '$window', 
    function ($scope, ScriptService, UserService, $window) {
        
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planVideoList = [];
        $scope.scriptVideoList = [];
        
        $scope.getPlanVideoStarByCoach = function() {
            ScriptService.getPlanVideoStarByCoach($scope.userSession.userId)
                    .then(
                            function (response) {
                                $scope.planVideoList = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching');
                            }
                    );
        };
        
        $scope.getScriptVideoStarByCoach = function() {
            ScriptService.getScriptVideoStarByCoach($scope.userSession.userId)
                    .then(
                            function (response) {
                                $scope.scriptVideoList = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching');
                            }
                    );
        };
    }]);