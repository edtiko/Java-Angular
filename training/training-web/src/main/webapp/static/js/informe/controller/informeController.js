trainingApp.controller('InformeController', ['$scope', 'InformeService', '$window',
    function ($scope, InformeService, $window) {
        
        $scope.videoList = [];
        $scope.chatList = [];
        $scope.mailList = [];
        $scope.option = 0;

        $scope.getVideoReport = function () {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            InformeService.getVideoReportRendimiento(user.userId,user.typeUser)
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
                                    
                                  console.debug(rows)
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
            
            InformeService.getVideoReportTimeResponse(user.userId,user.typeUser)
                    .then(
                            function (response) {
                                console.debug(response)
                                $scope.videoList = response.output;
                            },
                            function (errResponse) {
                                console.error('Error while fetching response time');
                            }
                    );
        };
        
        $scope.getChatReport = function () {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            InformeService.getChatReportRendimiento(user.userId,user.typeUser)
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
                                    
                                  console.debug(rows)
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
            
            InformeService.getChatReportTimeResponse(user.userId,user.typeUser)
                    .then(
                            function (response) {
                                console.debug(response)
                                $scope.chatList = response.output;
                            },
                            function (errResponse) {
                                console.error('Error while fetching response time');
                            }
                    );
        };
        
        
        $scope.getMailReport = function () {
            var user = JSON.parse($window.sessionStorage.getItem("userInfo"));

            InformeService.getMailReportRendimiento(user.userId,user.typeUser)
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
                                    
                                  console.debug(rows)
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
            
            InformeService.getMailReportTimeResponse(user.userId,user.typeUser)
                    .then(
                            function (response) {
                                console.debug(response)
                                $scope.mailList = response.output;
                            },
                            function (errResponse) {
                                console.error('Error while fetching response time');
                            }
                    );
        };
        
        $scope.showVideo = function() {
            $scope.option = 1;
            $scope.getVideoReport();
        };

        $scope.showChat = function() {
            $scope.option = 2;
            $scope.getChatReport();
        };

        $scope.showEmail = function() {
            $scope.option = 3;
            $scope.getMailReport();
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