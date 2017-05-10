trainingApp.controller("MessageStarController", ['$scope', 'MessageService', '$window', function ($scope, MessageService, $window) {
        $scope.messages = [];
        $scope.message = "";
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planMessage = {
            coachAssignedPlanId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            coachExtAthleteId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            message: '',
            messageUserId: {userId: ''},
            receivingUserId: {userId: ''},
            roleSelected: -1,
            mobile: false
        };

        var self = this;
        $scope.dataImage = "static/img/profile-default.png";
        $scope.glued = true;
        $scope.asesorImage = $window.sessionStorage.getItem("asesorImage");


        //Envia mensaje
        $scope.sendMessage = function () {
            if ($scope.userSession != null && $scope.planMessage.message != "") {
                $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                $scope.planMessage.receivingUserId.userId = $scope.asesorUserId;
                MessageService.send($scope.planMessage);
                $scope.planMessage.message = "";
            }
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
            $scope.messagesStar.push(message);
        });

        //Leer mensajes
        self.readMessages = function (fromUserId, toUserId) {
            MessageService.readMessages(-1, fromUserId, toUserId, -1, -1).then(
                    function (data) {
                        //$scope.getReceived();
                        console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getMessagesByUser = function (userId) {
            $scope.loading = true;
            MessageService.getMessagesByReceivingUserSendingUser($scope.userSession.userId, userId).then(
                    function (data) {
                        $scope.messagesStar = data;
                        $scope.loading = false;
                        self.readMessages(userId, $scope.userSession.userId); 
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        self.init = function () {
            if ($scope.userSession != null) {
                $scope.getMessagesByUser($scope.asesorUserId);

            } else {
                $scope.setUserSession();
            }
        };



        self.init();


    }]);