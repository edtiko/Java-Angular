trainingApp.controller('MarketingReportController', function ($scope, MarketingReportService, UserService, SportEquipmentService, DisciplineService) {
    var self = this;
    $scope.countries = [];
    $scope.pulsometers = [];
    $scope.potentiometers = [];
    $scope.modelsPotentiometer = [];
    $scope.modelsPulsometer = [];
    $scope.bikes = [];
    $scope.modelsBike = [];
    $scope.ages = [];
    $scope.shoes = [];
    $scope.optionSelected = 4;
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
    $scope.reportList = [];
    $scope.count = 0;
    $scope.selected = [];
    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1,
        sportEquipmentType: $scope.optionSelected, initDate: $scope.initDate, endDate: $scope.endDate,
        countryId: $scope.countryId, shoe: $scope.shoe, potentiometer: $scope.potentiometer,
        potentiometerModel: $scope.modelPotentiometer, pulsometer: $scope.pulsometer,
        pulsometerModel: $scope.modelPulsometer, bike: $scope.bike, modelBike: $scope.modelBike,
        sex: $scope.sex, discipline: $scope.discipline, age: $scope.age, role: $scope.role

    };

    function success(response) {
        if (response.data.status == 'fail') {
            $scope.showMessage(response.data.output);
        } else {
            return response.data.output;
        }

        return null;
    }

    $scope.getPaginate = function () {
        $scope.promise = MarketingReportService.getPaginate($scope.query, function (response) {
            $scope.reportList = success(response);

            if ($scope.reportList.length > 0) {
                $scope.count = $scope.reportList[0].count;
            }
        }).$promise;
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
    self.fetchAllCountries();

    self.getBikes = function () {
        SportEquipmentService.getBikes().then(
                function (d) {
                    $scope.bikes = d;
                    $scope.bikes.unshift({sportEquipmentId: '', name: 'Seleccione', brand: 'Seleccione'});
                    $scope.modelsBike = [];

                },
                function (errResponse) {
                    console.error('Error while bikes');
                    console.error(errResponse);
                }
        );
    };
    self.getBikes();

    $scope.getModelsBike = function (sportEquipmentId) {
        if (sportEquipmentId != undefined && sportEquipmentId != null) {

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
        }

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
        if (sportEquipmentId != undefined && sportEquipmentId != null) {
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
        }

    };

    $scope.getModelsPulsometer = function (sportEquipmentId) {
        if (sportEquipmentId != undefined && sportEquipmentId != null) {
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
        }
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

    self.getRunningShoes = function () {
        SportEquipmentService.getRunningShoes().then(
                function (d) {
                    $scope.shoes = d;
                    $scope.shoes.unshift({sportEquipmentId: -1, name: 'Seleccione', brand: 'Seleccione'});
                    $scope.shoe = -1;
                },
                function (errResponse) {
                    console.error('Error while running shoes ');
                    console.error(errResponse);
                }
        );
    };
    self.getRunningShoes();

    $scope.getPaginate();

});