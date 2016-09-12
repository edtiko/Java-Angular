/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.UserTrainingOrder;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.UserTrainingOrderService;
import co.com.expertla.training.web.controller.configuration.ActivityController;
import co.com.expertla.training.web.enums.StatusResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import org.apache.commons.codec.binary.Base64;
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
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.activity", "msgRegistroCreado"));
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
