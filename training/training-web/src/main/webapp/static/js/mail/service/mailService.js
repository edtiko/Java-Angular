'use strict';
trainingApp.service('MailService', ['$http', '$q', function ($http, $q) {

        var service = {}, listener = $q.defer(), socket = {
            client: null,
            stomp: null
        };

        service.RECONNECT_TIMEOUT = 30000;
        service.SOCKET_URL = $contextPath + "/mail";
        service.CHAT_BROKER = "/app/mail/";
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

        var getMail = function (data) {
            var mail = JSON.parse(data);

            return mail;
        };

        var startListener = function () {
            if (service.SESSION_ID != null) {
                socket.stomp.subscribe("/queue/mail/" + service.SESSION_ID, function (data) {
                    listener.notify(getMail(data.body));
                }, { id: service.SESSION_ID });
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
        
          service.getAvailableMails = function (planId, userId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'mail/get/count/available/' + planId + '/' + userId+'/'+tipoPlan+'/'+roleSelected)
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
        
         service.getReceivedMails = function (planId, userId,toUserId, tipoPlan, roleSelected) {
            return $http.get($contextPath + 'mail/get/count/received/' + planId + '/' + userId+'/'+toUserId+'/'+tipoPlan+'/'+roleSelected)
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

        service.getMails = function (userId) {
            return $http.get($contextPath + '/get/mails/by/user/' + userId)
                    .then(
                            function (response) {
                                return response.data;
                            },
                            function (errResponse) {
                                console.error('Error while fetching mails');
                                return $q.reject(errResponse);
                            }
                    );
        },
                service.getSentMails = function (userId) {
                    return $http.get($contextPath + '/get/sent/mails/by/user/' + userId)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching mails');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.getAllRecipientsByCoach = function (userId) {
                    return $http.get($contextPath + '/get/all/recipients/by/coach/' + userId)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching mails');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.getAllRecipientsByStar = function (userId) {
                    return $http.get($contextPath + '/get/all/recipients/by/star/' + userId)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching mails');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.getMailsByReceivingUserFromSendingUser = function (receivingUserId, sendingUserId) {
                    return $http.get($contextPath + '/get/mails/by/receivingUser/from/sendingUser/' + receivingUserId + '/' + sendingUserId)
                            .then(
                                    function (response) {
                                        return response.data.output;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching mails');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                  service.getMailsByPlan = function (tipoPlan, sendingUserId, receivingUserId, planId, roleSelected, fromto) {
                    return $http.get($contextPath + '/get/mails/by/plan/' + tipoPlan + '/' + sendingUserId +'/'+ receivingUserId+'/' +planId+'/'+roleSelected+'/'+fromto)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while fetching mails by plan');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.createMailCommunication = function (mail) {
                    return $http.post($contextPath + '/mailCommunication/create', mail)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while creating mail communication');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                service.readEmail = function (id) {
                    return $http.get($contextPath + '/mailCommunication/read/'+id)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while updating mail communication');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                
                service.resendEmail  = function (id, planId) {
                    return $http.get($contextPath + '/mailCommunication/resend/'+id+'/'+planId)
                            .then(
                                    function (response) {
                                        return response.data;
                                    },
                                    function (errResponse) {
                                        console.error('Error while resend mail communication');
                                        return $q.reject(errResponse);
                                    }
                            );
                },
                
                   service.getUsersMails = function (userId){ 
                  return $http.get($contextPath + 'get/user/mails/'+userId)
                    .then(
                            function (response) {
                                return response.data.output;
                            },
                            function (errResponse) {
                                console.error('Error while get/user/mails/');
                                return $q.reject(errResponse);
                            }
                    );
                  };

        return service;

    }]);