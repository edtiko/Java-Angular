package co.com.expertla.training.service.impl.questionnaire;

import co.com.expertla.training.dao.questionnaire.QuestionnaireResponseHistoryDao;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireRespHistory;
import co.com.expertla.training.service.questionnaire.QuestionnaireResponseHistoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manager Questionnaire Response History Implementation <br>
 * Creation Info: 
 * date 15/08/2015
 * @author Angela Ramirez
 */
@Service
public class QuestionnaireResponseHistoryServiceImpl implements QuestionnaireResponseHistoryService{
    
    @Autowired
    private QuestionnaireResponseHistoryDao questionnaireResponseHistoryDao;
    
    @Override
    public QuestionnaireRespHistory create(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception {
        return questionnaireResponseHistoryDao.create(questionnaireResponseHistory);
    }

    @Override
    public QuestionnaireRespHistory merge(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception {
        return questionnaireResponseHistoryDao.merge(questionnaireResponseHistory);
    }

    @Override
    public void remove(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception {
        questionnaireResponseHistoryDao.remove(questionnaireResponseHistory);
    }

    @Override
    public List<QuestionnaireRespHistory> findAll(SePaginator sePaginator) throws Exception {
        return questionnaireResponseHistoryDao.findAll(sePaginator);
    }

    @Override
    public List<QuestionnaireRespHistory> findByQuestionnaireResponseHistoryId(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception {
        return questionnaireResponseHistoryDao.findByQuestionnaireResponseHistoryId(questionnaireResponseHistory);
    }
    
}