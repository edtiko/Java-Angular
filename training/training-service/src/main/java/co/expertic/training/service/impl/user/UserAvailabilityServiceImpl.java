package co.expertic.training.service.impl.user;

import co.expertic.training.dao.user.UserAvailabilityDao;
import co.expertic.training.model.dto.UserAvailabilityDTO;
import co.expertic.training.model.entities.UserAvailability;
import co.expertic.training.service.user.UserAvailabilityService;
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
            dto.setDayNumber(0);
            dto.setChecked(userAvailability.getMonday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Martes");
            dto.setDayNumber(1);
            dto.setChecked(userAvailability.getTuesday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Miercoles");
            dto.setDayNumber(2);
            dto.setChecked(userAvailability.getWednesday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Jueves");
            dto.setDayNumber(3);
            dto.setChecked(userAvailability.getThursday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Viernes");
            dto.setDayNumber(4);
            dto.setChecked(userAvailability.getFriday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Sabado");
            dto.setDayNumber(5);
            dto.setChecked(userAvailability.getSaturday());
            dtoList.add(dto);
            dto = new UserAvailabilityDTO();
            dto.setDay("Domingo");
            dto.setDayNumber(6);
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
    public UserAvailability create(UserAvailability userAvailability) throws Exception {
        return userAvailabilityDao.create(userAvailability);
    }
 
    @Override
    public UserAvailability merge(UserAvailability userAvailability) throws Exception {
        return userAvailabilityDao.merge(userAvailability);
    }
}