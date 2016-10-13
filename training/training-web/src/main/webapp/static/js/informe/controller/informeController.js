trainingApp.controller('InformeController', ['$scope', 'InformeService', '$window',
    function ($scope, InformeService, $window) {

        $scope.getReportRendimiento = function () {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            InformeService.getReportRendimiento(user.userId)
                    .then(
                            function (response) {
                                console.debug(response)
                                $scope.charList = response.output;

                                google.charts.setOnLoadCallback(drawChart);
                                function drawChart() {

                                    var title = 'Rendimiento';
                                    
                                    
                                    var rows = new Array();
                                    rows[0] = [];
                                    rows[0] = ['Element', 'Valor', { role: 'style' }];
                                    for (var i = 0; i < $scope.charList.length; i++) {
                                        rows[i+1] = [];
                                        rows[i+1] = [$scope.charList[i].name, 
                                        Number($scope.charList[i].value), $scope.charList[i].style];
                                    }
                                    
                                    console.debug([
         ['Element', 'Density', { role: 'style' }],
         ['Copper', 8.94, '#b87333'],            // RGB value
         ['Silver', 10.49, 'silver'],            // English color name
         ['Gold', 19.30, 'gold'],

       ['Platinum', 21.45, 'color: #e5e4e2' ], // CSS-style declaration
      ]);console.debug(rows)
                                    var data = google.visualization.arrayToDataTable(rows);
                                    // Set chart options

                                    var options = {'title': title,
                                        "hAxis": {showTextEvery: 1},
                                        'width': 1100,
                                        'pointSize': 7,
                                        'height': 600};

                                    // Instantiate and draw our chart, passing in some options.
                                    var chart = new google.visualization.ColumnChart(document.getElementById('chart_rendimiento_div'));
                                    chart.draw(data, options);
                                }
                            },
                            function (errResponse) {
                                console.error('Error while fetching activity performance');
                            }
                    );
        };
        
        $scope.showVideo = function() {
            $scope.getReportRendimiento();
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