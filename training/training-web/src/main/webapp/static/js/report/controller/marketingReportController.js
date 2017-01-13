trainingApp.controller('MarketingReportController', ['$scope', 'MarketingReportService', 'UserService','SportEquipmentService','DisciplineService', '$window',
    function ($scope, MarketingReportService, UserService, SportEquipmentService, DisciplineService, $window) {
        var self = this;
        $scope.countries = [];
        $scope.pulsometers = [];
        $scope.potentiometers = [];
        $scope.modelsPotentiometer = [];
        $scope.modelsPulsometer = [];
        $scope.optionSelected = 1;
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];

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
        self.fetchAllCountries();

        $scope.getBikes = function (bikeTypeId) {
            SportEquipmentService.getBikesByBikeTypeId(bikeTypeId).then(
                    function (d) {
                        if (d.detail == null) {
                            $scope.bikes = d.output;
                            $scope.bikes.unshift({sportEquipmentId: '', name: 'Seleccione', brand: 'Seleccione'});
                            $scope.bikes.push({sportEquipmentId: -2, name: 'Otro', brand: 'Otro'});
                            $scope.getModelsBike($scope.userProfile.bikes);
                        } else {
                            console.log("No se encontraron bicicletas de ese tipo");
                        }
                    },
                    function (errResponse) {
                        console.error('Error while bikes');
                        console.error(errResponse);
                    }
            );
        };

        self.getPulsometers = function () {
            SportEquipmentService.getPulsometers().then(
                    function (d) {
                        $scope.pulsometers = d;
                        $scope.pulsometers.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'}, {sportEquipmentId: -2, name: 'Otro', brand: 'Otro'});
                    },
                    function (errResponse) {
                        console.error('Error while pulsometers');
                        console.error(errResponse);
                    }
            );
        };
        self.getPulsometers();

        self.getPotentiometers = function () {
            SportEquipmentService.getPotentiometers().then(
                    function (d) {
                        $scope.potentiometers = d;
                        $scope.potentiometers.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'}, {sportEquipmentId: -2, name: 'Otro', brand: 'Otro'});
                    },
                    function (errResponse) {
                        console.error('Error while potentiometers');
                        console.error(errResponse);
                    }
            );
        };
        self.getPotentiometers();


        $scope.getModelsPotentiometer = function (sportEquipmentId) {

                SportEquipmentService.getModelsBySportEquipmentId(sportEquipmentId).then(
                        function (d) {
                            $scope.modelsPotentiometer = d;
                            $scope.modelsPotentiometer.unshift({modelEquipmentId: -1, name: 'Seleccione'});
                        },
                        function (errResponse) {
                            console.error('Error while models potentiometer');
                            console.error(errResponse);
                        }
                );
            
        };

        $scope.getModelsPulsometer = function (sportEquipmentId) {

                SportEquipmentService.getModelsBySportEquipmentId(sportEquipmentId).then(
                        function (d) {
                            $scope.modelsPulsometer = d;
                            $scope.modelsPulsometer.unshift({modelEquipmentId: -1, name: 'Seleccione'});
                        },
                        function (errResponse) {
                            console.error('Error while models pulsometer');
                            console.error(errResponse);
                        }
                );
            
        };
        
           self.getSportDisciplines = function () {
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
        self.getSportDisciplines();

    }]);