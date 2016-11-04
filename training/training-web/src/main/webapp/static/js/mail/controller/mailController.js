trainingApp.controller("MailController", ['$scope', 'MailService', '$window', 'DashboardService', 'SupervStarCoachService',
    function ($scope, MailService, $window, DashboardService, SupervStarCoachService) {
        $scope.mailsReceived = [];
        $scope.mailsSent = [];
        $scope.athletes = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.sendingUserId = JSON.parse($window.sessionStorage.getItem("sendingUserId"));
        $scope.mailSelected = '';
        $scope.searchTextReceiverUser = '';
        $scope.selectedItemReceiverUser = null;
        $scope.mailCommunication = {
            id: '',
            receivingUser: {userId: ''},
            sendingUser: {userId: ''},
            message: '',
            subject: ''
        };
        $scope.create = false;
        $scope.receivingUserSelected = {};
        $scope.searchText = '';
        $scope.received = false;
        $scope.supervisors = [];
        $scope.tabIndex = $window.sessionStorage.getItem("tabIndex");
        $scope.tabIndex2 = $window.sessionStorage.getItem("tabIndex2");
        $scope.recipients = [];

        $scope.getMails = function () {
            MailService.getMails($scope.userSession.userId).then(
                    function (data) {
                        $scope.mailsReceived = data.entity.output;
                    },
                    function (error) {
                        console.error(error);
                    });
        };

        $scope.getReceivedMails = function () {
            MailService.getMailsByReceivingUserFromSendingUser($scope.userSession.userId, $scope.sendingUserId).then(
                    function (data) {
                        $scope.mailsReceived = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getSentMails = function () {
            MailService.getMailsByReceivingUserFromSendingUser($scope.sendingUserId, $scope.userSession.userId).then(
                    function (data) {
                        $scope.mailsSent = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
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

        $scope.getAllRecipientsByCoach = function () {
            MailService.getAllRecipientsByCoach($scope.userSession.userId).then(
                    function (data) {
                        $scope.recipients = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getAllRecipientsByStar = function () {
            MailService.getAllRecipientsByStar($scope.userSession.userId).then(
                    function (data) {
                        $scope.recipients = data.entity.output;
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };
        
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

        $scope.getAssignedAthletes = function () {
            DashboardService.getAssignedAthletes($scope.userSession.userId).then(
                    function (data) {
                        $scope.athletes = data.entity.output;
                        if ($scope.athletes == null) {
                            $scope.showMessage("No tiene planes asignados.");
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });

            if ($scope.userSession.typeUser == $scope.userSessionTypeUserSupervisor) {
                DashboardService.getAssignedUserBySupervisor($scope.userSession.userId).then(
                        function (data) {
                            if (data.status == 'success') {
                                $scope.athletes = data.output;
                            }
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            } else {
                DashboardService.getAssignedAthletes($scope.userSession.userId).then(
                        function (data) {
                            $scope.athletes = data.entity.output;
                            if ($scope.athletes == null) {
                                $scope.showMessage("No tiene planes asignados.");
                            }
                        },
                        function (error) {
                            //$scope.showMessage(error);
                            console.error(error);
                        });
            }


        };

        $scope.selectReceivedMail = function (id) {
            for (var i = 0; i < $scope.mailsReceived.length; i++) {
                if ($scope.mailsReceived[i].mailCommunicationId === id) {
                    $scope.mailSelected = angular.copy($scope.mailsReceived[i]);
                    $scope.received = true;
                    if ($scope.mailSelected.read == false) {
                        $scope.updateMailCommunication($scope.mailSelected);
                    }
                    break;
                }
            }
        };

        $scope.selectSentMail = function (id) {
            for (var i = 0; i < $scope.mailsSent.length; i++) {
                if ($scope.mailsSent[i].mailCommunicationId === id) {
                    $scope.mailSelected = angular.copy($scope.mailsSent[i]);
                    $scope.received = false;
                    break;
                }
            }
        };

        $scope.selectUser = function (userId) {
            $scope.mailSelected = null;
            $window.sessionStorage.setItem("sendingUserId", userId);
            $scope.received = false;
            $scope.pageSelected = $scope.views.email.page;
            //$window.location.href = "#mail";
        };

        $scope.selectAthlete = function (e) {
            $window.sessionStorage.setItem("coachAssignedPlanSelected", JSON.stringify(e));
            $window.location.href = "#dashboard";
        };
        
        $scope.selectCoach = function (coachAssignedPlanSelected) {
             $window.sessionStorage.setItem("coachAssignedPlanSelected", JSON.stringify(coachAssignedPlanSelected));
            $window.location.href = "#dashboard";
        };


        $scope.addMail = function () {
            if ($scope.userSession != null && $scope.sendingUserId != null && $scope.mailCommunication.message != ""
                    && $scope.mailCommunication.subject != "") {
                $scope.mailCommunication.sendingUser.userId = $scope.userSession.userId;
                $scope.mailCommunication.receivingUser.userId = $scope.sendingUserId;
                if ($scope.create) {
                    if ($scope.receivingUserSelected != '' && parseInt($scope.receivingUserSelected) > 0) {
                        $scope.mailCommunication.receivingUser.userId = $scope.receivingUserSelected;
                    }
                }
                $scope.createMailCommunication($scope.mailCommunication);
            }
        };

        $scope.createMailCommunication = function (mail) {
            MailService.createMailCommunication(mail)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.showMessage(d.output);
                                    $scope.mailCommunication = null;
                                    $scope.receivingUserSelected = '';
                                    $scope.create = false;
                                    $scope.selectUser($scope.sendingUserId);
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating mail communication.');
                            }
                    );
        };

        $scope.updateMailCommunication = function (mail) {
            var date = mail.creationDate;
            mail.read = true;
            mail.creationDate = '';
            MailService.updateMailCommunication(mail)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    console.log(d.output);
                                    $scope.mailSelected.creationDate = date;
                                } else {
                                    console.log(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating mail communication.');
                            }
                    );
        };

        $scope.showCreate = function () {
            $scope.create = true;
        };

        $scope.getSupervisorByCoachId = function (coachId) {
            SupervStarCoachService.getByCoachId(coachId)
                    .then(
                            function (d) {
                                if (d.status == 'success') {
                                    $scope.supervisors = d.output;
                                } else {
                                    $scope.showMessage(d.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating mail communication.');
                            }
                    );
        };

        $scope.onTabChanges = function (currentTabIndex) {
            $window.sessionStorage.setItem("tabIndex", currentTabIndex);
            $scope.tabIndex = currentTabIndex;
            if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {
                if ($scope.tabIndex == 3) {
                    $scope.getMails();
                    $scope.getSentMailsByUserLogged();
                } else {
                    $scope.getSentMails();
                    $scope.getReceivedMails();
                }
            } 
            else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserSupervisor) {
                if (currentTabIndex == 0) {
                    $scope.getAssignedStarCoachBySupervisor();
                } else if (currentTabIndex == 1) {
                    $scope.getAssignedAtleteCoachBySupervisor();
                } else {
                     $scope.getAssignedUserBySupervisor();
                }
            } else  if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachEstrella) {
                if ($scope.tabIndex == 2) {
                    $scope.getMails();
                    $scope.getSentMailsByUserLogged();
                } 
            } 
        };

        $scope.onTabChanges2 = function (currentTabIndex) {
            $window.sessionStorage.setItem("tabIndex2", currentTabIndex);
            $scope.tabIndex2 = currentTabIndex;
             $scope.getSentMails();
                    $scope.getReceivedMails();
        };

        $scope.getAssignedStarCoachBySupervisor = function () {
            DashboardService.getAssignedStarCoachBySupervisor($scope.userSession.userId).then(
                    function (data) {
                        var res = data.output;

                        if (data.status == 'success') {
                            $scope.supervisorUserAssignedList = angular.copy(res);
                        }

                    },
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.getAssignedAtleteCoachBySupervisor = function () {
            DashboardService.getAssignedAtleteCoachBySupervisor($scope.userSession.userId).then(
                    function (data) {
                        var res = data.output;

                        if (data.status == 'success') {
                            $scope.supervisorUserAssignedList = angular.copy(res);
                        }

                    },
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        $scope.getAssignedUserBySupervisor = function () {
            DashboardService.getAssignedUserBySupervisor($scope.userSession.userId).then(
                    function (data) {
                        var res = data.output;

                        if (data.status == 'success') {
                            $scope.recipients = angular.copy(res);
                        }

                    },
                    function (error) {
                        $scope.showMessage(error);
                        console.error(error);
                    });
        };
        
        $scope.getAssignedAthletesByStar = function () {
            DashboardService.getAssignedAthletesByStar($scope.userSession.userId).then(
                    function (data) {
                        $scope.athletes = data.entity.output;
                        if ($scope.athletes == null) {
                            $scope.showMessage("No tiene planes asignados.");
                        }
                    },
                    function (error) {
                        //$scope.showMessage(error);
                        console.error(error);
                    });
        };

        $scope.init = function () {
            if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoach) {
                $scope.getAssignedAthletes();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachInterno) {
                $scope.getAllRecipientsByCoach();
                $scope.getAssignedAthletes();
                $scope.getSupervisorByCoachId($scope.userSession.userId);
                $scope.getSentMails();
                $scope.getReceivedMails();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserAtleta) {
                $scope.getSentMails();
                $scope.getReceivedMails();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserSupervisor) {
                $scope.getAssignedStarCoachBySupervisor();
            } else if ($scope.userSession != null && $scope.userSession.typeUser === $scope.userSessionTypeUserCoachEstrella) {
                $scope.getAssignedAthletesByStar();
                $scope.getAllRecipientsByStar();
            }
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