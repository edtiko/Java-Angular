trainingApp.controller("ChatController", ['$scope','chatService', function($scope, chatService) {
  $scope.messages = [];
  $scope.message = "";
  $scope.max = 140;

  $scope.addMessage = function() {
    chatService.send($scope.message);
    $scope.message = "";
  };

  chatService.receive().then(null, null, function(message) {
    $scope.messages.push(message);
  });
}]);