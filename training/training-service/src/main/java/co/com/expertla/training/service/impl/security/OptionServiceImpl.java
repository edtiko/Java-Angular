package co.com.expertla.training.service.impl.security;

import co.com.expertla.training.dao.security.OptionDao;
import co.com.expertla.training.model.dto.OptionDTO;
import co.com.expertla.training.model.entities.Option;
import co.com.expertla.training.service.security.OptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Option Service Impl <br>
* Info. Creación: <br>
* fecha 26/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("optionService")
@Transactional
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDao optionDao;

    @Override
    public Option create(Option option) throws Exception {
        return optionDao.create(option);
    }

    @Override
    public Option store(Option option) throws Exception {
       return optionDao.merge(option);
    }

    @Override
    public void remove(Option option) throws Exception {
        optionDao.remove(option, option.getOptionId());
    }

    @Override
    public List<Option> findAll() throws Exception {
        return optionDao.findAll();
    }

    @Override
    public List<Option> findAllActive() throws Exception {
        return optionDao.findAllActive();
    }

    @Override
    public List<OptionDTO> findPaginate(int first, int max, String order) throws Exception {
        return optionDao.findPaginate(first, max, order);
    }

    @Override
    public List<Option> findByOption(Option option) throws Exception {
        return optionDao.findByOption(option);
    }

    @Override
    public List<Option> findByFiltro(Option option) throws Exception {
        return optionDao.findByFiltro(option);
    }

}