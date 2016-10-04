package co.com.expertla.training.service.impl.questionnaire;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.constant.MessageBundle;
import co.com.expertla.training.dao.questionnaire.QuestionDao;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Question;
import co.com.expertla.training.model.util.UtilValidation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.expertla.training.service.questionnaire.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
    
    @Autowired
    private QuestionDao questionDao;
    private List<String> errorList;
    private final UtilValidation util = new UtilValidation();
    
    @Override
    public Question create(Question question) throws Exception {
        errorList = validateMandatoryFields(question);
        if(errorList == null || errorList.isEmpty()){
            List<Question> listQuestion = findByName(question.getName());
            
            if(listQuestion != null && !listQuestion.isEmpty()) {
                throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES,"questionExists"));
            }
            question.setCreationDate(new Date());
           return questionDao.create(question);
        } else {
            throw new TrainingException(errorList);
        }
    }

    @Override
    public Question merge(Question question) throws Exception {
        errorList = validateMandatoryFields(question);
        if (errorList == null || errorList.isEmpty()) {

            List<Question> questionList = findByQuestionId(question);
            if (questionList != null && !questionList.isEmpty()) {
                Question objQuestion = questionList.get(0);
                if (!objQuestion.getName().equals(question.getName())) {
                    List<Question> listQuestion = findByName(question.getName());

                    if (listQuestion != null && !listQuestion.isEmpty()) {
                        throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionExists"));
                    }
                }
                
                if(question.getStateId() == null) {
                    question.setStateId(objQuestion.getStateId());
                }
                
                question.setCreationDate(objQuestion.getCreationDate());
                return questionDao.merge(question);

            } else {
                throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_PROPERTIES, "questionDoesNotExist"));
            }

        } else {
            throw new TrainingException(errorList);
        }
    }

    @Override
    public void remove(Question question) throws Exception {
        questionDao.remove(question);
    }

    @Override
    public List<Question> findAll(SePaginator sePaginator) throws Exception {
        return questionDao.findAll(sePaginator);
    }

    @Override
    public List<Question> findByQuestionId(Question question) throws Exception {
        return questionDao.findByQuestionId(question);
    }
    
    /**
     * Creation Info: 
     * date 15/08/2015
     * @author Angela Ramirez
     *
     * Specify and validate mandatory fields
     * @param question
     * @return a list of errors
     */
    private List<String> validateMandatoryFields(Question question) {
        errorList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("name", question.getName());
        map.put("unit",question.getUnit());
        map.put("dataTypeId",question.getDataTypeId());
        return errorList = util.validateFields(map);
    }
    
    @Override
    public List<Question> findByName(String name) throws Exception{
        return questionDao.findByName(name);
    }
    
}