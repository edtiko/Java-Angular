trainingApp.service("PaymentReportService", ['$http', '$q', function ($http, $q) {

        var service = {};
        
          service.find = function (query) {
            return $http.post($contextPath + 'report/payment/find', query)
                    .then(
                            function (response) {
                                return response.data.output;
                            },
                            function (errResponse) {
                                console.error('Error while getting service ' + errResponse);
                                return $q.reject(errResponse);
                            }
                    );
        };

        return service;

    }]);
