package co.com.expertla.training.user.service.impl;

import co.com.expertla.training.model.entities.DisciplineUser;
import co.com.expertla.training.user.service.DisciplineUserService;
import co.com.expertla.training.user.dao.DisciplineUserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* Service for Discipline <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Service("disciplineUserService")
@Transactional
public class DisciplineUserServiceImpl implements DisciplineUserService {
           
    @Autowired
    private DisciplineUserDao disciplineUserDao;

    @Override
    public DisciplineUser create(DisciplineUser disciplineUser) throws Exception {
        return disciplineUserDao.create(disciplineUser);
    }
 
}