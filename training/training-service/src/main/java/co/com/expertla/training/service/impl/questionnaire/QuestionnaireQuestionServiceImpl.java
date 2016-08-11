package co.com.expertla.training.service.impl.questionnaire;


import co.com.expertla.training.dao.questionnaire.DataTypeDao;
import co.com.expertla.training.dao.questionnaire.QuestionOptionDao;
import co.com.expertla.training.dao.questionnaire.QuestionnaireQuestionDao;
import co.com.expertla.training.dao.questionnaire.QuestionnaireResponseDao;
import co.com.expertla.training.dao.questionnaire.ResponseOptionDao;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.QuestionDTO;
import co.com.expertla.training.model.dto.QuestionOptionDTO;
import co.com.expertla.training.model.dto.QuestionnaireCategoryDTO;
import co.com.expertla.training.model.dto.QuestionnaireQuestionDTO;
import co.com.expertla.training.model.dto.QuestionnaireQuestionFormatDTO;
import co.com.expertla.training.model.dto.QuestionnaireResponseDTO;
import co.com.expertla.training.model.dto.ResponseOptionDTO;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.DataType;
import co.com.expertla.training.model.entities.Questionnaire;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import co.com.expertla.training.model.entities.ResponseOption;
import co.com.expertla.training.service.questionnaire.QuestionnaireQuestionService;
import co.com.expertla.training.service.questionnaire.QuestionnaireResponseService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionnaireQuestionServiceImpl implements QuestionnaireQuestionService{
    
    @Autowired
    private QuestionnaireQuestionDao questionnaireQuestionDao;
    @Autowired
    private DataTypeDao dataTypeDao;
    @Autowired
    private QuestionnaireResponseDao questionnaireResponseDao;
    @Autowired
    private ResponseOptionDao responseOptionDao;
    @Autowired
    private QuestionOptionDao questionOptionDao;
    @Autowired
    private QuestionnaireResponseService questionResponseService;
    
    @Override
    public void create(List<QuestionnaireQuestionDTO> questionnaireQuestion) throws Exception, TrainingException {
        
        questionnaireQuestion.stream().forEach((objDto) -> {
             
            try {
                questionResponseService.create(objDto.getQuestionnaireResponseList());
            } catch (Exception ex) {
                Logger.getLogger(QuestionnaireQuestionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        });

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
    public List<QuestionnaireQuestion> findByQuestionnaireQuestionId(Integer questionnaireQuestion) throws Exception {
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
    public List<QuestionnaireQuestionDTO> findByCategoryIdAndQuestionnaireIdAndUserId(QuestionnaireResponse questionnaireResponseParam) throws Exception {
        List<QuestionnaireQuestionFormatDTO> resultList = questionnaireQuestionDao.findByCategoryIdAndQuestionnaireId(questionnaireResponseParam);
        List<QuestionnaireQuestionDTO> questionnaireQuestionList = new ArrayList<>();
        HashMap<Integer, QuestionDTO> questionMap = new HashMap<>();
        HashMap<Integer, QuestionnaireQuestionDTO> questionnaireQuestionMap = new HashMap<>();
        QuestionnaireQuestionDTO  questionnaireQuestionDto = new QuestionnaireQuestionDTO();
        QuestionDTO question = new QuestionDTO();
        QuestionOptionDTO questionOption = new QuestionOptionDTO();
        DataType dataType = new DataType();
        QuestionnaireResponseDTO questionnaireResponseDto = new QuestionnaireResponseDTO();
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
    private List<Integer> obtainQuestionnaireQuestionIds(List<QuestionnaireQuestionFormatDTO> list) {
        List<Integer> ids = new ArrayList<>();
        HashMap<Integer,QuestionnaireQuestionFormatDTO> map = new HashMap<>();
        list.stream().filter((obj) -> (!map.containsKey(obj.getQuestionnaireQuestionId()))).map((obj) -> {
            map.put(obj.getQuestionnaireQuestionId(), obj);
            return obj;
        }).forEach((obj) -> {
            ids.add(obj.getQuestionnaireQuestionId());
        });
        return ids;
    } 
    
     private List<Integer> getQuestionnaireQuestionIds(List<QuestionnaireQuestion> list) {
        List<Integer> ids = new ArrayList<>();
        HashMap<Integer,QuestionnaireQuestion> map = new HashMap<>();
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
    
    private void category(QuestionDTO question, QuestionnaireQuestionDTO questionnaireQuestionDto, QuestionnaireResponseDTO questionnaireResponseDto,
            QuestionnaireQuestionFormatDTO objDto,HashMap<Integer, QuestionnaireQuestionDTO> questionnaireQuestionMap, List<QuestionnaireQuestionDTO> questionnaireQuestionList,
            HashMap<Integer, QuestionDTO> questionMap,DataType dataType,HashMap<Integer, String> map, QuestionOptionDTO questionOption) {
                
                question = new QuestionDTO();
                questionnaireQuestionDto = new QuestionnaireQuestionDTO();
                questionnaireResponseDto = new QuestionnaireResponseDTO();
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
                        questionOption = new QuestionOptionDTO();
                        questionOption.setName(objDto.getQuestionOptionName());
                        questionOption.setQuestionOptionId(objDto.getQuestionOptionId());
                        questionOption.setStateId(objDto.getStateId());
                        //questionnaireQuestionDto.getQuestionOptionList().add(questionOption);
                        //questionnaireQuestionDto.getHashOption().put(questionOption.getQuestionOptionId(), questionnaireQuestionDto.getQuestionOptionList().size() - 1);
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
    private QuestionDTO buildQuestionObject(QuestionnaireQuestionFormatDTO objDto, DataType dataType) {
        QuestionDTO question = new QuestionDTO();
        //question.setDataTypeId(dataType.getDataTypeId());
        question.setDescription(objDto.getQuestionDesc());
        question.setName(objDto.getQuestionName());
        question.setQuestionId(objDto.getQuestionId());
        question.setQuestionType(objDto.getQuestionType());
        return question;
    }
    
    /**
     * Builds the questionnaireResponse to be added to the questionnaireQuestion
     * @param responseList
     * @param questionnaireQuestionMap 
     */
    private void buildQuestionnaireResponse(List<QuestionnaireResponse> responseList, HashMap<Integer, QuestionnaireQuestionDTO> questionnaireQuestionMap) {
        QuestionnaireResponseDTO questionnaireResponseDto; 
        for (QuestionnaireResponse obj : responseList) {
            questionnaireResponseDto = new QuestionnaireResponseDTO();
            //questionnaireResponseDto.setCreationDate(obj.getCreationDate());
            questionnaireResponseDto.setQuestionnaireResponseId(obj.getQuestionnaireResponseId());
            questionnaireResponseDto.setResponse(obj.getResponse());
            questionnaireResponseDto.setResponseTypeId(obj.getResponseTypeId());
            //questionnaireQuestionMap.get(obj.getQuestionnaireQuestionId().getQuestionnaireQuestionId()).setQuestionnaireResponse(questionnaireResponseDto);         
        }
    }
    
    /**
     * Builds the responseOption List to be added to the questionnaireResponse that belongs to the questionnaireQuestion
     * @param responseOptionList
     * @param questionnaireQuestionMap 
     */
    private void buildResponseOption(List<ResponseOption> responseOptionList, HashMap<Integer, QuestionnaireQuestionDTO> questionnaireQuestionMap) {
        if (responseOptionList != null && !responseOptionList.isEmpty()) {
            QuestionOptionDTO questionOption;
            ResponseOptionDTO responseOptionDto;
            QuestionnaireResponseDTO questionnaireResponseDto;
            QuestionnaireQuestionDTO questionnaireQuestionDto;
            for (ResponseOption objResponse : responseOptionList) {
                questionOption = new QuestionOptionDTO();
                questionOption.setName(objResponse.getQuestionOptionId().getName());
                questionOption.setQuestionOptionId(objResponse.getQuestionOptionId().getQuestionOptionId());
                questionOption.setStateId(objResponse.getQuestionOptionId().getStateId());
                responseOptionDto = new ResponseOptionDTO();
                responseOptionDto.setQuestionOptionId(questionOption);
                responseOptionDto.setResponseOptionId(objResponse.getResponseOptionId());
                questionnaireQuestionDto = questionnaireQuestionMap.get(objResponse.getQuestionnaireResponseId().getQuestionnaireQuestionId().getQuestionnaireQuestionId());
                //questionnaireResponseDto = questionnaireQuestionDto.getQuestionnaireResponse();
                //questionnaireResponseDto.getResponseOptionList().add(responseOptionDto);
                //questionnaireQuestionDto.setQuestionnaireResponse(questionnaireResponseDto);
            }
        }
    }

    @Override
    public List<QuestionnaireQuestionDTO> findByDisciplineId(Integer disciplineId, Integer userId) throws Exception {
        List<QuestionnaireQuestion> list = questionnaireQuestionDao.findByDisciplineId(disciplineId, userId);
        List<QuestionnaireQuestionDTO> listResult = new ArrayList<>();
   
          if (list != null && !list.isEmpty()) {
              list.stream().forEach((questionnaireQuestion) -> {
                  QuestionnaireQuestionDTO q = new QuestionnaireQuestionDTO();
                  QuestionDTO questionDto = QuestionDTO.mapFromQuestionEntity(questionnaireQuestion.getQuestionId());
                  QuestionnaireCategoryDTO categoryDTO = QuestionnaireCategoryDTO.mapFromQuestionnaireCategoryEntity(questionnaireQuestion.getQuestionnaireCategoryId());
                  List<QuestionnaireResponseDTO> questionnaireResponseList = questionnaireQuestion.getQuestionnaireResponseCollection().stream().map(QuestionnaireResponseDTO::mapFromQuestionnaireResponseEntity).collect(Collectors.toList());
                  List<QuestionOptionDTO> questionOptionList =  questionnaireQuestion.getQuestionId().getQuestionOptionCollection().stream().map(QuestionOptionDTO::mapFromQuestionOptionEntity).collect(Collectors.toList());
                  
                  q.setQuestionnaire(questionnaireQuestion.getQuestionnaireId().getName());
                  questionDto.setQuestionOptionList(questionOptionList);
                  q.setQuestionnaireQuestionId(questionnaireQuestion.getQuestionnaireQuestionId());
                  q.setQuestionId(questionDto);
                  q.setQuestionOrder(questionnaireQuestion.getQuestionOrder());
                  q.setQuestionnaireResponseList(questionnaireResponseList);
                  q.setQuestionnaireCategoryId(categoryDTO);
                  listResult.add(q);
            });
  
            
            
        }
        
        return listResult;
    }
}