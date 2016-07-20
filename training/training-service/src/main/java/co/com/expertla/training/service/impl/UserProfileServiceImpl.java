package co.com.expertla.training.service.impl;

import co.com.expertla.training.dao.EquipmentUserProfileDao;
import co.com.expertla.training.dao.SportDao;
import co.com.expertla.training.dao.UserProfileDao;
import co.com.expertla.training.dao.UserSportDao;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.dto.UserAvailabilityDTO;
import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.Modality;
import co.com.expertla.training.model.entities.Objetive;
import co.com.expertla.training.model.entities.Sport;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.model.entities.UserSport;
import co.com.expertla.training.service.UserAvailabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.service.UserProfileService;
import java.util.List;

/**
* Service for User Profile <br>
* Creation Date : <br>
* date 14/07/2016 <br>
* @author Angela Ramírez
**/
@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
           
    @Autowired
    private UserProfileDao userProfileDao;
           
    @Autowired
    private EquipmentUserProfileDao equipmentUserProfileDao;
           
    @Autowired
    private UserAvailabilityService userAvailabilityService;
           
    @Autowired
    private UserSportDao userSportDao;
    
    @Override
    public UserProfileDTO findByUserId(Integer id) throws  Exception {
        UserProfileDTO userProfile = userProfileDao.findByUserId(id);
        //Bikes
        List<SportEquipmentDTO> listBikes = equipmentUserProfileDao.findBikesByUserId(id);
//        List<Integer> bikeIds = new ArrayList<>();
//        if(listBikes != null && !listBikes.isEmpty()) {
//            listBikes.stream().forEach((listBike) -> {
//                bikeIds.add(listBike.getSportEquipmentId());
//            });
//            userProfile.setBikes(bikeIds);
//        }
        userProfile.setBikes(listBikes);
        //Pulsometer
        List<SportEquipmentDTO> listPulsometer = equipmentUserProfileDao.findPulsometersByUserId(id);
        if(listPulsometer != null && !listPulsometer.isEmpty()) {
            userProfile.setPulsometer(listPulsometer.get(0).getSportEquipmentId());
        } else {
            userProfile.setPulsometer(-1);
        }
        //Potentiometer
        List<SportEquipmentDTO> listPotentiometer = equipmentUserProfileDao.findPotentiometersByUserId(id);
        if(listPotentiometer != null && !listPotentiometer.isEmpty()) {
            userProfile.setPotentiometer(listPotentiometer.get(0).getSportEquipmentId());
        } else {
            userProfile.setPotentiometer(-1);
        }
        
        List<UserAvailabilityDTO> availability = userAvailabilityService.findDtoByUserId(id);
        userProfile.setAvailability(availability);
        return userProfile;
        
    }
    
    @Override
    public UserProfileDTO merge(UserProfileDTO dto) throws Exception {
        UserProfile userProfile = new UserProfile(); 
        UserAvailability availability = userAvailabilityService.findByUserId(dto.getUserId());
        UserSport sport = userSportDao.findByUserId(dto.getUserId());
        convertUserProfileDTOToEntities(dto, userProfile, availability,sport);
        userProfile=userProfileDao.merge(userProfile);
        userSportDao.merge(sport);
        userAvailabilityService.merge(availability);
        dto = findByUserId(userProfile.getUserId().getUserId());
        return dto;
    }
    
    private void convertUserProfileDTOToEntities(UserProfileDTO dto, UserProfile userProfile, UserAvailability availability,UserSport sport) {
        userProfile.setAboutMe(dto.getAboutMe());
        userProfile.setAgeSport(dto.getAgeSport());
        userProfile.setIndPower(dto.getIndPower());
        userProfile.setIndPulsometer(dto.getIndPulsometer());
        userProfile.setObjetiveId(new Objetive(dto.getObjetive()));
        userProfile.setPower(dto.getPower());
        userProfile.setPpm(dto.getPpm());
        userProfile.setSportsAchievements(dto.getSportsAchievements());
        userProfile.setUserId(new User(dto.getUserId()));
        userProfile.setUserProfileId(dto.getUserProfileId());
        userProfile.setModalityId(new Modality(dto.getModality()));
        List<UserAvailabilityDTO> list = dto.getAvailability();
        for (UserAvailabilityDTO userAvailabilityDTO : list) {
            if(userAvailabilityDTO.getDay().equals("Lunes")) {
                availability.setMonday(userAvailabilityDTO.getChecked());
            } else if(userAvailabilityDTO.getDay().equals("Martes")) {
                availability.setTuesday(userAvailabilityDTO.getChecked());
            } else if(userAvailabilityDTO.getDay().equals("Miercoles")) {
                availability.setWednesday(userAvailabilityDTO.getChecked());
            } else if(userAvailabilityDTO.getDay().equals("Jueves")) {
                availability.setThursday(userAvailabilityDTO.getChecked());
            } else if(userAvailabilityDTO.getDay().equals("Viernes")) {
                availability.setFriday(userAvailabilityDTO.getChecked());
            } else if(userAvailabilityDTO.getDay().equals("Sabado")) {
                availability.setSaturday(userAvailabilityDTO.getChecked());
            } else if(userAvailabilityDTO.getDay().equals("Domingo")) {
                availability.setSunday(userAvailabilityDTO.getChecked());
            }
        }
        availability.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        sport.setSportId(new Sport(dto.getSport()));
    }
 
}