trainingApp.controller("VideoController", ['$scope','videoService', function($scope, videoService) {
    var configuration  = {
            init : $scope.initiateRecord,
            recConf:{   
                recorvideodsize : 0.4, 
                webpquality     : 0.7, 
                framerate       : 15,  
                videoWidth      : 500,
                videoHeight     : 375,            
            },

            recfuncConf :{
                showbuton : 2000,
                url : $contextPath+"/video/upload",
                chunksize : 1048576,
                recordingtime : 17,
                requestparam : "filename",
                videoname : "video.webm",
                audioname : "audio.wav", 
            },

            output :{
                recordingthumb : null,
               	recordinguploaded : function(){
               		alert("uploaded");
               	}
            },

            recordingerror : function(){
            	alert("browser not compatible");
            }      
    }
	$scope.camconfiguration = configuration;
}]);