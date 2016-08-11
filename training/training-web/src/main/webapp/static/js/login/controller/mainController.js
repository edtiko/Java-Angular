// create the controller and inject Angular's $scope
trainingApp.controller('mainController', ['$scope', 'AuthService', 'VisibleFieldsUserService', '$window', function ($scope, AuthService, VisibleFieldsUserService, $window) {

        $scope.showSuccessAlert = false;
        $scope.successTextAlert = "";
        $scope.appReady = true;
        $scope.fields = [];
		$scope.dt = new Date();
        $scope.session = {apiKey:'',sessionId:'', token:''};
        
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

        $scope.switchBool = function (value) {
            $scope[value] = !$scope[value];
        };

        $scope.showMessage = function (msg, type) {
            $scope.successTextAlert = msg;
            var e = angular.element('#messages');
            $scope.showSuccessAlert = true;
            if (type === 'error') {
                e.removeClass('alert-success');
                e.addClass('alert-danger');

            } else {
                e.removeClass('alert-danger');
                e.addClass('alert-success');
            }
            document.body.scrollTop = document.documentElement.scrollTop = 0;

        };

        $scope.clear = function () {
            $scope.dt = null;
        };

        $scope.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: true
        };

        $scope.dateOptions = {
            //dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            //minDate: new Date(),
            startingDay: 1
        };

        // Disable weekend selection
        function disabled(data) {
            var date = data.date,
                    mode = data.mode;
            return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
        }

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

        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 1);
        $scope.events = [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];

        function getDayClass(data) {
            var date = data.date,
                    mode = data.mode;
            if (mode === 'day') {
                var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                for (var i = 0; i < $scope.events.length; i++) {
                    var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }

            return '';
        }
        ;

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
        
        $scope.getVisibleFieldsUserByUser = function () {
            var user = JSON.parse(sessionStorage.getItem("userInfo"));
            if (user != null) {
                VisibleFieldsUserService.getVisibleFieldsUserByUser(user)
                        .then(
                                function (response) {
                                    $scope.fields = response;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Visible Fields');
                                    console.error(errResponse);
                                }
                        );
            }
        };
        
        $scope.logout = function () {
            window.location = 'http://181.143.227.220:8081/cpt/my-account/customer-logout/';
        };    }]);

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