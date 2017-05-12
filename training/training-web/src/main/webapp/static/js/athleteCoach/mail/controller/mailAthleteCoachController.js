trainingApp.controller("MailAthleteCoachController", ['$scope', 'MailService', '$window', '$mdDialog',
    function ($scope, MailService, $window, $mdDialog) {
        var self = this;
        $scope.mailsReceived = [];
        $scope.mailsSent = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = $scope.userSession.planSelected; 
        $scope.roleSelected = -1;
        $scope.mailSelected = '';
        $scope.searchTextReceiverUser = '';
        $scope.selectedItemReceiverUser = null;
        $scope.availableMail = $scope.planSelected.coachCommunication.availableMail;
        $scope.mailCount = $scope.planSelected.coachCommunication.planMail;
        $scope.receivedMail = $scope.planSelected.coachCommunication.receivedMail;

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

        $scope.receivingUserSelected = {};
        $scope.searchText = '';
        $scope.received = false;



        $scope.views = {
            received: 'static/views/athleteCoach/mail/received.html',
            sent: 'static/views/athleteCoach/mail/sent.html',
            mailSelected: 'static/views/athleteCoach/mail/mailSelected.html'
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

             

                    $scope.mailCommunication.coachExtAthleteId = $scope.planSelected.id;
                    $scope.mailCommunication.roleSelected = $scope.roleSelected;
                    $scope.mailCommunication.sendingUser.userId = $scope.userSession.userId;
                    $scope.mailCommunication.receivingUser.userId = $scope.planSelected.coachUserId.userId;
                    $scope.createMailCommunication($scope.mailCommunication);
                



            }
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
                $scope.init();
            }

        });



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



        self.getEmailCoach = function (tipoPlan) {
            if ($scope.planSelected != null) {
                $scope.getSentMailsByPlan(tipoPlan, $scope.userSession.userId, $scope.planSelected.coachUserId.userId, $scope.planSelected.id, $scope.roleSelected);
                $scope.getReceivedMailsByPlan(tipoPlan, $scope.userSession.userId, $scope.planSelected.coachUserId.userId, $scope.planSelected.id, $scope.roleSelected);
                $scope.mailCommunication.mailto = $scope.planSelected.coachUserId.fullName;
                $scope.mailCommunication.receivingUser.userId = $scope.planSelected.coachUserId.userId;
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
            $scope.init();
        };

        $scope.getEmailCount = function () {
            var tipoPlan = "EXT";
            $scope.getAvailableMail($scope.planSelected.id, $scope.userSession.userId, tipoPlan, $scope.roleSelected,
                    function (data) {
                        $scope.availableMail = data.entity.output;
                    });
        };



        $scope.init = function () {

            if ($scope.userSession != null) {
                self.getEmailCoach("EXT");
                $scope.verRecibidos();
            } else {
                $scope.setUserSession();
            }

        };

        $scope.init();
        
    }]);