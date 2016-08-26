
trainingApp.controller('RegisterUserController', ['$scope', 'UserService', '$window', 'DisciplineService','RoleService' ,function ($scope, UserService,
            $window, DisciplineService,RoleService) {
        var self = this;
        $scope.user = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', phone: '', countryId: '',
            disciplineId: '',stateId: '', roleId: '', profilePhoto: '', urlVideo:'',aboutMe:''};
        $scope.users = [];
        $scope.countries = [];
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
        $scope.disciplines = [];
        $scope.roles = [];
        $scope.profileImage = "static/img/profile-default.png";
        
        $scope.fetchAllUsers = function () {
            UserService.fetchAllUsers()
                    .then(
                            function (d) {
                                $scope.users = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching users');
                            }
                    );
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

        self.createUser = function (user) {
            UserService.createInternalUser(user)
                    .then(
                            function (d) {
                                if (d.detail == null) {
                                    $scope.showMessage("Usuario registrado correctamente.");
                                    $scope.resetUser();
                                    $scope.profileImage = "static/img/profile-default.png";
                                    $scope.fetchAllUsers();
                                } else {
                                    $scope.showMessage(d.detail);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );
        };

        self.updateUser = function (user) {
            UserService.mergeInternalUser(user)
                    .then(
                            function (d) {
                                if (d.detail == null) {
                                    $scope.resetUser();
                                    $scope.showMessage("Usuario editado correctamente.");
                                    $scope.profileImage = "static/img/profile-default.png";
                                    $scope.fetchAllUsers();
                                } else {
                                    $scope.showMessage(d.detail);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while updating User.');
                            }
                    );
        };

        self.deleteUser = function (id) {
            UserService.deleteUser(id)
                    .then(
                            self.fetchAllUsers,
                            function (errResponse) {
                                console.error('Error while deleting User.');
                            }
                    );
        };

        $scope.submitUser = function () {
            if ($scope.user.userId === null) {
                self.createUser($scope.user);
            } else {
                self.updateUser($scope.user);
            }
        };

        $scope.editUser = function (id) {
            for (var i = 0; i < $scope.users.length; i++) {
                if ($scope.users[i].userId === id) {
                    $scope.user = angular.copy($scope.users[i]);
                    $scope.getImageProfile($scope.users[i].userId);
                    break;
                }
            }
        };
        
        $scope.inactivateUser = function (id) {
            for (var i = 0; i < $scope.users.length; i++) {
                if ($scope.users[i].userId === id) {
                    $scope.user = angular.copy($scope.users[i]);
                    break;
                }
            }
            $scope.user.stateId = 0;
            self.updateUser($scope.user);
            $scope.resetUser();
        };
        
        $scope.activateUser = function (id) {
            for (var i = 0; i < $scope.users.length; i++) {
                if ($scope.users[i].userId === id) {
                    $scope.user = angular.copy($scope.users[i]);
                    break;
                }
            }
            $scope.user.stateId = 1;
            self.updateUser($scope.user);
        };

        self.remove = function (id) {
            if ($scope.user.userId === id) {
                self.resetUser();
            }
            self.deleteUser(id);
        };


        $scope.resetUser = function () {
            $scope.user = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', phone: '', countryId: '',
            disciplineId: '',stateId: '', roleId: ''};
            $scope.formUser.$setPristine(); //reset Form
        };

        this.getSportDisciplines = function () {
            DisciplineService.getSportDisciplines().then(
                    function (d) {
                        $scope.disciplines = d;
                    },
                    function (errResponse) {
                        console.error('Error while disciplines');
                        console.error(errResponse);
                    }
            );
        };
        
        $scope.fetchAllRoles = function () {
            RoleService.getRoles()
                    .then(
                            function (d) {
                                $scope.roles = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching roles');
                            }
                    );
        };
        
        $scope.uploadFile = function (file) {

            //var file = $scope.myFile;
            if(file !== undefined && $scope.isImage(file.type)){
                $scope.showMessage("Debe seleccionar una imagen valida.", "error"); 
                //$window.alert("Debe seleccionar una imagen valida.");
            } else if ($scope.user.userId != "" && file != null) {

                console.log('file is ');
                console.dir(file);
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
        $scope.fetchAllRoles();
        $scope.fetchAllUsers();
        this.getSportDisciplines();
        self.fetchAllCountries();

    }]);