package co.expertic.training.dao.impl.questionnaire;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.questionnaire.QuestionnaireResponseDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QuestionnaireResponseDaoImpl extends BaseDAOImpl<QuestionnaireResponse> implements QuestionnaireResponseDao {

    private final int batchSize = 10;

    @Override
    public QuestionnaireResponse create(QuestionnaireResponse questionnaireResponse) throws DAOException {
        return super.create(questionnaireResponse);
    }

    @Override
    public List<QuestionnaireResponse> create(List<QuestionnaireResponse> questionnaireResponse) throws DAOException {

        List<QuestionnaireResponse> listCreated = bulkSave(questionnaireResponse);
        return listCreated;
    }

    @Override
    public QuestionnaireResponse merge(QuestionnaireResponse questionnaireResponse) throws DAOException {
        return super.merge(questionnaireResponse);
    }

    @Override
    public void remove(QuestionnaireResponse questionnaireResponse) throws DAOException {
        super.remove(questionnaireResponse, questionnaireResponse.getQuestionnaireResponseId());
    }

    @Override
    public List<QuestionnaireResponse> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireResponse u");
        setPageNumber(sePaginator.getInitialRow());
        setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireResponse> findByQuestionnaireResponseId(Integer questionnaireResponseId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireResponse u ");
        builder.append("WHERE u.questionnaireResponseId = :questionnaireResponseId");
        setParameter("questionnaireResponseId", questionnaireResponseId);
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireResponse> findByQuestionnaireCategoryId(Integer questionnaireCategoryId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireResponse u ");
        builder.append("WHERE u.questionnaireQuestionId.questionnaireCategoryId.questionnaireCategoryId = :questionnaireCategoryId");
        setParameter("questionnaireCategoryId", questionnaireCategoryId);
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireResponse> findByCategoryIdAndQuestionId(Integer questionnaireCategoryId, Integer questionnaireQuestionId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireResponse u ");
        builder.append("WHERE u.questionnaireQuestionId.questionnaireCategoryId.questionnaireCategoryId = :questionnaireCategoryId");
        builder.append("AND u.questionnaireQuestionId.questionnaireQuestionId = :questionnaireQuestionId");
        setParameter("questionnaireCategoryId", questionnaireCategoryId);
        setParameter("questionnaireQuestion", questionnaireQuestionId);
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireResponse> findByUserIdAndCategoryId(QuestionnaireResponse questionnaireResponse) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireResponse u ");
        builder.append("WHERE u.questionnaireQuestionId.questionnaireCategoryId.questionnaireCategoryId = :questionnaireCategoryId ");
        builder.append("AND u.userId.userId = :userId");
        setParameter("userId", questionnaireResponse.getUserId().getUserId());
        setParameter("questionnaireCategoryId", questionnaireResponse.getQuestionnaireQuestionId().getQuestionnaireCategoryId().getQuestionnaireCategoryId());
        return createQuery(builder.toString());
    }

    @Override
    public List<QuestionnaireResponse> findByUserIdAndQuestionnaireQuestionId(Integer questionnaireQuestionId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM QuestionnaireResponse u ");
        builder.append("WHERE u.questionnaireQuestionId.questionnaireQuestionId = :questionnaireQuestionId ");
        builder.append("AND u.userId.userId = :userId");
        setParameter("userId", userId);
        setParameter("questionnaireQuestionId", questionnaireQuestionId);
        return createQuery(builder.toString());
    }

    @Override
    @Transactional
    public void createQuestionnaireResponseHistory(String questionnaireQuestionId, Integer seUserId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into questionnaire_resp_history ");
        builder.append("select * from questionnaire_response where user_id = ");
        builder.append(seUserId);
        builder.append(" and questionnaire_question_id in (").append(questionnaireQuestionId).append(")");
        executeNativeUpdate(builder.toString());
    }

    @Override
    @Transactional
    public void createResponseOptionHistory(String questionnaireQuestionIds, Integer seUserId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into response_option_history ");
        builder.append("select * from response_option ");
        builder.append("where questionnaire_response_id in ( ");
        builder.append("select questionnaire_response_id from questionnaire_response where user_id = ");
        builder.append(seUserId);
        builder.append(" and questionnaire_question_id in (").append(questionnaireQuestionIds).append("))");
        executeNativeUpdate(builder.toString());
    }

    @Override
    public void deleteQuestionnaireResponse(String questionnaireQuestionIds, Integer seUserId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("delete from questionnaire_response ");
        builder.append("where questionnaire_question_id in (").append(questionnaireQuestionIds).append(") ");
        builder.append("and user_id = ").append(seUserId);
        executeNativeUpdate(builder.toString());
    }

    @Override
    public void deleteResponseOption(String questionnaireQuestionIds, Integer seUserId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("delete from response_option ");
        builder.append("where questionnaire_response_id in ( ");
        builder.append("select questionnaire_response_id from questionnaire_response where user_id = ");
        builder.append(seUserId);
        builder.append(" and questionnaire_question_id in (").append(questionnaireQuestionIds).append("))");
        executeNativeUpdate(builder.toString());
    }
    


    private <T extends QuestionnaireResponse> List<T> bulkSave(List<T> entities) {
        final List<T> savedEntities = new ArrayList<T>(entities.size());
        int i = 0;
        for (T t : entities) {
            savedEntities.add(persistOrMerge(t));
            i++;
            if (i % batchSize == 0) {
                // Flush a batch of inserts and release memory.
                getEntityManager().flush();
                getEntityManager().clear();
            }
        }
        return savedEntities;
    }

    private <T extends QuestionnaireResponse> T persistOrMerge(T t) {
        if (t.getQuestionnaireResponseId() == null) {
            getEntityManager().persist(t);
            return t;
        } else {
            return getEntityManager().merge(t);
        }
    }

}
