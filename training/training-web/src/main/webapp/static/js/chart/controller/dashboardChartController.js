trainingApp.controller('DashboardChartController', ['$scope', 'UserActivityPerformanceService', '$window',
    function ($scope, UserActivityPerformanceService, $window) {
        $scope.userActivityPerformance = {userActivityPerformanceId: null,
            userId: {userId: null, name: ''},
            activityId: {activityId: null, name: ''},
            value: '',
            activityPerformanceMetafieldId: {activityPerformanceMetafieldId: null, name: '', label: '', dataType: ''},
            executedDate: ''};
        $scope.userActivityPerformanceList = [];
        $scope.days = 365;
        $scope.metafield = 1;
        $scope.weekly = false;
        $scope.currentNavItem = 'twelvemonths';
        $scope.activities = [];
        $scope.distance = [];
        $scope.calories = [];
        $scope.userSession = JSON.parse($window.sessionStorage.getItem("userInfo"));
        var self = this;
        $scope.numSessions = 0;
        $scope.numActivities = 0;
        $scope.numDistance = 0;
        $scope.speedAverage = 0;
        $scope.numCalories = 0;

        function substractDays(date, days) {
            var result = new Date(date);
            result.setDate(result.getDate() - days);
            return result.toISOString().slice(0, 10);
        }

        $scope.getDataChart = function (metafield) {
            return  UserActivityPerformanceService.getByRangeDateAndUserAndVariableAndDaysWeekly($scope.userSession.userId, substractDays(getDate(), $scope.days), getDate(), metafield, $scope.days, $scope.weekly)
                    .then(
                            function (data) {
                                var array = [];
                                data.output.forEach(function (v) {
                                    var date = v.executedDate.split("-");
                                    var executedDate = new Date(date[0], date[1], date[2]);
                                    if (v.value != 0) {
                                        array.push({x: executedDate, y: Math.round(Number((v.value)))});
                                    }
                                });
                                return array;
                            },
                            function (error) {
                                console.error('Error while fetching activity performance' + error);
                            }
                    );
        };

        $scope.getChart = function (days, weekly) {

            $scope.days = days;
            $scope.weekly = weekly;
            var metafieldActivities = 1;
            var metafieldCalories = 2;
            var metafieldDistance = 3;

            if (days == 7) {
                $scope.valueFormatString = 'DD';
                $scope.intervalType = 'day';
            } else if (weekly) {
                $scope.valueFormatString = 'DD';
                $scope.intervalType = 'day';
            } else {
                $scope.valueFormatString = 'MMM';
                $scope.intervalType = 'month';
            }

            $scope.getDataChart(metafieldActivities).then(
                    function (data) {
                        $scope.activities = data;
                        $scope.getDataChart(metafieldDistance).then(
                                function (data) {
                                    $scope.distance = data;
                                    $scope.drawChart();
                                }
                        );
                    }
            );

        };

        $scope.drawChart = function () {
             chart = new CanvasJS.Chart(document.getElementById('chartContainer'), {
                animationEnabled: true,
                axisY: [
                    {
                        // title: "Actividades",
                        lineColor: "rgba(0,100,248,.7)",
                        gridThickness: 0
                    },
                    {
                        //title: "Distancia (Km)",
                        lineColor: "rgba(170,253,111,.7)",
                        gridThickness: 0
                    }
                ],
                data: [
                    {
                        type: "splineArea",
                        connectNullData:true,
                        color: "rgba(0,100,248,.7)",
                        markerType: "none",
                        showInLegend: true,
                        legendText: "Actividades",
                        markerSize: 15,
                        axisXIndex: 0,
                        axisX: {
                            valueFormatString: "MMM",
                            interval: 1,
                            intervalType: "month"

                        },
                        dataPoints: $scope.activities
                    },
                    {
                        type: "splineArea",
                        connectNullData:true,
                        color: "rgba(170,253,111,.7)",
                        markerType: "none",
                        showInLegend: true,
                        legendText: "Distancia(km)",
                        axisYIndex: 1,
                        axisX: {
                            valueFormatString: $scope.valueFormatString,
                            interval: 1,
                            intervalType: $scope.intervalType

                        },
                        dataPoints: $scope.distance
                    }

                ]
            });
            isNoDataAvailable();
            chart.render();
        };

        function isNoDataAvailable() {
            var options = chart.options;

            if (!options.axisY)
                options.axisY = {};

            for (var i = 0; i < options.data.length; i++) {
                if (options.data[i].dataPoints.length === 0)
                    options.axisY.maximum = 0;
                else {
                    options.axisY.maximum = null;
                    break;
                }
            }
        }

        self.getWeeklyGoals = function () {
            UserActivityPerformanceService.getWeeklyGoals($scope.userSession.userId).then(
                    function (data) {
                        //$scope.numSessions = data.numSessions;
                        $scope.numActivities = data.numActivities;
                        $scope.numDistance = (data.distance/1000);
                        $scope.speedAverage = (data.speedAverage/1000);
                        $scope.numCalories = data.numCalories;
                    },
                    function (error) {
                        console.log(error);
                    }
            );
        };

        if ($scope.userSession == null) {
            $scope.$on('userSession', function (event, args) {
                $scope.getChart($scope.days, $scope.weekly);
                self.getWeeklyGoals();
            });

        } else {
            $scope.getChart($scope.days, $scope.weekly);
            self.getWeeklyGoals();
        }



    }]);