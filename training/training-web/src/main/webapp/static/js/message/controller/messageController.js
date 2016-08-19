trainingApp.controller("MessageController", ['$scope', 'messageService','$window', function ($scope, messageService,$window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        $scope.planMessage = {coachAssignedPlanId: '', message: '', athleteUserId: '', coachUserId: ''};
        $scope.athletes = [];
        $scope.coachAssignedPlanId = '';
        var self = this;
        var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.addMessage = function () {
            if($scope.coachAssignedPlanId !== ''){
            $scope.planMessage.coachAssignedPlanId = $scope.coachAssignedPlanId;
            $scope.planMessage.athleteUserId = 98;
            $scope.planMessage.coachUserId = user.userId;
            messageService.send($scope.planMessage);
            $scope.message = "";
        }else{
            $scope.showMessage("Seleccione primero un atleta");
        }
        };

        messageService.receive().then(null, null, function (message) {
            $scope.messages.push(message);
        });

        self.getAssignedAthletes = function () {
            if(user != null){
            messageService.getAssignedAthletes(user.userId).then(
                    function (data) {
                        $scope.athletes = data.entity.output;
                    },
                    function(error){
                        //$scope.showMessage(error);
                        console.error(error);
                    });
                }
        };
        self.getAssignedAthletes();
        
        
        $scope.selectChat = function(id){
            $scope.coachAssignedPlanId = id;
           messageService.getMessages(id).then(
                    function (data) {
                        $scope.messages = data.entity.output;
                    },
                    function(error){
                        //$scope.showMessage(error);
                        console.error(error);
                    }); 
        };


    }]);