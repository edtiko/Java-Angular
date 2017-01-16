trainingApp.controller('MarketingReportController', ['$scope', 'MarketingReportService', 'UserService','SportEquipmentService','DisciplineService', '$window',
    function ($scope, MarketingReportService, UserService, SportEquipmentService, DisciplineService, $window) {
        var self = this;
        $scope.countries = [];
        $scope.pulsometers = [];
        $scope.potentiometers = [];
        $scope.modelsPotentiometer = [];
        $scope.modelsPulsometer = [];
        $scope.bikes = [];
        $scope.modelsBike = [];
        $scope.optionSelected = 1;
        $scope.sexOptions = [
            {code: "m", sex: "Masculino"},
            {code: "f", sex: "Femenino"}
        ];
           $scope.roles = [
            {id: 1, name: "Atletas"},
            {id: 2, name: "Supervisor"},
            {id: 3, name: "Coach externo"},
            {id: 0, name: "Todos"}
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

        self.getBikes = function () {
            SportEquipmentService.getBikes().then(
                    function (d) {
                        if (d.detail == null) {
                            $scope.bikes = d.output;
                            $scope.bikes.unshift({sportEquipmentId: '', name: 'Seleccione', brand: 'Seleccione'});
                            //$scope.bikes.push({sportEquipmentId: -2, name: 'Otro', brand: 'Otro'});
                            $scope.getModelsBike($scope.filter.bike);
                        } else {
                            console.log("No se encontraron bicicletas ");
                        }
                    },
                    function (errResponse) {
                        console.error('Error while bikes');
                        console.error(errResponse);
                    }
            );
        };
        self.getBikes();
        
        $scope.getModelsBike = function (sportEquipmentId) {

            SportEquipmentService.getModelsBySportEquipmentId(sportEquipmentId).then(
                    function (d) {
                        $scope.modelsBike = d;
                        $scope.modelsBike.unshift({modelEquipmentId: -1, name: 'Seleccione'});
                    },
                    function (errResponse) {
                        console.error('Error while models bikes');
                        console.error(errResponse);
                    }
            );

        };

        self.getPulsometers = function () {
            SportEquipmentService.getPulsometers().then(
                    function (d) {
                        $scope.pulsometers = d;
                        $scope.pulsometers.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'}); 
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
                        $scope.potentiometers.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'});
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
        
        
        self.getUserAges = function () {
            UserService.getUserAges().then(
                    function (d) {
                        $scope.ages = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching ages');
                        console.error(errResponse);
                    }
            );
        };
        self.getUserAges();

    }]);