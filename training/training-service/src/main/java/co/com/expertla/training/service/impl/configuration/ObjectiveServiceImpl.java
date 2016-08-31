package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.model.dto.ObjectiveDTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.com.expertla.training.service.configuration.ObjectiveService;
import co.com.expertla.training.dao.configuration.ObjectiveDao;

/**
* Service for Discipline <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Service("objectiveService")
@Transactional
public class ObjectiveServiceImpl implements ObjectiveService {
           
    @Autowired
    private ObjectiveDao objectiveDao;

    @Override
    public List<ObjectiveDTO> findAll() throws Exception {
        return objectiveDao.findAll();
    }
    
    @Override
    public List<ObjectiveDTO> findByDiscipline(Integer disciplineId) throws Exception {
        return objectiveDao.findByDiscipline(disciplineId);
    }
 
}