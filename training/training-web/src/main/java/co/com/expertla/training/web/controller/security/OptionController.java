package co.com.expertla.training.web.controller.security;

import co.com.expertla.training.model.dto.OptionDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.entities.Option;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.security.OptionService;
import co.com.expertla.training.web.enums.StatusResponse;
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
* Option Controller <br>
* Info. Creaci贸n: <br>
* fecha 25/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class OptionController {

    @Autowired
    OptionService optionService;  

    /**
     * Crea option <br>
     * Info. Creaci贸n: <br>
     * fecha 25/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param option
     * @return
     */
    @RequestMapping(value = "option/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createOption(@RequestBody Option option) {
            ResponseService responseService = new ResponseService();
        try {           
            optionService.create(option);
            responseService.setOutput("Option creado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear option");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica option <br>
     * Info. Creaci贸n: <br>
     * fecha 25/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param option
     * @return
     */
    @RequestMapping(value = "option/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateOption(@RequestBody Option option) {
            ResponseService responseService = new ResponseService();
        try {           
            optionService.store(option);
            responseService.setOutput("Option editado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar option");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina option <br>
     * Info. Creaci贸n: <br>
     * fecha 25/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param option
     * @return
     */
    @RequestMapping(value = "option/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteOption(@RequestBody Option option) {
            ResponseService responseService = new ResponseService();
        try {           
            optionService.remove(option);
            responseService.setOutput("Option eliminado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar option");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta option <br>
     * Info. Creaci贸n: <br>
     * fecha 25/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/option/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Option> optionList = optionService.findAllActive();
            responseService.setOutput(optionList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar option");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta option <br>
     * Info. Creaci髇: <br>
     * fecha 25/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/option/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {     
            List<OptionDTO> optionList = optionService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(optionList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar option");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
