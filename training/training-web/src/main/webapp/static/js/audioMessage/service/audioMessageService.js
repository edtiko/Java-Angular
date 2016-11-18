'use strict';
trainingApp.service("AudioMessageService", ['$http', '$q', function ($http, $q) {
 var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        };
        
        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/voice";
        service.CHAT_BROKER = "/app/voice/";
        service.SESSION_ID = "";

        service.receive = function () {
            return listener.promise;
        };

        service.send = function (audio) {
            var url = service.CHAT_BROKER + audio.sesionId;
            socket.stomp.send(url, {
                priority: 9
            }, JSON.stringify({id:audio.id}));
        };
        
           var reconnect = function () {
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getAudio = function (data) {
            var video = JSON.parse(data);

            return video;
        };

        var startListener = function () {
            if (service.SESSION_ID != null) {
                socket.stomp.subscribe("/queue/audio/" + service.SESSION_ID, function (data) {
                    listener.notify(getAudio(data.body));
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
        service.getAudiosByUser = function (planId, userId, fromto, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'audio/get/audios/'+planId +"/"+ userId + "/" + fromto+"/"+tipoPlan+'/'+roleSelected)
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
        
          service.uploadRecord = function (blob) {
            return $http.post($contextPath + 'audio/upload/',blob)
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

        service.getAudioByPath = function (audioPath) {
            return $http.get($contextPath + 'audio/files/' + audioPath)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching audio data ');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
        service.getAvailableAudios = function (planId, userId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'audio/get/count/available/' + planId + '/' + userId+'/'+tipoPlan+'/'+roleSelected)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while available audios');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
         service.getAudiosReceived = function (planId, userId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'audio/get/count/received/' + planId + '/' + userId+'/'+tipoPlan+'/'+roleSelected)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while received audios');
                                return $q.reject(errResponse);
                            }
                    );
        };
        
         service.readAudios = function (planId, userId,tipoPlan) {
            return $http.get($contextPath + 'audio/read/all/' + planId + '/' + userId+'/'+tipoPlan)
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
        
         service.readAudio = function (planVideoId) {
            return $http.get($contextPath + 'audio/read/' + planVideoId)
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

        return service;

    }]);