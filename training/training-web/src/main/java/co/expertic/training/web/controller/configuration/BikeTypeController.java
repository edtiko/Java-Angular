package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.BikeTypeDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.BikeType;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.BikeTypeService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.Date;
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
* BikeType Controller <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class BikeTypeController {

    @Autowired
    BikeTypeService bikeTypeService;  

    /**
     * Crea bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @return
     */
    @RequestMapping(value = "bikeType/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createBikeType(@RequestBody BikeType bikeType) {
            ResponseService responseService = new ResponseService();
        try {  
            BikeType bikeTypeName = new BikeType();
            bikeTypeName.setName(bikeType.getName());
            List<BikeType> listBikeTypeName = bikeTypeService.findByName(bikeTypeName);
            
            if(listBikeTypeName != null && !listBikeTypeName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.biketype", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            bikeType.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            bikeType.setCreationDate(new Date());
            bikeTypeService.create(bikeType);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.biketype", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BikeTypeController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @return
     */
    @RequestMapping(value = "bikeType/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateBikeType(@RequestBody BikeType bikeType) {
            ResponseService responseService = new ResponseService();
        try {    
            BikeType bikeTypeName = new BikeType();
            bikeTypeName.setName(bikeType.getName());
            List<BikeType> listBikeTypeName = bikeTypeService.findByName(bikeTypeName);
            
            if(listBikeTypeName != null && !listBikeTypeName.isEmpty()) {
                boolean existName = false;
                for (BikeType bikeType1 : listBikeTypeName) {
                    if (!bikeType1.getBikeTypeId().equals(bikeType.getBikeTypeId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.biketype", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            bikeType.setLastUpdate(new Date());
            bikeTypeService.store(bikeType);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.biketype", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BikeTypeController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @return
     */
    @RequestMapping(value = "bikeType/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteBikeType(@RequestBody BikeType bikeType) {
            ResponseService responseService = new ResponseService();
        try {           
            bikeTypeService.remove(bikeType);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.biketype", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BikeTypeController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/bikeType/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<BikeType> bikeTypeList = bikeTypeService.findAllActive();
            responseService.setOutput(bikeTypeList);
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
     * Consulta bikeType paginado <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/bikeType/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<BikeTypeDTO> bikeTypeList = bikeTypeService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(bikeTypeList);
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
