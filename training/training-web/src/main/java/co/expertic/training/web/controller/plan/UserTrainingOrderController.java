/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.plan;


import co.expertic.base.util.MessageUtil;
import co.expertic.training.model.entities.UserTrainingOrder;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.plan.UserTrainingOrderService;
import co.expertic.training.web.controller.configuration.ActivityController;
import co.expertic.training.web.enums.StatusResponse;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Andres Lopez
 */
@RestController
public class UserTrainingOrderController {
    
    @Autowired
    UserTrainingOrderService userTrainingOrderService;
    
    /**
     * Crea orden del portal <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userTrainingOrder
     * @return
     */
    @RequestMapping(value = "user/order/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createOrder(@RequestBody UserTrainingOrder userTrainingOrder) {
            ResponseService responseService = new ResponseService();
        try {  
            userTrainingOrderService.create(userTrainingOrder);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activity", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
