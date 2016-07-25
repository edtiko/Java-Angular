package co.com.expertla.training.dao.impl.questionnaire;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.questionnaire.QuestionDao;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Question;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDaoImpl extends BaseDAOImpl<Question> implements QuestionDao{


    @Override
    public Question create(Question question) throws DAOException {
        return super.create(question);
    }

    @Override
    public Question merge(Question question) throws DAOException {
        return super.merge(question);
    }

    @Override
    public void remove(Question question) throws DAOException {
        super.remove(question, question.getQuestionId());
    }
	
    @Override
    public List<Question> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM Question u");
	setPageNumber(sePaginator.getInitialRow());
	setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<Question> findByQuestionId(Question question) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM Question u ");
        builder.append("WHERE u.questionId = :questionId");
        setParameter("questionId", question.getQuestionId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<Question> findByName(String name) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM Question u ");
        builder.append("WHERE u.name = :name");
        setParameter("name", name);
        return createQuery(builder.toString());        
    }

}