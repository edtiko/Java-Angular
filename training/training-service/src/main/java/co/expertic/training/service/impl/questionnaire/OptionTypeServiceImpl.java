package co.expertic.training.service.impl.questionnaire;

import co.expertic.training.dao.questionnaire.OptionTypeDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.OptionType;
import co.expertic.training.service.questionnaire.OptionTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionTypeServiceImpl implements OptionTypeService {

    @Autowired
    private OptionTypeDao optionTypeDao;

    @Override
    public OptionType create(OptionType optionType) throws Exception {
        return optionTypeDao.create(optionType);
    }

    @Override
    public OptionType merge(OptionType optionType) throws Exception {
        return optionTypeDao.merge(optionType);
    }

    @Override
    public void remove(OptionType optionType) throws Exception {
        optionTypeDao.remove(optionType);
    }

    @Override
    public List<OptionType> findAll(SePaginator sePaginator) throws Exception {
        return optionTypeDao.findAll(sePaginator);
    }

    @Override
    public List<OptionType> findByOptionTypeId(OptionType optionType) throws Exception {
        return optionTypeDao.findByOptionTypeId(optionType);
    }
}
