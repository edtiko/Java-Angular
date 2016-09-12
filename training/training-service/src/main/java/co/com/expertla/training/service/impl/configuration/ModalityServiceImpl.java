package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.ModalityDao;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.model.entities.Modality;
import co.com.expertla.training.service.configuration.ModalityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Modality Service Impl <br>
* Info. Creación: <br>
* fecha Sep 5, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("modalityService")
@Transactional
public class ModalityServiceImpl implements ModalityService {

    @Autowired
    private ModalityDao modalityDao;

    @Override
    public Modality create(Modality modality) throws Exception {
        return modalityDao.create(modality);
    }

    @Override
    public Modality store(Modality modality) throws Exception {
       return modalityDao.merge(modality);
    }

    @Override
    public void remove(Modality modality) throws Exception {
        modalityDao.remove(modality, modality.getModalityId());
    }

    @Override
    public List<Modality> findAllActive() throws Exception {
        return modalityDao.findAllActive();
    }

    @Override
    public List<ModalityDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return modalityDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<Modality> findByModality(Modality modality) throws Exception {
        return modalityDao.findByModality(modality);
    }

    @Override
    public List<Modality> findByFiltro(Modality modality) throws Exception {
        return modalityDao.findByFiltro(modality);
    }

    @Override
    public List<ModalityDTO> findAll() throws Exception {
        return modalityDao.findAll();
    }

    @Override
    public List<ModalityDTO> findByDisciplineId(Integer id) throws Exception {
        return modalityDao.findByDisciplineId(id);
    }

    @Override
    public List<ModalityDTO> findByObjectiveId(Integer objectiveId) throws Exception {
        return modalityDao.findByObjectiveId(objectiveId);
    }
}