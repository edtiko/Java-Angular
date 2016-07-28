package co.com.expertla.training.dao.impl.questionnaire;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.questionnaire.QuestionOptionDao;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionOption;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionOptionDaoImpl extends BaseDAOImpl<QuestionOption> implements QuestionOptionDao{


    @Override
    public QuestionOption create(QuestionOption questionOption) throws DAOException {
        return super.create(questionOption);
    }

    @Override
    public QuestionOption merge(QuestionOption questionOption) throws DAOException {
        return super.merge(questionOption);
    }

    @Override
    public void remove(QuestionOption questionOption) throws DAOException {
        super.remove(questionOption, questionOption.getQuestionOptionId());
    }
	
    @Override
    public List<QuestionOption> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionOption u");
	setPageNumber(sePaginator.getInitialRow());
	setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionOption> findByQuestionOptionId(QuestionOption questionOption) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionOption u ");
        builder.append("WHERE u.questionOptionId = :questionOptionId");
        setParameter("questionOptionId", questionOption.getQuestionOptionId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionOption> findByQuestionId(Integer questionId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionOption u ");
        builder.append("WHERE u.questionId = :questionId");
        setParameter("questionId", questionId);
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionOption> findByName(String name) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionOption u ");
        builder.append("WHERE u.name = :name");
        setParameter("name", name);
        return createQuery(builder.toString());
    }

}