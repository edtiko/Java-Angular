package co.com.expertla.training.dao.impl.questionnaire;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.questionnaire.QuestionnaireResponseHistoryDao;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireRespHistory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionnaireResponseHistoryDaoImpl extends BaseDAOImpl<QuestionnaireRespHistory> implements QuestionnaireResponseHistoryDao{


    @Override
    public QuestionnaireRespHistory create(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException {
        return super.create(questionnaireResponseHistory);
    }

    @Override
    public QuestionnaireRespHistory merge(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException {
        return super.merge(questionnaireResponseHistory);
    }

    @Override
    public void remove(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException {
        super.remove(questionnaireResponseHistory, questionnaireResponseHistory.getQuestionnaireRespHistoryId());
    }
	
    @Override
    public List<QuestionnaireRespHistory> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireRespHistory u");
	setPageNumber(sePaginator.getInitialRow());
	setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireRespHistory> findByQuestionnaireResponseHistoryId(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireRespHistory u ");
        builder.append("WHERE u.questionnaireResponseHistoryId = :questionnaireResponseHistoryId");
        setParameter("questionnaireResponseHistoryId", questionnaireResponseHistory.getQuestionnaireRespHistoryId());
        return createQuery(builder.toString());
    }

}