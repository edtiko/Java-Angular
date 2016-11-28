trainingApp.controller('RegisterUserController', ['$scope', 'UserService', '$window', '$mdDialog',
    'DisciplineService', 'RoleService',
    function ($scope, UserService, $window, $mdDialog, DisciplineService, RoleService) {
        
        var self = this;
        $scope.user = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', phone: '', countryId: '',
            disciplineId: '', stateId: '', roleId: '', profilePhoto: '', urlVideo: '', aboutMe: '', userCreate: '', updateUser: ''};
        $scope.userList = [];
        $scope.countries = [];
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
        $scope.disciplines = [];
        $scope.roles = [];
        $scope.profileImage = "static/img/profile-default.png";
        $scope.selected = [];
        $scope.count = 0;
        var bookmark;

        $scope.filter = {
            options: {
                debounce: 500
            }
        };

        $scope.query = {
            filter: '',
            order: 'name',
            limit: 10,
            page: 1
        };

        $scope.getUserPaginate = function () {
            $scope.promise = UserService.getPaginate($scope.query, function (response) {
                $scope.userList = success(response);

                if ($scope.userList.length > 0) {
                    $scope.count = $scope.userList[0].count;
                }
            }).$promise;
        };

        function success(response) {
            if (response.data.status == 'fail') {
                $scope.showMessage(response.data.output);
            } else {
                return response.data.output;
            }

            return null;
        }

        $scope.fetchAllUsers = function () {
            UserService.fetchAllUsers()
                    .then(
                            function (d) {
                                $scope.userList = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching userList');
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

        self.createUser = function (user,file) {
            UserService.createInternalUser(user)
                    .then(
                            function (d) {
                                if (d.detail == null) {
                                    $scope.showMessage("Usuario registrado correctamente.");
                                    $scope.profileImage = "static/img/profile-default.png";
                                    $scope.user = d.output;
                                    if (file !== undefined && file != null) {
                                        $scope.uploadFile(file);
                                    }
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

        $scope.submitUser = function (form, file) {
            $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
            if (form.$valid) {
                if ($scope.user.userId === null) {
                $scope.user.userCreate = $scope.userSession.userId;
                self.createUser($scope.user,file);
            } else {
                $scope.user.userUpdate = $scope.userSession.userId;
                self.updateUser($scope.user,file);
            }
            } else {
                form.$setSubmitted();
            }
        };       

        $scope.editUser = function (id, ev) {
            $scope.showCreateUser(ev);
            
            for (var i = 0; i < $scope.userList.length; i++) {
                if ($scope.userList[i].userId === id) {
                    $scope.user = angular.copy($scope.userList[i]);
                    $scope.getImageProfile($scope.userList[i].userId);
                    break;
                }
            }
            
        };

        $scope.inactivateUser = function (id) {
            for (var i = 0; i < $scope.userList.length; i++) {
                if ($scope.userList[i].userId === id) {
                    $scope.user = angular.copy($scope.userList[i]);
                    break;
                }
            }
            $scope.user.stateId = 0;
            self.updateUser($scope.user);
            $scope.resetUser();
        };

        $scope.activateUser = function (id) {
            for (var i = 0; i < $scope.userList.length; i++) {
                if ($scope.userList[i].userId === id) {
                    $scope.user = angular.copy($scope.userList[i]);
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

        $scope.removeFilter = function () {
            $scope.filter.show = false;
            $scope.query.filter = '';

            if ($scope.filter.form.$dirty) {
                $scope.filter.form.$setPristine();
            }
        };

        $scope.$watch('query.filter', function (newValue, oldValue) {
            if (!oldValue) {
                bookmark = $scope.query.page;
            }

            if (newValue !== oldValue) {
                $scope.query.page = 1;
            }

            if (!newValue) {
                $scope.query.page = bookmark;
            }

            $scope.getUserPaginate();
        });

        $scope.openUser = function (ev) {

            $scope.user = {userId: null,
                login: '',
                firstName: '',
                secondName: '',
                email: '',
                disciplineId: '',
                sex: '',
                phone: '',
                countryId: '',
                roleId: '',
                description: '',
                urlVideo: '',
                stateId: '',
                userCreate: '', userUpdate: '', userCreateName: '', userUpdateName: ''};
            $scope.showCreateUser(ev);

        };

        $scope.showCreateUser = function (ev) {

            $mdDialog.show({
                controller: UserController,
                scope: $scope.$new(),
                templateUrl: 'static/views/security/create-user.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen, // Only for -xs, -sm breakpoints.
                resolve: {
                    disciplineList: function () {
                        return $scope.disciplines;
                    },
                    countries: function () {
                        return $scope.countries;
                    },
                    roleList: function () {
                        return $scope.roles;
                    },
                    user: function () {
                        return $scope.user;
                    }

                }
            })
                    .then(function (answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function () {
                        $scope.status = 'You cancelled the dialog.';
                   });
        };

        function UserController($scope, $mdDialog,
                disciplineList,
                countries,
                roleList,
                user) {
            
            $scope.disciplines = disciplineList;
            $scope.countries = countries;
            $scope.roles = roleList;
            $scope.user = user;

            $scope.hide = function () {
                $mdDialog.hide();
            };
            $scope.cancel = function () {
                $mdDialog.cancel();
            };

        }
        
        $scope.resetUser = function () {
            $scope.user = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', phone: '', countryId: '',
                disciplineId: '', stateId: '', roleId: ''};
//            $scope.formUser.$setPristine(); //reset Form
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
                                $scope.roles = d.output;
                            },
                            function (errResponse) {
                                console.error('Error while fetching roles');
                            }
                    );
        };

        $scope.uploadFile = function (file) {

            //var file = $scope.myFile;
            if (file !== undefined && $scope.isImage(file.type)) {
                $scope.showMessage("Debe seleccionar una imagen valida.", "error");
                //$window.alert("Debe seleccionar una imagen valida.");
            } else if ($scope.user.userId != "" && file != null) {
                UserService.uploadFileToUrl(file, $scope.user.userId)
                        .then(
                                function (msg) {
                                    $scope.showMessage("Imagen cargada correctamente.");
                                    $scope.getImageProfile($scope.user.userId);
                                    $scope.resetUser();

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
        $scope.getUserPaginate();
        $scope.fetchAllRoles();
        this.getSportDisciplines();
        self.fetchAllCountries();
        

        $scope.deleteUser = function (user) {
            var confirm = $mdDialog.confirm()
                    .title('Confirmaci\u00f3n')
                    .textContent('\u00BFDesea eliminar el registro?')
                    .ariaLabel('Lucky day')
                    .ok('Aceptar')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {

                UserService.deleteUser(user)
                        .then(
                                function (d) {
                                    if (d.status == 'success') {
                                        $scope.resetUser();
                                        $scope.showMessage(d.output);
                                        $scope.getUserPaginate();
                                    } else {
                                        $scope.showMessage(d.output);
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while deleting User.');
                                }
                        );
            }, function () {
            });
        };
            
        $scope.getUserPaginate();
        this.getSportDisciplines();
        self.fetchAllCountries();
        $scope.fetchAllRoles();

    }]);



