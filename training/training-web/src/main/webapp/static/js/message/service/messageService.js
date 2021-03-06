trainingApp.service("messageService", ['$q', '$timeout', '$http', '$window', function ($q, $timeout, $http, $window) {

        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        }, messageIds = [];


        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/chat";
        service.CHAT_TOPIC = "/topic/message";
        service.CHAT_BROKER = "/app/chat/";
        service.SESSION_ID = "";

        service.receive = function () {
            return listener.promise;
        };

        service.send = function (message) {
            var id = Math.floor(Math.random() * 1000000);
            var url = service.CHAT_BROKER + message.coachAssignedPlanId.id;
            socket.stomp.send(url, {
                priority: 9
            }, JSON.stringify(message));
            messageIds.push(id);
        };
        service.getAssignedAthletes = function (coachUserId) {
            return $http.get($contextPath + 'get/athtletes/' + coachUserId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching athletes');
                                return $q.reject(errResponse);
                            }
                    );
        };
        service.getAssignedCoach = function (athleteUserId) {
            return $http.get($contextPath + 'get/coach/' + athleteUserId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching coach');
                                return $q.reject(errResponse);
                            }
                    );
        };
        service.getMessages = function (coachAssignedPlanId) {
            return $http.get($contextPath + '/get/messages/' + coachAssignedPlanId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching messages');
                                return $q.reject(errResponse);
                            }
                    );
        };
        service.getMessagesByReceivingUserSendingUser = function (receivingUser,sendingUser) {
            return $http.get($contextPath + '/get/messages/by/receivingUser/sendingUser/' + receivingUser +'/'+sendingUser)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching messages');
                                return $q.reject(errResponse);
                            }
                    );
        };
        service.getAvailableMessages = function (coachAssignedPlanId, userId) {
            return $http.get($contextPath + 'get/count/available/messages/' + coachAssignedPlanId + '/' + userId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while available messages');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
         service.getMessagesReceived = function (coachAssignedPlanId, userId) {
            return $http.get($contextPath + 'get/count/received/messages/' + coachAssignedPlanId + '/' + userId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while available messages');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
         service.readMessages = function (coachAssignedPlanId, userId) {
            return $http.get($contextPath + 'read/messages/' + coachAssignedPlanId + '/' + userId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while reading messages');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
         service.readMessage = function (planMessageId) {
            return $http.get($contextPath + 'read/message/' + planMessageId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while reading message');
                                return $q.reject(errResponse);
                            }
                    );
        };

        var reconnect = function () {
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getMessage = function (data) {
            var message = JSON.parse(data);

            return message;
        };

        var startListener = function () {
            if (service.SESSION_ID != null) {
                socket.stomp.subscribe("/queue/message/" + service.SESSION_ID, function (data) {
                    listener.notify(getMessage(data.body));
                });
            }
        };
        service.initialize = function (sessionId) {
            if (service.SESSION_ID == "") {
                service.SESSION_ID = sessionId;
                socket.client = new SockJS(service.SOCKET_URL);
                socket.stomp = Stomp.over(socket.client);
                socket.stomp.connect({}, startListener);
                socket.stomp.onclose = reconnect;
            }
        };

        //initialize();
        return service;
    }]);