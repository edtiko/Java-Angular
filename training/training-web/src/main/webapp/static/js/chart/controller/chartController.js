trainingApp.controller('ChartController', ['$scope', 'UserActivityPerformanceService', '$window',
    function ($scope, UserActivityPerformanceService, $window) {
        var self = this;
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
        $scope.numSessions = 0;
        $scope.numActivities = 0;
        $scope.numDistance = 0;
        $scope.speedAverage = 0;
        $scope.numCalories = 0;
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        $scope.planSelected = JSON.parse($window.sessionStorage.getItem("planSelected"));
        $scope.userId = $scope.userSession.userId;
        if ($scope.planSelected != null) {
            if ($scope.planSelected.athleteUserId != undefined) {
                $scope.userId = $scope.planSelected.athleteUserId.userId;
            }
        }

        $scope.getReportByMetafieldOneWeek = function (metafield) {
            
            UserActivityPerformanceService.getByRangeDateAndUserAndVariable($scope.userId, substractDays(getDate(), $scope.days), getDate(), metafield)
                    .then(
                            function (response) {
                                $scope.userActivityPerformanceList = response.output;
                          
                                google.charts.load('current', {packages: ['corechart', 'gauge']});
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
                                    } else if (metafield == 7) {
                                        data.addColumn('number', 'Potencia Maxima');
                                        title = 'Potencia Maxima';
                                    } else if (metafield == 8) {
                                        data.addColumn('number', 'Potencia Media');
                                        title = 'Potencia Media';
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
                                        //'width': 800,
                                        'pointSize': 7,
                                        'hAxis': {showTextEvery: 1},
                                        'height': 300};

                                    // Instantiate and draw our chart, passing in some options.

                                    if (metafield == 1) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 2) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 3) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 4) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 5) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 6) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 7) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 8) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    }
                                    chart.draw(data, options);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while fetching activity performance');
                            }
                    );
        };

        $scope.getReportByMetafieldMonthlyOrWeekly = function (metafield, weekly, currentNavItem) {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
            var planAthleteSelected = JSON.parse(sessionStorage.getItem("planSelected"));
            if (planAthleteSelected != null) {
                user.userId = planAthleteSelected.athleteUserId.userId;
            }
            UserActivityPerformanceService.getByRangeDateAndUserAndVariableAndDaysWeekly($scope.userId, substractDays(getDate(), $scope.days), getDate(), metafield, $scope.days, weekly)
                    .then(
                            function (response) {
                                $scope.userActivityPerformanceList = response.output;

                                google.charts.setOnLoadCallback(drawChart);
                                function drawChart() {

                                    var title = '';
                                    if(google ==  null){
                                        google.charts.load('current', {packages: ['corechart']});
                                    }
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
                                    } else if (metafield == 7) {
                                        data.addColumn('number', 'Potencia Maxima');
                                        title = 'Potencia Maxima';
                                    } else if (metafield == 8) {
                                        data.addColumn('number', 'Potencia Media');
                                        title = 'Potencia Media';
                                    }

                                    var rows = new Array();
                                    var index = 0;
                                    if (weekly) {
                                        for (var i = 0; i < $scope.userActivityPerformanceList.length-1; i++) {

                                            if ($scope.userActivityPerformanceList[i].executedDate != undefined) {
                                                rows[index] = [];

                                                if (currentNavItem == 'week') {
                                                    var fechaFinal = $scope.sumaFecha(7, $scope.userActivityPerformanceList[i].executedDate);
                                                    rows[index].push(
                                                            $scope.parseDateToStringMonthDay($scope.userActivityPerformanceList[i].executedDate) + '-' + $scope.parseDateToStringMonthDay(fechaFinal),
                                                            Number($scope.userActivityPerformanceList[i].value)
                                                            );
                                                    index++;
                                                } else {
                                                    rows[index].push(
                                                            $scope.parseDateToStringMonthDay($scope.userActivityPerformanceList[i].executedDate),
                                                            Number($scope.userActivityPerformanceList[i].value)
                                                            );
                                                    index++;
                                                }

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
                                        //'width': 800,
                                        'pointSize': 7,
                                        'height': 300};

                                    // Instantiate and draw our chart, passing in some options.
                                    if (metafield == 1) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 2) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 3) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 4) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 5) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 6) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 7) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                                    } else if (metafield == 8) {
                                        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
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
                $scope.getReportByMetafieldMonthlyOrWeekly($scope.metafield, weekly, $scope.currentNavItem);
            }
        };

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

        $scope.sumaFecha = function (d, fecha)
        {
            var Fecha = new Date();
            var sFecha = fecha || (Fecha.getDate() + "/" + (Fecha.getMonth() + 1) + "/" + Fecha.getFullYear());
            var sep = sFecha.indexOf('/') != -1 ? '/' : '-';
            var aFecha = sFecha.split(sep);
            var fecha = aFecha[2] + '-' + (aFecha[1]-1) + '-' + aFecha[0];
            fecha = new Date(aFecha[0], (aFecha[1]-1), aFecha[2]);
            fecha.setDate(fecha.getDate() + parseInt(d-1));
            var anno = fecha.getFullYear();
            var mes = fecha.getMonth()+1;
            var dia = fecha.getDate();
            mes = (mes < 10) ? ("0" + mes) : mes;
            dia = (dia < 10) ? ("0" + dia) : dia;
            var fechaFinal = anno + sep + mes + sep + dia;
            return (fechaFinal);
        }

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

        self.getWeeklyGoals = function () {
            UserActivityPerformanceService.getWeeklyGoals($scope.userId).then(
                    function (data) {
                        $scope.numSessions = data.numSessions;
                        $scope.numActivities = data.numActivities;
                        $scope.numDistance = (data.distance / 1000);
                        $scope.speedAverage = (data.speedAverage / 1000);
                        $scope.numCalories = data.numCalories;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };
        
       

        if ($scope.userSession != null) {
            $scope.getReport($scope.metafield, $scope.days, $scope.weekly, $scope.currentNavItem);
             self.getWeeklyGoals();
        }


    }]);