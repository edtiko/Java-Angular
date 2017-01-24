package co.expertic.training.web.controller.questionnaire;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.web.enums.StatusResponse;
import co.expertic.training.constant.MessageBundle;
import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireRespHistory;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.questionnaire.QuestionnaireResponseHistoryService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("questionnaireResponseHistory")
public class QuestionnaireResponseHistoryController {

    @Autowired
    private QuestionnaireResponseHistoryService questionnaireResponseHistoryService;



    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public @ResponseBody Response create(@RequestBody QuestionnaireRespHistory questionnaireResponseHistory) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireResponseHistory == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            questionnaireResponseHistoryService.create(questionnaireResponseHistory);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_HISTORY_PROPERTIES, "questionnaireResponseHistoryCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireResponseHistoryController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseHistoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        } 
    }

    /**
     * Updates questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @return 
     */

    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public @ResponseBody Response updateQuestionnaireResponseHistory(@RequestBody QuestionnaireRespHistory questionnaireResponseHistory) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireResponseHistory == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<QuestionnaireRespHistory> questionnaireResponseHistoryList = questionnaireResponseHistoryService.findByQuestionnaireResponseHistoryId(questionnaireResponseHistory);
            if (questionnaireResponseHistoryList != null && !questionnaireResponseHistoryList.isEmpty()) {
                questionnaireResponseHistoryService.merge(questionnaireResponseHistory);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_HISTORY_PROPERTIES, "questionnaireResponseHistoryUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_HISTORY_PROPERTIES, "questionnaireResponseHistoryDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireResponseHistoryController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseHistoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    

    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST)
    public @ResponseBody Response getQuestionnaireResponseHistoryById(@RequestBody QuestionnaireRespHistory questionnaireResponseHistory) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireResponseHistory == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            List<QuestionnaireRespHistory> questionnaireResponseHistoryList = questionnaireResponseHistoryService.findByQuestionnaireResponseHistoryId(questionnaireResponseHistory);
            if(questionnaireResponseHistoryList != null && !questionnaireResponseHistoryList.isEmpty()) {
                QuestionnaireRespHistory questionnaireResponseHistoryObj = questionnaireResponseHistoryList.get(0);
                responseService.setOutput(questionnaireResponseHistoryObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_HISTORY_PROPERTIES, "questionnaireResponseHistoryNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseHistoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }

     @RequestMapping(value = "/get/all/", method = RequestMethod.POST)
    public @ResponseBody Response getAllQuestionnaireResponseHistory(@RequestBody SePaginator sePaginator) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<QuestionnaireRespHistory> questionnaireResponseHistoryList = questionnaireResponseHistoryService.findAll(sePaginator);
            responseService.setOutput(questionnaireResponseHistoryList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseHistoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
}
