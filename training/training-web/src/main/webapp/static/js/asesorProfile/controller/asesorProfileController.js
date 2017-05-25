
trainingApp.controller('AsesorProfileController', ['$scope', 'UserService', '$window', '$location', 'VisibleFieldsUserService', '$mdDialog', 
    function ($scope, UserService, $window, $location, VisibleFieldsUserService, $mdDialog) {
        var self = this;
        $scope.currentNavItem = 0;
        $scope.profileImage = $window.sessionStorage.getItem("profileImage");
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.steps = [
            {
                templateUrl: 'static/views/asesorProfile/step1.html',
                hasForm: true,
                title: 'DATOS PERSONALES'
            },
            {
                templateUrl: 'static/views/asesorProfile/step2.html',
                hasForm: true,
                title: 'DATOS DE CONTACTO'
            }
        ];
        $scope.onStepChange = function (index, event) {

            if (index == 2 || index == 3) {
                $scope.submitUser();
            }
            $scope.currentNavItem = index - 1;
            //console.log("el paso activo es:" + index);
        };

        $scope.user = {userId: null, firstName: '', secondName: '', login: '', password: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage: '', instagramPage: '', twitterPage: '', webPage: '', countryId: '', profilePhoto: '', age: ''};
        $scope.countries = [];
        $scope.states = [];
        $scope.cities = [];
        $scope.birthdateDt = null;
        $scope.dataImage = "static/img/profile-default.png";
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
        $scope.isImage = false;
        $scope.errorMessages = [];
        
         $scope.validate = function (step) {
            var res = true;
            if (step == 1) {
                if ($scope.user.firstName == null) {
                    var uname = angular.element(document.querySelector('#uname'));
                    uname.blur();
                    window.scrollTo(0, 10);
                    res = false;
                }
                if ($scope.user.secondName == null) {
                    var secondName = angular.element(document.querySelector('#secondName'));
                    secondName.blur();
                    window.scrollTo(0, 10);
                    res = false;
                }
                if ($scope.user.lastName == null) {
                    var lastName = angular.element(document.querySelector('#lname'));
                    lastName.blur();
                    window.scrollTo(0, 10);
                    res = false;
                }
                if ($scope.user.sex == null) {
                    var sex = angular.element(document.querySelector('#sex'));
                    sex.blur();
                    window.scrollTo(0, 10);
                    res = false;
                }
                if ($scope.birthdateDt == null) {
                    var birthDate = angular.element(document.querySelector('#birthDate'));
                    birthDate.blur();
                    res = false;
                    //window.scrollTo(0, 10);
                }

            } else if (step == 2) {
                if ($scope.user.email == null) {
                    var email = angular.element(document.querySelector('#email'));
                    email.blur();
                    window.scrollTo(0, 10);
                    res = false;
                }

            } 
            
            return res;

        };


        self.fetchAllCountries = function () {
            UserService.fetchAllCountries()
                    .then(
                            function (response) {
                                $scope.countries = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching Currencies');
                            }
                    );
        };
        $scope.getStatesByCountry = function (countryId, change) {
            if (change) {
                $scope.user.cityId = '';
                $scope.user.federalStateId = '';
            }
            if (countryId != null) {
                UserService.getStatesByCountry(countryId)
                        .then(
                                function (response) {
                                    $scope.states = response;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            }
        };
        $scope.getCitiesByState = function (stateId, change) {
            if (change) {
                $scope.user.cityId = '';
            }
            if (stateId != null) {
                UserService.getCitiesByState(stateId)
                        .then(
                                function (response) {
                                    $scope.cities = response;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies');
                                }
                        );
            }
        };

        $scope.showAge = function (d) {
            $scope.birthdateDt = d;

            var date = d.split("/");
            var obj = new Date(date[2], date[1], date[0]);
            $scope.user.age = $scope.calculateAge(obj);
        };

        self.getUserById = function () {

            UserService.getUserById($scope.userSession.userId)
                    .then(
                            function (d) {

                                $scope.user = d;

                                $scope.getStatesByCountry($scope.user.countryId);
                                $scope.getCitiesByState($scope.user.federalStateId);
                                $scope.getImageProfile($scope.user.userId);

                                //$scope.getVisibleFieldsUserByUser($scope.user);

                                if ($scope.user.birthDate != null) {
                                    var date = $scope.user.birthDate.split("/");
                                    var obj = new Date(date[2], date[1], date[0]);
                                    $scope.birthdateDt = $scope.user.birthDate;
                                    $scope.user.age = $scope.calculateAge(obj);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while fetching Currencies');
                            }
                    );

        };

       /* $scope.visibleField = function (tableName, columnName) {
            for (var i = 0; i < $scope.fields.length; i++) {
                if ($scope.fields[i].tableName == tableName && $scope.fields[i].columnName == columnName) {
                    return true;
                }
            }
            return false;
        };

        $scope.setVisibleField = function (value, tableName, columnName) {
            if (value === true) {
                $scope.visibleFields.push({tableName: tableName, columnName: columnName, userId: $scope.user.userId});
            } else {
                $scope.deleteFieldInArray({tableName: tableName, columnName: columnName, userId: $scope.user.userId}, $scope.visibleFields);
            }
        };

        $scope.deleteFieldInArray = function (field, array) {
            var length = array.length;
            for (var i = 0; i < length; i++) {
                if (array[i].tableName == field.tableName && array[i].columnName == field.columnName) {
                    $scope.visibleFields.splice(i, 1);
                    break;
                }
            }
        };*/

        $scope.getUserSession(function (res) {
            $window.sessionStorage.setItem("userInfo", JSON.stringify(res.data.output));
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            $scope.userSession = user;

            self.fetchAllCountries();
            self.getUserById();
        });
        
        $scope.isImage = function (type) {
            if (type.indexOf("image") !== -1) {
                return false;
            }
            return true;
        };
        
        $scope.getImageProfile = function (userId) {
            if (userId != null) {
                UserService.getImageProfile(userId)
                        .then(
                                function (response) {
                                    if (response != "") {
                                        $scope.profileImage = "data:image/png;base64," + response;
                                    } else {
                                        $scope.profileImage = "static/img/profile-default.png";
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Image Profile');
                                }
                        );
            }
        };


        $scope.uploadFile = function (file) {

            if (file.files[0] !== undefined) {
                var file = file.files[0];
            }
            if (file !== undefined && $scope.isImage(file.type)) {
                $scope.showMessage("Debe seleccionar una imagen valida.", "error");
            } else if ($scope.user.userId != "" && file != null) {

                UserService.uploadFileToUrl(file, $scope.user.userId)
                        .then(
                                function (msg) {
                                    $scope.showMessage("Imagen cargada correctamente.");
                                    $scope.getImageProfile($scope.user.userId);
                                },
                                function (errResponse) {
                                    console.error('Error while upload image user.');
                                }
                        );
            } else {
                $scope.showMessage("Debe seleccionar una imagen.", "error");
            }
        };


        self.updateUser = function (user, id, file) {
            user.birthDate = $scope.birthdateDt;
            var userUpdate = user;
            userUpdate.profilePhoto = '';
            userUpdate.profilePhotoBase64 = '';
            UserService.updateUser(user, id)
                    .then(
                            function (msg) {
                                $scope.editConfirmation();
                                //$scope.getVisibleFieldsUserByUser();
                                if (file !== undefined && file != null) {
                                    $scope.uploadFile(file);
                                }

                            },
                            function (errResponse) {
                                $scope.editConfirmation();
                                console.error('Error while updating User.' + errResponse);
                            }
                    );
            /*VisibleFieldsUserService.createVisibleFieldsUser(user.userId, $scope.visibleFields).then(
                    function (msg) {
                        $scope.setUserSession();
                    },
                    function (errResponse) {
                        console.error('Error while creating visible fields.');
                        console.error(errResponse);
                    }
            );*/
        };

        $scope.editConfirmation = function () {
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/views/asesorProfile/editConfirmation.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }
            });
        };

        $scope.errorConfirmation = function () {
            $mdDialog.show({
                scope: $scope.$new(),
                templateUrl: 'static/views/asesorProfile/errorMessage.html',
                parent: angular.element(document.querySelector('#trainingApp')),
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }
            });
        };

        $scope.submitUser = function (file) {

            self.updateUser($scope.user, $scope.user.userId, file);

        };




    }]);