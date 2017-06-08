trainingApp.controller("MessageAsesorController", ['$scope', 'MessageService', '$window', function ($scope, MessageService, $window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planMessage = {
            coachAssignedPlanId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            coachExtAthleteId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            message: '',
            messageUserId: {userId: ''},
            receivingUserId: {userId: ''},
            roleSelected: '',
            mobile: false
        };

        var self = this;
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.dataImage = "static/img/profile-default.png";
        $scope.glued = true;
        //$scope.starImage = $window.sessionStorage.getItem("starImage");
        //$scope.asesorImage = $window.sessionStorage.getItem("asesorImage");
        $scope.msgStar = [];
        $scope.items = [];
        $scope.msgAsesorEnabled = false;

        //Carga datos del chat según el tipo de plan
        self.getChat = function (tipoPlan) {
            $scope.loading = true;
            if ($scope.planSelected != null) {
                MessageService.getMessages($scope.planSelected.id, $scope.userSession.userId, $scope.planSelected.athleteUserId.userId, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            $scope.messages = data.output;
                            $scope.loading = false;
                            $scope.msgAsesorEnabled = true;
                            self.readMessages(tipoPlan, $scope.roleSelected, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId);

                            $scope.messages.filter(function (m) {
                                return  $scope.userSession.userId != m.messageUserId.userId
                            }).forEach(function (v) {
                                if ($scope.items.indexOf(v.id) === -1)
                                    $scope.items.push(v.id);

                            });
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }

        };


        //Envia mensaje
        $scope.addMessage = function () {

            self.sendMessageIn();

        };



        //Envia mensaje para planes Coach Interno
        self.sendMessageIn = function () {
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, $scope.planSelected.athleteUserId.userId, "IN", $scope.roleSelected, function (data) {
                $scope.availableMessage = data.output;
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    $scope.planMessage.roleSelected = $scope.roleSelected;
                    $scope.planMessage.receivingUserId.userId = $scope.planSelected.athleteUserId.userId;


                    MessageService.send($scope.planMessage);
                    $scope.wsocket.send(JSON.stringify($scope.planMessage));

                    $scope.planMessage.message = "";
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                    self.getMessageCount();
                }
            });
        };

        //Recibir Mensajes en tiempo real
        MessageService.receive().then(null, null, function (message) {
            if ($scope.msgAsesorEnabled) {
                if ($scope.userSession.userId == message.receivingUserId.userId) {
                    MessageService.readMessage(message.id).then(
                            function (data) {
                                // $scope.getReceived();
                            },
                            function (error) {
                                //$scope.showMessage(error);
                                console.error(error);
                            });

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
                        console.error(error);
                    });
        };

        //Leer mensajes
        self.readMessages = function (tipoPlan, roleSelected, fromUserId, toUserId) {
            MessageService.readMessages($scope.planSelected.id, fromUserId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        self.getMessageCount();
                        console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.getMessageCount = function () {
            var tipoPlan = "IN";
            var athleteUserId = $scope.planSelected.athleteUserId.userId;
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, athleteUserId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function (data) {
                $scope.availableMessageStar = data.output;
            });
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, athleteUserId, tipoPlan, $scope.userSessionTypeUserCoachInterno, function (data) {
                $scope.availableMessageSup = data.output;
            });
            $scope.getReceivedMessages($scope.planSelected.id, athleteUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.receivedMessageStar = data.output;
                    });

            $scope.getReceivedMessages($scope.planSelected.id, athleteUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.receivedMessageSup = data.output;
                    });
            $scope.getReceivedAthleteAsesor(athleteUserId, $scope.planSelected.id);
        };

        self.init = function () {
            if ($scope.userSession != null) {
                $scope.getMessagesByRole($scope.userSessionTypeUserCoachEstrella);
                $scope.messageCountStar = $scope.planSelected.starCommunication.planMsg;
                $scope.messageCountSup = $scope.planSelected.asesorCommunication.planMsg;
                self.getMessageCount();

            } else {
                $scope.setUserSession();
            }
        };

        $scope.resendStar = function () {
            MessageService.resendMessageStar($scope.planSelected.id, $scope.msgStar).then(
                    function (data) {
                        $scope.getMessagesByRole($scope.userSessionTypeUserCoachEstrella);
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };

        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            } else {
                list.push(item);
            }
        };

        $scope.toggleAll = function () {
            if ($scope.msgStar.length === $scope.items.length) {
                $scope.msgStar = [];
            } else if ($scope.msgStar.length === 0 || $scope.msgStar.length > 0) {
                $scope.msgStar = $scope.items;
            }
        };

        $scope.isChecked = function () {
            return $scope.msgStar.length === $scope.items.length;
        };

        $scope.getMessagesByRole = function (role) {
            $scope.messages = [];
            $scope.roleSelected = role;
            if (role == $scope.userSessionTypeUserCoachEstrella) {
                $scope.userMsgSelected = $scope.planSelected.starUserId.fullName;
   
            } else {
                $scope.userMsgSelected = $scope.planSelected.coachUserId.fullName;
            }

            self.getChat("IN");

        };


        self.init();

        $scope.$on('$destroy', function () {
            $scope.msgAsesorEnabled = false;
        });


    }]);