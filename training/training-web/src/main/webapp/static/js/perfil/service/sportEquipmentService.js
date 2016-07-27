'use strict';

trainingApp.factory('SportEquipmentService', ['$http', '$q', function($http, $q){
    return {

            getSportEquipment: function(){
                    return $http.get($contextPath+'sportEquipment/get/all')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting disciplines');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getRunningShoes: function(){
                    return $http.get($contextPath+'sportEquipment/get/running/shoes')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting running shoes');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getBikes: function(){
                    return $http.get($contextPath+'sportEquipment/get/bikes')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting bikes');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getPulsometers: function(){
                    return $http.get($contextPath+'sportEquipment/get/pulsometers')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting pulsometers');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            getPotentiometers: function(){
                    return $http.get($contextPath+'sportEquipment/get/potentiometers')
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while getting potentiometers');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
        };
 
}]);