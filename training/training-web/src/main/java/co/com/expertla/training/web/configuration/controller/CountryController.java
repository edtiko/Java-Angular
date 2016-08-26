package co.com.expertla.training.web.configuration.controller;

import co.com.expertla.training.service.configuration.CountryService;
import co.com.expertla.training.model.entities.Country;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
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
* Country Controller <br>
* Info. Creación: <br>
* fecha 19/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class CountryController {

    @Autowired
    CountryService countryService;  

    /**
     * Crea country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @return
     */
    @RequestMapping(value = "country/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createCountry(@RequestBody Country country) {
            ResponseService responseService = new ResponseService();
        try {           
            countryService.create(country);
            responseService.setOutput("Country creado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear country");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @return
     */
    @RequestMapping(value = "country/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateCountry(@RequestBody Country country) {
            ResponseService responseService = new ResponseService();
        try {           
            countryService.store(country);
            responseService.setOutput("Country editado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar country");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @return
     */
    @RequestMapping(value = "country/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteCountry(@RequestBody Country country) {
            ResponseService responseService = new ResponseService();
        try {           
            countryService.remove(country);
            responseService.setOutput("Country eliminado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar country");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/country/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Country> countryList = countryService.findAll();
            responseService.setOutput(countryList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar country");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
