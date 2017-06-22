trainingApp.controller("MessageController", ['$scope', 'MessageService', '$window', function ($scope, MessageService, $window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
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
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.dataImage = "static/img/profile-default.png";
        $scope.glued = true;
        //$scope.starImage = $window.sessionStorage.getItem("starImage");
        //$scope.asesorImage = $window.sessionStorage.getItem("asesorImage");
        $scope.messageEnabled = false;

        //Carga datos del chat seg�n el tipo de plan
        self.getChat = function (tipoPlan) {
            $scope.loading = true;
            if ($scope.userSession.planSelected != null) {
                var coach = $scope.userSession.planSelected.coachUserId.userId;

                var star = $scope.userSession.planSelected.starUserId.userId;

                MessageService.getMessages($scope.userSession.planSelected.id, $scope.userSession.userId, coach, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            data.output.forEach(function (m) {
                                m.creationDate = new Date(m.creationDate);
                                $scope.messages.push(m);
                            });
                            $scope.loading = false;
                            $scope.messageEnabled = true;
                            self.readMessages();
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });

                MessageService.getMessages($scope.userSession.planSelected.id, $scope.userSession.userId, star, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            data.output.forEach(function (m) {
                                m.creationDate = new Date(m.creationDate);
                                $scope.messages.push(m);
                            });
                            $scope.loading = false;

                            self.readMessages();
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }

        };


        //Envia mensaje para planes Coach Interno
        $scope.sendMessage = function () { 
            var coachUserId = $scope.userSession.planSelected.coachUserId.userId;
            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, coachUserId, "IN", $scope.roleSelected, function (data) {
                $scope.availableMessage = data.output;
                if ($scope.userSession != null && $scope.userSession.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.userSession.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.userSession.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    $scope.planMessage.roleSelected = $scope.roleSelected;
                    $scope.planMessage.receivingUserId.userId = $scope.userSession.planSelected.coachUserId.userId;

                    MessageService.send($scope.planMessage);
                    //$scope.wsocket.send(JSON.stringify($scope.planMessage));                  
                    $scope.planMessage.message = "";

                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                    self.getMessageCount();
                }
            });
        };

        //Recibir Mensajes en tiempo real
        MessageService.receive().then(null, null, function (message) {
            if ($scope.messageEnabled) {
                if ($scope.userSession.userId == message.receivingUserId.userId) {

                    MessageService.readMessage(message.id).then(
                            function (data) {
                                self.getMessageCount();
                            },
                            function (error) {
                                console.error(error);
                            });

                    if (message.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                        $scope.receivedMessageStar++;
                    } else if (message.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                        $scope.receivedMessageSup++;
                    }
                }

                if ($scope.roleSelected == message.roleSelected) {
                    $scope.messages.push(message);
                }
                self.getMessageCount();
            }
        });


        //Traer la cantidad de mensajes disponibles
        self.getAvailableMessages = function (coachAssignedPlanId, userId, toUserId, tipoPlan, roleSelected, fn) {
            MessageService.getAvailableMessages(coachAssignedPlanId, userId, toUserId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        //Leer mensajes
        self.readMessages = function () {

            var planId = $scope.userSession.planSelected.id;
            var coachUserId = $scope.userSession.planSelected.coachUserId.userId;
            var starUserId = $scope.userSession.planSelected.starUserId.userId;
            var toUserId = $scope.userSession.userId;

            MessageService.readMessages(planId, coachUserId, toUserId, "IN", $scope.roleSelected).then(
                    function (data) {
                        $scope.getReceived(toUserId, planId, "IN");
                        //console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });

            MessageService.readMessages(planId, starUserId, toUserId, "IN", $scope.roleSelected).then(
                    function (data) {
                        $scope.getReceived(toUserId, planId, "IN");
                        //console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.getMessageCount = function () {
            var tipoPlan = "IN";
            var coachUserId = $scope.userSession.planSelected.coachUserId.userId;
            var starUserId = $scope.userSession.planSelected.starUserId.userId;

            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, coachUserId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.availableMessageStar = data.output;
            });
            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, coachUserId, tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.availableMessageSup = data.output;
            });

            $scope.getReceivedMessages($scope.userSession.planSelected.id, coachUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.receivedMessageStar = data.output;
                        $scope.getReceivedMessages($scope.userSession.planSelected.id, starUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                                function (data) {
                                    $scope.receivedMessageStar = $scope.receivedMessageStar + data.output;
                                });
                    });

            $scope.getReceivedMessages($scope.userSession.planSelected.id, coachUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.receivedMessageSup = data.output;
                    });

            $scope.getReceived($scope.userSession.userId, $scope.userSession.planSelected.id, "IN");

            //$scope.getMessagesByRole($scope.roleSelected);
        };

        $scope.getMessagesByRole = function (role) {
            $scope.messages = [];
            $scope.roleSelected = role;
            if (role == $scope.userSessionTypeUserCoachEstrella) {
                $scope.userMsgSelected = $scope.userSession.planSelected.starUserId.fullName;
            } else {
                $scope.userMsgSelected = $scope.userSession.planSelected.coachUserId.fullName;
            }
            self.getChat("IN");

        };


        self.init = function () {
            if ($scope.userSession != null) {
                $scope.getMessagesByRole($scope.userSessionTypeUserCoachEstrella);
                //$scope.availableMessageStar = $scope.userSession.planSelected.starCommunication.availableMsg;
                $scope.messageCountStar = $scope.userSession.planSelected.starCommunication.planMsg;

                //$scope.receivedMessageStar = $scope.userSession.planSelected.starCommunication.receivedMsg;
                //$scope.availableMessageSup = $scope.userSession.planSelected.asesorCommunication.availableMsg;
                $scope.messageCountSup = $scope.userSession.planSelected.asesorCommunication.planMsg;
                self.getMessageCount();

                //$scope.receivedMessageSup = $scope.userSession.planSelected.asesorCommunication.receivedMsg;

            } else {
                $scope.setUserSession();
            }
        };

        self.init();

        $scope.$on('$destroy', function () {
            $scope.messageEnabled = false;
        });


    }]);