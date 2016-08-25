package co.com.expertla.training.service.impl.user;

import co.com.expertla.training.dao.CityDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.dao.user.UserDao;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.City;
import co.com.expertla.training.service.user.UserService;
import java.util.Date;
import java.util.stream.Collectors;
import co.com.expertla.training.dao.FederalStateDao;
import co.com.expertla.training.dao.security.RoleUserDao;
import co.com.expertla.training.dao.user.DisciplineUserDao;
import co.com.expertla.training.model.dto.CityDTO;
import co.com.expertla.training.model.dto.FederalStateDTO;
import co.com.expertla.training.model.entities.Discipline;
import co.com.expertla.training.model.entities.DisciplineUser;
import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.model.entities.RoleUser;

@Service("usuarioService")
@Transactional
public class UserServiceImpl implements UserService {

    public static final Short STATE_ACTIVE = 1;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private FederalStateDao federalStateDao;
     
     @Autowired
     private RoleUserDao roleUserDao;

    @Autowired
    private DisciplineUserDao disciplineUserDao;

    @Override
    public List<UserDTO> findAllUsers() {
        return userDao.findAllUsers().stream().map(UserDTO::mapFromUserEntity).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Integer id) {
        return UserDTO.mapFromUserEntity(userDao.findById(id));
    }

    @Override
    public UserDTO findByName(String name) {
        /*for(Usuario user : users){
            if(user.getUsername().equalsIgnoreCase(name)){
                return user;
            }
        }*/
        return null;
    }

    @Override
    public UserDTO findUserByUsername(String username) throws Exception {
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findUserByUsername(username));
        RoleUser roleUser = roleUserDao.findByUserId(user.getUserId());
        user.setTypeUser(roleUser != null ? roleUser.getRoleId().getName():"");
        return user;
    }

    @Transactional
    @Override
    public Integer saveUser(User user) throws Exception {
        return userDao.saveUser(user);
    }

    @Transactional
    @Override
    public int updateUser(UserDTO userDTO) {
        User user = userDao.findById(userDTO.getUserId());
        City city = cityDao.findById(userDTO.getCityId());
        user.setName(userDTO.getFirstName());
        user.setSecondName(userDTO.getSecondName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setBirthDate(userDTO.getBirthDate());
        user.setAddress(userDTO.getAddress());
        user.setSex(userDTO.getSex());
        user.setWeight(userDTO.getWeight());
        user.setPhone(userDTO.getPhone());
        user.setCellphone(userDTO.getCellphone());
        user.setCityId(city);
        user.setPostalCode(userDTO.getPostalCode());
        user.setFacebookPage(userDTO.getFacebookPage());
        user.setTwitterPage(userDTO.getTwitterPage());
        user.setInstagramPage(userDTO.getInstagramPage());
        user.setWebPage(userDTO.getWebPage());
        user.setCreationDate(new Date());

        return userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Integer id) {

        User user = userDao.findById(id);
        userDao.deleteUser(user);
    }

    @Override
    public boolean isUserExist(UserDTO user) {
        return findByName(user.getFirstName()) != null;
    }

    @Override
    public void deleteAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public List<CountryDTO> findAllCountries() {
//        return countryDao.findAll().stream().map(CountryDTO::mapFromCountryEntity).collect(Collectors.toList());
//    }

    @Override
    public List<FederalStateDTO> findStatesByCountry(Integer countryId) {
        return federalStateDao.findStatesByCountryId(countryId).stream().map(FederalStateDTO::mapFromStateEntity).collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> findCitiesByState(Integer stateId) {
        return cityDao.findCitiesByState(stateId).stream().map(CityDTO::mapFromCityEntity).collect(Collectors.toList());
    }

    @Override
    public boolean isUser(String username, String password) {
        return userDao.isUser(username, password);
    }

    @Override
    public void saveProfilePhoto(byte[] bytes, Integer userId) {
        userDao.saveProfilePhoto(bytes, userId);
    }

    @Override
    public List<UserDTO> findAllUsersWithDiscipline() throws Exception {
        return userDao.findAllUsersWithDiscipline();
    }

    @Override
    public void createInternalUser(UserDTO dto) throws Exception {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setSex(dto.getSex());
        user.setPhone(dto.getPhone());
        user.setCreationDate(new Date());

        DisciplineUser discipline = new DisciplineUser();
        discipline.setUserId(user);
        discipline.setDiscipline(new Discipline(dto.getDisciplineId()));

        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(user);
        roleUser.setRoleId(new Role(dto.getRoleId()));

        userDao.create(user);
        disciplineUserDao.create(discipline);
        roleUserDao.create(roleUser);
    }

    @Override
    public void editInternalUser(UserDTO dto) throws Exception {
        User user = userDao.findById(dto.getUserId());
        user.setLogin(dto.getLogin());
        user.setName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setSex(dto.getSex());
        user.setPhone(dto.getPhone());

        DisciplineUser discipline = disciplineUserDao.findByUserId(dto.getUserId());
        if (discipline != null) {
            discipline.setDiscipline(new Discipline(dto.getDisciplineId()));
            disciplineUserDao.merge(discipline);
        }

       RoleUser roleUser = roleUserDao.findByUserId(dto.getUserId());
        if (roleUser != null) {
            roleUser.setRoleId(new Role(dto.getRoleId()));
            roleUserDao.merge(roleUser);
        }

        userDao.merge(user);
    }
}