package co.com.expertla.training.web.controller.questionnaire;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.StatusResponse;
import co.com.expertla.training.constant.MessageBundle;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.QuestionnaireDTO;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Questionnaire;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.questionnaire.QuestionnaireService;
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

/** 
 * REST Web Service for Questionnaire <br>
 * Creation Info:  <br>
 * date 15/08/2015 <br>
 * @author Angela Ramirez
 */
@RequestMapping("/questionnaire")
@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;


    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public @ResponseBody Response create(@RequestBody Questionnaire questionnaire) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaire == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            questionnaire.setStateId(Short.parseShort(Status.ACTIVE.getName()));
            questionnaireService.create(questionnaire);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }


    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public @ResponseBody Response updateQuestionnaire(@RequestBody Questionnaire questionnaire) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaire == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<Questionnaire> questionnaireList = questionnaireService.findByQuestionnaireId(questionnaire);
            if (questionnaireList != null && !questionnaireList.isEmpty()) {
                questionnaireService.merge(questionnaire);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());

            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }


    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    public @ResponseBody Response deleteQuestionnaire(@RequestBody List<Questionnaire> questionnaire) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaire == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            for (Questionnaire objQuestionnaire : questionnaire) {
                List<Questionnaire> questionnaireList = questionnaireService.findByQuestionnaireId(objQuestionnaire);
                if (questionnaireList != null && !questionnaireList.isEmpty()) {
                    Questionnaire questionnaireObj = questionnaireList.get(0);
                    questionnaireObj.setStateId(Short.parseShort(Status.DELETE.getName()));
                    questionnaireService.merge(questionnaireObj);
                    strResponse.append(String.format(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireDeleted"), questionnaireObj.getQuestionnaireId()));
                    strResponse.append(",");
                } else {
                    strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireDoesNotExist"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                }
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    

    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST)
    public @ResponseBody Response getQuestionnaireById(@RequestBody Questionnaire questionnaire) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaire == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<Questionnaire> questionnaireList = questionnaireService.findByQuestionnaireId(questionnaire);
            if (questionnaireList != null && !questionnaireList.isEmpty()) {
                Questionnaire questionnaireObj = questionnaireList.get(0);
                responseService.setOutput(questionnaireObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
    

    @RequestMapping(value = "/get/all/", method = RequestMethod.POST)
    public @ResponseBody Response getAmountQuestionAndResponseByQuestionnaire(@RequestBody SePaginator sePaginator) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<Questionnaire> questionnaireList = questionnaireService.findAmountQuestionAndResponseByQuestionnaire(sePaginator);
            
            if (questionnaireList != null && !questionnaireList.isEmpty()) {
                responseService.setOutput(questionnaireList);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
    

    @RequestMapping(value = "/get/by/questionnaireId/", method = RequestMethod.POST)
    public @ResponseBody Response getByQuestionnaireId(@RequestBody Questionnaire questionnaire) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaire == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionnaireDTO> questionnaireList = questionnaireService.findQuestionnaireDtoByQuestionnaireId(questionnaire);
            if (questionnaireList != null && !questionnaireList.isEmpty()) {
                QuestionnaireDTO questionnaireObj = questionnaireList.get(0);
                responseService.setOutput(questionnaireObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_PROPERTIES, "questionnaireNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
}
