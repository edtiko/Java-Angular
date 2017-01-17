package co.expertic.training.dao.impl.questionnaire;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.questionnaire.DataTypeDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.DataType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DataTypeDaoImpl extends BaseDAOImpl<DataType> implements DataTypeDao{

    @Override
    public DataType create(DataType dataType) throws DAOException {
        return super.create(dataType);
    }

    @Override
    public DataType merge(DataType dataType) throws DAOException {
        return super.merge(dataType);
    }

    @Override
    public void remove(DataType dataType) throws DAOException {
        super.remove(dataType, dataType.getDataTypeId());
    }
	
    @Override
    public List<DataType> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM DataType u");
	setPageNumber(sePaginator.getInitialRow());
	setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<DataType> findByDataTypeId(DataType dataType) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM DataType u ");
        builder.append("WHERE u.dataTypeId = :dataTypeId");
        setParameter("dataTypeId", dataType.getDataTypeId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<DataType> findByName(String name) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM DataType u ");
        builder.append("WHERE u.name = :name");
        setParameter("name", name);
        return createQuery(builder.toString());
    }
}