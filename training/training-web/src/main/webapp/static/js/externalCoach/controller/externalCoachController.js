trainingApp.controller("ExternalCoachController", ['$scope', 'ExternalCoachService', 'UserService', '$window', '$mdDialog', '$q', function ($scope, ExternalCoachService, UserService, $window, $mdDialog, $q) {

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
                                } else {
                                    $scope.showMessage(response.entity.detail, "Error");
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

        self.simulateQuery = false;
        self.isDisabled = false;

        self.repos = loadAthletes;
        self.querySearch = querySearch;
        self.selectedItemChange = selectedItemChange;
        self.searchTextChange = searchTextChange;

        // ******************************
        // Internal methods
        // ******************************

        /**
         * Search for repos... use $timeout to simulate
         * remote dataservice call.
         */
        function querySearch(query) {
            var results = query ? self.repos.filter(createFilterFor(query)) : self.repos,
                    deferred;
            if (self.simulateQuery) {
                deferred = $q.defer();
                $timeout(function () {
                    deferred.resolve(results);
                }, Math.random() * 1000, false);
                return deferred.promise;
            } else {
                return results;
            }
        }

        function searchTextChange(text) {
            console.info('Text changed to ' + text);
        }

        function selectedItemChange(item) {
            console.info('Item changed to ' + JSON.stringify(item));
        }

        /**
         * Build `components` list of key/value pairs
         */
        function loadAthletes(){
            var repos = [];
            ExternalCoachService.loadAthletes().then(
                            function (response) {

                                repos = response;

                            },
                            function (errResponse) {
                                console.error('Error while get athletes.');
                            }
                    );

            return repos.map(function (repo) {
                repo.value = repo.fullname.toLowerCase();
                return repo;
            });
        };

        /**
         * Create filter function for a query string
         */
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(item) {
                return (item.value.indexOf(lowercaseQuery) === 0);
            };

        }


    }]);