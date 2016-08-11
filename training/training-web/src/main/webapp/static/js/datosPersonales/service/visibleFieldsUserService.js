'use strict';
 
trainingApp.factory('VisibleFieldsUserService', ['$http', '$q', function($http, $q){
    return {
            
            createVisibleFieldsUser: function(visibleFieldsUser) {
                    return $http.post($contextPath+'visibleFieldsUser/create',visibleFieldsUser)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating visibleFieldsUser');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            mergeVisibleFieldsUser: function(visibleFieldsUser){
                    return $http.post($contextPath+'visibleFieldsUser/merge', visibleFieldsUser)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while merging visibleFieldsUser');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            getVisibleFieldsUserByUser: function(user){
                
                    return $http.post($contextPath+'visibleFieldsUser/get/by/userId',user)
                            .then(
                                    function(response){
                                        return response.data.entity.output;
                                    }, 
                                    function(errResponse){
                                        console.error(user);
                                        console.error('Error while getting visibleFieldsUser');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
        };
 
}]);