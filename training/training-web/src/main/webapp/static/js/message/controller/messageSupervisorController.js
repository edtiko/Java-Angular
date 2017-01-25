trainingApp.controller("MessageSupervisorController", ['$scope', 'messageService', '$window', function ($scope, messageService) {
        $scope.messages = [];
        $scope.message = "";
        $scope.max = 140;
        
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

                            
                            self.readMessages($scope.userSession.planSelected, tipoPlan, $scope.roleSelected, $scope.userSession.planSelected.athleteUserId.userId, $scope.userSession.userId);
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }
            
        };
        

       //Envia mensaje
        $scope.addMessage = function () {
            self.sendMessage();
        };


        //Envia mensaje para planes Coach Interno
        self.sendMessage = function () {
            self.getAvailableMessages($scope.userSession.planSelected.id, $scope.userSession.userId, "IN", $scope.roleSelected, function () {
                if ($scope.userSession != null && $scope.userSession.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.userSession.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.userSession.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.userSession.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    $scope.planMessage.receivingUserId.userId = $scope.userSession.planSelected.athleteUserId.userId;
           
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

                $scope.receivedMessageCount++;
            }
            $scope.messages.push(message);
        });
        
        //Traer la cantidad de mensajes disponibles
        self.getAvailableMessages = function (coachAssignedPlanId, userId, tipoPlan, roleSelected, fn) {
            var res = null;
            messageService.getAvailableMessages(coachAssignedPlanId, userId, tipoPlan, roleSelected).then(
                    function (data) {
                        res = data.entity.output;
                        $scope.availableMessage = res;
                        if (fn != null) {
                            fn();
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
                    return res;
        };
        
        //Leer mensajes
        self.readMessages = function (planSelected, tipoPlan, roleSelected, fromUserId, toUserId) {

            messageService.readMessages(planSelected.id, fromUserId, toUserId, tipoPlan, roleSelected).then(
                    function (data) {
                        //$scope.getMessageCount();
                        console.log(data.entity.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
 
        self.init = function () {
            if ($scope.userSession != null) {

                self.getChat("IN");

            } else {
                $scope.showMessage("El usuario no se encuentra logueado");
                $scope.logout();
            }
        };

            self.init();
        
        
    }]);