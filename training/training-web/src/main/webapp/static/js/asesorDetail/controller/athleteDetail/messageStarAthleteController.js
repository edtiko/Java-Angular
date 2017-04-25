trainingApp.controller("MessageStarAthleteController", ['$scope', 'MessageService', '$window', function ($scope, MessageService, $window) {
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
        $scope.starImage = $window.sessionStorage.getItem("starImage");
        $scope.asesorImage = $window.sessionStorage.getItem("asesorImage");

        //Carga datos del chat según el tipo de plan
        self.getChat = function (tipoPlan) {
            $scope.loading = true;
            if ($scope.planSelected != null) {
                MessageService.getMessages($scope.planSelected.id, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            $scope.messages = data.output;
                            $scope.loading = false;

                            self.readMessages(tipoPlan, $scope.roleSelected, $scope.userSession.userId, $scope.planSelected.coachUserId.userId);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }

        };
        
        $scope.setStarManageMessage = function () {
            MessageService.setStarManageMessage($scope.planSelected.id).then(
                    function (data) {

                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };


        //Envia mensaje
        $scope.addMessage = function () {

            self.sendMessageIn();

        };



        //Envia mensaje para planes Coach Interno
        self.sendMessageIn = function () {
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, "IN", $scope.roleSelected, function (data) {
                 $scope.availableMessage = data;
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    $scope.planMessage.roleSelected = $scope.roleSelected;
                    $scope.planMessage.receivingUserId.userId = $scope.planSelected.coachUserId.userId;
             

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
        self.getAvailableMessages = function (coachAssignedPlanId, userId, tipoPlan, roleSelected, fn) {
            MessageService.getAvailableMessages(coachAssignedPlanId, userId, tipoPlan, roleSelected).then(
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
            var tipoPlan = "IN";
             self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function(data){
                 $scope.availableMessageStar = data;
             });
             
        };

        self.init = function () {
            if ($scope.userSession != null) {
                $scope.getMessagesByRole($scope.userSessionTypeUserCoachEstrella);
                $scope.availableMessageStar = $scope.planSelected.starCommunication.availableMsg;
                $scope.messageCountStar = $scope.planSelected.starCommunication.planMsg;
                $scope.receivedMessageStar = $scope.planSelected.starCommunication.receivedMsg;
                self.getChat("IN");

            } else {
                $scope.showMessage("El usuario no se encuentra logueado");
                $scope.logout();
            }
        };

        $scope.getMessagesByRole = function (role) {
            $scope.messages = [];
            $scope.roleSelected = role;
            $scope.userMsgSelected = $scope.planSelected.athleteUserId.fullName;
            self.getChat("IN");

        };


            self.init();
        

    }]);