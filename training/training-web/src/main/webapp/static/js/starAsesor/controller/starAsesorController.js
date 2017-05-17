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

        $scope.setFilter = function (letter) {
            $scope.filt = letter;
        };

        $scope.startsWith = function (star) {
            var lowerStr = (star.fullName + "").toLowerCase();
            var letter = $scope.filt;
            if (letter != undefined) {
                return lowerStr.indexOf(letter.toLowerCase()) === 0;
            }else{
                $scope.starsFiltered = null;
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
                        data.forEach(function (v) {
                            MessageService.initialize(v.userId + $scope.userSession.userId);
                            MailService.initialize(v.userId + $scope.userSession.userId);
                        });
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
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        $scope.getMessagesByUser = function (user) {
            $scope.userMsgSelected = user;
            MessageService.getMessagesByReceivingUserSendingUser($scope.userSession.userId, user.userId).then(
                    function (data) {
                        $scope.messagesAsesor = data;
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

                                $scope.init();
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

        $scope.readEmail = function (id) {

            MailService.readEmail(id)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    //$scope.getReceived();
                                } else {
                                    console.log(d.output);
                                }
                            },
                            function (error) {
                                console.error('Error while updating mail communication.' + error);
                            }
                    );
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
            if (message.id != "" && $scope.userSession != null && $scope.userSession.userId != message.messageUserId.userId) {
                $scope.messageReceivedCount++;
            }

            $scope.messagesAsesor.push(message);
        });


        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {

                $scope.mailReceivedCount++;

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

        self.getMessagesReceivedCount = function () {
            MessageService.getMessagesReceived(-1, $scope.userSession.userId, -1, -1, -1).then(
                    function (data) {
                        $scope.messageReceivedCount = data.output;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        self.getMailReceivedCount = function () {
            MailService.getReceivedMails(-1, $scope.userSession.userId, -1, -1, -1).then(
                    function (data) {
                        $scope.mailReceivedCount = data.output;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };


        self.init = function () {
            self.getMessagesReceivedCount();
            self.getMailReceivedCount();
            self.getStars();
            self.getUsersMessagesByUser();
            self.getUsersMailsByUser();

        };

        self.init();

    }]);