//var trainingApp = angular.module('trainingApp', []);

trainingApp.controller('calendarController', function ($scope) {
    var self = this;
    $scope.firstName = "Johns";
    self.user = {userId: null, name: '', lastName: '', email: '', sex: '', weight: '', phone: '', cellphone: '', state_id: '', city_id: '', address: '', postal_code: '', birth_date: '', facebook_page: ''};

    self.reset = function () {
        alert('reset');
    };
});