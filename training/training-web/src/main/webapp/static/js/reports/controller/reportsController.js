trainingApp.controller('ReportsController', ['$scope', 'UserActivityPerformanceService', '$window',
    function ($scope, UserActivityPerformanceService, $window) {
        $scope.userActivityPerformance = {userActivityPerformanceId: null,
            userId: {userId: null, name: ''},
            activityId: {activityId: null, name: ''},
            value: '',
            activityPerformanceMetafieldId: {activityPerformanceMetafieldId: null, name: '', label: '', dataType: ''},
            executedDate: ''};
        $scope.userActivityPerformanceList = [];
        $scope.days = 7;
        $scope.metafield = 1;
        $scope.weekly = false;
        $scope.currentNavItem = 'day';

        $scope.getReportByMetafieldOneWeek = function (metafield) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            UserActivityPerformanceService.getByRangeDateAndUserAndVariable(user.userId, substractDays(getDate(), $scope.days), getDate(), metafield)
                    .then(
                            function (response) {
                                $scope.userActivityPerformanceList = response.output;

                                google.charts.setOnLoadCallback(drawChart);
                                function drawChart() {

                                    var title = '';
                                    var data = new google.visualization.DataTable();
                                    data.addColumn('string', 'tiempo');
                                    if (metafield == 1) {
                                        data.addColumn('number', 'Actividades');
                                        title = 'Actividades';
                                    } else if (metafield == 2) {
                                        data.addColumn('number', 'Calorias');
                                        title = 'Calorias Totales';
                                    } else if (metafield == 3) {
                                        data.addColumn('number', 'Distancia');
                                        title = 'Distancia Total';
                                    } else if (metafield == 4) {
                                        data.addColumn('number', 'Frecuencia Cardiaca Maxima');
                                        title = 'Frecuencia Cardiaca Maxima';
                                    } else if (metafield == 5) {
                                        data.addColumn('number', 'Frecuencia Cardiaca Media');
                                        title = 'Frecuencia Cardiaca Media';
                                    } else if (metafield == 6) {
                                        data.addColumn('number', 'Ritmo Medio');
                                        title = 'Ritmo Medio';
                                    }
                                    var rows = new Array();
                                    for (var i = 0; i < $scope.days; i++) {
                                        rows[i] = [];
                                        rows[i].push(getCurrentDay($scope.parseDateToJavascriptDate($scope.userActivityPerformanceList[i].executedDate)), Number($scope.userActivityPerformanceList[i].value));
                                    }
                                    data.addRows(rows);
                                    // Set chart options
                                    var options = {'title': title,
                                        'is3D': true,
                                        'width': 1100,
                                        'pointSize': 7,
                                        'hAxis': {showTextEvery: 1},
                                        'height': 600};

                                    // Instantiate and draw our chart, passing in some options.

                                    if (metafield == 1) {
                                        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                                    } else if (metafield == 2) {
                                        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                                    } else if (metafield == 3) {
                                        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                                    } else if (metafield == 4) {
                                        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                    } else if (metafield == 5) {
                                        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                    } else if (metafield == 6) {
                                        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                    }
                                    chart.draw(data, options);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while fetching activity performance');
                            }
                    );
        };
//    $scope.getReportByMetafieldOneWeek($scope.metafield);

        $scope.getReportByMetafieldMonthlyOrWeekly = function (metafield, weekly) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            UserActivityPerformanceService.getByRangeDateAndUserAndVariableAndDaysWeekly(user.userId, substractDays(getDate(), $scope.days), getDate(), metafield, $scope.days, weekly)
                    .then(
                            function (response) {
                                $scope.userActivityPerformanceList = response.output;

                                google.charts.setOnLoadCallback(drawChart);
                                function drawChart() {

                                    var title = '';
                                    var data = new google.visualization.DataTable();
                                    data.addColumn('string', 'tiempo');
                                    if (metafield == 1) {
                                        data.addColumn('number', 'Actividades');
                                        title = 'Actividades';
                                    } else if (metafield == 2) {
                                        data.addColumn('number', 'Calorias');
                                        title = 'Calorias Totales';
                                    } else if (metafield == 3) {
                                        data.addColumn('number', 'Distancia');
                                        title = 'Distancia Total';
                                    } else if (metafield == 4) {
                                        data.addColumn('number', 'Frecuencia Cardiaca Maxima');
                                        title = 'Frecuencia Cardiaca Maxima';
                                    } else if (metafield == 5) {
                                        data.addColumn('number', 'Frecuencia Cardiaca Media');
                                        title = 'Frecuencia Cardiaca Media';
                                    } else if (metafield == 6) {
                                        data.addColumn('number', 'Ritmo Medio');
                                        title = 'Ritmo Medio';
                                    }

                                    var rows = new Array();
                                    if (weekly) {
                                        for (var i = 0; i < $scope.userActivityPerformanceList.length; i++) {
                                            rows[i] = [];
                                            if ($scope.userActivityPerformanceList[i].executedDate != undefined) {
                                                rows[i].push($scope.parseDateToStringMonthDay($scope.userActivityPerformanceList[i].executedDate), Number($scope.userActivityPerformanceList[i].value));
                                            }
                                        }
                                    } else {
                                        for (var i = 0; i < $scope.userActivityPerformanceList.length; i++) {
                                            rows[i] = [];
                                            rows[i].push(getCurrentMonth($scope.parseDateToJavascriptDate($scope.userActivityPerformanceList[i].executedDate)), Number($scope.userActivityPerformanceList[i].value));
                                        }
                                    }
                                    data.addRows(rows);
                                    // Set chart options

                                    var options = {'title': title,
                                        "hAxis": {showTextEvery: 1},
                                        'width': 1100,
                                        'pointSize': 7,
                                        'height': 600};

                                    // Instantiate and draw our chart, passing in some options.
                                    if (metafield == 1) {
                                        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                                    } else if (metafield == 2) {
                                        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                                    } else if (metafield == 3) {
                                        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                                    } else if (metafield == 4) {
                                        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                    } else if (metafield == 5) {
                                        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                    } else if (metafield == 6) {
                                        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                    }
                                    chart.draw(data, options);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while fetching activity performance');
                            }
                    );
        };

        $scope.getReport = function (metafield, days, weekly, currentNavItem) {
            if (currentNavItem != 'undefined' && currentNavItem == 'day') {
                days = 7;
            } else if (currentNavItem != 'undefined' && currentNavItem == 'week') {
                days = 28;
                weekly = true;
            } else if (currentNavItem != 'undefined' && currentNavItem == 'sixmonths') {
                days = 168;
            } else if (currentNavItem != 'undefined' && currentNavItem == 'twelvemonths') {
                days = 365;
            }
            if (days == 7) {
                $scope.currentNavItem = 'day';
                if (days != null) {
                    $scope.days = days;
                }
                if (metafield != null) {
                    $scope.metafield = metafield;
                }
                $scope.getReportByMetafieldOneWeek($scope.metafield);
            } else {
                $scope.days = days;
                if (days == 28) {
                    $scope.currentNavItem = 'week';
                } else if (days == 168) {
                    $scope.currentNavItem = 'sixmonths';
                } else {
                    $scope.currentNavItem = 'twelvemonths';
                }
                if (metafield != null) {
                    $scope.metafield = metafield;
                }
                $scope.getReportByMetafieldMonthlyOrWeekly($scope.metafield, weekly);
            }
        };
        $scope.getReport($scope.metafield, $scope.days, $scope.weekly, $scope.currentNavItem);

        $scope.parseDateToJavascriptDate = function (date) {
            var dateParts = date.split("-");
            return new Date(dateParts[0], (dateParts[1] - 1), dateParts[2]);
        };

        $scope.parseDateToStringMonthDay = function (date) {
            var dateParts = date.split("-");
            var monthNames = ["Ene", "Feb", "Mar", "Abr", "Mayo", "Jun",
                "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
            ];
            return monthNames[(dateParts[1] - 1)] + ' ' + dateParts[2];
        };

        function substractDays(date, days) {
            var result = new Date(date);
            result.setDate(result.getDate() - days);
            return result.toISOString().slice(0, 10);
        }

        function getCurrentDay(date) {
            var dayNames = ['Dom', "Lun", "Mar", "Mier", "Jue", "Vie", "Sab"];
            return dayNames[date.getDay()];
        }

        function getCurrentMonth(date) {
            var monthNames = ["Ene", "Feb", "Mar", "Abr", "Mayo", "Jun",
                "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
            ];
            return monthNames[date.getMonth()];
        }


    }]);