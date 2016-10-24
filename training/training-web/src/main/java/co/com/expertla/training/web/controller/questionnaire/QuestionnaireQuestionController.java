package co.com.expertla.training.web.controller.questionnaire;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.web.enums.StatusResponse;
import co.com.expertla.training.constant.MessageBundle;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.QuestionnaireQuestionDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.Questionnaire;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.DisciplineService;
import co.com.expertla.training.service.questionnaire.QuestionnaireQuestionService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Web Service of QuestionnaireQuestion <br>
 * Creation Info:  <br>
 * date 26/07/2016 <br>
 *
 * @author Edwin Gómez
 */
@RequestMapping("questionnaireQuestion")
@RestController
public class QuestionnaireQuestionController {

    @Autowired
    private QuestionnaireQuestionService questionnaireQuestionService;
    @Autowired
    private DisciplineService disciplineService;

    @RequestMapping(value = "/create/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Response create(@RequestBody List<QuestionnaireQuestionDTO> questionnaireQuestionList) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireQuestionList == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            questionnaireQuestionService.create(questionnaireQuestionList);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_QUESTION_PROPERTIES, "questionnaireQuestionCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/update/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Response updateQuestionnaireQuestion(@RequestBody QuestionnaireQuestion questionnaireQuestion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireQuestion == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<QuestionnaireQuestion> questionnaireQuestionList = questionnaireQuestionService.findByQuestionnaireQuestionId(questionnaireQuestion.getQuestionnaireQuestionId());
            if (questionnaireQuestionList != null && !questionnaireQuestionList.isEmpty()) {
                questionnaireQuestionService.merge(questionnaireQuestion);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_QUESTION_PROPERTIES, "questionnaireQuestionUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());

            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_QUESTION_PROPERTIES, "questionnaireQuestionDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/delete/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Response deleteQuestionnaireQuestion(@RequestBody List<QuestionnaireQuestion> questionnaireQuestion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireQuestion == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            for (QuestionnaireQuestion objQuestionnaireQuestion : questionnaireQuestion) {
                List<QuestionnaireQuestion> questionnaireQuestionList = questionnaireQuestionService.findByQuestionnaireQuestionId(objQuestionnaireQuestion.getQuestionnaireQuestionId());
                if (questionnaireQuestionList != null && !questionnaireQuestionList.isEmpty()) {
                    QuestionnaireQuestion questionnaireQuestionObj = questionnaireQuestionList.get(0);
                    questionnaireQuestionObj.setStateId(Short.parseShort(Status.DELETE.getId()));
                    questionnaireQuestionService.merge(questionnaireQuestionObj);
                    strResponse.append(String.format(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_QUESTION_PROPERTIES, "questionnaireQuestionDeleted"), questionnaireQuestionObj.getQuestionnaireQuestionId()));
                    strResponse.append(",");
                } else {
                    strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_QUESTION_PROPERTIES, "questionnaireQuestionDoesNotExist"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                }
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Response getQuestionnaireQuestionById(@RequestBody QuestionnaireQuestion questionnaireQuestion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireQuestion == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionnaireQuestion> questionnaireQuestionList = questionnaireQuestionService.findByQuestionnaireQuestionId(questionnaireQuestion.getQuestionnaireQuestionId());
            if (questionnaireQuestionList != null && !questionnaireQuestionList.isEmpty()) {
                QuestionnaireQuestion questionnaireQuestionObj = questionnaireQuestionList.get(0);
                responseService.setOutput(questionnaireQuestionObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_QUESTION_PROPERTIES, "questionnaireQuestionNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/questionnaire/user/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Response getAllQuestionnaireQuestion(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer disciplineId = disciplineService.findByUserId(userId).getDisciplineId();
            if (disciplineId == null) {
                strResponse.append("No existe una disciplina asociada al usuario recibido");
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return Response.status(Response.Status.NOT_FOUND).entity(responseService).build();
            } else {
                List<QuestionnaireQuestionDTO> questionnaireQuestionList = questionnaireQuestionService.findByDisciplineId(disciplineId, userId);
                responseService.setOutput(questionnaireQuestionList);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/by/questionnaireId/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Response getByQuestionnaireId(@RequestBody Questionnaire questionnaire) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaire == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionnaireQuestion> questionnaireQuestionList = questionnaireQuestionService.findByQuestionnaireId(questionnaire);
            if (questionnaireQuestionList != null && !questionnaireQuestionList.isEmpty()) {
                responseService.setOutput(questionnaireQuestionList);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_QUESTION_PROPERTIES, "questionnaireQuestionNotFoundByQuestionnaireId"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/questionnaire/user/movil", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<ResponseService> getAllQuestionnaireQuestionMovil(@RequestBody UserDTO userDto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer disciplineId = disciplineService.findByUserId(userDto.getUserId()).getDisciplineId();
            if (disciplineId == null) {
                strResponse.append("No existe una disciplina asociada al usuario recibido");
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return new ResponseEntity<>(responseService, HttpStatus.NOT_FOUND);
            } else {
                List<QuestionnaireQuestionDTO> questionnaireQuestionList = questionnaireQuestionService.findByDisciplineId(disciplineId, userDto.getUserId());
                responseService.setOutput(questionnaireQuestionList);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireQuestionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
