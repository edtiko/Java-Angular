trainingApp.controller("MessageCoachController", ['$scope', 'MessageService', '$window', function ($scope, MessageService, $window) {
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
        $scope.roleSelected = -1;
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.dataImage = "static/img/profile-default.png";
        $scope.glued = true;
        $scope.msgStar = [];
        $scope.items = [];

        //Carga datos del chat según el tipo de plan
        self.getChat = function (tipoPlan) {
            $scope.loading = true;
            if ($scope.planSelected != null) {
                MessageService.getMessages($scope.planSelected.id, $scope.userSession.userId, $scope.planSelected.athleteUserId.userId ,tipoPlan, -1).then(
                        function (data) {
                            $scope.messages = data.output;
                            $scope.loading = false;

                            self.readMessages(tipoPlan, -1, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId);
 
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }

        };





        //Envia mensaje para planes Coach Interno
        $scope.sendMessage = function () {
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, "EXT", $scope.roleSelected, function (data) {
                $scope.availableMessage = data;
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachExtAthleteId.id = $scope.planSelected.id;
                    $scope.planMessage.coachExtAthleteId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachExtAthleteId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    $scope.planMessage.roleSelected = $scope.roleSelected;
                    $scope.planMessage.receivingUserId.userId = $scope.planSelected.athleteUserId.userId;


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
                            // $scope.getReceived();
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });

            }
            $scope.messages.push(message);
        });

        //Traer la cantidad de mensajes disponibles
        self.getAvailableMessages = function (coachExtAthleteId, userId, tipoPlan, roleSelected, fn) {
            MessageService.getAvailableMessages(coachExtAthleteId, userId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        console.error(error);
                    });
        };

        //Leer mensajes
        self.readMessages = function (tipoPlan, roleSelected, fromUserId, toUserId) {
            MessageService.readMessages($scope.planSelected.id, fromUserId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        //$scope.getReceived();
                        console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getMessageCount = function () {
            var tipoPlan = "EXT";
 
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected, function (data) {
                $scope.availableMessage = data;
            });

        };

        self.init = function () {
            if ($scope.userSession != null) {
                $scope.availableMessage = $scope.planSelected.coachCommunication.availableMsg;
                $scope.messageCount = $scope.planSelected.coachCommunication.planMsg;
                $scope.receivedMessage = $scope.planSelected.coachCommunication.receivedMsg;
                self.getChat("EXT");

            } else {
                $scope.setUserSession();
            }
        };


        self.init();


    }]);