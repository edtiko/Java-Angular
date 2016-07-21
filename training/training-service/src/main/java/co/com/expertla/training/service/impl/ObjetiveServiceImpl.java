package co.com.expertla.training.service.impl;

import co.com.expertla.training.dao.ObjetiveDao;
import co.com.expertla.training.model.dto.ObjetiveDTO;
import co.com.expertla.training.service.ObjetiveService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Service for Discipline <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Service("objetiveService")
@Transactional
public class ObjetiveServiceImpl implements ObjetiveService {
           
    @Autowired
    private ObjetiveDao objetiveDao;

    @Override
    public List<ObjetiveDTO> findAll() throws Exception {
        return objetiveDao.findAll();
    }
 
}