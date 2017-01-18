package co.expertic.training.service.impl.user;

import co.expertic.training.model.entities.DisciplineUser;
import co.expertic.training.service.user.DisciplineUserService;
import co.expertic.training.dao.user.DisciplineUserDao;

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
    
    @Override
    public DisciplineUser store(DisciplineUser disciplineUser) throws Exception {
        return disciplineUserDao.merge(disciplineUser);
    }

    @Override
    public DisciplineUser findByUserId(Integer id) throws Exception {
        return disciplineUserDao.findByUserId(id);
    }
 
}