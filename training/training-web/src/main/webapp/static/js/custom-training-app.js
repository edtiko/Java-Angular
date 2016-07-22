'use strict';

//var App = angular.module('myApp',[]);

// create the module and name it trainingApp
var trainingApp = angular.module('trainingApp', ['ngRoute','frontendServices','ui.bootstrap'])
   .config(function ($routeProvider) {
    $routeProvider

            .when('/login', {
                templateUrl: 'static/views/login.html',
                controller: 'UserController'
            })
            // route for the home page
            .when('/data-person', {
                templateUrl: 'static/views/datosPersonales/user.html',
                controller: 'UserController'
            })

            // route for the about page
            .when('/about', {
                templateUrl: 'static/views/about.html',
                controller: 'aboutController'
            })
            
            .when('/calendar', {
                templateUrl: 'static/views/calendar.html',
                controller: 'calendarController'
            })

            // route for the contact page
            .when('/contact', {
                templateUrl: 'static/views/contact.html',
                controller: 'contactController'
            });
});

// create the controller and inject Angular's $scope
trainingApp.controller('mainController', function ($scope) {
    $scope.today = function () {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
        $scope.dt = null;
    };

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: true
    };

    $scope.dateOptions = {
        //dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        //minDate: new Date(),
        startingDay: 1
    };

    // Disable weekend selection
    function disabled(data) {
        var date = data.date,
                mode = data.mode;
        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

    /*$scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();*/

    $scope.open = function () {
        $scope.popup.opened = true;
    };

    $scope.setDate = function (year, month, day) {
        $scope.dt = new Date(year, month, day);
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = 'dd/MM/yyyy';
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
            date: tomorrow,
            status: 'full'
        },
        {
            date: afterTomorrow,
            status: 'partially'
        }
    ];

    function getDayClass(data) {
        var date = data.date,
                mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }
});

trainingApp.controller('aboutController', function ($scope) {
    $scope.message = 'Look! I am an about page.';
});

trainingApp.controller('contactController', function ($scope) {
    $scope.message = 'Contact us! JK. This is just a demo.';
});

trainingApp.controller('DatepickerCtrl', function ($scope) {

});

/**
 * 
 * @param {type} element
 * @returns 
 */
function compileAngularElement(element) {
    var $div = $(element);
    // The parent of the new element
    var $target = $("[ng-app]");
    angular.element($target).injector().invoke(['$compile', function ($compile) {
            var $scope = angular.element($div).scope();
            $compile($div)($scope);
            // Finally, refresh the watch expressions in the new element
            $scope.$apply();
        }]);
}


trainingApp.controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
          var self = this;
          self.user={userId:null,name:'', login: '', password: '', lastName:'',email:'', sex:'', weight:'', phone:'', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage:'', countryId: ''};
          self.users=[];
          $scope.countries=[];
          $scope.states=[];
          $scope.cities=[];
         $scope.sexOptions = {
            m: "Masculino",
            f: "Femenino"
        };    
         self.fetchAllCountries = function(){
              UserService.fetchAllCountries()
                  .then(
                               function(response) {
                                    $scope.countries = response;
                               },
                                function(errResponse){
                                    console.error('Error while fetching Currencies');
                                }
                       );
          };
           $scope.getStatesByCountry = function(countryId){
               if(countryId != null){
              UserService.getStatesByCountry(countryId)
                  .then(
                               function(response) {
                                    $scope.states = response;
                               },
                                function(errResponse){
                                    console.error('Error while fetching Currencies');
                                }
                       );}
          };
           $scope.getCitiesByState = function(stateId){
               if(stateId != null){
              UserService.getCitiesByState(stateId)
                  .then(
                               function(response) {
                                    $scope.cities = response;
                               },
                                function(errResponse){
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
               
        self.edit = function (id) {
            console.log('id to be edited', id);
            for (var i = 0; i < self.users.length; i++) {
                if (self.users[i].userId === id) {
                    $scope.getStatesByCountry(self.users[i].countryId);
                    $scope.getCitiesByState(self.users[i].federalStateId);
                    self.users[i].birthDay = new Date('01-05-1991');
                    self.users[i].postalCode = 'hola';
                    self.user = angular.copy(self.users[i]);
                    break;
                }
            }
        };
               
          self.remove = function(id){
              console.log('id to be deleted', id);
              if(self.user.userId === id) {//clean form if the user to be deleted is shown there.
                 self.reset();
              }
              self.deleteUser(id);
          };
 
           
          self.reset = function(){
              self.user={userId:null,name:'', login: '', password: '', lastName:'',email:'', sex:'', weight:'', phone:'', cellphone: '', federalStateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage:'', countryId: ''};
              $scope.myForm.$setPristine(); //reset Form
          };
          
             self.login = function() {
        
                  console.log('Loging User', self.user);    
                  self.authenticateUser(self.user.login, self.user.password);
          
          };
 
      }]);
  
trainingApp.controller('calendarController', function ($scope) {
    var self = this;
    $scope.firstName = "Johns";
    self.user = {userId: null, name: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', state_id: '', city_id: '', address: '', postal_code: '', birth_date: '', facebook_page: ''};

    self.reset = function () {
        alert('reset');
    };
});  