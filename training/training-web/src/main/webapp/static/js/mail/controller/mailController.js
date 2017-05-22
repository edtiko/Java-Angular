trainingApp.controller("MailController", ['$scope', 'MailService', '$window', '$mdDialog',
    function ($scope, MailService, $window, $mdDialog) {
        var self = this;
        $scope.mailsReceived = [];
        $scope.mailsSent = [];
        $scope.athletes = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.roleSelected = $scope.userSessionTypeUserCoachEstrella;

        $scope.mailSelected = '';
        $scope.availableMailStar = $scope.userSession.planSelected.starCommunication.availableMail;
        $scope.mailCountStar = $scope.userSession.planSelected.starCommunication.planMail;
        $scope.receivedMailStar = $scope.userSession.planSelected.starCommunication.receivedMail;
        $scope.availableMailSup = $scope.userSession.planSelected.asesorCommunication.availableMail;
        $scope.mailCountSup = $scope.userSession.planSelected.asesorCommunication.planMail;
        $scope.receivedMailSup = $scope.userSession.planSelected.asesorCommunication.receivedMail;
        
        $scope.mailCommunication = {
            id: '',
            mailto: '',
            receivingUser: {userId: ''},
            sendingUser: {userId: ''},
            coachExtAthleteId: '',
            coachAssignedPlanId: '',
            message: '',
            subject: '',
            roleSelected: $scope.roleSelected
        };

        $scope.received = false;



        $scope.views = {
            received: 'static/views/mail/received.html',
            sent: 'static/views/mail/sent.html',
            mailSelected: 'static/views/mail/mailSelected.html'
        };

        $scope.dialogEmail = function () {
            $scope.reset();
            $mdDialog.show({
                controller: sendEmailController,
                scope: $scope.$new(),
                templateUrl: 'static/views/mail/mailTemplate.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: $scope.customFullscreen
            });
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

        $scope.addMail = function () {
            if ($scope.userSession != null && $scope.mailCommunication.message != ""
                    && $scope.mailCommunication.subject != "") {

                $scope.mailCommunication.sendingUser.userId = $scope.userSession.userId;
                $scope.mailCommunication.coachAssignedPlanId = $scope.userSession.planSelected.id;
                $scope.mailCommunication.roleSelected = $scope.roleSelected;

                $scope.createMailCommunication($scope.mailCommunication);
            }
        };

        $scope.verRecibidos = function () {
            $scope.recibidos = true;
            $scope.enviados = false;
            $scope.viewMailSelected = $scope.views.received;
        };

        $scope.verEnviados = function () {
            $scope.recibidos = false;
            $scope.enviados = true;
            $scope.viewMailSelected = $scope.views.sent;
        };

        $scope.getReceivedMailsByPlan = function (tipoPlan, sendingUserId, receivingUserId, planId, roleSelected) {
            MailService.getMailsByPlan(tipoPlan, sendingUserId, receivingUserId, planId, roleSelected, "to").then(
                    function (data) {
                        $scope.mailsReceived = data.entity.output;
                        if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                            $scope.mailsReceived.forEach(function (value, index) {
                                if (value.sendingUser.userId != $scope.userSession.userId) {
                                    value.sendingUser = $scope.userSession.planSelected.starUserId;
                                }
                            });
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getSentMailsByPlan = function (tipoPlan, sendingUserId, receivingUserId, planId, roleSelected) {
            MailService.getMailsByPlan(tipoPlan, sendingUserId, receivingUserId, planId, roleSelected, "from").then(
                    function (data) {
                        $scope.mailsSent = data.entity.output;
                        if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                            $scope.mailsSent.forEach(function (value, index) {
                                if (value.receivingUser.userId != $scope.userSession.userId) {
                                    value.receivingUser = $scope.userSession.planSelected.starUserId;
                                }
                            });
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };



             //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {
                self.init();
                $scope.getReceived();
            }

        });


        $scope.selectReceivedMail = function (id) {
            for (var i = 0; i < $scope.mailsReceived.length; i++) {
                if ($scope.mailsReceived[i].mailCommunicationId === id) {
                    $scope.mailSelected = angular.copy($scope.mailsReceived[i]);
                    if ($scope.mailSelected.read == false) {
                        $scope.readEmail(id);
                        $scope.mailsReceived[i].read = true;
                    }
                    $scope.received = true;
                    break;
                }
            }
            $scope.viewMailSelected = $scope.views.mailSelected;
        };

        $scope.selectSentMail = function (id) {
            for (var i = 0; i < $scope.mailsSent.length; i++) {
                if ($scope.mailsSent[i].mailCommunicationId === id) {
                    $scope.mailSelected = angular.copy($scope.mailsSent[i]);
                    $scope.received = false;
                    break;
                }
            }
            $scope.viewMailSelected = $scope.views.mailSelected;
        };


        $scope.createMailCommunication = function (mail) {
            MailService.createMailCommunication(mail)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.reset();
                                    self.getEmailCount();
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

        $scope.reset = function () {
            $scope.mailCommunication.subject = '';
            $scope.mailCommunication.message = '';
        };

        $scope.readEmail = function (id) {

            MailService.readEmail(id)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                     $scope.getReceived();
                                } else {
                                    console.log(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating mail communication.');
                            }
                    );
        };

       

        self.getEmailAthlete = function () {
            if ($scope.userSession.planSelected != null) {

                var toUser = $scope.userSession.planSelected.coachUserId.userId;

                $scope.getSentMailsByPlan("IN", $scope.userSession.userId, $scope.userSession.planSelected.coachUserId.userId, $scope.userSession.planSelected.id, $scope.roleSelected);
                $scope.getReceivedMailsByPlan("IN", $scope.userSession.userId, toUser, $scope.userSession.planSelected.id, $scope.roleSelected);

                if ($scope.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                    $scope.mailCommunication.mailto = $scope.userSession.planSelected.coachUserId.fullName;
                } else if ($scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                    $scope.mailCommunication.mailto = $scope.userSession.planSelected.starUserId.fullName;
                } else {
                    $scope.mailCommunication.mailto = $scope.userSession.planSelected.coachUserId.fullName;
                }
                $scope.mailCommunication.receivingUser.userId = $scope.userSession.planSelected.coachUserId.userId;

            }
        };

        //EMAIL COUNT
        $scope.getAvailableMail = function (planId, userId, tipoPlan, roleSelected, fn) {
            var res = 0;
            MailService.getAvailableMails(planId, userId, tipoPlan, roleSelected).then(
                    fn,
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
            return res;
        };
        
        self.getEmailUser = function () {
            $scope.getSentMails();
            $scope.getReceivedMails();
            $scope.mailCommunication.mailto = $scope.sendingUser.fullName;
        };

        self.init = function () {

            if ($scope.userSession != null) {

                    self.getEmailAthlete();
                    self.getEmailCount();
                }else{
                    $scope.setUserSession();

            }


            $scope.verRecibidos();
        };
        
        $scope.getEmailByRole = function (role) {
            $scope.roleSelected = role;
            self.init();
        };
        
        self.getEmailCount = function () {
            var tipoPlan = "IN";
            var coachUserId = $scope.userSession.planSelected.coachUserId.userId;
            var starUserId = $scope.userSession.planSelected.starUserId.userId;

            $scope.getAvailableMail($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.availableMailStar = data.entity.output;
                    });
            $scope.getAvailableMail($scope.userSession.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.availableMailSup = data.entity.output;
                    });

            $scope.getReceivedMails($scope.userSession.planSelected.id, coachUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.receivedMailStar = data.output;
                        $scope.getReceivedMails($scope.userSession.planSelected.id, starUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                                function (data) {
                                    $scope.receivedMailStar =  $scope.receivedMailStar + data.output;
                                });
                    });
            $scope.getReceivedMails($scope.userSession.planSelected.id, coachUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.receivedMailSup = data.output;

                    });
        };



        self.init();

    }]);