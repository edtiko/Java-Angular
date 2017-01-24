package co.expertic.training.dao.impl.questionnaire;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.questionnaire.ResponseOptionDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireResponse;
import co.expertic.training.model.entities.ResponseOption;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class ResponseOptionDaoImpl extends BaseDAOImpl<ResponseOption> implements ResponseOptionDao {

    private final int batchSize = 10;

    
    @Override
    public ResponseOption create(ResponseOption responseOption) throws DAOException {
        return super.create(responseOption);
    }
    
    @Override
    @Transactional
    public List<ResponseOption> create(List<ResponseOption> responseOption) throws DAOException {
      
        List<ResponseOption> listCreated = bulkSave(responseOption);
        return listCreated;
    }

    @Override
    public ResponseOption merge(ResponseOption responseOption) throws DAOException {
        return super.merge(responseOption);
    }

    @Override
    public void remove(ResponseOption responseOption) throws DAOException {
        super.remove(responseOption, responseOption.getResponseOptionId());
    }
	
    @Override
    public List<ResponseOption> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM ResponseOption u");
	setPageNumber(sePaginator.getInitialRow());
	setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<ResponseOption> findByResponseOptionId(ResponseOption responseOption) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM ResponseOption u ");
        builder.append("WHERE u.responseOptionId = :responseOptionId");
        setParameter("responseOptionId", responseOption.getResponseOptionId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<ResponseOption> findByQuestionnaireResponseIds(List<Integer> questionnaireResponseIds) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM ResponseOption u ");
        builder.append("WHERE u.questionnaireResponseId.questionnaireResponseId in :questionnaireResponseId");
        setParameter("questionnaireResponseId", questionnaireResponseIds);
        return createQuery(builder.toString());
    }
    
    @Override
    public List<ResponseOption> findByQuestionnaireResponseId(QuestionnaireResponse questionnaireResponseId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM ResponseOption u ");
        builder.append("WHERE u.questionnaireResponseId.questionnaireResponseId = :questionnaireResponseId");
        setParameter("questionnaireResponseId", questionnaireResponseId.getQuestionnaireResponseId());
        return createQuery(builder.toString());
    }
    


    private <T extends ResponseOption> List<T> bulkSave(List<T> entities) {
        final List<T> savedEntities = new ArrayList<>(entities.size());
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

    private <T extends ResponseOption> T persistOrMerge(T t) {
        if (t.getQuestionnaireResponseId() == null) {
            getEntityManager().persist(t);
            return t;
        } else {
            return getEntityManager().merge(t);
        }
    }
}