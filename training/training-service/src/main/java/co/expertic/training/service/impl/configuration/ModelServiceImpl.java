package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.ModelDao;
import co.expertic.training.model.dto.ModelDTO;
import co.expertic.training.model.entities.Model;
import co.expertic.training.service.configuration.ModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Model Service Impl <br>
* Info. Creación: <br>
* fecha Oct 19, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("modelService")
@Transactional
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelDao modelDao;

    @Override
    public Model create(Model model) throws Exception {
        return modelDao.create(model);
    }

    @Override
    public Model store(Model model) throws Exception {
       return modelDao.merge(model);
    }

    @Override
    public void remove(Model model) throws Exception {
        modelDao.remove(model, model.getModelId());
    }

    @Override
    public List<Model> findAll() throws Exception {
        return modelDao.findAll();
    }

    @Override
    public List<Model> findAllActive() throws Exception {
        return modelDao.findAllActive();
    }

    @Override
    public List<ModelDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return modelDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<Model> findByModel(Model model) throws Exception {
        return modelDao.findByModel(model);
    }

    @Override
    public List<Model> findByFiltro(Model model) throws Exception {
        return modelDao.findByFiltro(model);
    }

    @Override
    public List<Model> findByName(Model model) throws Exception {
        return modelDao.findByName(model);
    }
}