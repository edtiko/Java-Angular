/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.user;

import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.user.MechanicService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Edwin G
 */
@RestController
@RequestMapping("/mechanic")
public class MechanicController {

    private static final Logger LOGGER = Logger.getLogger(MechanicController.class);
    
    @Autowired
    private MechanicService mechanicService;

    @RequestMapping(value = "/get/athletes/paginate/{userId}", method = RequestMethod.POST)
    public ResponseEntity<ResponseService> getAthletesPaginate(@PathVariable("userId") Integer userId, @RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            List<UserResumeDTO> athletes = mechanicService.findAthletesByUserPaginate(userId, paginateDto);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(athletes);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
}
