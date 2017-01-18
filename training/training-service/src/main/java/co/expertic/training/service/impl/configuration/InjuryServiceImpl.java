/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.InjuryDao;
import co.expertic.training.model.entities.Injury;
import co.expertic.training.service.configuration.InjuryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Transactional
@Service
public class InjuryServiceImpl implements InjuryService{
    
    @Autowired
    InjuryDao injuryDao;

    @Override
    public Injury findById(Integer id) throws Exception {
        return injuryDao.findById(id);
    }

    @Override
    public List<Injury> findAll() throws Exception {
         return injuryDao.findAll();
    }
    
}
