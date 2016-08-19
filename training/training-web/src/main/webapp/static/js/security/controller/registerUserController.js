
trainingApp.controller('RegisterUserController', ['$scope', 'UserService', '$window', 'DisciplineService','RoleService' ,function ($scope, UserService,
            $window, DisciplineService,RoleService) {
        var self = this;
        $scope.user = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', phone: '', countryId: '',
            disciplineId: '',stateId: '', roleId: ''};
        $scope.users = [];
        $scope.countries = [];
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
        $scope.disciplines = [];
        $scope.roles = [];
        
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
                            function (msg) {
                                $scope.fetchAllUsers();
                                $scope.showMessage("Usuario registrado correctamente.");
                            },
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );
        };

        self.updateUser = function (user) {
            UserService.mergeInternalUser(user)
                    .then(
                            function (msg) {
                                $scope.fetchAllUsers();
                                $scope.showMessage("Usuario editado correctamente.");
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
            console.log('id to be deleted', id);
            if ($scope.user.userId === id) {//clean form if the user to be deleted is shown there.
                self.resetUser();
            }
            self.deleteUser(id);
        };


        $scope.resetUser = function () {
            $scope.user = {firstName: '', secondName: '', login: '', password: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage: '', countryId: '', profilePhoto: '', age: ''};
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
        $scope.fetchAllRoles();
        $scope.fetchAllUsers();
        this.getSportDisciplines();
        self.fetchAllCountries();

    }]);