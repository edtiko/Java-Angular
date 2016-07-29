package co.com.expertla.training.service.impl;

import co.com.expertla.training.configuration.dao.DisciplineDao;
import co.com.expertla.training.model.dto.DisciplineDTO;
import co.com.expertla.training.service.DisciplineService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* Service for Discipline <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Service("disciplineService")
@Transactional
public class DisciplineServiceImpl implements DisciplineService {
           
    @Autowired
    private DisciplineDao disciplineDao;

    @Override
    public List<DisciplineDTO> findAll() throws Exception {
        return disciplineDao.findAll();
    }

    @Override
    public DisciplineDTO findByUserId(Integer userId) throws Exception {
     
        return disciplineDao.findByUserId(userId).get(0);
    }
 
}