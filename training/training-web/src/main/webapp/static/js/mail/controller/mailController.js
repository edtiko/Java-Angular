trainingApp.controller("MailController", ['$scope', 'MailService', '$window', '$mdDialog',
    function ($scope, MailService, $window, $mdDialog) {
        var self = this;
        $scope.mailsReceived = [];
        $scope.mailsSent = [];
        $scope.athletes = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.sendingUser = JSON.parse($window.sessionStorage.getItem("selectedUser"));


        $scope.mailSelected = '';
        $scope.searchTextReceiverUser = '';
        $scope.selectedItemReceiverUser = null;
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
                //parent: angular.element(document.querySelector('#dashboard-container')),
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
                if ($scope.planSelected != null) {
                    if ($scope.planSelected.external) {
                        $scope.mailCommunication.coachExtAthleteId = $scope.planSelected.id;
                    } else {
                        $scope.mailCommunication.coachAssignedPlanId = $scope.planSelected.id;
                    }
                } else if ($scope.sendingUser != null) {
                    $scope.mailCommunication.sendingUser.userId = $scope.userSession.userId;
                    $scope.mailCommunication.receivingUser.userId = $scope.sendingUser.userId;
                }


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

        $scope.getReceivedMailsByPlan = function (tipoPlan, userId, planId, roleSelected) {
            MailService.getMailsByPlan(tipoPlan, userId, planId, roleSelected).then(
                    function (data) {
                        $scope.mailsReceived = data.entity.output;
                        if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                            $scope.mailsReceived.forEach(function (value, index) {
                                if (value.sendingUser.userId != $scope.userSession.userId) {
                                    value.sendingUser = $scope.planSelected.starUserId;
                                }
                            });
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getSentMailsByPlan = function (tipoPlan, userId, planId, roleSelected) {
            MailService.getMailsByPlan(tipoPlan, userId, planId, roleSelected).then(
                    function (data) {
                        $scope.mailsSent = data.entity.output;
                        if ($scope.userSession.typeUser == $scope.userSessionTypeUserAtleta && $scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                            $scope.mailsSent.forEach(function (value, index) {
                                if (value.receivingUser.userId != $scope.userSession.userId) {
                                    value.receivingUser = $scope.planSelected.starUserId;
                                }
                            });
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };



        $scope.getReceivedMails = function () {
            MailService.getMailsByReceivingUserFromSendingUser($scope.userSession.userId, $scope.sendingUser.userId).then(
                    function (data) {
                        $scope.mailsReceived = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getSentMails = function () {
            MailService.getMailsByReceivingUserFromSendingUser($scope.sendingUser.userId, $scope.userSession.userId).then(
                    function (data) {
                        $scope.mailsSent = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getMails = function () {
            MailService.getMails($scope.userSession.userId).then(
                    function (data) {
                        $scope.mailsReceived = data.entity.output;
                    },
                    function (error) {
                        console.error(error);
                    });
        };

        $scope.getSentMailsByUserLogged = function () {
            MailService.getSentMails($scope.userSession.userId).then(
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
                                    $scope.getMailCount();
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
                                     $scope.getMailCount();
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
                $scope.getSentMailsByPlan(tipoPlan, $scope.userSession.userId, $scope.planSelected.id, $scope.roleSelected);
                $scope.getReceivedMailsByPlan(tipoPlan, $scope.planSelected.athleteUserId.userId, $scope.planSelected.id, $scope.roleSelected);
                $scope.mailCommunication.mailto = $scope.planSelected.athleteUserId.fullName;
                $scope.mailCommunication.receivingUser.userId = $scope.planSelected.athleteUserId.userId;
            }else if($scope.sendingUser != null){
                self.getEmailUser();
            }
        };

        self.getEmailAthlete = function () {
            if ($scope.planSelected != null) {
                if ($scope.planSelected.external) {
                    $scope.getSentMailsByPlan("EXT", $scope.userSession.userId, $scope.planSelected.id, -1);
                    $scope.getReceivedMailsByPlan("EXT", $scope.planSelected.coachUserId.userId, $scope.planSelected.id, -1);

                } else {
                    $scope.getSentMailsByPlan("IN", $scope.userSession.userId, $scope.planSelected.id, $scope.roleSelected);
                    $scope.getReceivedMailsByPlan("IN", $scope.planSelected.coachUserId.userId, $scope.planSelected.id, $scope.roleSelected);
                }
                if ($scope.roleSelected == $scope.userSessionTypeUserCoachInterno) {
                    $scope.mailCommunication.mailto = $scope.planSelected.coachUserId.fullName;
                } else if ($scope.roleSelected == $scope.userSessionTypeUserCoachEstrella) {
                    $scope.mailCommunication.mailto = $scope.planSelected.starUserId.fullName;
                } else {
                    $scope.mailCommunication.mailto = $scope.planSelected.coachUserId.fullName;
                }
                $scope.mailCommunication.receivingUser.userId = $scope.planSelected.coachUserId.userId;
            }
        };
        self.getEmailUser = function () {
            $scope.getSentMails();
            $scope.getReceivedMails();
            $scope.mailCommunication.mailto = $scope.sendingUser.fullName;
        };

        $scope.init = function () {

            switch ($scope.userSession.typeUser) {
                case $scope.userSessionTypeUserCoach:
                    self.getEmailCoach("EXT");
                    break;
                case $scope.userSessionTypeUserCoachInterno:
                    self.getEmailCoach("IN");
                    break;
                case $scope.userSessionTypeUserAtleta:
                    self.getEmailAthlete();
                    break;
                case $scope.userSessionTypeUserCoachEstrella:
                    self.getEmailUser();
                    break;
                case $scope.userSessionTypeUserAdmin:
                    self.getEmailUser();
                    break;
                default :
                    $scope.setUserSession();

            }


            $scope.verRecibidos();
        };


        /**
         * Create filter function for a query string
         * @param {type} query
         * @returns {Function}
         */
        function createFilterFor(query, value) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(athletes) {
                var a = eval('athletes' + '.' + value);
                return (angular.lowercase(a).indexOf(lowercaseQuery) >= 0);
            };

        }

        $scope.init();

    }]);