package co.expertic.training.web.controller.questionnaire;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.web.enums.StatusResponse;
import co.expertic.training.constant.MessageBundle;
import co.expertic.training.enums.Status;
import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.QuestionCategoryDTO;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireCategory;
import co.expertic.training.model.entities.QuestionnaireQuestion;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.questionnaire.QuestionnaireCategoryService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST Web Service of QuestionnaireCateogory <br>
 * Creation Info:  <br>
 * date 15/08/2015 <br>
 * @author Angela Ramirez
 */
@RequestMapping("/questionnaireCategory")
public class QuestionnaireCategoryController {

    @Autowired
    private QuestionnaireCategoryService questionnaireCategoryService;


    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public @ResponseBody Response create(@RequestBody QuestionnaireCategory questionnaireCategory) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireCategory == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            questionnaireCategory.setStateId(Short.parseShort(Status.ACTIVE.getId()));
            questionnaireCategoryService.create(questionnaireCategory);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }


    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public @ResponseBody Response updateQuestionnaireCategory(@RequestBody QuestionnaireCategory questionnaireCategory) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireCategory == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<QuestionnaireCategory> questionnaireCategoryList = questionnaireCategoryService.findByQuestionnaireCategoryId(questionnaireCategory);
            if (questionnaireCategoryList != null && !questionnaireCategoryList.isEmpty()) {
                questionnaireCategoryService.merge(questionnaireCategory);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());

            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }


    @RequestMapping(value = "/delete/", method = RequestMethod.POST)
    public @ResponseBody Response deleteQuestionnaireCategory(@RequestBody List<QuestionnaireCategory> questionnaireCategory) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (questionnaireCategory == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            for (QuestionnaireCategory objQuestionnaireCategory : questionnaireCategory) {
                List<QuestionnaireCategory> questionnaireCategoryList = questionnaireCategoryService.findByQuestionnaireCategoryId(objQuestionnaireCategory);
                if (questionnaireCategoryList != null && !questionnaireCategoryList.isEmpty()) {
                    QuestionnaireCategory questionnaireCategoryObj = questionnaireCategoryList.get(0);
                    questionnaireCategoryObj.setStateId(Short.parseShort(Status.INACTIVE.getId()));
                    questionnaireCategoryService.merge(questionnaireCategoryObj);
                    strResponse.append(String.format(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryDeleted"), objQuestionnaireCategory.getQuestionnaireCategoryId()));
                    strResponse.append(",");
                } else {
                    strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryDoesNotExist"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                }
            }

            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }


    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST)
    public @ResponseBody Response getQuestionnaireCategoryById(@RequestBody QuestionnaireCategory questionnaireCategory) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();

        try {
            if (questionnaireCategory == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            List<QuestionnaireCategory> questionnaireCategoryList = questionnaireCategoryService.findByQuestionnaireCategoryId(questionnaireCategory);
            if (questionnaireCategoryList != null && !questionnaireCategoryList.isEmpty()) {
                QuestionnaireCategory questionnaireCategoryObj = questionnaireCategoryList.get(0);
                responseService.setOutput(questionnaireCategoryObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }

  
    @RequestMapping(value = "/get/all/", method = RequestMethod.POST)
    public @ResponseBody Response getAllQuestionnaireCategory(@RequestBody SePaginator sePaginator) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            QuestionCategoryDTO questionnaireCategoryList = questionnaireCategoryService.findAll(sePaginator);
            responseService.setOutput(questionnaireCategoryList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
    

    @RequestMapping(value = "/get/by/questionnaireId/", method = RequestMethod.POST)
    public @ResponseBody Response getQuestionnaireCategoryParentsByQuestionnaire(@RequestBody QuestionnaireQuestion questionnaireQuestion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();

        try {
            if (questionnaireQuestion == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            QuestionCategoryDTO questionCategoryDto= questionnaireCategoryService.findByQuestionnaireId(questionnaireQuestion);
            if (questionCategoryDto != null) {
                responseService.setOutput(questionCategoryDto);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/by/questionnaireCategoryId/", method = RequestMethod.POST)
    public @ResponseBody Response getChildrenQuestionnaireCategoryByQuestionnaire(@RequestBody QuestionnaireQuestion questionnaireQuestion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();

        try {
            if (questionnaireQuestion == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            QuestionCategoryDTO questionCategoryDto= questionnaireCategoryService.findByQuestionnaireParentCategoryId(questionnaireQuestion);
            if (questionCategoryDto != null) {
                responseService.setOutput(questionCategoryDto);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.QUESTIONNAIRE_CATEGORY_PROPERTIES, "questionnaireCategoryNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(QuestionnaireCategoryController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
    
}
