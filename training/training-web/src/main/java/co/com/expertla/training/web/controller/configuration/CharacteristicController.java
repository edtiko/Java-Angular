package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.CharacteristicDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.entities.Characteristic;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.CharacteristicService;
import co.com.expertla.training.web.enums.StatusResponse;
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
* Characteristic Controller <br>
* Info. Creación: <br>
* fecha 8/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class CharacteristicController {

    @Autowired
    CharacteristicService characteristicService;  

    /**
     * Crea characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @return
     */
    @RequestMapping(value = "characteristic/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createCharacteristic(@RequestBody Characteristic characteristic) {
            ResponseService responseService = new ResponseService();
        try {  
            Characteristic characteristicName = new Characteristic();
            characteristicName.setName(characteristic.getName());
            List<Characteristic> listCharacteristicName = characteristicService.findByName(characteristicName);
            
            if(listCharacteristicName != null && !listCharacteristicName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.characteristic", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            characteristic.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            characteristic.setCreationDate(new Date());
            characteristicService.create(characteristic);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.characteristic", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CharacteristicController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @return
     */
    @RequestMapping(value = "characteristic/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateCharacteristic(@RequestBody Characteristic characteristic) {
            ResponseService responseService = new ResponseService();
        try {    
            Characteristic characteristicName = new Characteristic();
            characteristicName.setName(characteristic.getName());
            List<Characteristic> listCharacteristicName = characteristicService.findByName(characteristicName);
            
            if(listCharacteristicName != null && !listCharacteristicName.isEmpty()) {
                boolean existName = false;
                for (Characteristic characteristic1 : listCharacteristicName) {
                    if (!characteristic1.getCharacteristicId().equals(characteristic.getCharacteristicId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.characteristic", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            characteristic.setLastUpdate(new Date());
            characteristicService.store(characteristic);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.characteristic", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CharacteristicController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @return
     */
    @RequestMapping(value = "characteristic/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteCharacteristic(@RequestBody Characteristic characteristic) {
            ResponseService responseService = new ResponseService();
        try {           
            characteristicService.remove(characteristic);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.characteristic", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CharacteristicController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/characteristic/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Characteristic> characteristicList = characteristicService.findAllActive();
            responseService.setOutput(characteristicList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CharacteristicController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta characteristic paginado <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/characteristic/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<CharacteristicDTO> characteristicList = characteristicService.findPaginate(paginateDto.getPage(), 
                    paginateDto.getLimit(), paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(characteristicList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CharacteristicController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
