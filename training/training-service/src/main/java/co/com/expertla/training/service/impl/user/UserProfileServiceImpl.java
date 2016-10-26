package co.com.expertla.training.service.impl.user;

import co.com.expertla.training.dao.configuration.BrandDao;
import co.com.expertla.training.dao.configuration.ModelEquipmentDao;
import co.com.expertla.training.dao.configuration.SportEquipmentDao;
import co.com.expertla.training.dao.user.DisciplineUserDao;
import co.com.expertla.training.dao.user.EquipmentUserProfileDao;
import co.com.expertla.training.dao.user.UserDao;
import co.com.expertla.training.dao.user.UserProfileDao;
import co.com.expertla.training.dao.user.UserSportDao;
import co.com.expertla.training.dao.user.UserZoneDao;
import co.com.expertla.training.enums.SportEquipmentTypeEnum;
import co.com.expertla.training.model.dto.DashboardDTO;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.dto.UserAvailabilityDTO;
import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.BikeType;
import co.com.expertla.training.model.entities.Brand;
import co.com.expertla.training.model.entities.Discipline;
import co.com.expertla.training.model.entities.DisciplineUser;
import co.com.expertla.training.model.entities.Environment;
import co.com.expertla.training.model.entities.EquipmentUserProfile;
import co.com.expertla.training.model.entities.Injury;
import co.com.expertla.training.model.entities.Modality;
import co.com.expertla.training.model.entities.ModelEquipment;
import co.com.expertla.training.model.entities.Objective;
import co.com.expertla.training.model.entities.Sport;
import co.com.expertla.training.model.entities.SportEquipment;
import co.com.expertla.training.model.entities.SportEquipmentType;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.model.entities.UserSport;
import co.com.expertla.training.model.entities.UserZone;
import co.com.expertla.training.model.entities.Weather;
import co.com.expertla.training.service.user.UserAvailabilityService;
import co.com.expertla.training.service.user.UserProfileService;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for User Profile <br>
 * Creation Date : <br>
 * date 14/07/2016 <br>
 *
 * @author Angela Ramírez
 *
 */
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

    @Autowired
    private SportEquipmentDao sportEquipmentDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private ModelEquipmentDao modelEquipmentDao;

    @Autowired
    private UserZoneDao userZoneDao;

    @Override
    public UserProfileDTO findDTOByUserId(Integer id) throws Exception {
        UserProfileDTO userProfile = userProfileDao.findDTOByUserId(id);
        if (userProfile == null) {
            List<UserAvailabilityDTO> dtoList = new ArrayList<>();
            userProfile = new UserProfileDTO();
            User user = userDao.findById(id);
            userProfile.setUserId(id);
            DisciplineUser discipline = disciplineUserDao.findByUserId(id);
            if (discipline != null) {
                userProfile.setDiscipline(discipline.getDiscipline().getDisciplineId());
            }
            userProfile.setIndMetricSys(user.getIndMetricSys());
            initialAvailability(dtoList);
            userProfile.setAvailability(dtoList);
            return userProfile;
        }
        //RunnignShoes
        List<SportEquipmentDTO> listShoes = equipmentUserProfileDao.findDTORunningShoesByUserId(id);
        if (listShoes != null && !listShoes.isEmpty()) {
            userProfile.setShoes(listShoes.get(0).getSportEquipmentId());
        } else {
            userProfile.setShoes(-1);
        }
        //Bikes
        List<SportEquipmentDTO> listBikes = equipmentUserProfileDao.findDTOBikesByUserId(id);
        if (listBikes != null && !listBikes.isEmpty()) {
            userProfile.setBikes(listBikes.get(0).getSportEquipmentId());
            userProfile.setModelBike(listBikes.get(0).getModelEquipmentId());
            userProfile.setBikeType(listBikes.get(0).getBikeType());
        } else {
            userProfile.setBikes(-1);
        }
        //Pulsometer
        List<SportEquipmentDTO> listPulsometer = equipmentUserProfileDao.findDTOPulsometersByUserId(id);
        if (listPulsometer != null && !listPulsometer.isEmpty()) {
            userProfile.setPulsometer(listPulsometer.get(0).getSportEquipmentId());
            userProfile.setModelPulsometer(listPulsometer.get(0).getModelEquipmentId());
        } else {
            userProfile.setPulsometer(-1);
        }
        //Potentiometer
        List<SportEquipmentDTO> listPotentiometer = equipmentUserProfileDao.findDTOPotentiometersByUserId(id);
        if (listPotentiometer != null && !listPotentiometer.isEmpty()) {
            userProfile.setPotentiometer(listPotentiometer.get(0).getSportEquipmentId());
            userProfile.setModelPotentiometer(listPotentiometer.get(0).getModelEquipmentId());
        } else {
            userProfile.setPotentiometer(-1);
        }
        List<UserAvailabilityDTO> availability = userAvailabilityService.findDtoByUserId(id);
        if (availability == null || availability.isEmpty()) {
            initialAvailability(availability);
        }
        userProfile.setAvailability(availability);
        UserSport sport = userSportDao.findByUserId(id);

        if (userProfile != null) {
            if (sport != null && sport.getSportId() != null) {
                userProfile.setSport(sport.getSportId().getSportId());
            } else {
                userProfile.setSport(-1);
            }
        }

        List<UserZone> userZoneList = userZoneDao.findByUserId(id);
        if (userZoneList != null && !userZoneList.isEmpty()) {
            String[] zone = new String[5];
            for (UserZone userZone : userZoneList) {
                //zone type 1 = Ppm
                if (userZone.getZoneType().equals("1")) {
                    zone = userZone.getZoneTwo().split("-");
                    userProfile.setPpm81(new BigInteger(zone[0]));
                    userProfile.setPpm89(new BigInteger(zone[1]));
                    zone = userZone.getZoneThree().split("-");
                    userProfile.setPpm90(new BigInteger(zone[0]));
                    userProfile.setPpm93(new BigInteger(zone[1]));
                    zone = userZone.getZoneFour().split("-");
                    userProfile.setPpm94(new BigInteger(zone[0]));
                    userProfile.setPpm99(new BigInteger(zone[1]));
                    zone = userZone.getZoneFive().split("-");
                    userProfile.setPpm100(new BigInteger(zone[0]));
                    userProfile.setPpm102(new BigInteger(zone[1]));
                    zone = userZone.getZoneSix().split("-");
                    userProfile.setPpm103(new BigInteger(zone[0]));
                    userProfile.setPpm106(new BigInteger(zone[1]));
                }
                if (userZone.getZoneType().equals("2")) {
                    zone = userZone.getZoneTwo().split("-");
                    userProfile.setFtp56(new BigInteger(zone[0]));
                    userProfile.setFtp75(new BigInteger(zone[1]));
                    zone = userZone.getZoneThree().split("-");
                    userProfile.setFtp76(new BigInteger(zone[0]));
                    userProfile.setFtp90(new BigInteger(zone[1]));
                    zone = userZone.getZoneFour().split("-");
                    userProfile.setFtp91(new BigInteger(zone[0]));
                    userProfile.setFtp105(new BigInteger(zone[1]));
                    zone = userZone.getZoneFive().split("-");
                    userProfile.setFtp106(new BigInteger(zone[0]));
                    userProfile.setFtp120(new BigInteger(zone[1]));
                }
            }
        }
        DisciplineUser discipline = disciplineUserDao.findByUserId(id);
        userProfile.setDiscipline(discipline.getDiscipline().getDisciplineId());
        return userProfile;

    }

    private void initialAvailability(List<UserAvailabilityDTO> dtoList) {
        UserAvailabilityDTO dto = new UserAvailabilityDTO();
        dto.setDay("Lunes");
        dto.setChecked(false);
        dtoList.add(dto);
        dto = new UserAvailabilityDTO();
        dto.setDay("Martes");
        dto.setChecked(false);
        dtoList.add(dto);
        dto = new UserAvailabilityDTO();
        dto.setDay("Miercoles");
        dto.setChecked(false);
        dtoList.add(dto);
        dto = new UserAvailabilityDTO();
        dto.setDay("Jueves");
        dto.setChecked(false);
        dtoList.add(dto);
        dto = new UserAvailabilityDTO();
        dto.setDay("Viernes");
        dto.setChecked(false);
        dtoList.add(dto);
        dto = new UserAvailabilityDTO();
        dto.setDay("Sabado");
        dto.setChecked(false);
        dtoList.add(dto);
        dto = new UserAvailabilityDTO();
        dto.setDay("Domingo");
        dto.setChecked(false);
        dtoList.add(dto);
    }

    @Override
    public void create(UserProfileDTO dto) throws Exception {
        List<EquipmentUserProfile> sportEquipments = new ArrayList<>();
        EquipmentUserProfile equipment = new EquipmentUserProfile();
        UserProfile userProfile = new UserProfile();
        userProfile.setIndPulsometer(dto.getIndPulsometer() == null ? null : dto.getIndPulsometer());
        userProfile.setIndPower(dto.getIndPower() == null ? null : dto.getIndPower());

        if ("1".equals(dto.getIndPulsometer()) && !new Integer(-2).equals(dto.getPulsometer())) {
            equipment.setSportEquipmentId(new SportEquipment(dto.getPulsometer()));
            equipment.setUserProfileId(userProfile);

            if (dto.getModelPulsometer() != null) {
                equipment.setModelEquipmentId(new ModelEquipment(dto.getModelPulsometer()));
            }
            sportEquipments.add(equipment);
        }

        if ("1".equals(dto.getIndPower()) && !new Integer(-2).equals(dto.getPotentiometer())) {
            equipment = new EquipmentUserProfile();
            equipment.setSportEquipmentId(new SportEquipment(dto.getPotentiometer()));
            equipment.setUserProfileId(userProfile);

            if (dto.getModelPotentiometer() != null) {
                equipment.setModelEquipmentId(new ModelEquipment(dto.getModelPotentiometer()));
            }
            sportEquipments.add(equipment);
        }

        if (dto.getBikes() != null && !new Integer(-1).equals(dto.getBikes())) {
            equipment = new EquipmentUserProfile();
            equipment.setSportEquipmentId(new SportEquipment(dto.getBikes()));
            equipment.setUserProfileId(userProfile);
            if (dto.getModelBike() != null) {
                equipment.setModelEquipmentId(new ModelEquipment(dto.getModelBike()));
            }
            sportEquipments.add(equipment);
        }

        if (dto.getShoes() != null && !new Integer(-1).equals(dto.getShoes())) {
            equipment = new EquipmentUserProfile();
            equipment.setSportEquipmentId(new SportEquipment(dto.getShoes()));
            equipment.setUserProfileId(userProfile);
            sportEquipments.add(equipment);
        }

        if (dto.getOtherModelPulsometer() != null && dto.getOtherPulsometer() != null && !"".equals(dto.getOtherPulsometer()) && !"".equals(dto.getOtherModelPulsometer())) {
            SportEquipment newSportEquipment = new SportEquipment();
            Brand newBrand = new Brand();
            ModelEquipment newModel = new ModelEquipment();
            EquipmentUserProfile equipmentUser = new EquipmentUserProfile();
            newBrand.setName(dto.getOtherPulsometer());
            brandDao.create(newBrand);

            newSportEquipment.setName("Other Pulsometer");
            newSportEquipment.setBrandId(newBrand);
            newSportEquipment.setSportEquipmentTypeId(new SportEquipmentType(SportEquipmentTypeEnum.PULSOMETER.getId()));
            sportEquipmentDao.create(newSportEquipment);

            newModel.setName(dto.getOtherModelPulsometer());
            newModel.setSportEquipmentId(newSportEquipment);
            modelEquipmentDao.create(newModel);

            equipmentUser.setModelEquipmentId(newModel);
            equipmentUser.setSportEquipmentId(newSportEquipment);
            equipmentUser.setUserProfileId(userProfile);
            sportEquipments.add(equipmentUser);

        }
        if (dto.getOtherPotentiometer() != null && dto.getOtherModelPotentiometer() != null && !"".equals(dto.getOtherPotentiometer()) && !"".equals(dto.getOtherModelPotentiometer())) {
            SportEquipment newSportEquipment = new SportEquipment();
            Brand newBrand = new Brand();
            ModelEquipment newModel = new ModelEquipment();
            EquipmentUserProfile equipmentUser = new EquipmentUserProfile();
            newBrand.setName(dto.getOtherPotentiometer());
            brandDao.create(newBrand);

            newSportEquipment.setName("Other Potentiometer");
            newSportEquipment.setBrandId(newBrand);
            newSportEquipment.setSportEquipmentTypeId(new SportEquipmentType(SportEquipmentTypeEnum.POTENTIOMETER.getId()));
            sportEquipmentDao.create(newSportEquipment);

            newModel.setName(dto.getOtherModelPotentiometer());
            newModel.setSportEquipmentId(newSportEquipment);
            modelEquipmentDao.create(newModel);

            equipmentUser.setModelEquipmentId(newModel);
            equipmentUser.setSportEquipmentId(newSportEquipment);
            equipmentUser.setUserProfileId(userProfile);
            sportEquipments.add(equipmentUser);
        }
        if (dto.getOtherBike() != null && dto.getOtherModelBike() != null && !"".equals(dto.getOtherBike()) && !"".equals(dto.getOtherModelBike())) {
            SportEquipment newSportEquipment = new SportEquipment();
            Brand newBrand = new Brand();
            ModelEquipment newModel = new ModelEquipment();
            EquipmentUserProfile equipmentUser = new EquipmentUserProfile();
            newBrand.setName(dto.getOtherBike());
            brandDao.create(newBrand);

            newSportEquipment.setBikeTypeId(new BikeType(dto.getBikeType()));
            newSportEquipment.setName("Other Bike");
            newSportEquipment.setBrandId(newBrand);
            newSportEquipment.setSportEquipmentTypeId(new SportEquipmentType(SportEquipmentTypeEnum.BIKES.getId()));
            sportEquipmentDao.create(newSportEquipment);

            newModel.setName(dto.getOtherModelBike());
            newModel.setSportEquipmentId(newSportEquipment);
            modelEquipmentDao.create(newModel);

            equipmentUser.setModelEquipmentId(newModel);
            equipmentUser.setSportEquipmentId(newSportEquipment);
            equipmentUser.setUserProfileId(userProfile);
            sportEquipments.add(equipmentUser);
        }

        userProfile.setObjectiveId(dto.getObjective() == null ? null : new Objective(dto.getObjective()));
        UserAvailability availability = new UserAvailability();
        buildUserAvailabilityObject(dto, availability);
        availability.setUserProfileId(userProfile);
        userProfile.setModalityId(dto.getModality() == null ? null : new Modality(dto.getModality()));
        userProfile.setAgeSport(dto.getAgeSport());
        userProfile.setPpm(dto.getPpm());
        userProfile.setVo2Running(dto.getVo2Running());
        userProfile.setVo2Ciclismo(dto.getVo2Ciclismo());
        userProfile.setPower(dto.getPower());
        userProfile.setSportsAchievements(dto.getSportsAchievements());
        userProfile.setAboutMe(dto.getAboutMe());
        userProfile.setUserId(new User(dto.getUserId()));
        userProfile.setUserProfileId(dto.getUserProfileId());
        userProfile.setEnvironmentId(new Environment(dto.getEnvironmentId()));
        userProfile.setWeatherId(new Weather(dto.getWeatherId()));
        userProfile.setInjuryId(dto.getInjuryId() == null?null: new Injury(dto.getInjuryId()));
        userProfile.setDisease(dto.getDisease());
        User user = userDao.findById(dto.getUserId());
        if(!dto.getIndMetricSys().equals("-1")){
        user.setIndMetricSys(dto.getIndMetricSys());
        }
        user.setWeight(dto.getWeight());
        user.setHeight(dto.getHeight());

        UserSport sport = new UserSport();
        if (!new Integer(-1).equals(dto.getSport())) {
            sport.setSportId(new Sport(dto.getSport()));
            sport.setUserProfileId(userProfile);
        }

        userDao.merge(user);
        userProfileDao.create(userProfile);
        if (!sportEquipments.isEmpty()) {
            for (EquipmentUserProfile sportEquipment : sportEquipments) {
                equipmentUserProfileDao.create(sportEquipment);
            }
        }

        UserZone userZone = new UserZone();
        userZone.setZoneType("2");
        userZone.setUserId(user);
        userZone.setZoneTwo(dto.getFtp56() + "-" + dto.getFtp75());
        userZone.setZoneThree(dto.getFtp76() + "-" + dto.getFtp90());
        userZone.setZoneFour(dto.getFtp91() + "-" + dto.getFtp105());
        userZone.setZoneFive(dto.getFtp106() + "-" + dto.getFtp120());
        userZoneDao.create(userZone);
        userZone = new UserZone();
        userZone.setZoneType("1");
        userZone.setUserId(user);
        userZone.setZoneTwo(dto.getPpm81() + "-" + dto.getPpm89());
        userZone.setZoneThree(dto.getPpm90() + "-" + dto.getPpm93());
        userZone.setZoneFour(dto.getPpm94() + "-" + dto.getPpm99());
        userZone.setZoneFive(dto.getPpm100() + "-" + dto.getPpm102());
        userZone.setZoneSix(dto.getPpm103() + "-" + dto.getPpm106());
        userZoneDao.create(userZone);
        userSportDao.create(sport);
        userAvailabilityService.create(availability);
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
        user.setWeight(dto.getWeight());
        user.setHeight(dto.getHeight());
        if (equipments != null && !equipments.isEmpty()) {
            for (EquipmentUserProfile equipment : equipments) {
                if (equipment.getSportEquipmentId() != null && equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.PULSOMETER.getId())) {
                    pulsometer = equipment;
                } else if (equipment.getSportEquipmentId() != null && equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.POTENTIOMETER.getId())) {
                    potentiometer = equipment;
                } else if (equipment.getSportEquipmentId() != null && equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.RUNNING_SHOES.getId())) {
                    runningShoes = equipment;
                } else if (equipment.getSportEquipmentId() != null && equipment.getSportEquipmentId().getSportEquipmentTypeId().getSportEquipmentTypeId().equals(SportEquipmentTypeEnum.BIKES.getId())) {
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

        if (userProfile.getUserProfileId() != null) {
            userProfileDao.merge(userProfile);
        } else {
            userProfileDao.create(userProfile);
        }
        //Pulsometer
        if (pulsometer != null && pulsometer.getEquipmentUserProfileId() != null) {
            equipmentUserProfileDao.merge(pulsometer);
        } else if (pulsometer != null) {
            equipmentUserProfileDao.create(pulsometer);
        }
        //Potentiometer
        if (potentiometer != null && potentiometer.getEquipmentUserProfileId() != null) {
            equipmentUserProfileDao.merge(potentiometer);
        } else if (potentiometer != null) {
            equipmentUserProfileDao.create(potentiometer);
        }
        //Other Pulsometer
        if (dto.getOtherModelPulsometer() != null && dto.getOtherPulsometer() != null && !"".equals(dto.getOtherPulsometer()) && !"".equals(dto.getOtherModelPulsometer())) {
            SportEquipment newSportEquipment = new SportEquipment();
            Brand newBrand = new Brand();
            ModelEquipment newModel = new ModelEquipment();
            EquipmentUserProfile equipmentUser = new EquipmentUserProfile();
            newBrand.setName(dto.getOtherPulsometer());
            brandDao.create(newBrand);

            newSportEquipment.setName("Other Pulsometer");
            newSportEquipment.setBrandId(newBrand);
            newSportEquipment.setSportEquipmentTypeId(new SportEquipmentType(SportEquipmentTypeEnum.PULSOMETER.getId()));
            sportEquipmentDao.create(newSportEquipment);

            newModel.setName(dto.getOtherModelPulsometer());
            newModel.setSportEquipmentId(newSportEquipment);
            modelEquipmentDao.create(newModel);

            equipmentUser.setModelEquipmentId(newModel);
            equipmentUser.setSportEquipmentId(newSportEquipment);
            equipmentUser.setUserProfileId(userProfile);
            if (pulsometer != null && pulsometer.getEquipmentUserProfileId() != null) {
                equipmentUser.setEquipmentUserProfileId(pulsometer.getEquipmentUserProfileId());
                equipmentUserProfileDao.merge(equipmentUser);
            } else {
                equipmentUserProfileDao.create(equipmentUser);
            }

        }
        //Other Potentiometer
        if (dto.getOtherPotentiometer() != null && dto.getOtherModelPotentiometer() != null && !"".equals(dto.getOtherPotentiometer()) && !"".equals(dto.getOtherModelPotentiometer())) {
            SportEquipment newSportEquipment = new SportEquipment();
            Brand newBrand = new Brand();
            ModelEquipment newModel = new ModelEquipment();
            EquipmentUserProfile equipmentUser = new EquipmentUserProfile();
            newBrand.setName(dto.getOtherPotentiometer());
            brandDao.create(newBrand);

            newSportEquipment.setName("Other Potentiometer");
            newSportEquipment.setBrandId(newBrand);
            newSportEquipment.setSportEquipmentTypeId(new SportEquipmentType(SportEquipmentTypeEnum.POTENTIOMETER.getId()));
            sportEquipmentDao.create(newSportEquipment);

            newModel.setName(dto.getOtherModelPotentiometer());
            newModel.setSportEquipmentId(newSportEquipment);
            modelEquipmentDao.create(newModel);

            equipmentUser.setModelEquipmentId(newModel);
            equipmentUser.setSportEquipmentId(newSportEquipment);
            equipmentUser.setUserProfileId(userProfile);
            if (potentiometer != null && potentiometer.getEquipmentUserProfileId() != null) {
                equipmentUser.setEquipmentUserProfileId(potentiometer.getEquipmentUserProfileId());
                equipmentUserProfileDao.merge(equipmentUser);
            } else {
                equipmentUserProfileDao.create(equipmentUser);
            }
        }
        //Other Bike
        if (dto.getOtherBike() != null && dto.getOtherModelBike() != null && !"".equals(dto.getOtherBike()) && !"".equals(dto.getOtherModelBike())) {
            SportEquipment newSportEquipment = new SportEquipment();
            Brand newBrand = new Brand();
            ModelEquipment newModel = new ModelEquipment();
            EquipmentUserProfile equipmentUser = new EquipmentUserProfile();
            newBrand.setName(dto.getOtherBike());
            brandDao.create(newBrand);

            newSportEquipment.setBikeTypeId(new BikeType(dto.getBikeType()));
            newSportEquipment.setName("Other Bike");
            newSportEquipment.setBrandId(newBrand);
            newSportEquipment.setSportEquipmentTypeId(new SportEquipmentType(SportEquipmentTypeEnum.BIKES.getId()));
            sportEquipmentDao.create(newSportEquipment);

            newModel.setName(dto.getOtherModelBike());
            newModel.setSportEquipmentId(newSportEquipment);
            modelEquipmentDao.create(newModel);

            equipmentUser.setModelEquipmentId(newModel);
            equipmentUser.setSportEquipmentId(newSportEquipment);
            equipmentUser.setUserProfileId(userProfile);
            if (bike != null && bike.getEquipmentUserProfileId() != null) {
                equipmentUser.setEquipmentUserProfileId(bike.getEquipmentUserProfileId());
                equipmentUserProfileDao.merge(equipmentUser);
            } else {
                equipmentUserProfileDao.create(equipmentUser);
            }
        }
        //RunningShoes
        if (runningShoes != null && runningShoes.getEquipmentUserProfileId() != null) {
            equipmentUserProfileDao.merge(runningShoes);
        } else if (runningShoes != null) {
            equipmentUserProfileDao.create(runningShoes);
        }
        //Bike 
        if (bike != null && bike.getEquipmentUserProfileId() != null) {
            equipmentUserProfileDao.merge(bike);
        } else if (bike != null) {
            equipmentUserProfileDao.create(bike);
        }
        //User Availability 
        if (availability != null && availability.getUserAvailabilityId() != null) {
            userAvailabilityService.merge(availability);
        } else if (availability != null) {
            availability.setUserProfileId(userProfile);
            userAvailabilityService.create(availability);
        }
        if (sport != null && sport.getUserSportId() != null) {
            userSportDao.merge(sport);
        } else if (sport != null) {
            userSportDao.create(sport);
        }
        List<UserZone> userZoneList = userZoneDao.findByUserId(userProfile.getUserId().getUserId());
        if (userZoneList != null && !userZoneList.isEmpty()) {
            for (UserZone userZone : userZoneList) {
                //zone type 1 = Ppm
                if (userZone.getZoneType().equals("1")) {
                    userZone.setZoneTwo(dto.getPpm81() + "-" + dto.getPpm89());
                    userZone.setZoneThree(dto.getPpm90() + "-" + dto.getPpm93());
                    userZone.setZoneFour(dto.getPpm94() + "-" + dto.getPpm99());
                    userZone.setZoneFive(dto.getPpm100() + "-" + dto.getPpm102());
                    userZone.setZoneSix(dto.getPpm103() + "-" + dto.getPpm106());
                    userZoneDao.merge(userZone);
                } else if (userZone.getZoneType().equals("2")) {
                    userZone.setZoneTwo(dto.getFtp56() + "-" + dto.getFtp75());
                    userZone.setZoneThree(dto.getFtp76() + "-" + dto.getFtp90());
                    userZone.setZoneFour(dto.getFtp91() + "-" + dto.getFtp105());
                    userZone.setZoneFive(dto.getFtp106() + "-" + dto.getFtp120());
                    userZoneDao.merge(userZone);
                }
            }
        } else {
            UserZone userZone = new UserZone();
            userZone.setZoneType("2");
            userZone.setUserId(user);
            userZone.setZoneTwo(dto.getFtp56() + "-" + dto.getFtp75());
            userZone.setZoneThree(dto.getFtp76() + "-" + dto.getFtp90());
            userZone.setZoneFour(dto.getFtp91() + "-" + dto.getFtp105());
            userZone.setZoneFive(dto.getFtp106() + "-" + dto.getFtp120());
            userZoneDao.create(userZone);
            userZone = new UserZone();
            userZone.setZoneType("1");
            userZone.setUserId(user);
            userZone.setZoneTwo(dto.getPpm81() + "-" + dto.getPpm89());
            userZone.setZoneThree(dto.getPpm90() + "-" + dto.getPpm93());
            userZone.setZoneFour(dto.getPpm94() + "-" + dto.getPpm99());
            userZone.setZoneFive(dto.getPpm100() + "-" + dto.getPpm102());
            userZone.setZoneSix(dto.getPpm103() + "-" + dto.getPpm106());
            userZoneDao.create(userZone);
        }

        userDao.merge(user);
        disciplineUserDao.merge(discipline);
        return null;
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
        userProfile.setObjectiveId(dto.getObjective() == null ? null : new Objective(dto.getObjective()));
        userProfile.setPower(dto.getPower());
        userProfile.setPpm(dto.getPpm());
        userProfile.setSportsAchievements(dto.getSportsAchievements());
        userProfile.setUserId(new User(dto.getUserId()));
        userProfile.setVo2Running(dto.getVo2Running());
        userProfile.setVo2Ciclismo(dto.getVo2Ciclismo());
        userProfile.setUserProfileId(dto.getUserProfileId());
        userProfile.setEnvironmentId(new Environment(dto.getEnvironmentId()));
        userProfile.setWeatherId(new Weather(dto.getWeatherId()));
        userProfile.setModalityId(dto.getModality() == null ? null : new Modality(dto.getModality()));
        userProfile.setInjuryId(new Injury(dto.getInjuryId()));
        userProfile.setDisease(dto.getDisease());
    }

    /**
     * Builds the corresponding equipment object for the pulsometer
     *
     * @param dto
     * @param pulsometer
     * @return
     */
    private EquipmentUserProfile buildPulsometerObject(UserProfileDTO dto, EquipmentUserProfile pulsometer) {
        if (pulsometer != null && !new Integer(-1).equals(dto.getPulsometer()) && !new Integer(-2).equals(dto.getPulsometer())) {
            pulsometer.setSportEquipmentId(new SportEquipment(dto.getPulsometer()));
            if (dto.getModelPulsometer() != null) {
                pulsometer.setModelEquipmentId(new ModelEquipment(dto.getModelPulsometer()));
            }
        } else if (!new Integer(-1).equals(dto.getPulsometer()) && !new Integer(-2).equals(dto.getPulsometer())) {
            pulsometer = new EquipmentUserProfile();
            pulsometer.setSportEquipmentId(new SportEquipment(dto.getPulsometer()));
            pulsometer.setUserProfileId(new UserProfile(dto.getUserProfileId()));
            if (dto.getModelPulsometer() != null) {
                pulsometer.setModelEquipmentId(new ModelEquipment(dto.getModelPulsometer()));
            }
        }
        return pulsometer;
    }

    /**
     * Builds the corresponding equipment object for the potentiometer
     *
     * @param dto
     * @param potentiometer
     * @return
     */
    private EquipmentUserProfile buildPotentiometerObject(UserProfileDTO dto, EquipmentUserProfile potentiometer) {
        if (potentiometer != null && !new Integer(-1).equals(dto.getPotentiometer()) && !new Integer(-2).equals(dto.getPotentiometer())) {
            potentiometer.setSportEquipmentId(new SportEquipment(dto.getPotentiometer()));
            if (dto.getModelPotentiometer() != null) {
                potentiometer.setModelEquipmentId(new ModelEquipment(dto.getModelPotentiometer()));
            }
        } else if (!new Integer(-1).equals(dto.getPotentiometer()) && !new Integer(-2).equals(dto.getPotentiometer())) {
            potentiometer = new EquipmentUserProfile();
            potentiometer.setSportEquipmentId(new SportEquipment(dto.getPotentiometer()));
            potentiometer.setUserProfileId(new UserProfile(dto.getUserProfileId()));
            if (dto.getModelPotentiometer() != null) {
                potentiometer.setModelEquipmentId(new ModelEquipment(dto.getModelPotentiometer()));
            }
        }
        return potentiometer;
    }

    /**
     * Builds the corresponding equipment object for running shoes
     *
     * @param dto
     * @param runningShoes
     * @return
     */
    private EquipmentUserProfile buildRunningShoesObject(UserProfileDTO dto, EquipmentUserProfile runningShoes) {
        if (runningShoes != null && !new Integer(-1).equals(dto.getShoes())) {
            runningShoes.setSportEquipmentId(new SportEquipment(dto.getShoes()));
        } else if (!new Integer(-1).equals(dto.getShoes())) {
            runningShoes = new EquipmentUserProfile();
            runningShoes.setSportEquipmentId(new SportEquipment(dto.getShoes()));
            runningShoes.setUserProfileId(new UserProfile(dto.getUserProfileId()));
        }
        return runningShoes;
    }

    /**
     * Builds the corresponding equipment object for bike
     *
     * @param dto
     * @param bike
     * @return
     */
    private EquipmentUserProfile buildBikeObject(UserProfileDTO dto, EquipmentUserProfile bike) {
        if (bike != null && !new Integer(-1).equals(dto.getBikes())) {
            bike.setSportEquipmentId(new SportEquipment(dto.getBikes()));
            if (dto.getModelBike() != null) {
                bike.setModelEquipmentId(new ModelEquipment(dto.getModelBike()));
            }
        } else if (!new Integer(-1).equals(dto.getBikes())) {
            bike = new EquipmentUserProfile();
            bike.setSportEquipmentId(new SportEquipment(dto.getBikes()));
            bike.setUserProfileId(new UserProfile(dto.getUserProfileId()));
            if (dto.getModelBike() != null) {
                bike.setModelEquipmentId(new ModelEquipment(dto.getModelBike()));
            }
        }
        return bike;
    }

    /**
     * Build the user availability entity
     *
     * @param dto
     * @param availability
     * @return
     */
    private UserAvailability buildUserAvailabilityObject(UserProfileDTO dto, UserAvailability availability) {
        if (availability == null) {
            availability = new UserAvailability();
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
     *
     * @param dto
     * @param availability
     * @return
     */
    private UserSport buildUserSport(UserProfileDTO dto, UserSport sport) {
        if (!new Integer(-1).equals(dto.getSport()) && sport != null) {
            sport.setSportId(new Sport(dto.getSport()));
        } else if (!new Integer(-1).equals(dto.getSport())) {
            sport = new UserSport();
            sport.setUserProfileId(new UserProfile(dto.getUserProfileId()));
            sport.setSportId(new Sport(dto.getSport()));
        }
        return sport;
    }

    /**
     * Build the discipline user entity
     *
     * @param dto
     * @param availability
     * @return
     */
    private DisciplineUser buildDisciplineUser(UserProfileDTO dto, DisciplineUser discipline) {
        discipline.setDiscipline(new Discipline(dto.getDiscipline()));
        return discipline;
    }

    @Override
    public DashboardDTO findDashboardDTOByUserId(Integer id) throws Exception {
        DashboardDTO dashboard = userProfileDao.findDashboardDTOByUserId(id);
        //RunnignShoes
        List<SportEquipmentDTO> listShoes = equipmentUserProfileDao.findDTORunningShoesByUserId(id);
        if (listShoes != null && !listShoes.isEmpty()) {
            dashboard.setShoes(listShoes.get(0).getBrand());
        } else {
            dashboard.setShoes("");
        }
        //Bikes
        List<SportEquipmentDTO> listBikes = equipmentUserProfileDao.findDTOBikesByUserId(id);
        if (listBikes != null && !listBikes.isEmpty()) {
            dashboard.setBikes(listBikes.get(0).getBrand());
        } else {
            dashboard.setBikes("");
        }
        //Pulsometer
        List<SportEquipmentDTO> listPulsometer = equipmentUserProfileDao.findDTOPulsometersByUserId(id);
        if (listPulsometer != null && !listPulsometer.isEmpty()) {
            dashboard.setPulsometer(listPulsometer.get(0).getBrand());
        } else {
            dashboard.setPulsometer("");
        }
        //Potentiometer
        List<SportEquipmentDTO> listPotentiometer = equipmentUserProfileDao.findDTOPotentiometersByUserId(id);
        if (listPotentiometer != null && !listPotentiometer.isEmpty()) {
            dashboard.setPotentiometer(listPotentiometer.get(0).getBrand());
        } else {
            dashboard.setPotentiometer("");
        }
        StringBuilder userAvailability = new StringBuilder("");
        List<UserAvailabilityDTO> availability = userAvailabilityService.findDtoByUserId(id);
        if (availability != null && !availability.isEmpty()) {
            for (UserAvailabilityDTO dto : availability) {
                if (dto.getDay().equals("Lunes") && dto.getChecked()) {
                    userAvailability.append("Lunes");
                    userAvailability.append(",");
                }

                if (dto.getDay().equals("Martes") && dto.getChecked()) {
                    userAvailability.append("Martes");
                    userAvailability.append(",");
                }
                if (dto.getDay().equals("Miercoles") && dto.getChecked()) {
                    userAvailability.append("Miercoles");
                    userAvailability.append(",");
                }
                if (dto.getDay().equals("Jueves") && dto.getChecked()) {
                    userAvailability.append("Jueves");
                    userAvailability.append(",");
                }
                if (dto.getDay().equals("Viernes") && dto.getChecked()) {
                    userAvailability.append("Viernes");
                    userAvailability.append(",");
                }
                if (dto.getDay().equals("Sabado") && dto.getChecked()) {
                    userAvailability.append("Sabado");
                    userAvailability.append(",");
                }
                if (dto.getDay().equals("Domingo") && dto.getChecked()) {
                    userAvailability.append("Domingo");
                    userAvailability.append(",");
                }
            }

            if (userAvailability.length() > 0) {
                userAvailability.deleteCharAt(userAvailability.lastIndexOf(","));
            }
        }
        dashboard.setAvailability(userAvailability.toString());
        UserSport sport = userSportDao.findByUserId(id);
        if (sport != null && sport.getSportId() != null) {
            dashboard.setSport(sport.getSportId().getName());
        } else {
            dashboard.setSport("");
        }
        DisciplineUser discipline = disciplineUserDao.findByUserId(id);
        dashboard.setDiscipline(discipline.getDiscipline().getName());

        return dashboard;
    }
}
