package co.com.expertla.training.dao.impl.questionnaire;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.questionnaire.QuestionnaireQuestionDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.QuestionnaireQuestionFormatDTO;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Questionnaire;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionnaireQuestionDaoImpl extends BaseDAOImpl<QuestionnaireQuestion> implements QuestionnaireQuestionDao{


    @Override
    public QuestionnaireQuestion create(QuestionnaireQuestion questionnaireQuestion) throws DAOException {
        return super.create(questionnaireQuestion);
    }

    @Override
    public QuestionnaireQuestion merge(QuestionnaireQuestion questionnaireQuestion) throws DAOException {
        return super.merge(questionnaireQuestion);
    }

    @Override
    public void remove(QuestionnaireQuestion questionnaireQuestion) throws DAOException {
        super.remove(questionnaireQuestion, questionnaireQuestion.getQuestionnaireQuestionId());
    }
	
    @Override
    public List<QuestionnaireQuestion> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireQuestion u ");
        builder.append("WHERE u.stateId = ").append(Status.ACTIVE.getId());
        setPageNumber(sePaginator.getInitialRow());
        setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireQuestion> findByDisciplineId(Integer disciplineId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireQuestion u ");
       // builder.append(" left join u.questionnaireResponseCollection r ");
       // builder.append(" on r.userId.userId = ").append(userId);
        builder.append(" WHERE u.questionnaireId.disciplineId.disciplineId = ").append(disciplineId);
        builder.append(" AND u.stateId = ").append(Status.ACTIVE.getId());
       // setPageNumber(sePaginator.getInitialRow());
       // setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireQuestion> findByQuestionnaireQuestionId(Integer questionnaireQuestionId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireQuestion u ");
        builder.append("WHERE u.questionnaireQuestionId = :questionnaireQuestionId");
        setParameter("questionnaireQuestionId", questionnaireQuestionId);
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireQuestion> findByQuestionnaireIdAndCategoryId(QuestionnaireQuestion questionnaireQuestion) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select NEW co.com.expertla.neroo.model.QuestionnaireQuestion(  ");
        builder.append("u.questionnaireQuestionId, u.questionOrder, u.creationDate, ");
        builder.append("u.seStatusId, u.questionnaireCategoryId, u.questionId, u.questionnaireId,  ");
        builder.append("r ");
        builder.append(") FROM QuestionnaireQuestion u, QuestionnaireResponse r ");
        builder.append("WHERE u.questionnaireQuestionId = r.questionnaireQuestionId.questionnaireQuestionId ");
        builder.append("AND u.questionnaireId.questionnaireId = :questionnaireId ");
        builder.append("AND u.questionnaireCategoryId.questionnaireCategoryId = :questionnaireCategoryId ");        
        setParameter("questionnaireId", questionnaireQuestion.getQuestionnaireId().getQuestionnaireId());
        setParameter("questionnaireCategoryId", questionnaireQuestion.getQuestionnaireCategoryId().getQuestionnaireCategoryId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireQuestion> findByQuestionnaireId(Questionnaire questionnaire) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("Select u FROM JpaQuestionnaireQuestion u ");
        builder.append("WHERE u.questionnaireId.questionnaireId = :questionnaireId");
        setParameter("questionnaireId", questionnaire.getQuestionnaireId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireQuestionFormatDTO> findByCategoryIdAndQuestionnaireId(QuestionnaireResponse questionnaireResponse) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select @rownum/*'*/:=/*'*/@rownum+1 AS questionnaireQuestionFormatDtoId,q.question_id as questionId, q.data_type_id as dataTypeId, ");
        builder.append("q.se_status_id as questionStatus, q.name as questionName, q.description as questionDesc, q.unit, q.ind_additional as indAdditional,q.question_type as questionType,q.creation_date as questionDate, ");
        builder.append("qq.questionnaire_question_id as questionnaireQuestionId, qq.se_status_id as questionnaireQuestionStatus,qq.question_order as questionOrder, qq.creation_date as questionnaireQuestionDate, ");
        builder.append("o.question_option_id as questionOptionId, ");
        builder.append("o.se_status_id as questionOptionStatus, o.name as questionOptionName, o.label as questionOptionLabel ");
        builder.append("from (SELECT @rownum/*'*/:=/*'*/0) z, question q ");
        builder.append("join questionnaire_question qq ");
        builder.append("on q.question_id = qq.question_id ");
        builder.append("left join question_option o ");
        builder.append("on o.question_id = q.question_id ");
        
        if(questionnaireResponse.getQuestionnaireQuestionId() == null ||
           questionnaireResponse.getQuestionnaireQuestionId().getQuestionnaireCategoryId() == null ||
           questionnaireResponse.getQuestionnaireQuestionId().getQuestionnaireCategoryId().getQuestionnaireCategoryId() == null) {
            builder.append("where qq.questionnaire_category_id is ");
        } else {
            builder.append("where qq.questionnaire_category_id = ");
        }
        builder.append(questionnaireResponse.getQuestionnaireQuestionId().getQuestionnaireCategoryId().getQuestionnaireCategoryId());
        builder.append(" and qq.questionnaire_id = ");
        builder.append(questionnaireResponse.getQuestionnaireQuestionId().getQuestionnaireId().getQuestionnaireId());
        final Query query = getEntityManager().createNativeQuery(builder.toString(),QuestionnaireQuestionFormatDTO.class);

        List<QuestionnaireQuestionFormatDTO> resultList = query.getResultList();
        
        return resultList;
    }
}