trainingApp.controller('AthleteDetailController', ['$scope', 'AthleteService', 'DashboardService', '$window', '$location', '$mdDialog', '$routeParams',
    function ($scope, AthleteService, DashboardService, $window, $location, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athleteUserId = $routeParams.user;
        $scope.moduleSelected = 1;
        $scope.athleteView = {
            profile: 'static/views/athleteDetail/profile/profile.html',
            chat: 'static/views/athleteDetail/message/chat.html',
            mail: 'static/views/athleteDetail/mail/mail.html',
            audio: 'static/views/athleteDetail/audio/audio.html',
            video: 'static/views/athleteDetail/video/video.html',
            calendar: 'static/views/athleteDetail/calendar/calendar.html',
            chart: 'static/views/athleteDetail/chart/chart.html'
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
        
        
        $scope.getActivePlan = function(){
            AthleteService.getActivePlan($scope.athleteUserId, function(res){
                $scope.planSelected = res.data.output;
            });
        };
        
        
        self.init = function () {
            $scope.getProfile();
            $scope.getActivePlan();
        };
        
        self.init();

    }]);