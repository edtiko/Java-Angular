'use strict';

// create the module and name it trainingApp
var trainingApp = angular.module('trainingApp', ['ngRoute', 'frontendServices', 'ui.bootstrap'])
        .config(function ($routeProvider) {
            $routeProvider

                    /*.when('/login', {
                        templateUrl: 'static/views/login.html',
                        controller: 'UserController'
                    })*/
                    // route for the home page
                    .when('/data-person', {
                        templateUrl: 'static/views/datosPersonales/user.html',
                        controller: 'UserController'
                    })

                    // route for the about page
                    .when('/encuesta', {
                        templateUrl: 'static/views/questionnaire/survey.html',
                        controller: 'SurveyController'
                    })

                    // route for the contact page
                    .when('/contact', {
                        templateUrl: 'static/views/contact.html',
                        controller: 'contactController'
                    });
        });

// create the controller and inject Angular's $scope
trainingApp.controller('mainController', function ($scope) {

    $scope.clear = function () {
        $scope.dt = null;
    };


    $scope.dateOptions = {
        //dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        //minDate: new Date(),
        startingDay: 1
    };

    $scope.open = function () {
        $scope.popup.opened = true;
    };

    $scope.setDate = function (year, month, day) {
        $scope.dt = new Date(year, month, day);
    };

    $scope.format = 'dd/MM/yyyy';
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup = {
        opened: false
    };

});

trainingApp.controller('SurveyController', ['$scope', 'SurveyService', function ($scope, SurveyService)  {

    var self = this;
    self.encuesta = {};
    self.sePaginator = { initialRow: "1", maxRow: "10"};

     self.getAllQuestionnaireQuestion = function (paginator) {
            SurveyService.getAllQuestionnaireQuestion(paginator)
                    .then(
                            function (response) {
                                //self.encuesta = response;
                                console.log(response);
                            },
                            function (errResponse) {
                                console.error('Error while fetching Currencies');
                            }
                    );
        };
    self.getAllQuestionnaireQuestion(self.sePaginator);    
}]);

trainingApp.controller('contactController', function ($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});

trainingApp.controller('DatepickerCtrl', function ($scope) {

});


trainingApp.directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);


trainingApp.controller('UserController', ['$scope', 'UserService', function ($scope, UserService) {
        var self = this;
        self.user = {userId: null, name: '', login: '', password: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage: '', countryId: '', profilePhoto: ''};
        self.users = [];
        $scope.countries = [];
        $scope.states = [];
        $scope.cities = [];
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

                       ); }
          };
          self.fetchAllUsers = function(){
              UserService.fetchAllUsers()
                  .then(
                               function(d) {
                                    self.users = d;
                               },
                                function(errResponse){
                                    console.error('Error while fetching Currencies');
                                }
                       );
          };
            
          self.createUser = function(user){
              UserService.createUser(user)
                      .then(
                      self.fetchAllUsers, 
                              function(errResponse){
                                   console.error('Error while creating User.');
                              } 
                  );
          };
 
         self.updateUser = function(user, id){
              UserService.updateUser(user, id)
                      .then(
                              self.fetchAllUsers, 
                              function(errResponse){
                                   console.error('Error while updating User.');
                              } 
                  );
          };
          
            self.authenticateUser = function(login, password){
              UserService.authenticateUser(login, password)
                      .then(
                              function(errResponse){
                                   console.error('Error while authenticate User.');
                              } 
                  );
          };
 
         self.deleteUser = function(id){
              UserService.deleteUser(id)
                      .then(
                              self.fetchAllUsers, 
                              function(errResponse){
                                   console.error('Error while deleting User.');
                              } 
                  );
          };
 
          self.fetchAllUsers();
          self.fetchAllCountries();
 
          self.submit = function() {
              if(self.user.userId===null){
                  console.log('Saving New User', self.user);    
                  self.createUser(self.user);
              }else{
                  self.updateUser(self.user, self.user.userId);
                  console.log('User updated with id ', self.user.userId);
              }
              self.reset();
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

