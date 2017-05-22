trainingApp.controller("MailAsesorController", ['$scope', 'MailService', '$window', '$mdDialog',
    function ($scope, MailService, $window, $mdDialog) {
        var self = this;
        $scope.mailsReceived = [];
        $scope.mailsSent = [];
        $scope.athletes = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.roleSelected = $scope.userSessionTypeUserCoachInterno;

        $scope.mailSelected = '';
        $scope.searchTextReceiverUser = '';
        $scope.selectedItemReceiverUser = null;
        $scope.availableMailStar = $scope.planSelected.starCommunication.availableMail;
        $scope.mailCountStar = $scope.planSelected.starCommunication.planMail;
        $scope.availableMailSup = $scope.planSelected.asesorCommunication.availableMail;
        $scope.mailCountSup = $scope.planSelected.asesorCommunication.planMail;
        $scope.receivedMailStar = $scope.planSelected.starCommunication.receivedMail;
        $scope.receivedMailSup = $scope.planSelected.asesorCommunication.receivedMail;

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
            received: 'static/views/athleteDetail/mail/received.html',
            sent: 'static/views/athleteDetail/mail/sent.html',
            mailSelected: 'static/views/athleteDetail/mail/mailSelected.html'
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
        
        $scope.resendEmail = function (mailId) {
            MailService.resendEmail(mailId, $scope.planSelected.id).then(
                    function (data) {
                        $scope.getEmailByRole($scope.userSessionTypeUserCoachEstrella);
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
                if ($scope.planSelected != null) {

                    $scope.mailCommunication.coachAssignedPlanId = $scope.planSelected.id;
                    $scope.mailCommunication.roleSelected = $scope.roleSelected;
                    
                } else if ($scope.sendingUser != null) {
                    $scope.mailCommunication.sendingUser.userId = $scope.userSession.userId;
                    $scope.mailCommunication.receivingUser.userId = $scope.sendingUser.userId;
                }


                $scope.createMailCommunication($scope.mailCommunication);
            }
        };

        $scope.verRecibidoStar = function () {
            $scope.recibidostar = true;
            $scope.enviadostar = false;
            $scope.viewMailSelected = $scope.views.received;
        };
        
        $scope.verRecibidosAsesor = function () {
            $scope.recibidoasesor = true;
            $scope.enviadoasesor = false;
            $scope.viewMailSelected = $scope.views.received;
        };

        $scope.verEnviadoStar = function () {
            $scope.recibidostar = false;
            $scope.enviadostar = true;
            $scope.viewMailSelected = $scope.views.sent;
        };
        
        $scope.verEnviadosAsesor = function () {
            $scope.recibidoasesor = false;
            $scope.enviadoasesor = true;
            $scope.viewMailSelected = $scope.views.sent;
        };

        $scope.getReceivedMailsByPlan = function (tipoPlan, sendingUserId, receivingUserId, planId, roleSelected) {
            MailService.getMailsByPlan(tipoPlan, sendingUserId, receivingUserId, planId, roleSelected, "to").then(
                    function (data) {
                        $scope.mailsReceived = data.entity.output;
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
            }

        });

        $scope.selectedItemChange = function (item) {
            if (item != undefined) {
                $scope.receivingUserSelected = item;
            }

        };

        $scope.querySearchUsers = function (query, users, value) {
            var results = users;
            if (query != null) {
                results = users.filter(createFilterFor(query, value));
            }
            return results;
        };


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
                                    $scope.getEmailCount();
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



        self.getEmailCoach = function (tipoPlan) {
            if ($scope.planSelected != null) {
                $scope.getSentMailsByPlan(tipoPlan, $scope.userSession.userId, $scope.planSelected.athleteUserId.userId, $scope.planSelected.id, $scope.roleSelected);
                $scope.getReceivedMailsByPlan(tipoPlan, $scope.userSession.userId, $scope.planSelected.athleteUserId.userId, $scope.planSelected.id, $scope.roleSelected);
                $scope.mailCommunication.mailto = $scope.planSelected.athleteUserId.fullName;
                $scope.mailCommunication.receivingUser.userId = $scope.planSelected.athleteUserId.userId;
            } else if ($scope.sendingUser != null) {
                self.getEmailUser();
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


        $scope.getEmailByRole = function (role) {
            $scope.roleSelected = role;
            self.init();
        };

        $scope.getEmailCount = function () {
            var tipoPlan = "IN";
            var athleteUserId = $scope.planSelected.athleteUserId.userId;
            $scope.getAvailableMail($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.availableMailStar = data.entity.output;
                    });
            $scope.getAvailableMail($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.availableMailSup = data.entity.output;
                    });

            $scope.getReceivedMails($scope.planSelected.id, athleteUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachEstrella,
                    function (data) {
                        $scope.receivedMailStar = data.output;
                    });

            $scope.getReceivedMails($scope.planSelected.id, athleteUserId, $scope.userSession.userId, tipoPlan, $scope.userSessionTypeUserCoachInterno,
                    function (data) {
                        $scope.receivedMailSup = data.output;

                    });
        };
       
        self.init = function () {

            if ($scope.userSession != null) {
                self.getEmailCoach("IN");
                $scope.verRecibidoStar();
                $scope.verRecibidosAsesor();
            } else {
                $scope.setUserSession();
            }

        };

        self.init();
        
    }]);