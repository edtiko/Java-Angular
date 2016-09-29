trainingApp.controller("ExternalCoachController", ['$scope', 'ExternalCoachService', 'UserService', '$window', '$mdDialog', function ($scope, ExternalCoachService, UserService, $window, $mdDialog) {

        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athleteUser = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', countryId: '', disciplineId: $scope.userSession.disciplineId, phone: ''};
        $scope.coachExtAthlete = {id: '', athleteUserId: $scope.athleteUser, trainingPlanUserId: $scope.userSession.trainingPlanUserId};
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
        $scope.athletes = [];
        $scope.retired = "3"; //Estado RETIRADO

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
        self.fetchAthletes = function () {
            ExternalCoachService.fetchAthletes($scope.userSession.trainingPlanUserId, "ALL")
                    .then(
                            function (response) {
                                $scope.athletes = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching athletes');
                            }
                    );
        };

        self.createAthlete = function (coachExtAthlete) {
            ExternalCoachService.createAthlete(coachExtAthlete)
                    .then(
                            function (response) {
                                if (response.entity.status == 'success') {
                                    $scope.resetAthlete();
                                    self.fetchAthletes();
                                    $scope.showMessage(response.entity.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );

        };

        $scope.showRemoveAthleteCoach = function (id, name) {
            var confirm = $mdDialog.confirm()
                    .title('Confirmar')
                    .textContent('\u00BFEsta seguro de retirar el atleta: ' + name + ' ?')
                    .ariaLabel('retire')
                    .ok('Si')
                    .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                self.removeAthleteCoach(id);
            }, function () {

            });
        };

        self.removeAthleteCoach = function (id) {
            ExternalCoachService.removeAthleteCoach(id)
                    .then(
                            function (response) {
                                if (response.entity.status == 'success') {
                                    self.fetchAthletes();
                                    $scope.showMessage(response.entity.output);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while deleting User.');
                            }
                    );
        };

        $scope.submitAthlete = function () {

            self.createAthlete($scope.coachExtAthlete);

        };

        $scope.resetAthlete = function () {
            $scope.athleteUser = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', countryId: '', phone: ''};
            //$scope.myFormUser.$setPristine(); 
        };

        self.fetchAllCountries();
        self.fetchAthletes();


    }]);