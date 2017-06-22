trainingApp.controller('StarAsesorController', ['$scope', 'DashboardService', 'MessageService', 'MailService', '$window', '$mdDialog',
    function ($scope, DashboardService, MessageService, MailService, $window, $mdDialog) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.moduleSelected = 1;
        $scope.stars = [];
        $scope.starsFiltered = [];
        $scope.letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
        $scope.userMessages = [];
        $scope.messagesAsesor = [];
        $scope.userMails = [];
        $scope.mailsAsesor = [];
        $scope.mailsReceived = [];
        $scope.mailsSent = [];
        $scope.messageReceivedStarAsesor = 0;
        $scope.mailReceivedStarAsesor = 0;
        $scope.msgStarAsesorEnabled = true;

        //$scope.filt = 'A';

        $scope.starAsesorView = {
            stars: 'static/views/starAsesor/stars/stars.html',
            chat: 'static/views/starAsesor/message/chat.html',
            mail: 'static/views/starAsesor/mail/mail.html'
        };
        $scope.starPageSelected = $scope.starAsesorView.stars;

        $scope.planMessage = {
            coachAssignedPlanId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            coachExtAthleteId: {id: '', athleteUserId: {userId: ''}, coachUserId: {userId: ''}},
            message: '',
            messageUserId: {userId: ''},
            receivingUserId: {userId: ''},
            roleSelected: -1,
            mobile: false
        };

        $scope.mailCommunication = {
            id: '',
            mailto: '',
            receivingUser: {userId: ''},
            sendingUser: {userId: ''},
            coachExtAthleteId: '',
            coachAssignedPlanId: '',
            message: '',
            subject: '',
            roleSelected: -1
        };

        $scope.viewsMail = {
            received: 'static/views/starAsesor/mail/received.html',
            sent: 'static/views/starAsesor/mail/sent.html',
            mailSelected: 'static/views/starAsesor/mail/mailSelected.html'
        };

        $scope.selectReceivedMail = function (id) {
            for (var i = 0; i < $scope.mailsReceived.length; i++) {
                if ($scope.mailsReceived[i].mailCommunicationId === id) {
                    $scope.mailSelected = angular.copy($scope.mailsReceived[i]);
                    if ($scope.mailSelected.read == false) {
                        self.readEmail(id);
                        $scope.mailsReceived[i].read = true;
                    }
                    $scope.received = true;
                    break;
                }
            }
            $scope.viewMailSelected = $scope.viewsMail.mailSelected;
        };

        $scope.selectSentMail = function (id) {
            for (var i = 0; i < $scope.mailsSent.length; i++) {
                if ($scope.mailsSent[i].mailCommunicationId === id) {
                    $scope.mailSelected = angular.copy($scope.mailsSent[i]);
                    $scope.received = false;
                    break;
                }
            }
            $scope.viewMailSelected = $scope.viewsMail.mailSelected;
        };

        $scope.setFilter = function (letter) {
            $scope.filt = letter;
        };

        $scope.startsWith = function (star) {
            var lowerStr = (star.fullName + "").toLowerCase();
            var letter = $scope.filt;
            if (letter != undefined) {
                return lowerStr.indexOf(letter.toLowerCase()) === 0;
            } else {
                $scope.starsFiltered = $scope.stars;
                return true;
            }
        };

        self.getStars = function () {
            DashboardService.getAssignedStarByCoach($scope.userSession.userId).then(
                    function (data) {
                        $scope.stars = data.output;
                        $scope.starsFiltered = data.output;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        self.getUsersMessagesByUser = function () {
            MessageService.getUsersMessages($scope.userSession.userId).then(
                    function (data) {
                        $scope.userMessages = data;
                        if(data.length == 1){
                            $scope.getMessagesByUser(data[0]);
                        }
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        self.getUsersMailsByUser = function () {
            MailService.getUsersMails($scope.userSession.userId).then(
                    function (data) {
                        $scope.userMails = data;
                        if(data.length == 1){
                            $scope.getEmailByUser(data[0]);
                        }
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        //Leer mensajes
        self.readMessages = function () {

            var planId = -1;
            var userId = $scope.userMsgSelected.userId;
            var toUserId = $scope.userSession.userId;

            MessageService.readMessages(planId, userId, toUserId, -1, -1).then(
                    function (data) {
                        self.getMessagesReceivedCount(userId, function (data) {
                            $scope.msgUserReceivedCount = data.output;
                        });
                         self.getMessagesReceivedCount(-1, function (data) {
                            $scope.messageReceivedStarAsesor = data.output;
                        });
                        $scope.getUserNotification($scope.userSession.userId, -1, -1);
                        //console.log(data.output);
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getMessagesByUser = function (user) {
            $scope.userMsgSelected = user;
            $scope.msgUserReceivedCount = user.msgReceivedCount;
            MessageService.getMessagesByReceivingUserSendingUser($scope.userSession.userId, user.userId).then(
                    function (data) {
                        $scope.messagesAsesor = data;
                        $scope.messagesAsesor.forEach(function(v){
                                v.creationDate = new Date(v.creationDate);
                            });
                        self.readMessages();
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        $scope.getEmailByUser = function (user) {
            $scope.userMsgSelected = user;
            $scope.mailCommunication.mailto = user.fullName;
            $scope.mailCommunication.receivingUser.userId = user.userId;
            MailService.getMailsByReceivingUserFromSendingUser($scope.userSession.userId, user.userId).then(
                    function (data) {
                        $scope.mailsReceived = data;
                    },
                    function (error) {
                        console.log(error);
                    }
            );

            MailService.getMailsByReceivingUserFromSendingUser(user.userId, $scope.userSession.userId).then(
                    function (data) {
                        $scope.mailsSent = data;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
          $scope.verRecibidos();
        };

        $scope.addMail = function () {
            if ($scope.userSession != null && $scope.mailCommunication.message != ""
                    && $scope.mailCommunication.subject != "") {

                $scope.mailCommunication.sendingUser.userId = $scope.userSession.userId;
                $scope.mailCommunication.receivingUser.userId = $scope.userMsgSelected.userId;

                $scope.createMailCommunication($scope.mailCommunication);
            }
        };


        $scope.createMailCommunication = function (mail) {
            MailService.createMailCommunication(mail)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.resetMailDialog();
                                    //$scope.getEmailCount();
                                } else {
                                    $scope.showMessage(d.output);
                                }

                                self.init();
                            },
                            function (errResponse) {
                                console.error('Error while creating mail communication.');
                            }
                    );
        };


        $scope.dialogEmail = function () {
            $scope.resetMailDialog();
            $mdDialog.show({
                controller: sendEmailController,
                scope: $scope.$new(),
                templateUrl: 'static/views/mail/mailTemplate.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen
            });
        };

        $scope.resetMailDialog = function () {
            $scope.mailCommunication.subject = '';
            $scope.mailCommunication.message = '';
        };


        function sendEmailController($scope, $mdDialog) {

            $scope.sendMail = function () {
                $scope.addMail();
            };

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };
        }

        $scope.verRecibidos = function () {
            $scope.recibidos = true;
            $scope.enviados = false;
            $scope.viewMailSelected = $scope.viewsMail.received;
        };


        $scope.verEnviados = function () {
            $scope.recibidos = false;
            $scope.enviados = true;
            $scope.viewMailSelected = $scope.viewsMail.sent;
        };

        self.readEmail = function (id) {

            MailService.readEmail(id)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    self.getCountStarAsesor();
               
                                } else {
                                    console.log(d.output);
                                }
                            },
                            function (error) {
                                console.error('Error while updating mail communication.' + error);
                            }
                    );
        };
        
        self.getCountStarAsesor = function () {
            $scope.getUserNotification($scope.userSession.userId, -1, -1);
            self.getMessagesReceivedCount(-1, function (data) {
                $scope.messageReceivedStarAsesor = data.output;
            });
            self.getMailReceivedCount(-1, function (data) {
                $scope.mailReceivedStarAsesor = data.output;
            });
        };

        $scope.sendMessage = function () {
            if ($scope.userSession != null && $scope.planMessage.message != "") {
                $scope.planMessage.messageUserId.userId = $scope.userSession.userId;
                $scope.planMessage.receivingUserId.userId = $scope.userMsgSelected.userId;
                MessageService.send($scope.planMessage);
                $scope.planMessage.message = "";
            }
        };

        $scope.ignoreAccents = function (item) {
            if (!$scope.search)
                return true;
            var text = removeAccents(item.fullName.toLowerCase());
            var search = removeAccents($scope.search.toLowerCase());
            return text.indexOf(search) > -1;
        };

        function removeAccents(source) {

            var accent = [
                /[\300-\306]/g, /[\340-\346]/g, // A, a
                /[\310-\313]/g, /[\350-\353]/g, // E, e
                /[\314-\317]/g, /[\354-\357]/g, // I, i
                /[\322-\330]/g, /[\362-\370]/g, // O, o
                /[\331-\334]/g, /[\371-\374]/g, // U, u
                /[\321]/g, /[\361]/g, // N, n
                /[\307]/g, /[\347]/g, // C, c
            ],
                    noaccent = ['A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'N', 'n', 'C', 'c'];

            for (var i = 0; i < accent.length; i++) {
                source = source.replace(accent[i], noaccent[i]);
            }

            return source;

        } // removeAccents


        $scope.goStarAsesorMenu = function (module, index) {

            $scope.moduleSelected = index;

            switch (module) {
                case 'stars':
                    $scope.starPageSelected = $scope.starAsesorView.stars;
                    break;
                case 'chat':
                    $scope.starPageSelected = $scope.starAsesorView.chat;
                    break;
                case 'mail':
                    $scope.starPageSelected = $scope.starAsesorView.mail;
                    break;

            }

        };

        MessageService.receive().then(null, null, function (message) {
            if ($scope.msgStarAsesorEnabled) {
                if ($scope.userSession.userId == message.receivingUserId.userId) {
                    $scope.messageReceivedStarAsesor = $scope.messageReceivedStarAsesor + 1;
                    self.readMessages();
                }
            }
            
            if ($scope.userSession.userId == message.receivingUserId.userId) {
                    $scope.messageReceivedStarAsesor = $scope.messageReceivedStarAsesor + 1;
                }

            $scope.messagesAsesor.push(message);
        });


        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {

                $scope.mailReceivedStarAsesor = $scope.mailReceivedStarAsesor + 1;

            }

                $scope.mailsAsesor.push(email);
            
        });

        $scope.showLoading = function (loading) {
            if (loading) {
                $mdDialog.show({
                    scope: $scope.$new(),
                    templateUrl: 'static/views/athleteDetail/loading.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: false,
                    fullscreen: $scope.customFullscreen,
                    controller: function () {
                        $mdDialog.cancel();
                    }
                });
            } else {

                $mdDialog.cancel();
            }
        };

        self.getMessagesReceivedCount = function (userId, fn) {
            MessageService.getMessagesReceived(userId, $scope.userSession.userId, -1, -1, -1).then(
                    fn,
                    function (error) {
                        console.log(error);
                    }
            );
        };

        self.getMailReceivedCount = function (userId, fn) {
            MailService.getReceivedMails(userId, $scope.userSession.userId, -1, -1, -1).then(
                    fn,
                    function (error) {
                        console.log(error);
                    }
            );
        }; 


        self.init = function () {
            self.getMessagesReceivedCount(-1, function (data) {
                $scope.messageReceivedStarAsesor = data.output;
            });
            self.getMailReceivedCount(-1, function (data) {
                $scope.mailReceivedStarAsesor = data.output;
            });
            self.getStars();
            self.getUsersMessagesByUser();
            self.getUsersMailsByUser();

        };

        self.init();

        $scope.$on('$destroy', function () {
            $scope.msgStarAsesorEnabled = false;
        });

    }]);