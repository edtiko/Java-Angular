trainingApp.controller("MessageController", ['$scope', 'messageService', '$window', function ($scope, messageService, $window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        $scope.planMessage = {
            coachAssignedPlanId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            coachExtAthleteId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            message: '',
            messageUserId: {userId: ''}
        };
        $scope.athletes = [];
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.dataImage = "static/img/profile-default.png";
        $scope.glued = true;


        self.getChat = function (tipoPlan) {

            messageService.initialize($scope.planSelected.id);
            if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno || $scope.userSession.typeUser === $scope.userSessionTypeUserCoach) {
                $scope.dataImage = $scope.planSelected.athleteUserId.profilePhotoBase64;
                $scope.userChat = $scope.planSelected.athleteUserId.fullName;
            } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.dataImage = $scope.planSelected.coachUserId.profilePhotoBase64;
                $scope.userChat = $scope.planSelected.coachUserId.fullName;
            }
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan);
            messageService.getMessages($scope.planSelected.id, tipoPlan).then(
                    function (data) {
                        $scope.messages = data.entity.output;
                        self.readMessages($scope.planSelected, tipoPlan);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });

        };


        $scope.addMessage = function () {
            if ($scope.planSelected.external) {
                self.sendMessageExt();

            } else {
                self.sendMessageIn();

            }
        };

        self.sendMessageExt = function () {
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, "EXT", function () {
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachExtAthleteId.id = $scope.planSelected.id;
                    $scope.planMessage.coachExtAthleteId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachExtAthleteId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    messageService.send($scope.planMessage);
                    $scope.planMessage.message = "";
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                }
            });
        };


        self.sendMessageIn = function () {
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, "IN", function () {
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    messageService.send($scope.planMessage);
                    $scope.planMessage.message = "";
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                }
            });
        };

        messageService.receive().then(null, null, function (message) {
            $scope.messages.push(message);
            if (message.id != "" && $scope.userSession != null && $scope.userSession.userId != message.messageUserId.userId) {
                messageService.readMessage(message.id).then(
                        function (data) {
                            console.log(data.entity.output);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }
        });

        self.getAvailableMessages = function (coachAssignedPlanId, userId, tipoPlan, fn) {
            messageService.getAvailableMessages(coachAssignedPlanId, userId, tipoPlan).then(
                    function (data) {
                        $scope.availableMessage = data.entity.output;
                        if (fn != null) {
                            fn();
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.readMessages = function (planSelected, tipoPlan) {
            var userId = null;
            if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoach || $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {
                userId = planSelected.athleteUserId.userId;
            } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                userId = planSelected.coachUserId.userId;
            }
            messageService.readMessages(planSelected.id, userId, tipoPlan).then(
                    function (data) {
                        console.log(data.entity.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };


        if ($scope.userSession != null && $scope.planSelected != null) {
            if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoach) {

                self.getChat("EXT");

            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {

                self.getChat("IN");

            } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {

                if ($scope.planSelected.external) {
                    self.getChat("EXT");
                } else {
                    self.getChat("IN");
                }
            }
        } else {
            $scope.showMessage("El usuario no se encuentra logueado");
            $scope.logout();
        }



    }]);