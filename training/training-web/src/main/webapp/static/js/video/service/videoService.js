trainingApp.service("VideoService", ['$http', '$q', function ($http, $q) {
        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        };

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/send";
        service.CHAT_BROKER = "/app/send";
        service.SESSION_ID = "";
        service.sessionsId = [];

        service.receive = function () {
            return listener.promise;
        };

        service.send = function (message) {
            var url = service.CHAT_BROKER;
            socket.stomp.send(url, {
                priority: 9
            }, JSON.stringify(message));
        };

        var reconnect = function () {
            $timeout(function () {
                service.initialize();

            }, this.RECONNECT_TIMEOUT);
        };

        var getVideo = function (data) {
            var video = JSON.parse(data);

            return video;
        };

        var startListener = function () {

            socket.stomp.subscribe("/queue/video", function (data) {
                listener.notify(getVideo(data.body));
            });

        };
        service.initialize = function () {
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.connect({}, startListener);
            socket.stomp.onclose = reconnect;

        };
        service.getVideosByUser = function (planId, userId, toUserId, fromto, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'video/get/videos/' + planId + "/" + userId + "/" + toUserId + '/' + fromto + "/" + tipoPlan + '/' + roleSelected)
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

        service.getAvailableVideos = function (coachAssignedPlanId, userId, toUserId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'video/get/count/available/' + coachAssignedPlanId + '/' + userId + '/' + toUserId + '/' + tipoPlan + '/' + roleSelected)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while available videos');
                                return $q.reject(errResponse);
                            }
                    );
        };

        service.getVideosReceived = function (coachAssignedPlanId, fromUserId, toUserId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'video/get/count/received/' + coachAssignedPlanId + '/' + fromUserId + '/' + toUserId + '/' + tipoPlan + '/' + roleSelected)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while received videos');
                                return $q.reject(errResponse);
                            }
                    );
        };

        service.readVideos = function (coachAssignedPlanId, userId, tipoPlan) {
            return $http.get($contextPath + 'video/read/all/' + coachAssignedPlanId + '/' + userId + '/' + tipoPlan)
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

        service.readVideo = function (planVideoId) {
            return $http.get($contextPath + 'video/read/' + planVideoId)
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

        service.rejectVideo = function (planVideoId) {
            return $http.get($contextPath + 'video/rejected/atlethe/' + planVideoId)
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

        service.approveVideo = function (userId, planVideoId, guion) {
            return $http.get($contextPath + 'video/approve/' + planVideoId + '/' + userId + '?guion=' + guion)
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

        service.sendVideoToAtlethe = function (coachUserId, athleteUserId, planVideoId) {
            return $http.get($contextPath + 'video/send/video/to/atlethe/' + planVideoId + '/' + coachUserId + '/' + athleteUserId)
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

        return service;

    }]);