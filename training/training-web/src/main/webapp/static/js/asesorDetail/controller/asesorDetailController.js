trainingApp.controller('AsesorDetailController', ['$scope', 'DashboardService', 'MessageService', 'MailService', '$window', '$mdDialog', '$routeParams',
    function ($scope, DashboardService, MessageService, MailService, $window, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.asesorUserId = $routeParams.user;
        $scope.moduleSelected = 1;
        $scope.asesorView = {
            profile: 'static/views/asesorDetail/profile/profile.html',
            chat: 'static/views/asesorDetail/message/chat.html',
            mail: 'static/views/asesorDetail/mail/mail.html',
            athletes: 'static/views/asesorDetail/athletes/athletes.html',
            athleteDetail: 'static/views/asesorDetail/athleteDetail/athleteDetail.html'
        };


        $scope.goAsesorMenu = function (module, index) {
            $scope.moduleSelected = index;

            switch (module) {
                case 'profile':
                    $scope.asesorPageSelected = $scope.asesorView.profile;
                    break;
                case 'chat':
                    $scope.asesorPageSelected = $scope.asesorView.chat;
                    break;
                case 'mail':
                    $scope.asesorPageSelected = $scope.asesorView.mail;
                    break;
                case 'athletes':
                    $scope.asesorPageSelected = $scope.asesorView.athletes;
                    break;

            }

        };

        $scope.goStarAthleteDetail = function (athleteUserId) {
            $scope.starAthleteUserId = athleteUserId;
            $scope.asesorPageSelected = $scope.asesorView.athleteDetail;

        };

        $scope.getProfile = function () {
            $scope.asesorPageSelected = $scope.asesorView.profile;
            DashboardService.getDashboard($scope.asesorUserId, function (res) {
                $scope.userProfile = angular.copy(res.data.output);
            });

            $scope.getImageProfile($scope.asesorUserId, function (data) {
                if (data != "") {
                    $scope.asesorImage = "data:image/png;base64," + data;
                    $window.sessionStorage.setItem("asesorImage", $scope.asesorImage);
                } else {
                    $scope.asesorImage = "static/img/profile-default.png";
                }
            });
        };


        MessageService.receive().then(null, null, function (message) {
             if ($scope.userSession.userId == message.receivingUserId.userId) {
                $scope.messageReceivedCount = $scope.messageReceivedCount + 1;
            }
        });


        //notificación emails recibidos
        MailService.receive().then(null, null, function (email) {
            if (email.receivingUser.userId == $scope.userSession.userId) {

                $scope.mailReceivedCount = $scope.mailReceivedCount + 1;

            }

        });

        $scope.getMessagesReceivedCount = function () {
            MessageService.getMessagesReceived(-1, $scope.asesorUserId, $scope.userSession.userId, -1, -1).then(
                    function (data) {
                        $scope.messageReceivedCount = data.output;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        $scope.getMailReceivedCount = function () {
            MailService.getReceivedMails(-1, $scope.asesorUserId, $scope.userSession.userId, -1, -1).then(
                    function (data) {
                        $scope.mailReceivedCount = data.output;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        self.init = function () {
            if ($scope.userSession.userId != null && $scope.asesorUserId != null) {
                $scope.getProfile();
                MessageService.initialize($scope.userSession.userId + $scope.asesorUserId);
                MailService.initialize($scope.userSession.userId + $scope.asesorUserId);
                $scope.getMessagesReceivedCount();
                $scope.getMailReceivedCount();
            } else {
                $scope.setUserSession();
            }
        };

        self.init();

    }]);