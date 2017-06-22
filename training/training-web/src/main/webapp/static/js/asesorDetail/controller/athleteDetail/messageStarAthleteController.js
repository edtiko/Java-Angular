trainingApp.controller("MessageStarAthleteController", ['$scope', 'MessageService', '$window', function ($scope, MessageService, $window) {
        $scope.messagesStarAthlete = [];
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
        $scope.msgStarAthleteEnabled = false;
        $scope.roleSelected = $scope.userSessionTypeUserCoachEstrella;

        //Carga datos del chat seg�n el tipo de plan
        self.getChat = function (tipoPlan) {
            $scope.loading = true;
            if ($scope.planSelected != null) {
                MessageService.getMessages($scope.planSelected.id, $scope.userSession.userId, $scope.planSelected.athleteUserId.userId, tipoPlan, $scope.roleSelected).then(
                        function (data) {
                            $scope.messagesStarAthlete = data.output;
                            $scope.messagesStarAthlete.forEach(function(v){
                                v.creationDate = new Date(v.creationDate);
                            });
                            $scope.loading = false;
                            $scope.msgStarAthleteEnabled = true;
                            self.readMessages(tipoPlan, $scope.roleSelected, $scope.planSelected.athleteUserId.userId, $scope.userSession.userId);
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
            self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId,$scope.planSelected.athleteUserId.userId, "IN", $scope.roleSelected, function (data) {
                 $scope.availableMessage = data.output;
                if ($scope.userSession != null && $scope.planSelected != null && $scope.availableMessage > 0 && $scope.planMessage.message != "") {
                    $scope.planMessage.coachAssignedPlanId.id = $scope.planSelected.id;
                    $scope.planMessage.coachAssignedPlanId.athleteUserId.userId = $scope.planSelected.athleteUserId.userId;
                    $scope.planMessage.coachAssignedPlanId.coachUserId.userId = $scope.planSelected.coachUserId.userId;
                    $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                    $scope.planMessage.roleSelected = $scope.roleSelected;
                    $scope.planMessage.receivingUserId.userId = $scope.planSelected.athleteUserId.userId;
                    MessageService.send($scope.planMessage);
                    //$scope.wsocket.send(JSON.stringify($scope.planMessage));

                    $scope.planMessage.message = "";
                } else if ($scope.availableMessage == 0) {
                    $scope.showMessage("Ya consumi\u00f3 el limite de mensajes permitidos para su plan");
                }
            });
        };

        //Recibir Mensajes en tiempo real
        MessageService.receive().then(null, null, function (message) {
            if ($scope.msgStarAthleteEnabled) {
                if ($scope.userSession.userId == message.receivingUserId.userId) {
                    MessageService.readMessage(message.id).then(
                            function (data) {
                                $scope.getUserNotification($scope.userSession.userId, $scope.planSelected.id, "IN");
                            },
                            function (error) {
                                //$scope.showMessage(error);
                                console.error(error);
                            });

                }
                $scope.messagesStarAthlete.push(message);
                self.getMessageCount();
            }
        });

        //Traer la cantidad de mensajes disponibles
        self.getAvailableMessages = function (coachAssignedPlanId, userId, toUserId,tipoPlan, roleSelected, fn) {
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
                         $scope.getUserNotification($scope.userSession.userId, $scope.planSelected.id, "IN");
                        //console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        self.getMessageCount = function () {
            var tipoPlan = "IN";
             self.getAvailableMessages($scope.planSelected.id, $scope.userSession.userId, $scope.planSelected.athleteUserId.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella, function(data){
                 $scope.availableMessageStar = data.output;
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
                $scope.setUserSession();
            }
        };

        $scope.getMessagesByRole = function (role) {
            $scope.messagesStarAthlete = [];
            $scope.roleSelected = role;
            $scope.userMsgSelected = $scope.planSelected.athleteUserId.fullName;
            self.getChat("IN");
            self.getMessageCount();

        };


            self.init();
            
        $scope.$on('$destroy', function () {
            $scope.msgStarAthleteEnabled = false;
        });
        

    }]);