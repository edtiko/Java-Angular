trainingApp.controller('VideochatController', ['$scope', 'OTSession', '$window','videochatService', function ($scope, OTSession, $window,videochatService) {

        var call = JSON.parse($window.sessionStorage.getItem("sessionCall"));
        if (call != null) {
            OTSession.init(call.apiKey, call.sessionId, call.token);
        }

        $scope.streams = OTSession.streams;
    }]);