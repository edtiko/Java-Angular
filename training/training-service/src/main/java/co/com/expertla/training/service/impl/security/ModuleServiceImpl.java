package co.com.expertla.training.service.impl.security;

import co.com.expertla.training.dao.security.ModuleDao;
import co.com.expertla.training.model.dto.ModuleDTO;
import co.com.expertla.training.model.entities.Module;
import co.com.expertla.training.service.security.ModuleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Module Service Impl <br>
* Info. Creación: <br>
* fecha 26/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("moduleService")
@Transactional
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public Module create(Module module) throws Exception {
        return moduleDao.create(module);
    }

    @Override
    public Module store(Module module) throws Exception {
       return moduleDao.merge(module);
    }

    @Override
    public void remove(Module module) throws Exception {
        moduleDao.remove(module, module.getModuleId());
    }

    @Override
    public List<Module> findAll() throws Exception {
        return moduleDao.findAll();
    }

    @Override
    public List<Module> findAllActive() throws Exception {
        return moduleDao.findAllActive();
    }

    @Override
    public List<ModuleDTO> findPaginate(int first, int max, String order) throws Exception {
        return moduleDao.findPaginate(first, max, order);
    }

    @Override
    public List<Module> findByModule(Module module) throws Exception {
        return moduleDao.findByModule(module);
    }

    @Override
    public List<Module> findByFiltro(Module module) throws Exception {
        return moduleDao.findByFiltro(module);
    }

}