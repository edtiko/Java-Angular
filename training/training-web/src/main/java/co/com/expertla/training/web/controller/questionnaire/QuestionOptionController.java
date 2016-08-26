package co.com.expertla.training.web.controller.questionnaire;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.StatusResponse;
import co.com.expertla.training.constant.MessageBundle;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionOption;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.questionnaire.QuestionOptionService;
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
@RequestMapping(value = "/questionOption")
public class QuestionOptionController {

    @Autowired
    private QuestionOptionService questionOptionService;

    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public @ResponseBody
    Response create(@RequestBody QuestionOption questionOption) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionOption == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            questionOption.setStateId(Short.parseShort(Status.ACTIVE.getId()));
            questionOptionService.create(questionOption);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionOptionController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionOptionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public @ResponseBody
    Response updateQuestionOption(@RequestBody QuestionOption questionOption) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionOption == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<QuestionOption> questionOptionList = questionOptionService.findByQuestionOptionId(questionOption.getQuestionOptionId());
            if (questionOptionList != null && !questionOptionList.isEmpty()) {
                questionOptionService.merge(questionOption);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());

            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionOptionController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionOptionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    public @ResponseBody
    Response deleteQuestionOption(@RequestBody List<QuestionOption> listQuestionOption) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (listQuestionOption == null || listQuestionOption.isEmpty()) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            for (QuestionOption questionOption : listQuestionOption) {
                List<QuestionOption> questionOptionList = questionOptionService.findByQuestionOptionId(questionOption.getQuestionOptionId());

                if (questionOptionList != null && !questionOptionList.isEmpty()) {
                    QuestionOption objQuestionOption = questionOptionList.get(0);
                    objQuestionOption.setStateId(Short.parseShort(Status.DELETE.getId()));
                    questionOptionService.merge(objQuestionOption);
                    strResponse.append(String.format(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionDeleted"), objQuestionOption.getName()));
                    strResponse.append(",");
                } else {
                    strResponse.append(String.format(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionDeleteDoesNotExist"), questionOption.getQuestionOptionId()));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                }
            }
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionOptionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST)
    public @ResponseBody
    Response getQuestionOptionById(@RequestBody QuestionOption questionOption) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionOption == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionOption> questionOptionList = questionOptionService.findByQuestionOptionId(questionOption.getQuestionOptionId());
            if (questionOptionList != null && !questionOptionList.isEmpty()) {
                QuestionOption questionOptionObj = questionOptionList.get(0);
                responseService.setOutput(questionOptionObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionOptionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }

    @RequestMapping(value = "/get/all/", method = RequestMethod.POST)
    public @ResponseBody
    Response getAllQuestionOption(@RequestBody SePaginator sePaginator) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<QuestionOption> questionOptionList = questionOptionService.findAll(sePaginator);
            responseService.setOutput(questionOptionList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionOptionController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
}
