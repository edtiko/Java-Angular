trainingApp.controller("MailStarController", ['$scope', 'MailService', '$window', '$mdDialog',
    function ($scope, MailService, $window, $mdDialog) {
        var self = this;
        $scope.mailsReceived = [];
        $scope.mailsSent = [];
        $scope.athletes = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.roleSelected = -1;

        $scope.mailSelected = '';

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
            received: 'static/views/asesorDetail/mail/received.html',
            sent: 'static/views/asesorDetail/mail/sent.html',
            mailSelected: 'static/views/asesorDetail/mail/mailSelected.html'
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
                $scope.mailCommunication.receivingUser.userId = $scope.asesorUserId;


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
        


        $scope.getReceivedMails = function () {
            MailService.getMailsByReceivingUserFromSendingUser($scope.userSession.userId, $scope.asesorUserId).then(
                    function (data) {
                        $scope.mailsReceived = data;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getSentMails = function () {
            MailService.getMailsByReceivingUserFromSendingUser($scope.asesorUserId, $scope.userSession.userId).then(
                    function (data) {
                        $scope.mailsSent = data;
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
                                } else {
                                    $scope.showMessage(d.output);
                                }

                                $scope.init();
                            },
                            function (error) {
                                console.error('Error while creating mail communication.'+error);
                            }
                    );
        };

        $scope.reset = function () {
            $scope.mailCommunication.subject = '';
            $scope.mailCommunication.message = '';
        };
        
        $scope.toggleFn = function(){
            
        };

        $scope.readEmail = function (id) {

            MailService.readEmail(id)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                   // $scope.getReceived();
                                } else {
                                    console.log(d.output);
                                }
                            },
                            function (error) {
                                console.error('Error while updating mail communication.'+error);
                            }
                    );
        };



        self.getEmailUser = function () {
            $scope.getSentMails();
            $scope.getReceivedMails();
            $scope.mailCommunication.mailto = $scope.userProfile.fullName;
        };


        $scope.init = function () {

            if ($scope.userSession != null) {
                self.getEmailUser();
                $scope.verRecibidos();
            } else {
                $scope.setUserSession();
            }

        };

        $scope.init();
        
    }]);