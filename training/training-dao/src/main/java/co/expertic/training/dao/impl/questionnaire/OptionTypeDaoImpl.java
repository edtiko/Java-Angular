package co.expertic.training.dao.impl.questionnaire;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.questionnaire.OptionTypeDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.OptionType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OptionTypeDaoImpl extends BaseDAOImpl<OptionType> implements OptionTypeDao {


    @Override
    public OptionType create(OptionType optionType) throws DAOException {
        return super.create(optionType);
    }

    @Override
    public OptionType merge(OptionType optionType) throws DAOException {
        return super.merge(optionType);
    }

    @Override
    public void remove(OptionType optionType) throws DAOException {
        super.remove(optionType, optionType.getOptionTypeId());
    }
	
    @Override
    public List<OptionType> findAll(SePaginator sePaginator) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM OptionType u");
	setPageNumber(sePaginator.getInitialRow());
	setPageSize(sePaginator.getMaxRow());
        return createQuery(builder.toString());
    }

    @Override
    public List<OptionType> findByOptionTypeId(OptionType optionType) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select u FROM OptionType u ");
        builder.append("WHERE u.optionTypeId = :optionTypeId");
        setParameter("optionTypeId", optionType.getOptionTypeId());
        return createQuery(builder.toString());
    }
}