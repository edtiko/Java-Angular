/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.user;

import co.expertic.training.dao.user.UserFittingDao;
import co.expertic.training.dao.user.UserFittingHistoryDao;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.service.user.MechanicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class MechanicServiceImpl implements MechanicService {
    
    @Autowired
    UserFittingDao userFittingDao;
    
    @Autowired
    UserFittingHistoryDao userFittingHistoryDao;
    

    @Override
    public List<UserResumeDTO> findAthletesByUserPaginate(Integer userId, PaginateDto paginateDto) throws Exception {
        return userFittingDao.findAthletesByUserPaginate(userId, paginateDto);
    }

    @Override
    public List<UserResumeDTO> getAthletes() throws Exception {
        return userFittingDao.getAthletes();
    }
    
}
