package co.expertic.training.dao.impl.questionnaire;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.questionnaire.QuestionnaireDao;
import co.expertic.training.model.dto.QuestionnaireFormatDTO;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.Questionnaire;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionnaireDaoImpl extends BaseDAOImpl<Questionnaire> implements QuestionnaireDao{



    @Override
    public Questionnaire create(Questionnaire questionnaire) throws DAOException {
        return super.create(questionnaire);
    }

    @Override
    public Questionnaire merge(Questionnaire questionnaire) throws DAOException {
        return super.merge(questionnaire);
    }

    @Override
    public void remove(Questionnaire questionnaire) throws DAOException {
        super.remove(questionnaire, questionnaire.getQuestionnaireId());
    }
	
    @Override
    public List<Questionnaire> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM JpaQuestionnaire u");
        setPageNumber(sePaginator.getInitialRow());
        setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<Questionnaire> findByQuestionnaireId(Questionnaire questionnaire) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM JpaQuestionnaire u ");
        builder.append("WHERE u.questionnaireId = :questionnaireId");
        setParameter("questionnaireId", questionnaire.getQuestionnaireId());
        return createQuery(builder.toString());
    }
    


    @Override
    public List<Questionnaire> findAmountQuestionAndResponseByQuestionnaire(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select NEW co.expertic.neroo.model.Questionnaire(q.questionnaireId, q.name, q.seStatusId, q.providerId, q.creationDate, ");
        builder.append("(select COUNT(qq) FROM JpaQuestionnaireQuestion qq ");
        builder.append("WHERE qq.questionnaireId.questionnaireId = q.questionnaireId ) as questionAmount ,  ");
        builder.append("(select COUNT(r) FROM JpaQuestionnaireResponse r ");
        builder.append("WHERE r.questionnaireQuestionId.questionnaireId.questionnaireId = q.questionnaireId ) as responseAmount  , ");
        builder.append("(select COUNT(DISTINCT c.questionnaireCategoryId) FROM JpaQuestionnaireQuestion qq, JpaQuestionnaireCategory c ");
        builder.append("WHERE qq.questionnaireId.questionnaireId = q.questionnaireId ");
        builder.append("AND c.questionnaireCategoryId = qq.questionnaireCategoryId.questionnaireCategoryId ) as categoryAmount ) ");
        builder.append("FROM JpaQuestionnaire q ");
        
        setPageNumber(sePaginator.getInitialRow());
        setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<QuestionnaireFormatDTO> findQuestionnaireDtoByQuestionnaireId(Questionnaire questionnaire) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select @rownum/*'*/:=/*'*/@rownum+1 AS questionnaireFormatDtoId, f.questionnaire_id as questionnaireId,f.provider_id as providerId,f.se_status_id as questionnaireStatus, ");
        builder.append("f.name as questionnaireName,f.creation_date as questionnaireDate, ");
        builder.append("qq.questionnaire_question_id as questionnaireQuestionId, qq.question_id as qqQuestionId, qq.questionnaire_category_id as qqQuestionnaireCategoryId, ");
        builder.append("qq.se_status_id as questionnaireQuestionStatus,qq.question_order as questionOrder,qq.creation_date as questionnaireQuestionDate, ");
        builder.append("c.questionnaire_category_id as questionnaireCategoryId, c.questionnaire_parent_id as questionnaireParentId, c.se_status_id as questionnaireCategoryStatus, ");
        builder.append("c.name as questionnaireCategoryName, c.description as questionnaireCategoryDesc, c.creation_date as questionnaireCategoryDate, ");
        builder.append("q.question_id as questionId,q.data_type_id as dataTypeId,q.se_status_id as questionStatus, q.name as questionName, q.description as questionDesc, q.unit, ");
        builder.append("q.ind_additional as indAdditional,q.question_type as questionType,q.creation_date as questionDate, ");
        builder.append("r.questionnaire_response_id as questionnaireResponseId, r.questionnaire_question_id as rQuestionnaireQuestionId, r.question_option_id rQuestionOptionId, r.se_user_id as seUserId, r.response,r.creation_date as questionnaireResponseDate, ");
        builder.append("o.question_option_id as questionOptionId, o.question_id as oQuestionId, o.se_status_id questionOptionStatus, o.name as questionOptionName ");
        builder.append("from (SELECT @rownum/*'*/:=/*'*/0) z, questionnaire f ");
        builder.append("join questionnaire_question qq ");
        builder.append("on qq.questionnaire_id = f.questionnaire_id ");
        builder.append("join questionnaire_category c ");
        builder.append("on qq.questionnaire_category_id = c.questionnaire_category_id ");
        builder.append("join question q ");
        builder.append("on qq.question_id = q.question_id ");
        builder.append("left join questionnaire_response r ");
        builder.append("on r.questionnaire_question_id = qq.questionnaire_question_id ");
        builder.append("left join question_option o ");
        builder.append("on o.question_id = q.question_id ");
        builder.append("WHERE f.questionnaire_id=");
        builder.append(questionnaire.getQuestionnaireId());
        
        final Query query = getEntityManager().createNativeQuery(builder.toString(),QuestionnaireFormatDTO.class);

        List<QuestionnaireFormatDTO> resultList = query.getResultList();
        
        return resultList;
    }
}