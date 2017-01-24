trainingApp.service("ExternalCoachService", ['$http', '$q', function ($http, $q) {

        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        };

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/invitation";
        service.CHAT_BROKER = "/app/invitation/";
        service.SESSION_ID = "";

        service.receive = function () {
            return listener.promise;
        };

        service.send = function (mail) {
            var url = service.CHAT_BROKER + mail.sesionId;
            socket.stomp.send(url, {
                priority: 9
            }, JSON.stringify({id: mail.id}));
        };

        var reconnect = function () {
            $timeout(function () {
                initialize();
            }, this.RECONNECT_TIMEOUT);
        };

        var getInvitation = function (data) {
            var invitation = JSON.parse(data);

            return invitation;
        };

        var startListener = function () {
            if (service.SESSION_ID != null) {
                socket.stomp.subscribe("/queue/invitation/" + service.SESSION_ID, function (data) {
                    listener.notify(getInvitation(data.body));
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

        service.createAthlete = function (obj) {

            return $http.post($contextPath + 'externalCoach/create/athlete', obj)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while creating user');
                                return $q.reject(errResponse);
                            }
                    );
        },
                service.fetchAthletes = function (trainingPlanUserId, state) {
                    return $http.get($contextPath + 'externalCoach/get/athletes/' + trainingPlanUserId + "/" + state)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching athletes');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.removeAthleteCoach = function (coachExtAthleteId) {
                    return $http.get($contextPath + 'externalCoach/delete/athlete/' + coachExtAthleteId)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while deleting athlete');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.loadAthletes = function (query) {
                    return $http.get($contextPath + 'externalCoach/get/user/athletes/' + query)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching athletes');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.sendInvitation = function (coachExtAthlete) {
                    return $http.post($contextPath + 'externalCoach/sendInvitation', coachExtAthlete)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (error) {
                                        console.error('Error while creating user');
                                        return $q.reject(error);
                                    }
                            );
                },
                service.getInvitation = function (userId) {
                    return $http.get($contextPath + 'externalCoach/get/invitation/' + userId)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching invitation');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.acceptInvitation = function (id) {
                    return $http.get($contextPath + 'externalCoach/accept/invitation/' + id)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching invitation');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.rejectInvitation = function (id) {
                    return $http.get($contextPath + 'externalCoach/reject/invitation/' + id)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching invitation');
                                        return $q.reject(errResponse);
                                    }
                            );
                };

        return service;

    }]);