package co.com.expertla.training.web.controller.questionnaire;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.web.enums.StatusResponse;
import co.com.expertla.training.constant.MessageBundle;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.QuestionnaireQuestionDTO;
import co.com.expertla.training.model.dto.QuestionnaireResponseDTO;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.questionnaire.QuestionnaireResponseService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/questionnaireResponse")
public class QuestionnaireResponseController {

     private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(QuestionnaireResponseController.class);
     
    @Autowired
    private QuestionnaireResponseService questionnaireResponseService;

    /**
     * Creates questionnaireResponse <br>
     * Creation Info: <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * Info Update: <br>
     * date 25/11/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireResponseList
     * @return
     */

    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public @ResponseBody Response create(@RequestBody List<QuestionnaireResponseDTO> questionnaireResponseList) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireResponseList == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<QuestionnaireResponse> questionnaireResponseCreatedList = questionnaireResponseService.create(questionnaireResponseList);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_PROPERTIES, "questionnaireResponseCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(questionnaireResponseCreatedList);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    /**
     * Updates questionnaireResponse <br>
     * Creation Info: <br>
     * date 15/08/2015 <br>
     *
     * @author Angela Ramirez
     *
     * @param questionnaireResponseId
     * @return
     */
   
    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public @ResponseBody Response updateQuestionnaireResponse(@PathVariable("questionnaireResponseId") Integer questionnaireResponseId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireResponseId == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<QuestionnaireResponse> questionnaireResponseList = questionnaireResponseService.findByQuestionnaireResponseId(questionnaireResponseId);
            if (questionnaireResponseList != null && !questionnaireResponseList.isEmpty()) {
                //questionnaireResponseService.merge(questionnaireResponse);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_PROPERTIES, "questionnaireResponseUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());

            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_PROPERTIES, "questionnaireResponseDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    /**
     * Get questionnaireResponse by id <br>
     * Creation Info: <br>
     * date 15/08/2015 <br>
     *
     * @author Angela Ramirez
     *
     * @param questionnaireResponseId
     * @return
     */

    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST)
    public @ResponseBody Response getQuestionnaireResponseById(@PathVariable("questionnaireResponseId") Integer questionnaireResponseId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();

        try {
            if (questionnaireResponseId == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionnaireResponse> questionnaireResponseList = questionnaireResponseService.findByQuestionnaireResponseId(questionnaireResponseId);
            if (questionnaireResponseList != null && !questionnaireResponseList.isEmpty()) {
                QuestionnaireResponse questionnaireResponseObj = questionnaireResponseList.get(0);
                responseService.setOutput(questionnaireResponseObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_PROPERTIES, "questionnaireResponseNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }

    /**
     * Gets all the records from questionnaireResponse <br>
     * Creation Info: <br>
     * date 15/08/2015 <br>
     *
     * @author Angela Ramirez
     *
     * @param sePaginator
     * @return
     */

    @RequestMapping(value = "/get/all/", method = RequestMethod.POST)
    public @ResponseBody Response getAllQuestionnaireResponse(@RequestBody SePaginator sePaginator) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<QuestionnaireResponse> questionnaireResponseList = questionnaireResponseService.findAll(sePaginator);
            responseService.setOutput(questionnaireResponseList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }

    /**
     * Get questionnaireResponse by questionnaireCategoryId <br>
     * Creation Info: <br>
     * date 25/08/2015 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     *
     * @param questionnaireQuestion
     * @return
     */

    @RequestMapping(value = "/get/by/questionnaireCategoryId/", method = RequestMethod.POST)
    public @ResponseBody Response getQuestionnaireResponseByQuestionnaireCategoryId(@RequestBody QuestionnaireQuestionDTO questionnaireQuestion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();

        try {
            if (questionnaireQuestion == null || questionnaireQuestion.getQuestionnaireCategoryId() == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionnaireResponse> questionnaireResponseList = questionnaireResponseService.findByQuestionnaireCategoryId(1);
            if (questionnaireResponseList != null && !questionnaireResponseList.isEmpty()) {
                QuestionnaireResponse questionnaireResponseObj = questionnaireResponseList.get(0);
                responseService.setOutput(questionnaireResponseObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_PROPERTIES, "questionnaireResponseNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }

    /**
     * Get QuestionnaireResponse by CategoryId and UserId <br>
     * Creation Info: <br>
     * date 25/08/2015 <br>
     *
     * @author Andres Felipe Lopez Rodriguez Info Update: <br>
     * date 02/09/2015 <br>
     * @author Angela Ramirez
     *
     * @param questionnaireResponse
     * @return
     */
    @Path("get/by/questionnaireCategoryId/and/userId")
    @POST
    @Produces("application/json")
    public Response getByQuestionnaireQuestionCategoryIdAndUserId(QuestionnaireResponse questionnaireResponse) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();

        try {
            if (questionnaireResponse == null || questionnaireResponse.getUserId() == null
                    || questionnaireResponse.getQuestionnaireQuestionId() == null
                    || questionnaireResponse.getQuestionnaireQuestionId().getQuestionnaireCategoryId() == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionnaireResponse> questionnaireResponseList = questionnaireResponseService.findByUserIdAndCategoryId(questionnaireResponse);
            if (questionnaireResponseList != null && !questionnaireResponseList.isEmpty()) {
                QuestionnaireResponse questionnaireResponseObj = questionnaireResponseList.get(0);
                responseService.setOutput(questionnaireResponseObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_RESPONSE_PROPERTIES, "questionnaireResponseNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireResponseController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
}
