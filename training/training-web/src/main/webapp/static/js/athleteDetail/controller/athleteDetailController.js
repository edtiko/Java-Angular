trainingApp.controller('AthleteDetailController', ['$scope', 'AthleteService', 'DashboardService', '$window', '$location', '$mdDialog', '$routeParams',
    function ($scope, AthleteService, DashboardService, $window, $location, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athleteUserId = $routeParams.user;
        $scope.moduleSelected = 1;
        $scope.athleteView = {
            profile: 'static/views/athleteDetail/profile.html',
            chat: 'static/views/message/summaryAthlete.html',
            mail: 'static/views/mail/summaryAsesor.html',
            audio: 'static/views/audioMessage/video.html',
            video: 'static/views/video/message.html',
            calendar: 'static/views/calendar/audioMessage.html',
            chart: 'static/views/chart/mail.html'
        };


        $scope.goAthleteMenu = function (module, index) {
            $scope.moduleSelected = index;
            switch (module) {
                case 'profile':
                    $scope.athletePageSelected = $scope.athleteView.profile;
                    break;
                case 'chat':
                    $scope.athletePageSelected = $scope.athleteView.chat;
                    break;
                case 'mail':
                    $scope.athletePageSelected = $scope.athleteView.mail;
                    break;
                case 'calendar':
                    $scope.athletePageSelected = $scope.athleteView.calendar;
                    break;
                case 'audio':
                    $scope.athletePageSelected = $scope.athleteView.audio;
                    break;
                case 'video':
                    $scope.athletePageSelected = $scope.athleteView.video;
                    break;
                case 'chart':
                    $scope.athletePageSelected = $scope.athleteView.chart;
                    break;

            }

        };

        $scope.getProfile = function () {
          $scope.athletePageSelected = $scope.athleteView.profile;
            DashboardService.getDashboard($scope.athleteUserId, function(res){
                  $scope.userProfile = angular.copy(res.data.output);
            });

            $scope.getImageProfile($scope.athleteUserId, function (data) {
                if (data != "") {
                    $scope.athleteImage = "data:image/png;base64," + data;
                } else {
                    $scope.athleteImage = "static/img/profile-default.png";
                }
            });
        };
        
        
        $scope.getProfile();

    }]);