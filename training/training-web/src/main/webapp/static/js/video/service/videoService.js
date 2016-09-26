trainingApp.service("videoService", ['$http', '$q', function ($http, $q) {
        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        };
        
        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/send";
        service.CHAT_BROKER = "/app/send/";
        service.SESSION_ID = "";

        service.receive = function () {
            return listener.promise;
        };

        service.send = function (message) {
            var url = service.CHAT_BROKER + message.sesionId;
            socket.stomp.send(url, {
                priority: 9
            }, JSON.stringify({id:message.id}));
        };
        
           var reconnect = function () {
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getVideo = function (data) {
            var video = JSON.parse(data);

            return video;
        };

        var startListener = function () {
            if (service.SESSION_ID != null) {
                socket.stomp.subscribe("/queue/video/" + service.SESSION_ID, function (data) {
                    listener.notify(getVideo(data.body));
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
        service.getVideosByUser = function (userId, fromto) {
            return $http.get($contextPath + 'video/get/videos/' + userId + "/" + fromto)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching user videos ');
                                return $q.reject(errResponse);
                            }
                    );
        };

        service.getVideoByPath = function (videoPath) {
            return $http.get($contextPath + 'video/files/' + videoPath)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching video data ');
                                return $q.reject(errResponse);
                            }
                    );
        };

        return service;

    }]);