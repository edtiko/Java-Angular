trainingApp.service("messageService", ['$q', '$timeout', '$http','$window', function ($q, $timeout, $http,$window) {

        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        }, messageIds = [];
        
         var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/chat";
        service.CHAT_TOPIC = "/topic/message";
        service.CHAT_BROKER = "/app/chat/";

        service.receive = function () {
            return listener.promise;
        };

        service.send = function (message) {
            var id = Math.floor(Math.random() * 1000000);
            socket.stomp.send(service.CHAT_BROKER+message.athleteUserId, {
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
        	  
        var reconnect = function () {
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getMessage = function (data) {
            var message = JSON.parse(data), out = {};
            out.message = message.message;
            out.time = new Date(message.time);
            if (_.contains(messageIds, message.id)) {
                out.self = true;
                messageIds = _.remove(messageIds, message.id);
            }
            return out;
        };

        var startListener = function () {
            socket.stomp.subscribe("/queue/message/"+user.userId, function (data) {
                listener.notify(getMessage(data.body));
            });
        };

        var initialize = function () {
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.connect({}, startListener);
            socket.stomp.onclose = reconnect;
        };

        initialize();
        return service;
    }]);