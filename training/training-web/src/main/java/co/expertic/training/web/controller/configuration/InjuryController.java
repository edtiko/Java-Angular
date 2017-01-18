/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.configuration;

import co.expertic.training.model.entities.Injury;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.InjuryService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Activity Controller <br>
 * Info. Creación: <br>
 * fecha 25/10/2016 <br>
 *
 * @author Edwin Gómez
 *
 */
@RestController
@RequestMapping("/injury")
public class InjuryController {

    @Autowired
    InjuryService injuryService;

    /**
     * Consulta injury <br>
     * Info. Creación: <br>
     * fecha 25/10/2016 <br>
     *
     * @author Edwin Gómez
     * @return
     */
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {
            List<Injury> injuryList = injuryService.findAll();
            responseService.setOutput(injuryList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(InjuryController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

}
