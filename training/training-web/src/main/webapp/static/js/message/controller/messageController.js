trainingApp.controller("MessageController", ['$scope', 'messageService', function ($scope, messageService) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        $scope.userIdSelected = '';
        $scope.planMessage = {coachAssignedPlanId: '', message: ''};
        $scope.athletes = [];
        var self = this;

        $scope.addMessage = function () {
            messageService.send($scope.planMessage);
            $scope.message = "";
        };

        messageService.receive().then(null, null, function (message) {
            $scope.messages.push(message);
        });

        self.getAssignedAthletes = function () {
            messageService.getAssignedAthletes().then(
                    function (data) {
                        $scope.athletes = data;
                    });
        };
        self.getAssignedAthletes();


    }]);