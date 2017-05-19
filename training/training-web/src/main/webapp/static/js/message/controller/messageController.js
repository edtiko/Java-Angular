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
        $scope.starImage = $window.sessionStorage.getItem("starImage");
        $scope.asesorImage = $window.sessionStorage.getItem("asesorImage");

        //Carga datos del chat según el tipo de plan
        self.getChat = function (tipoPlan) {
            $scope.loading = true;
            if ($scope.userSession.planSelected != null) {
                var coach = $scope.userSession.planSelected.coachUserId.userId;

                var star = $scope.userSession.planSelected.starUserId.userId;

                MessageService.getMessages($scope.userSession.planSelected.id, $scope.userSession.userId, coach, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            data.output.forEach(function (m) {

                                $scope.messages.push(m);
                            });
                            $scope.loading = false;

                            self.readMessages();
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });

                MessageService.getMessages($scope.userSession.planSelected.id, $scope.userSession.userId, star, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            data.output.forEach(function (m) {

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
            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, "IN", $scope.roleSelected, function (data) {
                $scope.availableMessage = data;
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
                    $scope.getMessageCount();
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                }
            });
        };

        //Recibir Mensajes en tiempo real
        MessageService.receive().then(null, null, function (message) {
            if (message.id != "" && $scope.userSession != null && $scope.userSession.userId != message.messageUserId.userId) {
                MessageService.readMessage(message.id).then(
                        function (data) {
                            $scope.getReceived();
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });

                    if (message.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                        $scope.receivedMessageStar++;
                    } else if (message.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                        $scope.receivedMessageSup++;
                    }
             
            }
            $scope.messages.push(message);
        });

        //Traer la cantidad de mensajes disponibles
        self.getAvailableMessages = function (coachAssignedPlanId, userId, tipoPlan, roleSelected, fn) {
            MessageService.getAvailableMessages(coachAssignedPlanId, userId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        //Leer mensajes
        self.readMessages = function () {

            var planId = $scope.userSession.planSelected.id;
            var  userId = $scope.userSession.userId;
            var toUserId = $scope.userSession.planSelected.coachUserId.userId;
       
            MessageService.readMessages(planId, userId, toUserId, "IN", $scope.roleSelected).then(
                    function (data) {
                        $scope.getReceived();
                        console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getMessageCount = function () {
            var tipoPlan = "IN";

            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.availableMessageStar = data;
            });
            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.availableMessageSup = data;
            });

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
                $scope.availableMessageStar = $scope.userSession.planSelected.starCommunication.availableMsg;
                $scope.messageCountStar = $scope.userSession.planSelected.starCommunication.planMsg;
                $scope.receivedMessageStar = $scope.userSession.planSelected.starCommunication.receivedMsg;

                $scope.availableMessageSup = $scope.userSession.planSelected.asesorCommunication.availableMsg;
                $scope.messageCountSup = $scope.userSession.planSelected.asesorCommunication.planMsg;
                $scope.receivedMessageSup = $scope.userSession.planSelected.asesorCommunication.receivedMsg;

            } else {
                $scope.setUserSession();
            }
        };

        self.init();


    }]);