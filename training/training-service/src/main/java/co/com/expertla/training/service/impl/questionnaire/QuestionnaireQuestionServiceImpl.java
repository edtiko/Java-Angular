package co.com.expertla.training.service.impl.questionnaire;


import co.com.expertla.training.dao.questionnaire.DataTypeDao;
import co.com.expertla.training.dao.questionnaire.QuestionnaireQuestionDao;
import co.com.expertla.training.dao.questionnaire.QuestionnaireResponseDao;
import co.com.expertla.training.dao.questionnaire.ResponseOptionDao;
import co.com.expertla.training.model.dto.QuestionOptionDto;
import co.com.expertla.training.model.dto.QuestionnaireQuestionDto;
import co.com.expertla.training.model.dto.QuestionnaireQuestionFormatDto;
import co.com.expertla.training.model.dto.QuestionnaireResponseDto;
import co.com.expertla.training.model.dto.ResponseOptionDto;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.DataType;
import co.com.expertla.training.model.entities.Question;
import co.com.expertla.training.model.entities.Questionnaire;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import co.com.expertla.training.model.entities.ResponseOption;
import co.com.expertla.training.service.questionnaire.QuestionnaireQuestionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireQuestionServiceImpl implements QuestionnaireQuestionService{
    
    @Autowired
    private QuestionnaireQuestionDao questionnaireQuestionDao;
    @Autowired
    private DataTypeDao dataTypeDao;
    @Autowired
    private QuestionnaireResponseDao questionnaireResponseDao;
    @Autowired
    private ResponseOptionDao responseOptionDao;
    
    @Override
    public QuestionnaireQuestion create(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        return questionnaireQuestionDao.create(questionnaireQuestion);
    }

    @Override
    public QuestionnaireQuestion merge(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        return questionnaireQuestionDao.merge(questionnaireQuestion);
    }

    @Override
    public void remove(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        questionnaireQuestionDao.remove(questionnaireQuestion);
    }

    @Override
    public List<QuestionnaireQuestion> findAll(SePaginator sePaginator) throws Exception {
        return questionnaireQuestionDao.findAll(sePaginator);
    }

    @Override
    public List<QuestionnaireQuestion> findByQuestionnaireQuestionId(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        return questionnaireQuestionDao.findByQuestionnaireQuestionId(questionnaireQuestion);
    }
    
    @Override
    public List<QuestionnaireQuestion> findByQuestionnaireIdAndCategoryId(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        return questionnaireQuestionDao.findByQuestionnaireIdAndCategoryId(questionnaireQuestion);
    }
    
    @Override
    public List<QuestionnaireQuestion> findByQuestionnaireId(Questionnaire questionnaire) throws Exception {
        return questionnaireQuestionDao.findByQuestionnaireId(questionnaire);
    }
    
    @Override
    public List<QuestionnaireQuestionDto> findByCategoryIdAndQuestionnaireIdAndUserId(QuestionnaireResponse questionnaireResponseParam) throws Exception {
        List<QuestionnaireQuestionFormatDto> resultList = questionnaireQuestionDao.findByCategoryIdAndQuestionnaireId(questionnaireResponseParam);
        List<QuestionnaireQuestionDto> questionnaireQuestionList = new ArrayList<>();
        HashMap<Integer, Question> questionMap = new HashMap<>();
        HashMap<Integer, QuestionnaireQuestionDto> questionnaireQuestionMap = new HashMap<>();
        QuestionnaireQuestionDto  questionnaireQuestionDto = new QuestionnaireQuestionDto();
        Question question = new Question();
        QuestionOptionDto questionOption = new QuestionOptionDto();
        DataType dataType = new DataType();
        QuestionnaireResponseDto questionnaireResponseDto = new QuestionnaireResponseDto();
        if (resultList != null && !resultList.isEmpty()) {
            HashMap<Integer, String> map = buildDataTypeHash();
            List<Integer> questionnaireQuestionIds = obtainQuestionnaireQuestionIds(resultList);
            List<QuestionnaireResponse> responseList = questionnaireResponseDao.findByUserIdAndQuestionnaireQuestionId(questionnaireQuestionIds, questionnaireResponseParam.getUserId().getUserId());
            List<Integer> questionnaireResponseIds = new ArrayList<>();
            questionnaireResponseIds.add(0);
            
            resultList.stream().forEach((objDto) -> {
                category(question, questionnaireQuestionDto, questionnaireResponseDto, 
                        objDto, questionnaireQuestionMap, questionnaireQuestionList, questionMap, 
                        dataType, 
                        map,
                        questionOption);
            });
            if(responseList !=null && !responseList.isEmpty()){
                questionnaireResponseIds = obtainQuestionnaireResponseIds(responseList);
                buildQuestionnaireResponse(responseList, questionnaireQuestionMap);
                List<ResponseOption> responseOptionList = responseOptionDao.findByQuestionnaireResponseIds(questionnaireResponseIds);
                buildResponseOption(responseOptionList, questionnaireQuestionMap);
            }
            
            
        }
        
        return questionnaireQuestionList;
    }
   
    /**
     * Builds a HashMap with all the dataTypes created, being dataTypeId the key <br>
     * Creation Info:  <br>
     * date 29/09/2015 <br>
     * @author Angela Ramirez 
     * @return
     * @throws Exception 
     */
     private HashMap<Integer, String> buildDataTypeHash() throws Exception {
        HashMap<Integer, String> map = new HashMap<>();
        List<DataType> dataTypeList = dataTypeDao.findAll(new SePaginator());
        dataTypeList.stream().forEach((dt) -> {
            map.put(dt.getDataTypeId(), dt.getName());
        });
        return map;
    }
     
    /**
     * Gets a list of the unique questionnaireQuestionIds <br>
     * Creation Info:  <br>
     * date 29/09/2015 <br>
     * @author Angela Ramirez 
     * @return
     * @throws Exception 
     */
    private List<Integer> obtainQuestionnaireQuestionIds(List<QuestionnaireQuestionFormatDto> list) {
        List<Integer> ids = new ArrayList<>();
        HashMap<Integer,QuestionnaireQuestionFormatDto> map = new HashMap<>();
        list.stream().filter((obj) -> (!map.containsKey(obj.getQuestionnaireQuestionId()))).map((obj) -> {
            map.put(obj.getQuestionnaireQuestionId(), obj);
            return obj;
        }).forEach((obj) -> {
            ids.add(obj.getQuestionnaireQuestionId());
        });
        return ids;
    } 
    
    /**
     * Gets a list of the unique questionnaireResponseIds <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez 
     * @return
     * @throws Exception 
     */
    private List<Integer> obtainQuestionnaireResponseIds(List<QuestionnaireResponse> list) {
        List<Integer> ids = new ArrayList<>();
        HashMap<Integer,QuestionnaireResponse> map = new HashMap<>();
        list.stream().filter((obj) -> (!map.containsKey(obj.getQuestionnaireResponseId()))).map((obj) -> {
            map.put(obj.getQuestionnaireResponseId(), obj);
            return obj;
        }).forEach((obj) -> {
            ids.add(obj.getQuestionnaireResponseId());
        });
        return ids;
    } 
    
    private void category(Question question, QuestionnaireQuestionDto questionnaireQuestionDto, QuestionnaireResponseDto questionnaireResponseDto,
            QuestionnaireQuestionFormatDto objDto,HashMap<Integer, QuestionnaireQuestionDto> questionnaireQuestionMap, List<QuestionnaireQuestionDto> questionnaireQuestionList,
            HashMap<Integer, Question> questionMap,DataType dataType,HashMap<Integer, String> map, QuestionOptionDto questionOption) {
                
                question = new Question();
                questionnaireQuestionDto = new QuestionnaireQuestionDto();
                questionnaireResponseDto = new QuestionnaireResponseDto();
                //If the map of questionnaireQuestion doesn't contain the questionnaire question, creates the questionnaireQuestion
                //And add it to the map and the list, otherwise just obtain it from the map
                if(!questionnaireQuestionMap.containsKey(objDto.getQuestionnaireQuestionId())) {
                    //Build the questionnaire question object
                    questionnaireQuestionDto.setQuestionnaireQuestionId(objDto.getQuestionnaireQuestionId());
                    questionnaireQuestionDto.setCreationDate(objDto.getQuestionnaireQuestionDate());
                    questionnaireQuestionDto.setQuestionOrder(objDto.getQuestionOrder());
                    questionnaireQuestionDto.setStateId(objDto.getStateId());
                    questionnaireQuestionMap.put(objDto.getQuestionnaireQuestionId(), questionnaireQuestionDto);
                    questionnaireQuestionList.add(questionnaireQuestionDto);
                } else {
                    questionnaireQuestionDto = questionnaireQuestionMap.get(objDto.getQuestionnaireQuestionId());
                }
                
                //If the map of question doesn't contain the question, creates the question
                //And add it to the map, otherwise just obtain it from the map   
                if(!questionMap.containsKey(objDto.getQuestionId())) {
                    //Build the data type object
                    dataType = new DataType();
                    dataType.setName(map.get(objDto.getDataTypeId()));
                    dataType.setDataTypeId(objDto.getDataTypeId());
                    //Build the object Question
                    question = buildQuestionObject(objDto, dataType);
                    questionMap.put(objDto.getQuestionId(),question);
                } else {
                    question = questionMap.get(objDto.getQuestionId());
                }

                if (objDto.getQuestionOptionId() != null) {
                    if (!questionnaireQuestionDto.getHashOption().containsKey(objDto.getQuestionOptionId())) {
                        //Build the question option object
                        questionOption = new QuestionOptionDto();
                        questionOption.setName(objDto.getQuestionOptionName());
                        questionOption.setQuestionOptionId(objDto.getQuestionOptionId());
                        questionOption.setStateId(objDto.getStateId());
                        questionOption.setLabel(objDto.getQuestionOptionlabel());
                        questionnaireQuestionDto.getQuestionOptionList().add(questionOption);
                        questionnaireQuestionDto.getHashOption().put(questionOption.getQuestionOptionId(), questionnaireQuestionDto.getQuestionOptionList().size() - 1);
                    }
                }
                questionnaireQuestionDto.setQuestionId(question);
    }

    /**
     * Build the question object to be added to the questionnaireQuestion Object
     * @param objDto
     * @param dataType
     * @return 
     */
    private Question buildQuestionObject(QuestionnaireQuestionFormatDto objDto, DataType dataType) {
        Question question = new Question();
        question.setCreationDate(objDto.getQuestionDate());
        question.setDataTypeId(dataType);
        question.setDescription(objDto.getQuestionDesc());
        question.setIndAdditional(objDto.getIndAdditional());
        question.setName(objDto.getQuestionName());
        question.setQuestionId(objDto.getQuestionId());
        question.setQuestionType(objDto.getQuestionType());
        question.setStateId(objDto.getStateId());
        question.setUnit(objDto.getUnit());
        return question;
    }
    
    /**
     * Builds the questionnaireResponse to be added to the questionnaireQuestion
     * @param responseList
     * @param questionnaireQuestionMap 
     */
    private void buildQuestionnaireResponse(List<QuestionnaireResponse> responseList, HashMap<Integer, QuestionnaireQuestionDto> questionnaireQuestionMap) {
        QuestionnaireResponseDto questionnaireResponseDto; 
        for (QuestionnaireResponse obj : responseList) {
            questionnaireResponseDto = new QuestionnaireResponseDto();
            questionnaireResponseDto.setCreationDate(obj.getCreationDate());
            questionnaireResponseDto.setQuestionnaireResponseId(obj.getQuestionnaireResponseId());
            questionnaireResponseDto.setResponse(obj.getResponse());
            questionnaireResponseDto.setResponseTypeId(obj.getResponseTypeId());
            questionnaireQuestionMap.get(obj.getQuestionnaireQuestionId().getQuestionnaireQuestionId()).setQuestionnaireResponse(questionnaireResponseDto);         
        }
    }
    
    /**
     * Builds the responseOption List to be added to the questionnaireResponse that belongs to the questionnaireQuestion
     * @param responseOptionList
     * @param questionnaireQuestionMap 
     */
    private void buildResponseOption(List<ResponseOption> responseOptionList, HashMap<Integer, QuestionnaireQuestionDto> questionnaireQuestionMap) {
        if (responseOptionList != null && !responseOptionList.isEmpty()) {
            QuestionOptionDto questionOption;
            ResponseOptionDto responseOptionDto;
            QuestionnaireResponseDto questionnaireResponseDto;
            QuestionnaireQuestionDto questionnaireQuestionDto;
            for (ResponseOption objResponse : responseOptionList) {
                questionOption = new QuestionOptionDto();
                questionOption.setName(objResponse.getQuestionOptionId().getName());
                questionOption.setQuestionOptionId(objResponse.getQuestionOptionId().getQuestionOptionId());
                questionOption.setStateId(objResponse.getQuestionOptionId().getStateId());
                responseOptionDto = new ResponseOptionDto();
                responseOptionDto.setQuestionOptionId(questionOption);
                responseOptionDto.setResponseOptionId(objResponse.getResponseOptionId());
                questionnaireQuestionDto = questionnaireQuestionMap.get(objResponse.getQuestionnaireResponseId().getQuestionnaireQuestionId().getQuestionnaireQuestionId());
                questionnaireResponseDto = questionnaireQuestionDto.getQuestionnaireResponse();
                questionnaireResponseDto.getResponseOptionList().add(responseOptionDto);
                questionnaireQuestionDto.setQuestionnaireResponse(questionnaireResponseDto);
            }
        }
    }
}