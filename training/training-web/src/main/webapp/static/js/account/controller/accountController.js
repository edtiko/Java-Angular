trainingApp.controller('AccountController', ['$scope', 'UserActivityPerformanceService', '$window',
    function ($scope, UserActivityPerformanceService, $window) {

$scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));


  }]);