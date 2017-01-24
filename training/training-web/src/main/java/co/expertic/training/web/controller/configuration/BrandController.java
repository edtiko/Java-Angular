package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.BrandDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.Brand;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.BrandService;
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
* Brand Controller <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class BrandController {

    @Autowired
    BrandService brandService;  

    /**
     * Crea brand <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param brand
     * @return
     */
    @RequestMapping(value = "brand/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createBrand(@RequestBody Brand brand) {
            ResponseService responseService = new ResponseService();
        try {  
            Brand brandName = new Brand();
            brandName.setName(brand.getName());
            List<Brand> listBrandName = brandService.findByName(brandName);
            
            if(listBrandName != null && !listBrandName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.brand", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            brand.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            brand.setCreationDate(new Date());
            brandService.create(brand);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.brand", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BrandController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica brand <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param brand
     * @return
     */
    @RequestMapping(value = "brand/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateBrand(@RequestBody Brand brand) {
            ResponseService responseService = new ResponseService();
        try {    
            Brand brandName = new Brand();
            brandName.setName(brand.getName());
            List<Brand> listBrandName = brandService.findByName(brandName);
            
            if(listBrandName != null && !listBrandName.isEmpty()) {
                boolean existName = false;
                for (Brand brand1 : listBrandName) {
                    if (!brand1.getBrandId().equals(brand.getBrandId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.brand", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            brand.setLastUpdate(new Date());
            brandService.store(brand);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.brand", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BrandController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina brand <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param brand
     * @return
     */
    @RequestMapping(value = "brand/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteBrand(@RequestBody Brand brand) {
            ResponseService responseService = new ResponseService();
        try {           
            brandService.remove(brand);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.brand", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BrandController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta brand <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/brand/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Brand> brandList = brandService.findAllActive();
            responseService.setOutput(brandList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BrandController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta brand paginado <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/brand/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<BrandDTO> brandList = brandService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(brandList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BrandController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
