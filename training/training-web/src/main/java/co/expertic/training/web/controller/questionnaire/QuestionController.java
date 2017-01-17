package co.expertic.training.web.controller.questionnaire;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.web.enums.StatusResponse;
import co.expertic.training.constant.MessageBundle;
import co.expertic.training.enums.Status;
import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.QuestionnaireQuestionDTO;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.Question;
import co.expertic.training.model.entities.QuestionnaireResponse;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.questionnaire.QuestionService;
import co.expertic.training.service.questionnaire.QuestionnaireQuestionService;
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
 * REST Web Service of Question <br>
 * Creation Info:  <br>
 * date 15/08/2015 <br>
 * @author Angela Ramirez
 */
@RequestMapping("/question")
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private QuestionnaireQuestionService questionnaireQuestionService;


    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public @ResponseBody Response create(@RequestBody Question question) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (question == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            question.setStateId(Short.parseShort(Status.ACTIVE.getId()));
            questionService.create(question);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        } 
    }


    
    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public @ResponseBody Response updateQuestion(@RequestBody Question question) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (question == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<Question> questionList = questionService.findByQuestionId(question);
            if (questionList != null && !questionList.isEmpty()) {
                questionService.merge(question);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
 
    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    public @ResponseBody Response deleteQuestion(@RequestBody List<Question> listQuestion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (listQuestion == null || listQuestion.isEmpty()) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            for (Question question : listQuestion) {
                List<Question> questionList = questionService.findByQuestionId(question);
                
                if (questionList != null && !questionList.isEmpty()) {
                    Question objQuestion = questionList.get(0);
                    objQuestion.setStateId(Short.parseShort(Status.DELETE.getId()));
                    questionService.merge(objQuestion);
                    strResponse.append(String.format(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionDeleted"), objQuestion.getName()));
                    strResponse.append(",");
                } else {
                    strResponse.append(String.format(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionDeleteDoesNotExist"), question.getQuestionId()));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                }
            }
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST)
    public @ResponseBody Response getQuestionById(@RequestBody Question question) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (question == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            List<Question> questionList = questionService.findByQuestionId(question);
            if(questionList != null && !questionList.isEmpty()) {
                Question questionObj = questionList.get(0);
                responseService.setOutput(questionObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
    
  
    @RequestMapping(value = "/get/all/", method = RequestMethod.POST)
    public @ResponseBody Response getAllQuestion(@RequestBody SePaginator sePaginator) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<Question> questionList = questionService.findAll(sePaginator);
            responseService.setOutput(questionList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
    

    @RequestMapping(value = "/get/by/categoryId/and/questionnaireId/and/userId/", method = RequestMethod.POST)
    public @ResponseBody Response getByCategoryIdAndQuestionnaireId(@RequestBody QuestionnaireResponse questionnaireResponse) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireResponse == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            List<QuestionnaireQuestionDTO> questionList = questionnaireQuestionService.findByCategoryIdAndQuestionnaireIdAndUserId(questionnaireResponse);
            if(questionList != null && !questionList.isEmpty()) {
                responseService.setOutput(questionList);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
}
