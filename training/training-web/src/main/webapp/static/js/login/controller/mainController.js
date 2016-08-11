// create the controller and inject Angular's $scope
trainingApp.controller('mainController', ['$scope', 'AuthService', '$window', function ($scope, AuthService, $window) {
  
        $scope.successTextAlert = "";
        $scope.appReady = true;
        $scope.userLogin = "";
        $scope.session = {apiKey:'',sessionId:'', token:''};
        $scope.showSuccessAlertUser = false;
        $scope.showSuccessAlertDeportivos = false;
        $scope.showSuccessAlertEncuesta = false;
        
        this.getSessionOpenTok = function(){
            AuthService.getSessionOpenTok($scope).then(
                    function (d) {
                    },
                    function (errResponse) {
                        console.error('Error while get session open tok');
                        console.error(errResponse);
                    }
            );
        };

        $scope.switchBool = function (id) {
             var e = angular.element('#'+id);
             e.hide();
            //$scope[value] = !$scope[value];
        };

        $scope.showMessage = function (msg, type) {
            $scope.successTextAlert = msg;
            var e = angular.element('.messages');
            if (type === 'error') {
                e.removeClass('alert-success');
                e.addClass('alert-danger');

            } else {
                e.removeClass('alert-danger');
                e.addClass('alert-success');
            }
            //document.body.scrollTop = document.documentElement.scrollTop = 0;

        };

        $scope.clear = function () {
            $scope.dt = null;
        };

        $scope.dateOptions = {
            //dateDisabled: disabled,
            formatYear: 'yyyy',
            maxDate: new Date(2006, 1, 1),
            //minDate: new Date(),
            startingDay: 1
        };

 
        $scope.open = function () {
            $scope.popup.opened = true;
        };

        $scope.setDate = function (year, month, day) {
            $scope.dt = new Date(year, month, day);
        };

        $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = 'dd/MM/yyyy';
        $scope.altInputFormats = ['M!/d!/yyyy'];

        $scope.popup = {
            opened: false
        };

  

        this.setUserSession = function () {
            AuthService.setUserSession($scope).then(
                    function (d) {
                    },
                    function (errResponse) {
                        console.error('Error while merging the profile');
                        console.error(errResponse);
                    }
            );
        };
        this.setUserSession();
        this.getSessionOpenTok();

        this.getUserSession = function () {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            if (user != null) {
                $scope.appReady = true;
            }
            return user;
        };
        
        $scope.logout = function () {
            window.location = 'http://181.143.227.220:8081/cpt/my-account/customer-logout/';
        };
    }]);

trainingApp.directive('stringToNumber', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (value) {
                return '' + value;
            });
            ngModel.$formatters.push(function (value) {
                return parseFloat(value);
            });
        }
    };
});

function getDate() {
    var d = new Date();
    var ano = d.getFullYear();
    var mes = (d.getMonth() + 1);
    var dia = d.getDate();

    if (mes < 10) {
        mes = '0' + mes;
    }

    if (dia < 10) {
        dia = '0' + dia;
    }

    var fechaRecibo = ano + '-' + mes + '-' + dia;
    return fechaRecibo;
}