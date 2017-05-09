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

        $scope.filt = 'A';

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
            roleSelected: $scope.roleSelected,
            mobile: false
        };

        $scope.setFilter = function (letter) {
            $scope.filt = letter;
        };

        $scope.startsWith = function (star) {
            var lowerStr = (star.fullName + "").toLowerCase();
            var letter = $scope.filt;
            return lowerStr.indexOf(letter.toLowerCase()) === 0;
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
            MailService.getMailsByReceivingUserFromSendingUser($scope.userSession.userId, user.userId).then(
                    function (data) {
                        $scope.mailsAsesor = data;
                    },
                    function (error) {
                        console.log(error);
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


        self.init = function () {
            self.getStars();
            self.getUsersMessagesByUser();
            self.self.getUsersMailsByUser();
            MessageService.initialize($scope.userSession.userId);
            MailService.initialize($scope.userSession.userId);
        };

        self.init();

    }]);