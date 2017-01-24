'use strict';
trainingApp.service("MarketingReportService", ['$http', '$q', function ($http, $q) {

        return {
            getPaginate: function (query, res) {
                return $http.post($contextPath + 'report/marketing/paginated', query)
                        .then(
                                res,
                                function (errResponse) {
                                    console.error('Error while getting service ' + errResponse);
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };
    }]);
