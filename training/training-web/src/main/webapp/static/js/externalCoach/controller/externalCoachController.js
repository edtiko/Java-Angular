trainingApp.controller("ExternalCoachController", ['$scope', 'ExternalCoachService', 'UserService', '$window', '$mdDialog', '$q', function ($scope, ExternalCoachService, UserService, $window, $mdDialog, $q) {

        var self = this;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.athleteUser = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', countryId: '', disciplineId: $scope.userSession.disciplineId, phone: ''};
        $scope.coachExtAthlete = {id: '', athleteUserId: $scope.athleteUser, coachUserId:{ userId: $scope.userSession.userId}, trainingPlanUserId: $scope.userSession.trainingPlanUserId};
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
        $scope.athletes = [];
        $scope.retired = "3"; //Estado RETIRADO
        $scope.userSelected = null;
         
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
        $scope.users = [];
       loadAthletes("ALL");

        $scope.querySearch = function (query) {
             return  false;

        };
       

    $scope.searchTextChange = function (query) {
        $scope.userSelected = null;
        var results = query ? $scope.repos.filter(createFilterFor(query)) : $scope.repos;

        if (results.length > 0) {
            $scope.repos = results;
        } else {
          $scope.repos = $scope.users;
        }
    };



    function createFilterFor(query) {
        var lowercaseQuery = angular.lowercase(query);

        return function filterFn(item) {
            var value = removeAccents(item.fullName.toLowerCase());
            return (value.indexOf(lowercaseQuery) === 0);
        };

    }

    $scope.ignoreAccents = function (item) {
        if (!$scope.search)
            return true;
        var text = removeAccents(item.fullName.toLowerCase());
        var search = removeAccents($scope.search.toLowerCase());
        return text.indexOf(search) > -1;
    };

    function removeAccents(source) {

        var accent = [
            /[\300-\306]/g, /[\340-\346]/g, // A, a
            /[\310-\313]/g, /[\350-\353]/g, // E, e
            /[\314-\317]/g, /[\354-\357]/g, // I, i
            /[\322-\330]/g, /[\362-\370]/g, // O, o
            /[\331-\334]/g, /[\371-\374]/g, // U, u
            /[\321]/g, /[\361]/g, // N, n
            /[\307]/g, /[\347]/g, // C, c
        ],
                noaccent = ['A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'N', 'n', 'C', 'c'];

        for (var i = 0; i < accent.length; i++) {
            source = source.replace(accent[i], noaccent[i]);
        }

        return source;

    } // removeAccents

        $scope.selectedItemChange = function(item) {
           $scope.userSelected = item;
        };

        /**
         * Build `components` list of key/value pairs
         */
        function loadAthletes(search){
                var deferred = $q.defer();
            ExternalCoachService.loadAthletes(search).then(
                            function (response) {

                                $scope.repos = response;
                                $scope.users = response;
                                deferred.resolve(response);

                            },
                            function (errResponse) {
                                console.error('Error while get athletes.');
                            }
                    );
            
             return deferred.promise;
        }
        ;

        /**
         * Create filter function for a query string
         */
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(item) {
                var name = item.fullName.toLowerCase();
                var value = name.indexOf(lowercaseQuery) === 0;
                return value;
            };

        }
        
        
        $scope.sendInvitation = function () {
            $scope.coachExtAthlete.athleteUserId.userId = $scope.userSelected.userId;
            ExternalCoachService.sendInvitation($scope.coachExtAthlete).then(
                    function (response) {
                        if (response.entity.status == 'success') {
                            self.fetchAthletes();
                            $scope.showMessage(response.entity.output);
                             self.clear();
                        } else {
                            $scope.showMessage(response.entity.detail, "Error");
                        }

                    },
                    function (errResponse) {
                        console.error('Error while get athletes.');
                    }
            );

        };
        
        self.clear = function () {
            $scope.userSelected = null;
            $scope.searchText = '';
        };


    }]);