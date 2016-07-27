package co.com.expertla.training.service.impl.questionnaire;

import co.com.expertla.training.dao.questionnaire.QuestionnaireResponseDao;
import co.com.expertla.training.dao.questionnaire.ResponseOptionDao;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.QuestionnaireResponseDTO;
import co.com.expertla.training.model.dto.ResponseOptionDTO;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionOption;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import co.com.expertla.training.model.entities.ResponseOption;
import co.com.expertla.training.model.util.UtilValidation;
import co.com.expertla.training.service.questionnaire.QuestionnaireResponseService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireResponseServiceImpl implements QuestionnaireResponseService{
    
    @Autowired
    private QuestionnaireResponseDao questionnaireResponseDao;
    @Autowired
    private ResponseOptionDao responseOptionDao;
    private final UtilValidation util = new UtilValidation();
    private List<String> errorList;
   
    @Override
    public List<QuestionnaireResponse> create(List<QuestionnaireResponseDTO> questionnaireResponseDtoList) throws TrainingException, Exception {
        List<QuestionnaireResponse> questionnaireResponseCreated = new ArrayList<>();
        List<QuestionnaireResponse> list = new ArrayList<>();
        List<QuestionnaireResponse> listCreated = new ArrayList<>();
        if (questionnaireResponseDtoList != null && !questionnaireResponseDtoList.isEmpty()) {

            Integer userId = questionnaireResponseDtoList.get(0).getUserId().getUserId();
            String ids = obtainQuestionnaireQuestionId(questionnaireResponseDtoList);

            Runnable taskCreateHistory = () -> {
                try {
                    createQuestionnaireResponseHistory(ids, userId);
                    createResponseOptionHistory(ids, userId);
                } catch (Exception ex) {
                    Logger.getLogger(QuestionnaireResponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            // start the thread
            new Thread(taskCreateHistory).start();
            deleteResponseOption(ids, userId);
            deleteQuestionnaireResponse(ids, userId);
            QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
            for (QuestionnaireResponseDTO questionnaireResponseDto : questionnaireResponseDtoList) {
                list = createQuestionnaireResponseList(questionnaireResponseDto, questionnaireResponseCreated,
                        questionnaireResponse);
            }
            listCreated = questionnaireResponseDao.create(list);
            List<ResponseOption> responseOptionListToCreate = createResponseOptionList(questionnaireResponseDtoList, listCreated);
            responseOptionDao.create(responseOptionListToCreate);
        }
        return listCreated;
    }
    
    /**
     * Creates the list of questionnaire response to create <br>
     * Creation Info:  <br>
     * date 1/12/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireResponseDto
     * @param questionnaireResponseCreated
     * @param questionnaireResponse
     * @return
     * @throws NerooException
     * @throws Exception 
     */
    private List<QuestionnaireResponse> createQuestionnaireResponseList(QuestionnaireResponseDTO questionnaireResponseDto, List<QuestionnaireResponse> questionnaireResponseCreated,
            QuestionnaireResponse questionnaireResponse) throws TrainingException, Exception {
        errorList = validateMandatoryFields(questionnaireResponseDto);
        if (errorList == null || errorList.isEmpty()) {
            questionnaireResponse = new QuestionnaireResponse();
            questionnaireResponse.setCreationDate(new Date());
            questionnaireResponse.setQuestionnaireQuestionId(questionnaireResponseDto.getQuestionnaireQuestionId());
            questionnaireResponse.setResponse(questionnaireResponseDto.getResponse());
            questionnaireResponse.setResponseTypeId(questionnaireResponseDto.getResponseTypeId());
            questionnaireResponse.setUserId(questionnaireResponseDto.getUserId());
            questionnaireResponseCreated.add(questionnaireResponse);
            return questionnaireResponseCreated;
        } else {
            throw new TrainingException(errorList);
        }
    }
    
    /**
     * Creates the list of response option to create <br>
     * Creation Info:  <br>
     * date 1/12/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireResponseDtoList
     * @param response
     * @return 
     */
    private List<ResponseOption> createResponseOptionList(List<QuestionnaireResponseDTO> questionnaireResponseDtoList ,List<QuestionnaireResponse> response) {
        List<ResponseOptionDTO> responseOptionList = new ArrayList<>();
        List<ResponseOption> responseOptionListToCreate = new ArrayList<>();
        HashMap<String, QuestionnaireResponse> map = buildQuestionnaireResponseMap(response);
        QuestionnaireResponse questionnaireResponse;
        ResponseOption responseOption;
        QuestionOption questionOption;
        for(QuestionnaireResponseDTO questionnaireResponseDto : questionnaireResponseDtoList)   {
            responseOptionList = questionnaireResponseDto.getResponseOptionList();
            
            if (responseOptionList != null && !responseOptionList.isEmpty()) {
                questionnaireResponse = new QuestionnaireResponse();
                questionnaireResponse = map.get(questionnaireResponseDto.getUserId().getUserId()+"-"+questionnaireResponseDto.getQuestionnaireQuestionId().getQuestionnaireQuestionId());
                for (ResponseOptionDTO objResponse : responseOptionList) {
                    responseOption = new ResponseOption();
                    questionOption = new QuestionOption();
                    questionOption.setQuestionOptionId(objResponse.getQuestionOptionId().getQuestionOptionId());
                    responseOption.setQuestionOptionId(questionOption);
                    responseOption.setQuestionnaireResponseId(questionnaireResponse);
                    responseOptionListToCreate.add(responseOption);
                }
            }
        }         
        return responseOptionListToCreate;
    }
    
    @Override
    public QuestionnaireResponse merge(QuestionnaireResponse questionnaireResponse) throws Exception {
        return questionnaireResponseDao.merge(questionnaireResponse);
    }

    @Override
    public void remove(QuestionnaireResponse questionnaireResponse) throws Exception {
        questionnaireResponseDao.remove(questionnaireResponse);
    }

    @Override
    public List<QuestionnaireResponse> findAll(SePaginator sePaginator) throws Exception {
        return questionnaireResponseDao.findAll(sePaginator);
    }

    @Override
    public List<QuestionnaireResponse> findByQuestionnaireResponseId(Integer questionnaireResponse) throws Exception {
        return questionnaireResponseDao.findByQuestionnaireResponseId(questionnaireResponse);
    }

    @Override
    public List<QuestionnaireResponse> findByQuestionnaireCategoryId(Integer questionnaireCategory) throws Exception {
        return questionnaireResponseDao.findByQuestionnaireCategoryId(questionnaireCategory);
    }

    @Override
    public List<QuestionnaireResponse> findByCategoryIdAndQuestionId(Integer questionnaireCategory, Integer questionnaireQuestion) throws Exception {
        return questionnaireResponseDao.findByCategoryIdAndQuestionId(questionnaireCategory, questionnaireQuestion);
    }
    
    @Override
    public List<QuestionnaireResponse> findByUserIdAndCategoryId(QuestionnaireResponse questionnaireResponse) throws Exception {
        return questionnaireResponseDao.findByUserIdAndCategoryId(questionnaireResponse);
    }
    
    /**
     * Validates the mandatoryFields the questionnaireResponse object should have before persisting
     * @param questionnaireResponseDto
     * @return 
     */
    private List<String> validateMandatoryFields(QuestionnaireResponseDTO questionnaireResponseDto) {
        errorList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("questionnaireQuestionId", questionnaireResponseDto.getQuestionnaireQuestionId());
        map.put("seUserId", questionnaireResponseDto.getUserId());
        map.put("responseTypeId",questionnaireResponseDto.getResponseTypeId());
        return errorList = util.validateFields(map);
    }
    
    @Override
    public void createQuestionnaireResponseHistory(String questionnaireQuestionId, Integer seUserId) throws Exception {
        questionnaireResponseDao.createQuestionnaireResponseHistory(questionnaireQuestionId, seUserId);
    }
    
    /**
     * Get the questionnaireQuestionIds separated by commas <br>
     * Creation Info:  <br>
     * date 25/11/2015 <br>
     * @author Angela Ramirez
     * @param list
     * @return 
     */
    private String obtainQuestionnaireQuestionId(List<QuestionnaireResponseDTO> list) {
        StringBuilder ids = new StringBuilder();
        list.stream().forEach((obj) -> {
            ids.append(obj.getQuestionnaireQuestionId().getQuestionnaireQuestionId());
            ids.append(",");
        });
        ids.deleteCharAt(ids.lastIndexOf(","));
        return ids.toString();
    }
    
    @Override
    public void createResponseOptionHistory(String questionnaireQuestionIds, Integer seUserId) throws Exception {
        questionnaireResponseDao.createResponseOptionHistory(questionnaireQuestionIds, seUserId);
    }
    
    @Override
    public void deleteQuestionnaireResponse(String questionnaireQuestionIds, Integer seUserId) throws Exception {
        questionnaireResponseDao.deleteQuestionnaireResponse(questionnaireQuestionIds, seUserId);
    }
    
    @Override
    public void deleteResponseOption(String questionnaireQuestionIds, Integer seUserId) throws Exception {
        questionnaireResponseDao.deleteResponseOption(questionnaireQuestionIds, seUserId);
    }
    
    /**
     * Builds a Hashmap of questionnaire being the userId and questionnaireQuestionId the key, and value the questionnaire response created <br>
     * Creation Info:  <br>
     * date 1/12/2015 <br>
     * @author Angela Ramirez
     * @param list
     * @return 
     */
    private HashMap<String, QuestionnaireResponse> buildQuestionnaireResponseMap(List<QuestionnaireResponse> list) {
        HashMap<String, QuestionnaireResponse> map = new HashMap<>();
        list.stream().forEach((obj) -> {
            map.put(obj.getUserId().getUserId()+"-"+obj.getQuestionnaireQuestionId().getQuestionnaireQuestionId(), obj);
        });
        return map;
    }
}