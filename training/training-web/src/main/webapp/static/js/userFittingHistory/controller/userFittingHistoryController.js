trainingApp.controller('UserFittingHistoryController', ['$scope', 'UserFittingService', '$window', '$mdDialog', '$routeParams',
    function ($scope, UserFittingService, $window, $mdDialog, $routeParams) {
        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.userFitting = JSON.parse($window.sessionStorage.getItem("userFitting"));
        $scope.athleteUserId = $routeParams.user;
        $scope.userFittingHistory = [];
        
        $scope.query = {
            filter: '',
            order: 'creationDate',
            limit: 5,
            page: 1
        };
        
        $scope.states = [
            {stateId: 2 , name: "En espera de revisi\u00f3n"},
            {stateId: 5 , name: "Aprobado"},
            {stateId: 4 , name: "Rechazado"}
        ];

        self.getUserFittingHitory = function () {
            UserFittingService.getUserFittingHistory($scope.athleteUserId, function (res) {
                $scope.userFittingHistory = res.data.output;
            });

        };
        
        $scope.changeState = function(stateId, id){
             UserFittingService.changeState(stateId, id).then(
                    function(data){
                        $scope.showConfirmation();
                        self.init();
                    },
                    function(error){
                        console.log(error);
                    }
                    
                    );
        };


        $scope.showConfirmation = function () {
                $mdDialog.show({
                    scope: $scope.$new(),
                    templateUrl: 'static/views/userFittingHistory/confirmation.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: false,
                    fullscreen: $scope.customFullscreen,
                    controller: function () {
                        
                       $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                    }
                });
        };


        self.init = function () {
            self.getUserFittingHitory();
        };

        self.init();

    }]);