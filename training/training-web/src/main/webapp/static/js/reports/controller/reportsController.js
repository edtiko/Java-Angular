trainingApp.controller('ReportsController', ['$scope', 'UserActivityPerformanceService', '$window', '$mdDialog', 
    function ($scope, UserActivityPerformanceService, $window, $mdDialog) {
    $scope.userActivityPerformance ={userActivityPerformanceId:null,
    userId: {userId: null, name:''},        
    activityId: {activityId: null, name:''},        
    value: '',        
    activityPerformanceMetafieldId: {activityPerformanceMetafieldId: null, name:'',label:'',dataType:''},        
    executedDate: ''};
    $scope.userActivityPerformanceList = [];
    $scope.days = 7;
    $scope.metafield = 1;
    $scope.weekly = true;
    
    $scope.getReportByMetafieldOneWeek = function (metafield) {
        var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
        
        UserActivityPerformanceService.getByRangeDateAndUserAndVariable(user.userId,substractDays(getDate(),$scope.days),getDate(),metafield)
                    .then(
                            function (response) {
                                $scope.userActivityPerformanceList = response.output;
                                
                                google.charts.setOnLoadCallback(drawChart);
                                function drawChart() {

                                var title = '';
                                var data = new google.visualization.DataTable();
                                data.addColumn('date','tiempo');
                                if(metafield == 1) {
                                    data.addColumn('number','Actividades');
                                    title = 'Actividades';
                                } else if (metafield == 2 ) {
                                    data.addColumn('number','Calorias'); 
                                    title = 'Calorias Totales'; 
                                } else if (metafield == 3) {
                                    data.addColumn('number','Distancia'); 
                                    title = 'Distancia Total';
                                } else if(metafield == 4) {
                                    data.addColumn('number','Frecuencia Cardiaca Maxima'); 
                                    title = 'Frecuencia Cardiaca Maxima';
                                }else if(metafield == 5) {
                                    data.addColumn('number','Frecuencia Cardiaca Media'); 
                                    title = 'Frecuencia Cardiaca Media';
                                }else if(metafield == 6) {
                                    data.addColumn('number','Ritmo Medio'); 
                                    title = 'Ritmo Medio';
                                }
                                var rows = new Array();
                                for (var i = 0; i < $scope.days; i++) {
                                    rows[i] = [];
                                    rows[i].push($scope.parseDateToJavascriptDate($scope.userActivityPerformanceList[i].executedDate),Number($scope.userActivityPerformanceList[i].value));
                                }
                                data.addRows(rows);
                                // Set chart options
                                var options = {'title': title,
                                    is3D: true,
                                    'width': 800,
                                    'height': 600};

                                // Instantiate and draw our chart, passing in some options.
                                
                                if(metafield == 1) {
                                    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                                } else if (metafield == 2 ) {
                                    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                                } else if (metafield == 3) {
                                    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                                } else if(metafield == 4) {
                                    var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                }else if(metafield == 5) {
                                    var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                }else if(metafield == 6) {
                                    var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
                                }
                                chart.draw(data, options);}
                            },
                            function (errResponse) {
                                console.error('Error while fetching activity performance');
                            }
                    );
    };
    $scope.getReportByMetafieldOneWeek($scope.metafield);
    
    $scope.getReportByMetafieldMonthlyOrWeekly = function (metafield, weekly) {
        var user = JSON.parse($window.sessionStorage.getItem("userInfo"));
        
        UserActivityPerformanceService.getByRangeDateAndUserAndVariableAndDaysWeekly(user.userId,substractDays(getDate(),$scope.days),getDate(),metafield,$scope.days,weekly)
                    .then(
                            function (response) {
                                $scope.userActivityPerformanceList = response.output;
                                
                                google.charts.setOnLoadCallback(drawChart);
                                function drawChart() {

                                var data = new google.visualization.DataTable();
                                data.addColumn('date','tiempo');
                                if(metafield == 1) {
                                    data.addColumn('number','Actividades');
                                } else if (metafield == 2 ) {
                                    data.addColumn('number','Calorias'); 
                                } else if (metafield == 3) {
                                    data.addColumn('number','Frecuencia Cardiaca Maxima'); 
                                } 
                                
                                var rows = new Array();
                                for (var i = 0; i < $scope.userActivityPerformanceList.length; i++) {
                                    rows[i] = [];
                                    rows[i].push($scope.parseDateToJavascriptDate($scope.userActivityPerformanceList[i].executedDate),Number($scope.userActivityPerformanceList[i].value));
                                }
                                data.addRows(rows);
                                // Set chart options
                                
                                var options = {'title': 'Calorias Totales',
                                    'width': 800,
                                    'height': 600};

                                // Instantiate and draw our chart, passing in some options.
                                if(metafield == 1 ) {
                                    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                                } else if(metafield == 2 ) {
                                    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                                }
                                chart.draw(data, options);}
                            },
                            function (errResponse) {
                                console.error('Error while fetching activity performance');
                            }
                    );
    };
//    $scope.getReportByMetafieldMonthlyOrWeekly($scope.metafield,$scope.weekly);
    
    $scope.parseDateToJavascriptDate =function(date) {
        var dateParts = date.split("-");
        return new Date(dateParts[0], (dateParts[1] - 1), dateParts[2]);
    }; 
    
    function substractDays(date, days) {
            var result = new Date(date);
            result.setDate(result.getDate() - days);
            return result.toISOString().slice(0, 10);
        }
    
    

}]);