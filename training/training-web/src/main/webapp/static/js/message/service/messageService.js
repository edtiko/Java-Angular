trainingApp.service("MessageService", ['$q', '$timeout', '$http', '$window', function ($q, $timeout, $http, $window) {

        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        };


        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/chat";
        service.CHAT_TOPIC = "/topic/message";
        service.CHAT_BROKER = "/app/chat";
        service.SESSION_ID = "";
        service.WS_MOVIL;
        service.sessionIds = [];

        service.receive = function () {
            return listener.promise;
        };
        
        service.send = function (message) {
            //service.sendMovil(JSON.stringify(message));
     
            var url = service.CHAT_BROKER;
            socket.stomp.send(url, {
                priority: 9
            }, JSON.stringify(message));
         
        };

        service.getMessages = function (planId, userSessionId, receivingUserId, tipoPlan, roleSelected ) {
            return $http.get($contextPath + '/get/messages/' + planId+'/'+tipoPlan+'/'+roleSelected+'/'+userSessionId+'/'+receivingUserId)
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
                                return response.data.output;
                            },
                            function (errResponse) {
                                console.error('Error while fetching messages');
                                return $q.reject(errResponse);
                            }
                    );
        };

        service.getAvailableMessages = function (planId, userId, toUserId, tipoPlan,roleSelected) {
            return $http.get($contextPath + 'get/count/available/messages/' + planId + '/' + userId+'/'+toUserId+'/'+tipoPlan+'/'+roleSelected)
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
        
         service.getMessagesReceived = function (planId, userId, toUserId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'get/count/received/messages/' + planId + '/' + userId+'/'+toUserId+'/'+tipoPlan+'/'+roleSelected)
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
        
         service.readMessages = function (planId, userId, toUserId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'read/messages/' + planId + '/' + userId+'/'+toUserId+'/'+tipoPlan+'/'+roleSelected)
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
        
        service.setStarManageMessage = function (planId) {
            return $http.get($contextPath + 'set/star/manage/message/'+planId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while set/star/manage/message/');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
        service.resendMessageStar = function (planId, starMessages){
                  return $http.post($contextPath + 'resend/star/messages/'+planId,JSON.stringify({starMessages: starMessages}))
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while resend/star/messages');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
        service.getUsersMessages = function (userId){ 
                  return $http.get($contextPath + 'get/user/messages/'+userId)
                    .then(
                            function (response) {
                                return response.data.output;
                            },
                            function (errResponse) {
                                console.error('Error while get/user/messages');
                                return $q.reject(errResponse);
                            }
                    );
        };

        var reconnect = function () {
            $timeout(function () {
                
                    service.initialize();
                
            }, this.RECONNECT_TIMEOUT);
        };

        var getMessage = function (data) {
            var message = JSON.parse(data);

            return message;
        };

        var startListener = function () {
                socket.stomp.subscribe("/queue/message", function (data) {
                    listener.notify(getMessage(data.body));
                });
            
        };
        
        service.initialize = function () {
           // if (service.sessionIds.indexOf(sessionId) == -1) { 
             //   service.sessionIds.push(sessionId);
               // service.SESSION_ID = sessionId;
                socket.client = new SockJS(service.SOCKET_URL);
                socket.stomp = Stomp.over(socket.client);
                socket.stomp.connect({}, startListener);
                socket.stomp.onclose = reconnect;

            //}
        };

        //initialize();
        return service;
    }]);