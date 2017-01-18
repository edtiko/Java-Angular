package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.ColourIndicatorDao;
import co.expertic.training.model.dto.ColourIndicatorDTO;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.service.configuration.ColourIndicatorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ColourIndicator Service Impl <br>
* Info. Creación: <br>
* fecha Sep 14, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("colourIndicatorService")
@Transactional
public class ColourIndicatorServiceImpl implements ColourIndicatorService {

    @Autowired
    private ColourIndicatorDao colourIndicatorDao;

    @Override
    public ColourIndicator create(ColourIndicator colourIndicator) throws Exception {
        return colourIndicatorDao.create(colourIndicator);
    }

    @Override
    public ColourIndicator store(ColourIndicator colourIndicator) throws Exception {
       return colourIndicatorDao.merge(colourIndicator);
    }

    @Override
    public void remove(ColourIndicator colourIndicator) throws Exception {
        colourIndicatorDao.remove(colourIndicator, colourIndicator.getColourIndicatorId());
    }

    @Override
    public List<ColourIndicator> findAll() throws Exception {
        return colourIndicatorDao.findAll();
    }

    @Override
    public List<ColourIndicator> findAllActive() throws Exception {
        return colourIndicatorDao.findAllActive();
    }

    @Override
    public List<ColourIndicatorDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return colourIndicatorDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<ColourIndicator> findByColourIndicator(ColourIndicator colourIndicator) throws Exception {
        return colourIndicatorDao.findByColourIndicator(colourIndicator);
    }

    @Override
    public List<ColourIndicator> findByFiltro(ColourIndicator colourIndicator) throws Exception {
        return colourIndicatorDao.findByFiltro(colourIndicator);
    }

    @Override
    public List<ColourIndicator> findByName(ColourIndicator colourIndicator) throws Exception {
        return colourIndicatorDao.findByName(colourIndicator);
    }
}