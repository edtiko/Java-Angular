'use strict';
 
trainingApp.controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
          var self = this;
          self.user={userId:null,name:'',lastName:'',email:'', sex:'', weight:'', phone:'', cellphone: '', stateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage:''};
          self.users=[];
               
          self.fetchAllUsers = function(){
              UserService.fetchAllUsers()
                  .then(
                               function(d) {
                                    self.users = d;
                               },
                                function(errResponse){
                                    console.error('Error while fetching Currencies');
                                }
                       );
          };
            
          self.createUser = function(user){
              UserService.createUser(user)
                      .then(
                      self.fetchAllUsers, 
                              function(errResponse){
                                   console.error('Error while creating User.');
                              } 
                  );
          };
 
         self.updateUser = function(user, id){
              UserService.updateUser(user, id)
                      .then(
                              self.fetchAllUsers, 
                              function(errResponse){
                                   console.error('Error while updating User.');
                              } 
                  );
          };
 
         self.deleteUser = function(id){
              UserService.deleteUser(id)
                      .then(
                              self.fetchAllUsers, 
                              function(errResponse){
                                   console.error('Error while deleting User.');
                              } 
                  );
          };
 
          self.fetchAllUsers();
 
          self.submit = function() {
              if(self.user.userId===null){
                  console.log('Saving New User', self.user);    
                  self.createUser(self.user);
              }else{
                  self.updateUser(self.user, self.user.userId);
                  console.log('User updated with id ', self.user.userId);
              }
              self.reset();
          };
               
          self.edit = function(id){
              console.log('id to be edited', id);
              for(var i = 0; i < self.users.length; i++){
                  if(self.users[i].userId === id) {
                     self.user = angular.copy(self.users[i]);
                     break;
                  }
              }
          };
               
          self.remove = function(id){
              console.log('id to be deleted', id);
              if(self.user.userId === id) {//clean form if the user to be deleted is shown there.
                 self.reset();
              }
              self.deleteUser(id);
          };
 
           
          self.reset = function(){
              self.user={userId:null,name:'',lastName:'',email:'', sex:'', weight:'', phone:'', cellphone: '', stateId: '', cityId: '', address: '', postalCode: '', birthDate: '', facebookPage:''};
              $scope.myForm.$setPristine(); //reset Form
          };
 
      }]);