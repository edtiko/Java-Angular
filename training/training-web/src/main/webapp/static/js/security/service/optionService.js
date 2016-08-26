'use strict';
 
trainingApp.factory('OptionService', ['$http', '$q', function($http, $q){
    return {

            getPaginate: function(query, res){
                    return $http.post($contextPath+'option/paginated', query)
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getMasterOptions: function(res){
                    return $http.post($contextPath+'option/')
                            .then(
                                    res, 
                                    function(errResponse){
                                        console.error('Error while getting service ' + errResponse);
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
        };
 
}]);