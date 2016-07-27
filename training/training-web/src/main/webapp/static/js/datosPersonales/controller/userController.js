
trainingApp.controller('UserController', ['$scope', 'UserService', '$filter', function ($scope, UserService, $filter) {
        var self = this;
        self.user = {userId: null, name: '', login: '', password: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage: '', countryId: '', profilePhoto: ''};
        self.users = [];
        $scope.countries = [];
        $scope.states = [];
        $scope.cities = [];
        $scope.dateAsString = null;
        $scope.dt = null;
        $scope.dataImage = "static/img/profile-default.png";
        $scope.sexOptions = {
            m: "Masculino",
            f: "Femenino"
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
        $scope.getStatesByCountry = function (countryId) {
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
        $scope.getCitiesByState = function (stateId) {
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
        $scope.getImageProfile = function (userId) {
            if (userId != null) {
                UserService.getImageProfile(userId)
                        .then(
                                function (response) {
                                    if (response != "") {
                                        $scope.dataImage = "data:image/png;base64," + response;
                                    } else {
                                        $scope.dataImage = "static/img/profile-default.png"
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Image Profile');
                                }
                        );
            }
        };
        self.fetchAllUsers = function () {
            UserService.fetchAllUsers()
                    .then(
                            function (d) {
                                self.users = d;
                            },
                            function (errResponse) {
                                console.error('Error while fetching Currencies');
                            }
                    );
        };

        self.createUser = function (user) {
            UserService.createUser(user)
                    .then(
                            self.fetchAllUsers,
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );
        };

        self.updateUser = function (user, id) {
            UserService.updateUser(user, id)
                    .then(
                            self.fetchAllUsers,
                            function (errResponse) {
                                console.error('Error while updating User.');
                            }
                    );
        };

        self.authenticateUser = function (login, password) {
            UserService.authenticateUser(login, password)
                    .then(
                            function (errResponse) {
                                console.error('Error while authenticate User.');
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

        self.fetchAllUsers();
        self.fetchAllCountries();

        self.submit = function () {
            if (self.user.userId === null) {
                console.log('Saving New User', self.user);
                self.createUser(self.user);
            } else {
                self.updateUser(self.user, self.user.userId);
                console.log('User updated with id ', self.user.userId);
            }
            self.reset();
        };

        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < self.users.length; i++) {
                if (self.users[i].userId === id) {
                    $scope.getStatesByCountry(self.users[i].countryId);
                    $scope.getCitiesByState(self.users[i].federalStateId);
                    $scope.getImageProfile(id);
                    var date = self.users[i].birthDate.split("/");
                    $scope.dt = new Date(date[2], date[1] - 1, date[0]);

                    self.user = angular.copy(self.users[i]);
                    break;
                }
            }
        };

        self.remove = function (id) {
            console.log('id to be deleted', id);
            if (self.user.userId === id) {//clean form if the user to be deleted is shown there.
                self.reset();
            }
            self.deleteUser(id);
        };


        self.reset = function () {
            self.user = {userId: null, name: '', login: '', password: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage: '', countryId: '', profilePhoto: ''};
            $scope.myForm.$setPristine(); //reset Form
        };

        self.login = function () {

            console.log('Loging User', self.user);
            self.authenticateUser(self.user.login, self.user.password);

        };

        $scope.uploadFile = function () {
            var file = $scope.myFile;
            console.log('file is ');
            console.dir(file);
            UserService.uploadFileToUrl(file, self.user.userId);
        };

    }]);