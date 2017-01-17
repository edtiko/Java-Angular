package co.expertic.training.dao.impl.questionnaire;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.questionnaire.ResponseTypeDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.ResponseType;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Dao Response Type Implementation <br>
 * Creation Info: 
 * date 13/10/2015
 * @author Angela Ramirez
 */
@Repository
public class ResponseTypeDaoImpl extends BaseDAOImpl<ResponseType> implements ResponseTypeDao {


    @Override
    public ResponseType create(ResponseType responseType) throws DAOException {
        return super.create(responseType);
    }

    @Override
    public ResponseType merge(ResponseType responseType) throws DAOException {
        return super.merge(responseType);
    }

    @Override
    public void remove(ResponseType responseType) throws DAOException {
        super.remove(responseType, responseType.getResponseTypeId());
    }
	
    @Override
    public List<ResponseType> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM ResponseType u");
	setPageNumber(sePaginator.getInitialRow());
	setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<ResponseType> findByResponseTypeId(ResponseType responseType) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM ResponseType u ");
        builder.append("WHERE u.responseTypeId = :responseTypeId");
        setParameter("responseTypeId", responseType.getResponseTypeId());
        return createQuery(builder.toString());
    }
       
}