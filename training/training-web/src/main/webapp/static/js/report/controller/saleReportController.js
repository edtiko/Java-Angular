trainingApp.controller('SaleReportController', function ($scope, SaleReportService, UserService, TrainingPlanService, DisciplineService) {
    var self = this;
    $scope.countries = [];
    $scope.ages = [];
    $scope.sexOptions = [
        {code: "", sex: "Seleccione"},
        {code: "m", sex: "Masculino"},
        {code: "f", sex: "Femenino"}
    ];

    $scope.reportList = [];

    $scope.query = {
        initDate: '', endDate: '',
        countryId: '', trainingPlanId: '',
        sex: '', discipline: '', age: '', role: ''

    };


    function success(response) {
        if (response.data.status == 'fail') {
            $scope.showMessage(response.data.output);
        } else {
            return response.data.output;
        }

        return null;
    }
    
    $scope.drawChart = function(){

        google.charts.load('current', {packages: ['corechart']});

        if ($scope.reportList[0].length > 0) {
            google.charts.setOnLoadCallback(drawChartUser);
        }
        if ($scope.reportList[1].length > 0) {
            google.charts.setOnLoadCallback(drawChartPlan);
        }
        if ($scope.reportList[2].length > 0) {
            google.charts.setOnLoadCallback(drawChartRenovation);
        }
            
    };
    
    function drawChartUser() {
        var title = '';
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Tiempo');
        data.addColumn('number', 'Cantidad de usuarios');
        title = 'No. de usuarios registrados';

        var rows = new Array();
        for (var i = 0; i < $scope.reportList[0].length; i++) {
            rows[i] = [];
            rows[i].push($scope.reportList[0][i].name, Number($scope.reportList[0][i].count));
        }
        data.addRows(rows);
        // Set chart options
        var options = {'title': title,
            'is3D': true,
            'width': 700,
            'pointSize': 7,
            'hAxis': {showTextEvery: 1},
            'height': 400};

        // Instantiate and draw our chart, passing in some options.

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_user'));

        chart.draw(data, options);
    }
    
      function drawChartPlan() {
        var title = '';
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Tipo de plan');
        data.addColumn('number', 'Cantidad de usuarios');
        title = 'No. de planes vendidos';

        var rows = new Array();
        for (var i = 0; i < $scope.reportList[1].length; i++) {
            rows[i] = [];
            rows[i].push($scope.reportList[1][i].name, Number($scope.reportList[1][i].count));
        }
        data.addRows(rows);
        // Set chart options
        var options = {'title': title,
            'is3D': true,
            'width': 700,
            'pointSize': 7,
            'hAxis': {showTextEvery: 1},
            'height': 400};

        // Instantiate and draw our chart, passing in some options.

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_plan'));

        chart.draw(data, options);
    }
    
    function drawChartRenovation() {
        var title = '';
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Tipo de plan');
        data.addColumn('number', 'Cantidad de renovaciones');
        title = 'Renovaciones realizadas';

        var rows = new Array();
        for (var i = 0; i < $scope.reportList[2].length; i++) {
            rows[i] = [];
            rows[i].push($scope.reportList[2][i].name, Number($scope.reportList[2][i].count));
        }
        data.addRows(rows);
        // Set chart options
        var options = {'title': title,
            'is3D': true,
            'width': 700,
            'pointSize': 7,
            'hAxis': {showTextEvery: 1},
            'height': 400};

        // Instantiate and draw our chart, passing in some options.

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_plan'));

        chart.draw(data, options);
    }
    
    
    $scope.find = function () {
        SaleReportService.find($scope.query)
                .then(
                        function (data) {
                            $scope.reportList = data;
                            $scope.drawChart();
                        },
                        function (errResponse) {
                            console.error('Error while fetching Currencies');
                        }
                );
    };


    self.fetchAllCountries = function () {
        UserService.fetchAllCountries()
                .then(
                        function (response) {
                            $scope.countries = response;
                            $scope.countries.unshift({countryId: -1, name: 'Seleccione'});
                            $scope.query.countryId = -1;
                        },
                        function (errResponse) {
                            console.error('Error while fetching Currencies');
                        }
                );
    };
    self.fetchAllCountries();


    self.getSportDisciplines = function () {
        DisciplineService.getSportDisciplines().then(
                function (d) {
                    $scope.disciplines = d;
                    $scope.disciplines.unshift({disciplineId: -1, name: 'Seleccione'});
                    $scope.query.discipline = -1;
                },
                function (errResponse) {
                    console.error('Error while disciplines');
                    console.error(errResponse);
                }
        );
    };
    self.getSportDisciplines();
    
    self.getPlanTypes = function () {
        TrainingPlanService.getPlanTypes().then(
                function (d) {
                    $scope.planTypes = d;
                    $scope.planTypes.unshift({trainingPlanId: -1, name: 'Seleccione'});
                    $scope.query.trainingPlanId = -1;
                },
                function (errResponse) {
                    console.error('Error while planTypes');
                    console.error(errResponse);
                }
        );
    };
    self.getPlanTypes();


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


    $scope.resetForm = function () {
        $scope.query = {
            initDate: '', endDate: '',
            countryId: -1, trainingPlanId: -1,
            sex: '', discipline: -1, age: ''
        };
    };


});