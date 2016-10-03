package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.SupervStarCoachDao;
import co.com.expertla.training.model.dto.SupervStarCoachDTO;
import co.com.expertla.training.model.dto.UserAssignedSupervisorDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.SupervStarCoach;
import co.com.expertla.training.service.plan.SupervStarCoachService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SupervStarCoach Service Impl <br>
* Info. Creación: <br>
* fecha Sep 13, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("supervStarCoachService")
@Transactional
public class SupervStarCoachServiceImpl implements SupervStarCoachService {

    @Autowired
    private SupervStarCoachDao supervStarCoachDao;

    @Override
    public SupervStarCoach create(SupervStarCoach supervStarCoach) throws Exception {
        return supervStarCoachDao.create(supervStarCoach);
    }

    @Override
    public SupervStarCoach store(SupervStarCoach supervStarCoach) throws Exception {
       return supervStarCoachDao.merge(supervStarCoach);
    }

    @Override
    public void remove(SupervStarCoach supervStarCoach) throws Exception {
        supervStarCoachDao.remove(supervStarCoach, supervStarCoach.getSupervStarCoachId());
    }

    @Override
    public List<SupervStarCoach> findAll() throws Exception {
        return supervStarCoachDao.findAll();
    }

    @Override
    public List<SupervStarCoach> findAllActive() throws Exception {
        return supervStarCoachDao.findAllActive();
    }

    @Override
    public List<SupervStarCoachDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return supervStarCoachDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<SupervStarCoach> findBySupervStarCoach(SupervStarCoach supervStarCoach) throws Exception {
        return supervStarCoachDao.findBySupervStarCoach(supervStarCoach);
    }

    @Override
    public List<SupervStarCoach> findByFiltro(SupervStarCoach supervStarCoach) throws Exception {
        return supervStarCoachDao.findByFiltro(supervStarCoach);
    }
    
    @Override
    public List<SupervStarCoach> findByCoachId(Integer coachId) throws Exception {
        return supervStarCoachDao.findByCoachId(coachId);
    }

    @Override
    public List<UserAssignedSupervisorDTO> findBySupervisorId(Integer userId) throws Exception {
        return supervStarCoachDao.findBySupervisorId(userId);
    }
    
    @Override
    public List<UserAssignedSupervisorDTO> findAtleteCoachBySupervisorId(Integer userId) throws Exception {
        return supervStarCoachDao.findAtleteCoachBySupervisorId(userId);
    }
    
    @Override
    public List<UserDTO> findUsersBySupervisorId(Integer userId) throws Exception {
        List<UserDTO> list = new ArrayList<>();
        List<UserAssignedSupervisorDTO> athletesCoachs = supervStarCoachDao.findAtleteCoachBySupervisorId(userId);
        List<UserAssignedSupervisorDTO> stars =  supervStarCoachDao.findBySupervisorId(userId);
        for (UserAssignedSupervisorDTO star : stars) {
            list.add(star.getStarUserId());
        }
        for(UserAssignedSupervisorDTO o : athletesCoachs) {
            list.add(o.getAtleteUserId());
            list.add(o.getCoachUserId());
        }
        return list;
    }
    
    @Override
    public List<UserDTO> findByStarId(Integer userId) throws Exception {
        List<SupervStarCoach> supervisor = supervStarCoachDao.findByStarId(userId);
        List<UserDTO> users = new ArrayList<>();
        for (SupervStarCoach supervStarCoach : supervisor) {
            users.add(UserDTO.mapFromUserEntity(supervStarCoach.getSupervisorId()));
        }
        return users;
    }

}