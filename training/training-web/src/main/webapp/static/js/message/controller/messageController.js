trainingApp.controller("MessageController", ['$scope', 'messageService', '$window', function ($scope, messageService, $window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        $scope.planMessage = {
            coachAssignedPlanId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            coachExtAthleteId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            message: '',
            messageUserId: {userId: ''},
            receivingUserId: {userId: ''},
            roleSelected: $scope.roleSelected
        };
        $scope.athletes = [];
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.dataImage = "static/img/profile-default.png";
        $scope.glued = true;

        
        //Carga datos del chat según el tipo de plan
        self.getChat = function (tipoPlan) {

            /*messageService.initialize($scope.planSelected.id);
            if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno || $scope.userSession.typeUser === $scope.userSessionTypeUserCoach) {
                $scope.dataImage = $scope.planSelected.athleteUserId.profilePhotoBase64;
                $scope.userChat = $scope.planSelected.athleteUserId.fullName;
            } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta && tipoPlan == "IN") {
                $scope.dataImage = $scope.planSelected.starUserId.profilePhotoBase64;
                $scope.userChat = $scope.planSelected.starUserId.fullName;
            }else if($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta && tipoPlan == "EXT"){
                $scope.dataImage = $scope.planSelected.coachUserId.profilePhotoBase64;
                $scope.userChat = $scope.planSelected.coachUserId.fullName;
            }*/

            if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                messageService.getMessagesByReceivingUserSendingUser($scope.planSelected.coachUserId.userId, $scope.planSelected.starUserId.userId).then(
                        function (data) {
                            $scope.messages = data.entity.output;
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            } else {
                //self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan);
                messageService.getMessages($scope.planSelected.id, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            $scope.messages = data.entity.output;
                            self.readMessages($scope.planSelected, tipoPlan, $scope.roleSelected);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }
        };
        
        self.getChatUser = function(){
            
        };

       //Envia mensaje
        $scope.addMessage = function () {
            if ($scope.planSelected.external) {
                self.sendMessageExt();
            } else {
                self.sendMessageIn();
            }
        };

        //Envia mensaje para planes Coach Externo
        self.sendMessageExt = function () {
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, "EXT",-1, function () {
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachExtAthleteId.id = $scope.planSelected.id;
                    $scope.planMessage.coachExtAthleteId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachExtAthleteId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    messageService.send($scope.planMessage);
                    $scope.planMessage.message = "";
                    $scope.getMessageCount();
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                }
            });
        };

        //Envia mensaje para planes Coach Interno
        self.sendMessageIn = function () {
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, "IN", $scope.roleSelected, function () {
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;

                    if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachEstrella) {
                        $scope.planMessage.receivingUserId.userId = $scope.coachAssignedPlan.coachUserId.userId;
                    } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                        $scope.planMessage.receivingUserId.userId = $scope.planSelected.starUserId.userId;
                    } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                        $scope.planMessage.receivingUserId.userId = $scope.planSelected.coachUserId.userId;
                    }

                    messageService.send($scope.planMessage);
                    $scope.planMessage.message = "";
                    $scope.getMessageCount();
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                }
            });
        };

        //Recibir Mensajes en tiempo real
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
        
        //Traer la cantidad de mensajes disponibles
        self.getAvailableMessages = function (coachAssignedPlanId, userId, tipoPlan, roleSelected, fn) {
            messageService.getAvailableMessages(coachAssignedPlanId, userId, tipoPlan, roleSelected).then(
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
        
        //Leer mensajes
        self.readMessages = function (planSelected, tipoPlan, roleSelected) {
            var userId = null;
            if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoach || $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {
                userId = planSelected.athleteUserId.userId;
            } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                userId = planSelected.coachUserId.userId;
            }
            messageService.readMessages(planSelected.id, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        $scope.getMessageCount();
                        console.log(data.entity.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };


        if ($scope.userSession != null && $scope.planSelected != null) {
            if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoachEstrella) {

                //self.getAssignedAthletes();
                 self.getChatUser();
            }

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