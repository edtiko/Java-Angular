package co.com.expertla.training.web.controller;

import co.com.expertla.training.model.dto.DisciplineDTO;
import co.com.expertla.training.model.entities.ResponseService;
import co.com.expertla.training.service.DisciplineService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* Controller for Sport Disicipline <br>
* Creation Date : <br>
* date 14/07/2016 <br>
* @author Angela Ram�rez
**/
@RestController()
public class DisciplineController {
    
    @Autowired
    private DisciplineService disciplineService;
    
    @RequestMapping(value = "sportDiscipline/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAll() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<DisciplineDTO> disciplines = disciplineService.findAll();
            responseService.setOutput(disciplines);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(DisciplineController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

}
