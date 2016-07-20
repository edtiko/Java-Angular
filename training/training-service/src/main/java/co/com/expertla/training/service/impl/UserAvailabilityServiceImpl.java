package co.com.expertla.training.service.impl;

import co.com.expertla.training.dao.UserAvailabilityDao;
import co.com.expertla.training.model.dto.UserAvailabilityDTO;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.service.UserAvailabilityService;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Service for UserAvailability <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Service("userAvailabilityService")
@Transactional
public class UserAvailabilityServiceImpl implements UserAvailabilityService {
           
    @Autowired
    private UserAvailabilityDao userAvailabilityDao;

    @Override
    public List<UserAvailabilityDTO> findDtoByUserId(Integer id) throws Exception {
        List<UserAvailability> list = userAvailabilityDao.findByUserId(id);
        List<UserAvailabilityDTO> dtoList = new ArrayList<>();
        UserAvailabilityDTO dto = new UserAvailabilityDTO();
        if(list != null && !list.isEmpty()) {
            UserAvailability userAvailability = list.get(0);
            dto.setDay("Lunes");
            dto.setChecked(userAvailability.getMonday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Martes");
            dto.setChecked(userAvailability.getTuesday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Miércoles");
            dto.setChecked(userAvailability.getWednesday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Jueves");
            dto.setChecked(userAvailability.getThursday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Viernes");
            dto.setChecked(userAvailability.getFriday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Sábado");
            dto.setChecked(userAvailability.getSaturday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Domingo");
            dto.setChecked(userAvailability.getSunday());
            dtoList.add(dto);
            
        }
        return dtoList;
    }
    
    @Override
    public UserAvailability findByUserId(Integer id) throws Exception {
        List<UserAvailability> list =  userAvailabilityDao.findByUserId(id);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
 
    @Override
    public UserAvailability merge(UserAvailability userAvailability) throws Exception {
        return userAvailabilityDao.merge(userAvailability);
    }
}