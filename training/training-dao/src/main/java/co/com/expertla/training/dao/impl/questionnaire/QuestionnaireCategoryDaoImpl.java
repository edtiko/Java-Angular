package co.com.expertla.training.dao.impl.questionnaire;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.questionnaire.QuestionnaireCategoryDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionnaireCategoryDaoImpl extends BaseDAOImpl<QuestionnaireCategory> implements QuestionnaireCategoryDao{


    @Override
    public QuestionnaireCategory create(QuestionnaireCategory questionnaireCategory) throws DAOException {
        return super.create(questionnaireCategory);
    }

    @Override
    public QuestionnaireCategory merge(QuestionnaireCategory questionnaireCategory) throws DAOException {
        return super.merge(questionnaireCategory);
    }

    @Override
    public void remove(QuestionnaireCategory questionnaireCategory) throws DAOException {
        super.remove(questionnaireCategory, questionnaireCategory.getQuestionnaireCategoryId());
    }
	
    @Override
    public List<QuestionnaireCategory> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireCategory u");
        setPageNumber(sePaginator.getInitialRow());
        setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireCategory> findByQuestionnaireCategoryId(QuestionnaireCategory questionnaireCategory) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireCategory u ");
        builder.append("WHERE u.questionnaireCategoryId = :questionnaireCategoryId");
        setParameter("questionnaireCategoryId", questionnaireCategory.getQuestionnaireCategoryId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireCategory> findByQuestionnaireId(QuestionnaireQuestion questionnaireQuestion) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select NEW co.com.expertla.training.model.QuestionnaireCategory(c.questionnaireCategoryId, c.name, ");
        builder.append("c.description, c.creationDate, s.seStatusId, p, COUNT(u.questionId) as questionAmount ) ");
        builder.append("FROM  JpaQuestionnaireQuestion u inner join  u.questionnaireCategoryId c ");
        builder.append("inner join c.seStatusId s ");
        builder.append("left join c.questionnaireParentId p ");
        builder.append("WHERE p is null ");
        builder.append("AND c.seStatusId = ").append(Status.ACTIVE.getName());
        builder.append(" AND u.questionnaireId.questionnaireId = :questionnaireId ");
        
        if(questionnaireQuestion.getQuestionnaireCategoryId() != null &&
                questionnaireQuestion.getQuestionnaireCategoryId().getQuestionnaireCategoryId() != null) {
            builder.append("AND c.questionnaireCategoryId = :questionnaireCategoryId ");
        }
        
        builder.append("GROUP BY c.questionnaireCategoryId, c.name,");
        builder.append("c.description, c.creationDate, s.seStatusId, ");
        builder.append("c.questionnaireParentId");
        setParameter("questionnaireId", questionnaireQuestion.getQuestionnaireId().getQuestionnaireId());
        
        if(questionnaireQuestion.getQuestionnaireCategoryId() != null &&
                questionnaireQuestion.getQuestionnaireCategoryId().getQuestionnaireCategoryId() != null) {
            setParameter("questionnaireCategoryId", questionnaireQuestion.getQuestionnaireCategoryId().getQuestionnaireCategoryId());
        }
        
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireCategory> findByQuestionnaireParentCategoryId(QuestionnaireQuestion questionnaireQuestion) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select NEW co.com.expertla.training.model.QuestionnaireCategory(u.questionnaireCategoryId.questionnaireCategoryId, u.questionnaireCategoryId.name,");
        builder.append("u.questionnaireCategoryId.description, u.questionnaireCategoryId.creationDate, u.questionnaireCategoryId.seStatusId, ");
        builder.append("u.questionnaireCategoryId.questionnaireParentId, COUNT(u.questionId) as questionAmount ) FROM JpaQuestionnaireQuestion u ");
        builder.append("WHERE u.questionnaireCategoryId.questionnaireParentId.questionnaireCategoryId = :questionnaireCategoryId ");
        builder.append("AND u.seStatusId.seStatusId = ").append(Status.ACTIVE.getName());
        builder.append(" GROUP BY u.questionnaireCategoryId.questionnaireCategoryId, u.questionnaireCategoryId.name,");
        builder.append("u.questionnaireCategoryId.description, u.questionnaireCategoryId.creationDate, u.questionnaireCategoryId.seStatusId, ");
        builder.append("u.questionnaireCategoryId.questionnaireParentId");
        setParameter("questionnaireCategoryId", questionnaireQuestion.getQuestionnaireCategoryId().getQuestionnaireCategoryId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireCategory> findCategoryChildrenByQuestionnaireCategoryId(List<Integer> categoryIds) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u from QuestionnaireCategory u ");
        builder.append("WHERE u.questionnaireParentId.questionnaireCategoryId in :ids ");
        builder.append("AND u.seStatusId.seStatusId = ").append(Status.ACTIVE.getName());
        setParameter("ids", categoryIds);
        return createQuery(builder.toString());
    }
    
    
}