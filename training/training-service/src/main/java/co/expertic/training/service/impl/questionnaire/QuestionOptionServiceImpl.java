package co.expertic.training.service.impl.questionnaire;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.constant.MessageBundle;
import co.expertic.training.dao.questionnaire.QuestionOptionDao;
import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionOption;
import co.expertic.training.model.util.UtilValidation;
import co.expertic.training.service.questionnaire.QuestionOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionOptionServiceImpl implements QuestionOptionService{
    
    @Autowired
    private QuestionOptionDao questionOptionDao;
    private final UtilValidation util = new UtilValidation();
    private List<String> errorList;
    
    @Override
    public QuestionOption create(QuestionOption questionOption) throws Exception {
        errorList = validateMandatoryFields(questionOption);
        if(errorList == null || errorList.isEmpty()){
            List<QuestionOption> listQuestionOption = findByName(questionOption.getName());
            
            if(listQuestionOption != null && !listQuestionOption.isEmpty()) {
                throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES,"questionOptionExists"));
            }
           return questionOptionDao.create(questionOption);
        } else {
            throw new TrainingException(errorList);
        }
    }

    @Override
    public QuestionOption merge(QuestionOption questionOption) throws Exception {
        errorList = validateMandatoryFields(questionOption);
        if (errorList == null || errorList.isEmpty()) {

            List<QuestionOption> questionOptionList = findByQuestionOptionId(questionOption.getQuestionOptionId());
            if (questionOptionList != null && !questionOptionList.isEmpty()) {
                QuestionOption objQuestionOption = questionOptionList.get(0);
                if (!objQuestionOption.getName().equals(questionOption.getName())) {
                    List<QuestionOption> listQuestionOption = findByName(questionOption.getName());

                    if (listQuestionOption != null && !listQuestionOption.isEmpty()) {
                        throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionExists"));
                    }
                }

                
                return questionOptionDao.merge(questionOption);

            } else {
                throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.QUESTION_OPTION_PROPERTIES, "questionOptionDoesNotExist"));
            }

        } else {
            throw new TrainingException(errorList);
        }
    }

    @Override
    public void remove(QuestionOption questionOption) throws Exception {
        questionOptionDao.remove(questionOption);
    }

    @Override
    public List<QuestionOption> findAll(SePaginator sePaginator) throws Exception {
        return questionOptionDao.findAll(sePaginator);
    }

    @Override
    public List<QuestionOption> findByQuestionOptionId(Integer questionOption) throws Exception {
        return questionOptionDao.findByQuestionOptionId(questionOption);
    }
    
    /**
     * Creation Info: 
     * date 15/08/2015
     * @author Angela Ramirez
     *
     * Specify and validate mandatory fields
     * @param questionOption
     * @return a list of errors
     */
    private List<String> validateMandatoryFields(QuestionOption questionOption) {
        errorList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("name", questionOption.getName());
        return errorList = util.validateFields(map);
    }
    
    @Override
    public List<QuestionOption> findByName(String name) throws Exception{
        return questionOptionDao.findByName(name);
    }
}