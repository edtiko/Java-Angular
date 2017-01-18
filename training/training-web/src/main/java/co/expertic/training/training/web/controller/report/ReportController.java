/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.training.web.controller.report;

import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.ReportDTO;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.SportEquipmentService;
import co.expertic.training.service.configuration.TrainingPlanService;
import co.expertic.training.web.controller.configuration.BikeTypeController;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
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
 * @author Edwin G
 */
@RestController
@RequestMapping("report")
public class ReportController {
    
    @Autowired
    SportEquipmentService sportEquipmentService;
    
    @Autowired
    TrainingPlanService trainingPlanService;
    
        /**
     * Consulta marketing paginado <br>
     * Info. Creación: <br>
     * fecha Ene 16, 2017 <br>
     * @author  Edwin Gómez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/marketing/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> marketingPaginated(@RequestBody ReportDTO paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<ReportDTO> list = sportEquipmentService.findMarketingPaginate(paginateDto);
            responseService.setOutput(list);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BikeTypeController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    
    /**
     * Consulta Reporte de ventas <br>
     * Info. Creación: <br>
     * fecha Ene 18, 2017 <br>
     *
     * @author Edwin Gómez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/sale/find", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> saleReport(@RequestBody ReportDTO paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            List<List<ReportCountDTO>> list = trainingPlanService.findSaleReport(paginateDto);
            responseService.setOutput(list);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BikeTypeController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
     
}
