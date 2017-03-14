/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.plan;

import co.expertic.training.model.dto.AthleteDTO;
import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.CoachExtAthleteDTO;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.ColourIndicatorService;
import co.expertic.training.service.plan.CoachAssignedPlanService;
import co.expertic.training.service.plan.CoachExtAthleteService;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanMessageService;
import co.expertic.training.service.plan.PlanVideoService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edwin G
 */
@RestController
public class CoachAssignedPlanController {

    private static final Logger LOGGER = Logger.getLogger(CoachAssignedPlanController.class);

    @Autowired
    CoachAssignedPlanService coachService;

    @Autowired
    CoachExtAthleteService coachExtService;

    @Autowired
    MailCommunicationService mailCommunicationService;

    @Autowired
    ColourIndicatorService colourIndicatorService;

    @Autowired
    PlanVideoService planVideoService;

    @Autowired
    PlanMessageService planMessageService;

    @RequestMapping(value = "get/athtletes/{coachUserId}", method = RequestMethod.POST)
    public ResponseEntity<ResponseService> getAssignedAthletes(@PathVariable("coachUserId") Integer coachUserId, @RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
            List<CoachAssignedPlanDTO> athletes = coachService.findByCoachUserId(coachUserId, paginateDto);
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

            for (CoachAssignedPlanDTO athlete : athletes) {
                int countFirstColour = 0;
                int countSecondColour = 0;
                int countThirdColour = 0;
                List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(coachUserId, athlete.getAthleteUserId().getUserId());

                List<PlanMessageDTO> messages = planMessageService.getMessagesNotReadedByReceivingUserAndSendingUser(coachUserId, athlete.getAthleteUserId().getUserId());
//                planVideoService.getVideosByUser(coachUserId, coachUserId, fromto, tipoPlan);

                for (MailCommunicationDTO mail : mails) {
                    if (!mail.getRead()) {
                        mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
                        if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
                            countFirstColour++;
                        } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
                            countSecondColour++;
                        } else {
                            countThirdColour++;
                        }
                    }

                }

                for (PlanMessageDTO mail : messages) {
                    long hours = (calculateHourDifference(mail.getCreationDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
                if (countThirdColour > 0) {
                    athlete.setColor(thirdColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
                } else if (countSecondColour > 0) {
                    athlete.setColor(secondColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
                } else if (countFirstColour > 0) {
                    athlete.setColor(firstColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
                }
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(athletes);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "get/athletes/by/{coachUserId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getAthletes(@PathVariable("coachUserId") Integer coachUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {

            List<AthleteDTO> athletes = coachService.findAthletesByCoachUserId(coachUserId);

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(athletes);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "get/star/{coachUserId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAssignedStar(@PathVariable("coachUserId") Integer coachUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<UserDTO> stars = coachService.findStarByCoachUserId(coachUserId);

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(stars);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "get/assigned/plan/{athleteUserId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getAssignedPlan(@PathVariable("athleteUserId") Integer athleteUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            CoachAssignedPlanDTO assignedCoachInternal = coachService.findByAthleteUserId(athleteUserId);
            CoachExtAthleteDTO assignedCoachExternal = coachExtService.findByAthleteUserId(athleteUserId);
            if (assignedCoachInternal != null) {
                assignedCoachInternal.setExternal(false);
                responseService.setOutput(assignedCoachInternal);
            } else if (assignedCoachExternal != null) {
                assignedCoachExternal.setExternal(true);
                responseService.setOutput(assignedCoachExternal);
            } else {
                responseService.setStatus(StatusResponse.FAIL.getName());
                strResponse.append("El usuario no tiene asociado un plan activo.");
                responseService.setOutput(strResponse);
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(e.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "get/supervisors/by/star/{starUserId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getSupervisorsByStarUserId(@PathVariable("starUserId") Integer starUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<UserDTO> supervisors = coachService.findCoachByStarUserId(starUserId);

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(supervisors);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "get/count/plan/{coachUserId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getCountPlanByCoach(@PathVariable("coachUserId") Integer coachUserId) {
        ResponseService responseService = new ResponseService();
        try {
            List<ReportCountDTO> list = coachService.getCountByPlanCoach(coachUserId);
            responseService.setOutput(list);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(e.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
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
