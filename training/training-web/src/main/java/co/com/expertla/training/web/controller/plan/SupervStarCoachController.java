package co.com.expertla.training.web.controller.plan;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.MailCommunicationDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.SupervStarCoachDTO;
import co.com.expertla.training.model.dto.UserAssignedSupervisorDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.ColourIndicator;
import co.com.expertla.training.model.entities.SupervStarCoach;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.ColourIndicatorService;
import co.com.expertla.training.service.plan.MailCommunicationService;
import co.com.expertla.training.service.plan.SupervStarCoachService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.Date;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SupervStarCoach Controller <br>
 * Info. Creaci贸n: <br>
 * fecha Sep 13, 2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
@RestController
public class SupervStarCoachController {

    @Autowired
    SupervStarCoachService supervStarCoachService;

    @Autowired
    private MailCommunicationService mailCommunicationService;

    @Autowired
    private ColourIndicatorService colourIndicatorService;

    /**
     * Crea supervStarCoach <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 13, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     */
    @RequestMapping(value = "supervStarCoach/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createSupervStarCoach(@RequestBody SupervStarCoach supervStarCoach) {
        ResponseService responseService = new ResponseService();
        try {
            supervStarCoach.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            supervStarCoach.setCreationDate(new Date());
            supervStarCoachService.create(supervStarCoach);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.supervstarcoach", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica supervStarCoach <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 13, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     */
    @RequestMapping(value = "supervStarCoach/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateSupervStarCoach(@RequestBody SupervStarCoach supervStarCoach) {
        ResponseService responseService = new ResponseService();
        try {
//            supervStarCoach.setLastUpdate(new Date());
            supervStarCoachService.store(supervStarCoach);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.supervstarcoach", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina supervStarCoach <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 13, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     */
    @RequestMapping(value = "supervStarCoach/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteSupervStarCoach(@RequestBody SupervStarCoach supervStarCoach) {
        ResponseService responseService = new ResponseService();
        try {
            supervStarCoachService.remove(supervStarCoach);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.supervstarcoach", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta supervStarCoach <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 13, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/supervStarCoach/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {
            List<SupervStarCoach> supervStarCoachList = supervStarCoachService.findAllActive();
            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta supervStarCoach paginado <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 13, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/supervStarCoach/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
            List<SupervStarCoachDTO> supervStarCoachList = supervStarCoachService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                    paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta supervStarCoach por coach Id<br>
     * Info. Creaci贸n: <br>
     * fecha Sep 13, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param coachId
     * @return
     */
    @RequestMapping(value = "/supervStarCoach/get/by/coachId/{coachId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> findByCoachId(@PathVariable("coachId") Integer coachId) {
        ResponseService responseService = new ResponseService();
        try {
            List<SupervStarCoach> supervStarCoachList = supervStarCoachService.findByCoachId(coachId);
            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta supervStarCoach por supervisor Id<br>
     * Info. Creaci髇: <br>
     * fecha 19/09/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/coach/by/supervisor/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> findBySupervisor(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            List<UserAssignedSupervisorDTO> supervStarCoachList = supervStarCoachService.findBySupervisorId(userId);

            List<ColourIndicator> colours = colourIndicatorService.findAll();
            int firstOrder = 0;
            int secondOrder = 0;
            int thirdOrder = 0;
            String firstColour = "{'background-color':'white'}";
            String secondColour = "{'background-color':'white'}";
            String thirdColour = "{'background-color':'white'}";
            for (ColourIndicator colour : colours) {
                if (colour.getColourOrder().equals(1)) {
                    firstOrder = colour.getHoursSpent();
                    firstColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(2)) {
                    secondOrder = colour.getHoursSpent();
                    secondColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(3)) {
                    thirdOrder = colour.getHoursSpent();
                    thirdColour = colour.getColour();
                }
            }

//            for (UserAssignedSupervisorDTO userAssignedSupervisorDTO : supervStarCoachList) {
//
//                List<MailCommunicationDTO> listMailCommunicationDTO = mailCommunicationService.getMailsByReceivingUserIdFromSendingUserRead(userId,
//                        userAssignedSupervisorDTO.getCoachUserId().getUserId(), false);
//                
//
//                if (listMailCommunicationDTO != null && !listMailCommunicationDTO.isEmpty()) {
//                    MailCommunicationDTO mail = listMailCommunicationDTO.get(0);
//                    mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
//                    if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
//                        userAssignedSupervisorDTO.setColourCoach(firstColour);
//                    } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
//                        userAssignedSupervisorDTO.setColourCoach(secondColour);
//                    } else {
//                        userAssignedSupervisorDTO.setColourCoach(thirdColour);
//                    }
//                }
//                
//                listMailCommunicationDTO = mailCommunicationService.getMailsByReceivingUserIdFromSendingUserRead(userId,
//                        userAssignedSupervisorDTO.getStarUserId().getUserId(), false);
//                
//
//                if (listMailCommunicationDTO != null && !listMailCommunicationDTO.isEmpty()) {
//                    MailCommunicationDTO mail = listMailCommunicationDTO.get(0);
//                    mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
//                    if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
//                        userAssignedSupervisorDTO.setColourStar(firstColour);
//                    } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
//                        userAssignedSupervisorDTO.setColourStar(secondColour);
//                    } else {
//                        userAssignedSupervisorDTO.setColourStar(thirdColour);
//                    }
//                }
//            }

            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta supervStarCoach por supervisor Id<br>
     * Info. Creaci髇: <br>
     * fecha 19/09/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/atlete/coach/by/supervisor/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> findAtelteCoachBySupervisor(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            List<UserAssignedSupervisorDTO> supervStarCoachList = supervStarCoachService.findAtleteCoachBySupervisorId(userId);

            List<ColourIndicator> colours = colourIndicatorService.findAll();
            int firstOrder = 0;
            int secondOrder = 0;
            int thirdOrder = 0;
            String firstColour = "{'background-color':'white'}";
            String secondColour = "{'background-color':'white'}";
            String thirdColour = "{'background-color':'white'}";
            for (ColourIndicator colour : colours) {
                if (colour.getColourOrder().equals(1)) {
                    firstOrder = colour.getHoursSpent();
                    firstColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(2)) {
                    secondOrder = colour.getHoursSpent();
                    secondColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(3)) {
                    thirdOrder = colour.getHoursSpent();
                    thirdColour = colour.getColour();
                }
            }

            for (UserAssignedSupervisorDTO userAssignedSupervisorDTO : supervStarCoachList) {

                List<MailCommunicationDTO> listMailCommunicationDTO = mailCommunicationService.getMailsByReceivingUserIdFromSendingUserRead(userId,
                        userAssignedSupervisorDTO.getCoachUserId().getUserId(), false);
                

                if (listMailCommunicationDTO != null && !listMailCommunicationDTO.isEmpty()) {
                    MailCommunicationDTO mail = listMailCommunicationDTO.get(0);
                    mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
                    if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
                        userAssignedSupervisorDTO.setColourCoach(firstColour);
                    } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
                        userAssignedSupervisorDTO.setColourCoach(secondColour);
                    } else {
                        userAssignedSupervisorDTO.setColourCoach(thirdColour);
                    }
                }
                
                listMailCommunicationDTO = mailCommunicationService.getMailsByReceivingUserIdFromSendingUserRead(userId,
                        userAssignedSupervisorDTO.getAtleteUserId().getUserId(), false);
                

                if (listMailCommunicationDTO != null && !listMailCommunicationDTO.isEmpty()) {
                    MailCommunicationDTO mail = listMailCommunicationDTO.get(0);
                    mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
                    if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
                        userAssignedSupervisorDTO.setColourAtlete(firstColour);
                    } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
                        userAssignedSupervisorDTO.setColourAtlete(secondColour);
                    } else {
                        userAssignedSupervisorDTO.setColourAtlete(thirdColour);
                    }
                }
            }

            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta supervStarCoach por supervisor Id<br>
     * Info. Creaci髇: <br>
     * fecha 19/09/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/users/by/supervisor/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> findUsersBySupervisor(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            List<UserDTO> supervStarCoachList = supervStarCoachService.findUsersBySupervisorId(userId);
            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    private long calculateHourDifference(Date creationDate) {
        Date now = new Date();
        long diff = now.getTime() - creationDate.getTime();
        long hoursSpent = diff / (60 * 60 * 1000);
        return hoursSpent;
    }
}
