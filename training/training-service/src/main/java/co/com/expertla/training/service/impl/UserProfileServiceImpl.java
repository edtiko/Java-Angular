package co.com.expertla.training.service.impl;

import co.com.expertla.training.user.dao.DisciplineUserDao;
import co.com.expertla.training.user.dao.EquipmentUserProfileDao;
import co.com.expertla.training.user.dao.UserDao;
import co.com.expertla.training.user.dao.UserProfileDao;
import co.com.expertla.training.user.dao.UserSportDao;
import co.com.expertla.training.enums.SportEquipmentTypeEnum;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.dto.UserAvailabilityDTO;
import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.Discipline;
import co.com.expertla.training.model.entities.DisciplineUser;
import co.com.expertla.training.model.entities.EquipmentUserProfile;
import co.com.expertla.training.model.entities.Modality;
import co.com.expertla.training.model.entities.Objective;
import co.com.expertla.training.model.entities.Sport;
import co.com.expertla.training.model.entities.SportEquipment;
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
           
    @Autowired
    private DisciplineUserDao disciplineUserDao;
           
    @Autowired
    private UserDao userDao;
    
    @Override
    public UserProfileDTO findDTOByUserId(Integer id) throws  Exception {
        UserProfileDTO userProfile = userProfileDao.findDTOByUserId(id);
        //Bikes
        List<SportEquipmentDTO> listBikes = equipmentUserProfileDao.findDTOBikesByUserId(id);
        if(listBikes != null && !listBikes.isEmpty()) {
            userProfile.setBikes(listBikes.get(0).getSportEquipmentId());
        } else {
            userProfile.setBikes(-1);
        }
        //Pulsometer
        List<SportEquipmentDTO> listPulsometer = equipmentUserProfileDao.findDTOPulsometersByUserId(id);
        if(listPulsometer != null && !listPulsometer.isEmpty()) {
            userProfile.setPulsometer(listPulsometer.get(0).getSportEquipmentId());
        } else {
            userProfile.setPulsometer(-1);
        }
        //Potentiometer
        List<SportEquipmentDTO> listPotentiometer = equipmentUserProfileDao.findDTOPotentiometersByUserId(id);
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
        EquipmentUserProfile pulsometer = null;
        EquipmentUserProfile potentiometer = null;
        EquipmentUserProfile runningShoes = null;
        EquipmentUserProfile bike = null;
        
        UserAvailability availability = userAvailabilityService.findByUserId(dto.getUserId());
        UserSport sport = userSportDao.findByUserId(dto.getUserId());
        List<EquipmentUserProfile> equipments = equipmentUserProfileDao.findByUserId(dto.getUserId());
        DisciplineUser discipline = disciplineUserDao.findByUserId(dto.getUserId());
        User user = userDao.findById(dto.getUserId());
        
        user.setIndMetricSys(dto.getIndMetricSys());
        if(equipments != null && !equipments.isEmpty()) {
            for (EquipmentUserProfile equipment : equipments) {
                if(equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.PULSOMETER.getId())) {
                    pulsometer = equipment;
                } else if(equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.POTENTIOMETER.getId())) {
                    potentiometer = equipment;
                } else if(equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.RUNNING_SHOES.getId())) {
                    runningShoes = equipment;
                } else if(equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.BIKES.getId())) {
                    bike = equipment;
                }
            }
        }
        convertUserProfileDTOToUserProfile(dto, userProfile);
        potentiometer = buildPotentiometerObject(dto, potentiometer);
        pulsometer = buildPulsometerObject(dto, pulsometer);
        runningShoes = buildRunningShoesObject(dto, runningShoes);
        bike = buildBikeObject(dto, bike);
        availability = buildUserAvailabilityObject(dto, availability);
        sport = buildUserSport(dto, sport);
        discipline = buildDisciplineUser(dto, discipline);
        
        if(userProfile.getUserProfileId() != null ){
            userProfileDao.merge(userProfile);
        } else {
            userProfileDao.create(userProfile);
        }
        //Pulsometer
        if(pulsometer != null && pulsometer.getEquipmentUserProfileId() != null) {
            equipmentUserProfileDao.merge(pulsometer);   
        } else {
            equipmentUserProfileDao.create(pulsometer);
        }
        //Potentiometer
        if(potentiometer != null && potentiometer.getEquipmentUserProfileId() != null) {
            equipmentUserProfileDao.merge(potentiometer);   
        } else {
            equipmentUserProfileDao.create(potentiometer);
        }
        //RunningShoes
        if(runningShoes != null && runningShoes.getEquipmentUserProfileId() != null) {
            equipmentUserProfileDao.merge(runningShoes);   
        } else {
            equipmentUserProfileDao.create(runningShoes);
        }
        //Bike 
        if(bike != null && bike.getEquipmentUserProfileId() !=null) {
            equipmentUserProfileDao.merge(bike);
        } else {
            equipmentUserProfileDao.create(bike);
        }
        //User Availability 
        if(availability != null && availability.getUserAvailabilityId() != null) {
            userAvailabilityService.merge(availability);
        } else {
            userAvailabilityService.create(availability);
        }
        if(sport != null && sport.getUserSportId() != null) {
            userSportDao.merge(sport);
        } else {
            userSportDao.create(sport);
        }
        
        userDao.merge(user);
        disciplineUserDao.merge(discipline);
        return findDTOByUserId(userProfile.getUserId().getUserId());
    }
    
    @Override
    public UserProfile findByUserId(Integer id) throws Exception {
        return userProfileDao.findByUserId(id);
    }
    
    private void convertUserProfileDTOToUserProfile(UserProfileDTO dto, UserProfile userProfile) {
        userProfile.setAboutMe(dto.getAboutMe());
        userProfile.setAgeSport(dto.getAgeSport());
        userProfile.setIndPower(dto.getIndPower());
        userProfile.setIndPulsometer(dto.getIndPulsometer());
        userProfile.setObjectiveId(new Objective(dto.getObjetive()));
        userProfile.setPower(dto.getPower());
        userProfile.setPpm(dto.getPpm());
        userProfile.setSportsAchievements(dto.getSportsAchievements());
        userProfile.setUserId(new User(dto.getUserId()));
        userProfile.setUserProfileId(dto.getUserProfileId());
        userProfile.setModalityId(new Modality(dto.getModality()));
    }
    
    /**
     * Builds the corresponding equipment object for the pulsometer
     * @param dto
     * @param pulsometer
     * @return 
     */
    private EquipmentUserProfile buildPulsometerObject(UserProfileDTO dto, EquipmentUserProfile pulsometer) {
        if (!dto.getPulsometer().equals(-1) && pulsometer != null) {
            pulsometer.setSportEquipmentId(new SportEquipment(dto.getPulsometer()));
        } else if (!dto.getPulsometer().equals(-1)) {
            pulsometer = new EquipmentUserProfile();
            pulsometer.setSportEquipmentId(new SportEquipment(dto.getPotentiometer()));
            pulsometer.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        }
        return pulsometer;
    }
    
    /**
     * Builds the corresponding equipment object for the potentiometer
     * @param dto
     * @param potentiometer
     * @return 
     */
    private EquipmentUserProfile buildPotentiometerObject(UserProfileDTO dto, EquipmentUserProfile potentiometer) {
        if (!dto.getPotentiometer().equals(-1) && potentiometer != null) {
            potentiometer.setSportEquipmentId(new SportEquipment(dto.getPotentiometer()));
        } else if(!dto.getPotentiometer().equals(-1)){
            potentiometer = new EquipmentUserProfile();
            potentiometer.setSportEquipmentId(new SportEquipment(dto.getPotentiometer()));
            potentiometer.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        }
        return potentiometer;
    }
    
    /**
     * Builds the corresponding equipment object for running shoes
     * @param dto
     * @param runningShoes
     * @return 
     */
    private EquipmentUserProfile buildRunningShoesObject(UserProfileDTO dto, EquipmentUserProfile runningShoes) {
        if (!dto.getShoes().equals(-1) && runningShoes != null) {
            runningShoes.setSportEquipmentId(new SportEquipment(dto.getShoes()));
        } else if(!dto.getPotentiometer().equals(-1)){
            runningShoes = new EquipmentUserProfile();
            runningShoes.setSportEquipmentId(new SportEquipment(dto.getShoes()));
            runningShoes.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        }
        return runningShoes;
    }
    
    /**
     * Builds the corresponding equipment object for bike
     * @param dto
     * @param bike
     * @return 
     */
    private EquipmentUserProfile buildBikeObject(UserProfileDTO dto, EquipmentUserProfile bike) {
        if (!dto.getBikes().equals(-1) && bike != null) {
            bike.setSportEquipmentId(new SportEquipment(dto.getBikes()));
        } else if(!dto.getPotentiometer().equals(-1)){
            bike = new EquipmentUserProfile();
            bike.setSportEquipmentId(new SportEquipment(dto.getBikes()));
            bike.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        }
        return bike;
    }
    
    /**
     * Build the user availability entity
     * @param dto
     * @param availability
     * @return 
     */
    private UserAvailability buildUserAvailabilityObject(UserProfileDTO dto, UserAvailability availability) {
        if(availability == null ) {
            availability = new UserAvailability();
            availability.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        }
        
        List<UserAvailabilityDTO> list = dto.getAvailability();
        for (UserAvailabilityDTO userAvailabilityDTO : list) {
            switch (userAvailabilityDTO.getDay()) {
                case "Lunes":
                    availability.setMonday(userAvailabilityDTO.getChecked());
                    break;
                case "Martes":
                    availability.setTuesday(userAvailabilityDTO.getChecked());
                    break;
                case "Miercoles":
                    availability.setWednesday(userAvailabilityDTO.getChecked());
                    break;
                case "Jueves":
                    availability.setThursday(userAvailabilityDTO.getChecked());
                    break;
                case "Viernes":
                    availability.setFriday(userAvailabilityDTO.getChecked());
                    break;
                case "Sabado":
                    availability.setSaturday(userAvailabilityDTO.getChecked());
                    break;
                case "Domingo":
                    availability.setSunday(userAvailabilityDTO.getChecked());
                    break;
                default:
                    break;
            }
        }
     
        availability.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        return availability;
    }
    
    /**
     * Build the user sport entity
     * @param dto
     * @param availability
     * @return 
     */
    private UserSport buildUserSport (UserProfileDTO dto, UserSport sport) {
        if(!dto.getSport().equals(-1) && sport != null) {
            sport.setSportId(new Sport(dto.getSport())); 
        } else if (!dto.getSport().equals(-1)) {
            sport = new UserSport();
            sport.setUserProfileId(new UserProfile(dto.getUserProfileId()));
            sport.setSportId(new Sport(dto.getSport()));
        }
        return sport;
    }
    
    /**
     * Build the discipline user entity
     * @param dto
     * @param availability
     * @return 
     */
    private DisciplineUser buildDisciplineUser (UserProfileDTO dto, DisciplineUser discipline) {
        discipline.setDiscipline(new Discipline(dto.getDiscipline()));
        return discipline;
    }
}