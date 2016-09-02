package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.ModalityDao;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.service.configuration.ModalityService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Modality <br>
 * Creation Date : <br>
 * date 18/07/2016 <br>
 *
 * @author Angela Ramírez
*
 */
@Service("modalityService")
@Transactional
public class ModalityServiceImpl implements ModalityService {

    @Autowired
    private ModalityDao modalityDao;

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
