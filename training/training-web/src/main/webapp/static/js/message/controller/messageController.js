trainingApp.controller("MessageController", ['$scope', 'messageService', 'UserService', '$window', function ($scope, messageService, UserService, $window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        $scope.planMessage = {coachAssignedPlanId: {id:'', athleteUserId:{userId:''}, coachUserId: {userId:''}}, message: '', messageUserId: {userId:''}};
        $scope.athletes = [];
        $scope.coachAssignedPlan = null;
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.dataImage = "static/img/profile-default.png";


        $scope.addMessage = function () {
            if ($scope.userSession != null && $scope.coachAssignedPlan != null) {
                $scope.planMessage.coachAssignedPlanId.id = $scope.coachAssignedPlan.id;
                $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.coachAssignedPlan.athleteUserId.userId;
                $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.coachAssignedPlan.coachUserId.userId;
                $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                messageService.send($scope.planMessage);
                $scope.planMessage.message = "";

            } else {
                $scope.showMessage("Seleccione una relación atleta y coach");
            }
        };

        messageService.receive().then(null, null, function (message) {
            $scope.messages.push(message);
        });

        self.getAssignedAthletes = function () {
            messageService.getAssignedAthletes($scope.userSession.userId).then(
                    function (data) {
                        $scope.athletes = data.entity.output;
                        if($scope.athletes == null){
                            $scope.showMessage("No tiene planes asignados.");
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.getImageProfile = function (userId) {
            if (userId != null) {
                UserService.getImageProfile(userId)
                        .then(
                                function (response) {
                                    if (response != "") {
                                        $scope.dataImage = "data:image/png;base64," + response;
                                    } else {
                                        $scope.dataImage = "static/img/profile-default.png";
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Image Profile');
                                }
                        );
            }
        };

        $scope.selectChat = function (coachAssignedPlanSelected) {
            $scope.coachAssignedPlan = angular.copy(coachAssignedPlanSelected);
            messageService.initialize($scope.coachAssignedPlan.id);
            if ($scope.coachAssignedPlan != null && $scope.userSession.typeUser === 'Coach') {
                self.getImageProfile($scope.coachAssignedPlan.athleteUserId.userId);
                $scope.userChat = $scope.coachAssignedPlan.athleteUserId.fullName;
            } else if ($scope.coachAssignedPlan != null && $scope.userSession.typeUser === 'Atleta') {
                self.getImageProfile($scope.coachAssignedPlan.coachUserId.userId);
                $scope.userChat = $scope.coachAssignedPlan.coachUserId.fullName;
            }
            messageService.getMessages($scope.coachAssignedPlan.id).then(
                    function (data) {
                        $scope.messages = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        self.getChatUser = function(){
            var planSelected = JSON.parse($window.sessionStorage.getItem("coachAssignedPlanSelected"));
          if(planSelected != null){
               $scope.selectChat(planSelected);
          }  
        };

        self.getAssignedCoach = function () {
            messageService.getAssignedCoach($scope.userSession.userId).then(
                    function (data) {
                        $scope.selectChat(data.entity.output);

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        if ($scope.userSession != null && $scope.userSession.typeUser === 'Coach') {
            //self.getAssignedAthletes();
            self.getChatUser();
        } else if ($scope.userSession != null && $scope.userSession.typeUser === 'Atleta') {
            self.getAssignedCoach();
        }

    }]);