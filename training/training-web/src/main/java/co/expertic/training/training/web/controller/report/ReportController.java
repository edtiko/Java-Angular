/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.training.web.controller.report;

import co.com.expertla.training.model.dto.MarketingDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.SportEquipmentService;
import co.com.expertla.training.web.controller.configuration.BikeTypeController;
import co.com.expertla.training.web.enums.StatusResponse;
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
    
        /**
     * Consulta marketing paginado <br>
     * Info. Creación: <br>
     * fecha Ene 16, 2017 <br>
     * @author  Edwin Gómez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/marketing/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<MarketingDTO> list = sportEquipmentService.findMarketingPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), new Object());
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
