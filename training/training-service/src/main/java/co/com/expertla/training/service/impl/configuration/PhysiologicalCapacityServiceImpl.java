package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.PhysiologicalCapacityDao;
import co.com.expertla.training.model.dto.PhysiologicalCapacityDTO;
import co.com.expertla.training.model.entities.PhysiologicalCapacity;
import co.com.expertla.training.service.configuration.PhysiologicalCapacityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PhysiologicalCapacity Service Impl <br>
* Info. Creación: <br>
* fecha Sep 2, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("physiologicalCapacityService")
@Transactional
public class PhysiologicalCapacityServiceImpl implements PhysiologicalCapacityService {

    @Autowired
    private PhysiologicalCapacityDao physiologicalCapacityDao;

    @Override
    public PhysiologicalCapacity create(PhysiologicalCapacity physiologicalCapacity) throws Exception {
        return physiologicalCapacityDao.create(physiologicalCapacity);
    }

    @Override
    public PhysiologicalCapacity store(PhysiologicalCapacity physiologicalCapacity) throws Exception {
       return physiologicalCapacityDao.merge(physiologicalCapacity);
    }

    @Override
    public void remove(PhysiologicalCapacity physiologicalCapacity) throws Exception {
        physiologicalCapacityDao.remove(physiologicalCapacity, physiologicalCapacity.getPhysiologicalCapacityId());
    }

    @Override
    public List<PhysiologicalCapacity> findAll() throws Exception {
        return physiologicalCapacityDao.findAll();
    }

    @Override
    public List<PhysiologicalCapacity> findAllActive() throws Exception {
        return physiologicalCapacityDao.findAllActive();
    }

    @Override
    public List<PhysiologicalCapacityDTO> findPaginate(int first, int max, String order) throws Exception {
        return physiologicalCapacityDao.findPaginate(first, max, order);
    }

    @Override
    public List<PhysiologicalCapacity> findByPhysiologicalCapacity(PhysiologicalCapacity physiologicalCapacity) throws Exception {
        return physiologicalCapacityDao.findByPhysiologicalCapacity(physiologicalCapacity);
    }

    @Override
    public List<PhysiologicalCapacity> findByFiltro(PhysiologicalCapacity physiologicalCapacity) throws Exception {
        return physiologicalCapacityDao.findByFiltro(physiologicalCapacity);
    }

}