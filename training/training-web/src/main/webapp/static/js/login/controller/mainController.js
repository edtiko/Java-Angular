// create the controller and inject Angular's $scope
trainingApp.controller('mainController', ['$http', '$scope', 'AuthService', 'VisibleFieldsUserService',
    '$window', '$mdDialog','$mdToast', function ($http, $scope, AuthService, VisibleFieldsUserService, $window, $mdDialog,$mdToast) {

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
        $scope.showToast = function (msg) {
           // var pinTo = $scope.getToastPosition();
            $mdToast.show(
                    $mdToast.simple()
                    .textContent(msg)
                    .position("right")
                    .hideDelay(3000)
                    );
        };

        $scope.closeToast = function () {
            $mdToast.hide();
        };
        /*$scope.showMessage = function (msg, type) {
            $scope.message = msg;
            //1 es un tooltip
            if (type == 1) {
                ngDialog.open({
                    template: 'static/tmpls/tooltip.html',
                    className: 'ngdialog-theme-plain',
                    scope: $scope
                });
            } else {
                ngDialog.open({
                    template: 'static/tmpls/dialogConfirmation.html',
                    className: 'ngdialog-theme-plain',
                    scope: $scope
                });
            }

        };*/
          $scope.showMessage = function(msg, title) {
    // Appending dialog to document.body to cover sidenav in docs app
    // Modal dialogs should fully cover application
    // to prevent interaction outside of dialog
    
    var titleDefault = 'Confirmaci\u00f3n';
    if(title != ""){
        titleDefault = title;
    }
    
    
    $mdDialog.show(
      $mdDialog.alert()
        .parent(angular.element(document.querySelector('#user-container')))
        .clickOutsideToClose(true)
        .title(titleDefault)
        .textContent(msg)
        .ariaLabel('Alert Dialog Demo')
        .ok('Aceptar')
        //.targetEvent(ev)
    );
  };

        $scope.clear = function () {
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

        $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = 'dd/MM/yyyy';
        $scope.altInputFormats = ['M!/d!/yyyy'];

        $scope.popup = {
            opened: false
        };

        $scope.getUserSessionByResponse = function (res) {
            if (res.data.entity.output == null) {
                $scope.showMessage("El usuario no se encuentra logueado");
                $scope.logout();
                return res;
            }

            $scope.appReady = true;
            if (res.data.entity.output.secondName == null || res.data.entity.output.secondName == 'undefined') {
                $scope.userLogin = res.data.entity.output.firstName + " " + res.data.entity.output.lastName;
            } else {
                $scope.userLogin = res.data.entity.output.firstName + " " + res.data.entity.output.secondName + " " + res.data.entity.output.lastName;
            }
            $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.entity.output));
            return JSON.parse(sessionStorage.getItem("userInfo"));
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

        $scope.getUserSession = function (fn) {
            $http.get($contextPath + '/user/getUserSession')
                    .then(fn, function (errResponse) {
                        console.error('Error while getting ' + errResponse);
                    });
        };
        $scope.setUserSession();

        $scope.getVisibleFieldsUserByUser = function (user) {
            if (user != null) {
                VisibleFieldsUserService.getVisibleFieldsUserByUser(user)
                        .then(
                                function (response) {
                                    $scope.fields = response;
                                    for (var i = 0; i < $scope.fields.length; i++) {
                                        $scope[$scope.fields[i].tableName+$scope.fields[i].columnName] = true;
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
            if (birthday != null) {
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

trainingApp.directive('schrollBottom', function () {
  return {
    scope: {
      schrollBottom: "="
    },
    link: function (scope, element) {
      scope.$watchCollection('schrollBottom', function (newValue) {
        if (newValue)
        {
          $(element).scrollTop($(element)[0].scrollHeight);
        }
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