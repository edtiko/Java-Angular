trainingApp.controller("ExternalCoachController", ['$scope', 'ExternalCoachService','UserService', function ($scope, ExternalCoachService, UserService) {

        var self = this;
        $scope.user = {userId: null, firstName: '', secondName: '', login: '', lastName: '', email: '', sex: '', password: '', countryId: '',
            disciplineId: '', phone: ''};

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
            ExternalCoachService.fetchAthletes()
                    .then(
                            function (response) {
                                $scope.athletes = response;
                            },
                            function (errResponse) {
                                console.error('Error while fetching athletes');
                            }
                    );
        };
        
         self.createUser = function (user) {
            ExternalCoachService.createAthlete(user)
                    .then(
                            function (d) {
               
                            },
                            function (errResponse) {
                                console.error('Error while creating User.');
                            }
                    );
            
        };
        
           $scope.submitAthlete = function () {
  
              self.createUser($scope.user);
          
        };
        
        self.fetchAllCountries();
        self.fetchAthletes();


    }]);