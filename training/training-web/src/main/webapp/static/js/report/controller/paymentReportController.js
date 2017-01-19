trainingApp.controller('PaymentReportController', function ($scope, PaymentReportService, UserService, RoleService) {
    var self = this;

    $scope.roles = [{roleId: 3, name: 'Administrador'},{roleId: 4, name: 'Supervisor'},{roleId: 5, name: 'Estrella'}];
    $scope.users = [];
    $scope.query = {
        role: '', userId: ''
    };
    $scope.roles.unshift({roleId: -1, name: 'Seleccione'});
    $scope.query.role = -1;
    $scope.reportList = [];

    $scope.find = function () {
        PaymentReportService.find($scope.query).then(
                function (data) {
                    $scope.reportList = data;
                },
                function (error) {
                    console.log(error);
                }
        );
    };

    /*self.getRoles = function () {
        RoleService.getRoles().then(
                function (data) {
                    $scope.roles = data.output;
                    $scope.roles.unshift({roleId: -1, name: 'Seleccione'});
                    $scope.query.role = -1;
                },
                function (error) {
                    console.log(error);
                }
        );
    };
    self.getRoles();*/

    $scope.getUsersByRole = function (roleId) {
        UserService.getUsersByRole(roleId).then(
                function (data) {
                    $scope.users = data;
                    $scope.users.unshift({userId: -1, fullName: 'Seleccione'});
                    $scope.query.userId = -1;
                },
                function (error) {
                    console.log(error);
                }
        );
    };

    $scope.resetForm = function () {
        $scope.query = {
            role: -1, userId: -1
        };
    };


});