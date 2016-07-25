package co.com.expertla.training.service.impl.questionnaire;

import co.com.expertla.training.dao.questionnaire.DataTypeDao;
import co.com.expertla.training.dao.questionnaire.QuestionnaireDao;
import co.com.expertla.training.model.dto.QuestionOptionDto;
import co.com.expertla.training.model.dto.QuestionnaireCategoryDto;
import co.com.expertla.training.model.dto.QuestionnaireDto;
import co.com.expertla.training.model.dto.QuestionnaireFormatDto;
import co.com.expertla.training.model.dto.QuestionnaireQuestionDto;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.DataType;
import co.com.expertla.training.model.entities.Question;
import co.com.expertla.training.model.entities.Questionnaire;
import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import co.com.expertla.training.service.questionnaire.QuestionnaireService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manager Questionnaire Implementation <br>
 * Creation Info: 
 * date 15/08/2015
 * @author Angela Ramirez
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService{
    
    @Autowired
    private QuestionnaireDao questionnaireDao;
    @Autowired
    private DataTypeDao dataTypeDao;
    
    @Override
    public Questionnaire create(Questionnaire questionnaire) throws Exception {
        return questionnaireDao.create(questionnaire);
    }

    @Override
    public Questionnaire merge(Questionnaire questionnaire) throws Exception {
        return questionnaireDao.merge(questionnaire);
    }

    @Override
    public void remove(Questionnaire questionnaire) throws Exception {
        questionnaireDao.remove(questionnaire);
    }

    @Override
    public List<Questionnaire> findAll(SePaginator sePaginator) throws Exception {
        return questionnaireDao.findAll(sePaginator);
    }

    @Override
    public List<Questionnaire> findByQuestionnaireId(Questionnaire questionnaire) throws Exception {
        return questionnaireDao.findByQuestionnaireId(questionnaire);
    }
    

    @Override
    public List<Questionnaire> findAmountQuestionAndResponseByQuestionnaire(SePaginator sePaginator) throws Exception {
        return questionnaireDao.findAmountQuestionAndResponseByQuestionnaire(sePaginator);
    }
    
    @Override
    public List<QuestionnaireDto> findQuestionnaireDtoByQuestionnaireId(Questionnaire questionnaire) throws Exception {
        List<QuestionnaireFormatDto> resultList = questionnaireDao.findQuestionnaireDtoByQuestionnaireId(questionnaire);
        List<QuestionnaireDto> questionnaireList = new ArrayList<>();
        QuestionnaireDto questionnaireDto = new QuestionnaireDto();
        QuestionnaireQuestionDto qQuestionDto;
        QuestionnaireCategoryDto questionnaireCategoryDto;
        Question question;
        QuestionnaireResponse questionnaireResponse;
        QuestionOptionDto questionOption;
        DataType dataType;
        Integer categoryAmount = 0;
        Integer questionAmount = 0;
        Integer responseAmount = 0;
        if (resultList != null && !resultList.isEmpty()) {
            HashMap<Integer, String> map = buildDataTypeHash();
            for (QuestionnaireFormatDto objDto : resultList) {
                //Create questionnaireDto Object
                questionnaireCategoryDto = new QuestionnaireCategoryDto();
                qQuestionDto = new QuestionnaireQuestionDto();
                question = new Question();
                questionnaireResponse = new QuestionnaireResponse();
                questionOption = new QuestionOptionDto();
                questionnaireDto.setCreationDate(objDto.getQuestionnaireDate());
                questionnaireDto.setName(objDto.getQuestionnaireName());
                questionnaireDto.setQuestionnaireId(objDto.getQuestionnaireId());

                if (!questionnaireDto.getHashCategory().containsKey(objDto.getQuestionnaireCategoryId())) {
                    categoryAmount = categoryAmount + 1;
                    questionnaireCategoryDto.setCreationDate(objDto.getQuestionnaireCategoryDate());
                    questionnaireCategoryDto.setDescription(objDto.getQuestionnaireCategoryDesc());
                    questionnaireCategoryDto.setName(objDto.getQuestionnaireCategoryName());
                    questionnaireCategoryDto.setQuestionnaireCategoryId(objDto.getQuestionnaireCategoryId());
                    questionnaireCategoryDto.setQuestionnaireParentId(new QuestionnaireCategory(objDto.getQuestionnaireParentId()));
                    questionnaireDto.getQuestionnaireCategoryDtoList().add(questionnaireCategoryDto);
                    questionnaireDto.getHashCategory().put(questionnaireCategoryDto.getQuestionnaireCategoryId(), questionnaireDto.getQuestionnaireCategoryDtoList().size() - 1);
                } else {
                    questionnaireCategoryDto = questionnaireDto.getQuestionnaireCategoryDtoList().get(questionnaireDto.getHashCategory().get(objDto.getQuestionnaireCategoryId()));
                }

                if (!questionnaireCategoryDto.getHashQuestion().containsKey(objDto.getQuestionnaireQuestionId())) {
                    questionAmount = questionAmount + 1;
                    //Build the question object
                    question.setCreationDate(objDto.getQuestionDate());
                    dataType = new DataType();
                    dataType.setName(map.get(objDto.getDataTypeId()));
                    dataType.setDataTypeId(objDto.getDataTypeId());
                    question.setDataTypeId(dataType);
                    question.setDescription(objDto.getQuestionDesc());
                    question.setIndAdditional(objDto.getIndAdditional());
                    question.setName(objDto.getQuestionName());
                    question.setQuestionId(objDto.getQuestionId());
                    question.setQuestionType(objDto.getQuestionType());
                    question.setUnit(objDto.getUnit());
                    //Builde the questionnaire question and add it to the list
                    qQuestionDto.setQuestionId(question);
                    qQuestionDto.setCreationDate(objDto.getQuestionnaireQuestionDate());
                    qQuestionDto.setQuestionOrder(objDto.getQuestionOrder());
                    qQuestionDto.setQuestionnaireQuestionId(objDto.getQuestionnaireQuestionId());
                    questionnaireCategoryDto.getQuestionnaireQuestionDtoList().add(qQuestionDto);
                    questionnaireCategoryDto.getHashQuestion().put(qQuestionDto.getQuestionnaireQuestionId(), questionnaireCategoryDto.getQuestionnaireQuestionDtoList().size() - 1);
                } else {
                    qQuestionDto = questionnaireCategoryDto.getQuestionnaireQuestionDtoList().get(questionnaireCategoryDto.getHashQuestion().get(objDto.getQuestionnaireQuestionId()));
                }

                if (objDto.getQuestionnaireResponseId() != null) {
                    responseAmount = responseAmount + 1;
                    //Build the response question
                    questionnaireResponse.setQuestionnaireResponseId(objDto.getQuestionnaireResponseId());
                    questionnaireResponse.setCreationDate(objDto.getQuestionnaireResponseDate());
                    questionnaireResponse.setResponse(objDto.getResponse());
                   // questionnaireResponse.setUserId(new User(objDto.getUserId()));
//                    qQuestionDto.setQuestionnaireResponse(questionnaireResponse);
                }

                if (objDto.getQuestionOptionId() != null) {
                    if (!qQuestionDto.getHashOption().containsKey(objDto.getQuestionOptionId())) {
                        questionOption.setName(objDto.getQuestionOptionName());
                        questionOption.setQuestionOptionId(objDto.getQuestionOptionId());
                       // questionOption.setSeStatusId(new SeStatus(objDto.getQuestionOptionStatus()));
                        qQuestionDto.getQuestionOptionList().add(questionOption);
                        qQuestionDto.getHashOption().put(questionOption.getQuestionOptionId(), qQuestionDto.getQuestionOptionList().size() - 1);
                    }

                }
                
                questionnaireDto.setCategoryAmount(categoryAmount);
                questionnaireDto.setQuestionAmount(questionAmount);
                questionnaireDto.setResponseAmount(responseAmount);
                questionnaireList.add(questionnaireDto);
            }
        }

        return questionnaireList;
    }
    
    private HashMap<Integer, String> buildDataTypeHash() throws Exception {
        HashMap<Integer, String> map = new HashMap<>();
        List<DataType> dataTypeList = dataTypeDao.findAll(new SePaginator());
        dataTypeList.stream().forEach((dt) -> {
            map.put(dt.getDataTypeId(), dt.getName());
        });
        return map;
    }
}