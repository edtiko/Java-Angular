trainingApp.controller("MessageController", ['$scope', 'messageService', '$window', function ($scope, messageService, $window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        $scope.availableMessageStar = $scope.userSession.starCommunication.availableMsg;
        $scope.messageCountStar = $scope.userSession.starCommunication.planMsg;
        $scope.availableMessageSup = $scope.userSession.supervisorCommunication.availableMsg;
        $scope.messageCountSup = $scope.userSession.supervisorCommunication.planMsg;
        
        $scope.planMessage = {
            coachAssignedPlanId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            coachExtAthleteId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            message: '',
            messageUserId: {userId: ''},
            receivingUserId: {userId: ''},
            roleSelected: $scope.roleSelected,
            mobile: false
        };
        $scope.athletes = [];
        var self = this;
       // $scope.userSession.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected")); // Plan activo
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo")); // Usuario de la sesión
        $scope.selectedUser = JSON.parse($window.sessionStorage.getItem("selectedUser")); //Usuario interno seleccionado
        $scope.dataImage = "static/img/profile-default.png";
        $scope.glued = true;

        
        //Carga datos del chat según el tipo de plan
        self.getChat = function (tipoPlan) {
             $scope.loading = true;
            if($scope.userSession.planSelected != null) {
                messageService.getMessages($scope.userSession.planSelected.id, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            $scope.messages = data.output;
                            $scope.loading = false;
                           
                            $scope.messages.forEach(function (value, index) {
                                if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                                    if (value.messageUserId.userId != $scope.userSession.userId) {
                                        value.messageUserId = $scope.userSession.planSelected.starUserId;
                                    }
                                }
                                if(value.messageUserId.userId == $scope.userSession.planSelected.starUserId.userId){
                                     value.messageUserId = $scope.userSession.planSelected.starUserId;
                                }
                                if(value.messageUserId.userId == $scope.userSession.planSelected.coachUserId.userId && 
                                        $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella){
                                     value.messageUserId = $scope.userSession.planSelected.starUserId;
                                }
                                else if(value.messageUserId.userId == $scope.userSession.planSelected.coachUserId.userId && 
                                        $scope.roleSelected == $scope.userSessionTypeUserCoachInterno){
                                     value.messageUserId = $scope.userSession.planSelected.coachUserId;
                                }
                                if(value.messageUserId.userId == $scope.userSession.planSelected.athleteUserId.userId){
                                     value.messageUserId = $scope.userSession.planSelected.athleteUserId;
                                }
                            });
                            
                            self.readMessages($scope.userSession.planSelected, tipoPlan, $scope.roleSelected, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            } else if ($scope.selectedUser != null) {
                
                    messageService.getMessagesByReceivingUserSendingUser($scope.selectedUser.userId, $scope.userSession.userId).then(
                            function (data) {
                                $scope.messages = data.entity.output;
                            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                                $scope.messages.forEach(function (value, index) {
                                    if (value.messageUserId.userId != $scope.userSession.userId) {
                                        value.messageUserId = $scope.userSession.planSelected.starUserId;
                                    }
                                });
                            }
                                $scope.loading = false;
                            self.readMessages(-1, tipoPlan, -1, $scope.selectedUser.userId, $scope.userSession.userId);
                            },
                            function (error) {
                                //$scope.showMessage(error);
                                console.error(error);
                            });
                
            }
            
        };
        

       //Envia mensaje
        $scope.addMessage = function () {
            
            if ($scope.userSession.planSelected!= null && $scope.userSession.planSelected.external) {
                self.sendMessageExt();
            } else if($scope.userSession.planSelected!= null &&  !$scope.userSession.planSelected.external){
                self.sendMessageIn();
            }else if($scope.selectedUser != null){
                self.sendMessage();
                
            }
        };
        
        self.sendMessage = function () {
            if ($scope.userSession != null && $scope.planMessage.message != "") {
                $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                $scope.planMessage.receivingUserId.userId = $scope.selectedUser.userId;
                messageService.send($scope.planMessage);
                $scope.planMessage.message = "";
            }
        };

        //Envia mensaje para planes Coach Externo
        self.sendMessageExt = function () {
            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, "EXT",-1, function () {
                if ($scope.userSession != null && $scope.userSession.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachExtAthleteId.id = $scope.userSession.planSelected.id;
                    $scope.planMessage.coachExtAthleteId.athleteUserId.userId = $scope.userSession.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachExtAthleteId.coachUserId.userId = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                       if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoach) {
                        $scope.planMessage.receivingUserId.userId = $scope.userSession.planSelected.athleteUserId.userId;
                    } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                        $scope.planMessage.receivingUserId.userId = $scope.userSession.planSelected.coachUserId.userId;
                    }
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
            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, "IN", $scope.roleSelected, function () {
                if ($scope.userSession != null && $scope.userSession.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.userSession.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.userSession.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;

                    if ($scope.userSession.typeUser == $scope.userSessionTypeUserCoachInterno) {
                        $scope.planMessage.receivingUserId.userId = $scope.userSession.planSelected.athleteUserId.userId;
                    } else if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta) {
                        $scope.planMessage.receivingUserId.userId = $scope.userSession.planSelected.coachUserId.userId;
                    }

                    messageService.send($scope.planMessage);
                    $scope.wsocket.send(JSON.stringify($scope.planMessage));
                    
                    $scope.planMessage.message = "";
                    $scope.getMessageCount();
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                }
            });
        };

        //Recibir Mensajes en tiempo real
        messageService.receive().then(null, null, function (message) {
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
            
            if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella 
                    && message.messageUserId.userId == $scope.userSession.planSelected.coachUserId.userId) {
                message.messageUserId = $scope.userSession.planSelected.starUserId;
            } else if (message.messageUserId.userId == $scope.userSession.planSelected.starUserId.userId) {
                message.messageUserId = $scope.userSession.planSelected.starUserId;
            } else if (message.messageUserId.userId == $scope.userSession.planSelected.coachUserId.userId) {
                message.messageUserId = $scope.userSession.planSelected.coachUserId;
            } else if (message.messageUserId.userId == $scope.userSession.planSelected.athleteUserId.userId) {
                message.messageUserId = $scope.userSession.planSelected.athleteUserId;
            }
            
                 $scope.messages.push(message);
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
        self.readMessages = function (planSelected, tipoPlan, roleSelected, fromUserId, toUserId) {
            var userId = null;
            var planId = planSelected.id;
            if(planSelected != -1){
            if ($scope.userSession.typeUser === $scope.userSessionTypeUserCoach || $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {
                userId = planSelected.athleteUserId.userId;
            } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                userId = planSelected.coachUserId.userId;
            }
        }else{
            planId = -1;
            userId = fromUserId;
        }
            messageService.readMessages(planId, userId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        //$scope.getMessageCount();
                        console.log(data.entity.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        $scope.getMessageCount = function () {
            $scope.availableMessage = $scope.userSession.starCommunication.availableMsg;
        };
                
        self.init = function () {
            $scope.roleSelected = $scope.userSessionTypeUserCoachEstrella;
            if ($scope.selectedUser != null) {

                self.getChat("IN");
            } else if ($scope.userSession != null) {

                if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoach) {

                    self.getChat("EXT");
                } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {

                    self.getChat("IN");
                } else if ($scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {

                    if ($scope.userSession.planSelected.external) {
                        self.getChat("EXT");
                    } else {
                        self.getChat("IN");
                    }
                }
            } else {
                $scope.showMessage("El usuario no se encuentra logueado");
                $scope.logout();
            }
        };

        $scope.getMessagesByRole = function (role) {
            $scope.roleSelected = role;
            if(role == $scope.userSessionTypeUserCoachEstrella){
                $scope.userMsgSelected = $scope.userSession.planSelected.starUserId.fullName;
            }else{
                $scope.userMsgSelected = $scope.userSession.planSelected.coachUserId.fullName;
            }
            if ($scope.userSession.planSelected.external) {
                self.getChat("EXT");
            } else {
                self.getChat("IN");
            }
        };


        $scope.getMessagesByRole($scope.userSessionTypeUserCoachEstrella);
    }]);