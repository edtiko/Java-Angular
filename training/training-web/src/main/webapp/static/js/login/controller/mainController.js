// create the controller and inject Angular's $scope
trainingApp.controller('mainController', ['$scope', 'AuthService', 'VisibleFieldsUserService', '$window', 'ngDialog', function ($scope, AuthService, VisibleFieldsUserService, $window, ngDialog) {

        $scope.successTextAlert = "";
        $scope.fields = [];
        $scope.visibleFields = [];
        $scope.appReady = true;
        $scope.userLogin = "";

        $scope.switchBool = function (id) {
            var e = angular.element('#' + id);
            e.hide();
            //$scope[value] = !$scope[value];
        };

        $scope.showMessage = function (msg, type) {
            $scope.message = msg;
            
            ngDialog.open({
                template: 'static/tmpls/dialogConfirmation.html',
                className: 'ngdialog-theme-plain',
                scope: $scope
            });

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


        /*$scope.toggleMin = function () {
         $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
         $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
         };
         
         $scope.toggleMin();*/

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



        $scope.setUserSession = function () {
            AuthService.setUserSession($scope).then(
                    function (d) {
                    },
                    function (errResponse) {
                        console.error('Error while merging the profile');
                        console.error(errResponse);
                    }
            );
        };
        $scope.setUserSession();

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
                                    for (var i = 0; i < $scope.fields.length; i++) {
                                        if (!$scope.inFieldsArray({tableName: $scope.fields[i].tableName, columnName: $scope.fields[i].columnName, userId: user.userId}, $scope.visibleFields)) {
                                            $scope.visibleFields.push({tableName: $scope.fields[i].tableName, columnName: $scope.fields[i].columnName, userId: user.userId});
                                        }
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Visible Fields');
                                    console.error(errResponse);
                                }
                        );
            }
        };
        
        $scope.inFieldsArray = function (field, array) {
            var length = array.length;
            for (var i = 0; i < length; i++) {
                if (array[i].tableName == field.tableName && array[i].columnName == field.columnName)
                    return true;
            }
            return false;
        };
        
        $scope.calculateAge = function (birthday) { // birthday is a date
            if(birthday != null){
            var ageDifMs = Date.now() - birthday.getTime();
            var ageDate = new Date(ageDifMs); // miliseconds from epoch
            return Math.abs(ageDate.getUTCFullYear() - 1970);
        }
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