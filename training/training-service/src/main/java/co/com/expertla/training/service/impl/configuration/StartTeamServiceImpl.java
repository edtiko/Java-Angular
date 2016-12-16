package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.StartTeamDao;
import co.com.expertla.training.model.dto.StarTeamDTO;
import co.com.expertla.training.model.entities.StarTeam;
import co.com.expertla.training.service.configuration.StartTeamService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* StartTeam Service Impl <br>
* Info. Creación: <br>
* fecha 1/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("startTeamService")
@Transactional
public class StartTeamServiceImpl implements StartTeamService {

    @Autowired
    private StartTeamDao startTeamDao;

    @Override
    public StarTeam create(StarTeam startTeam) throws Exception {
        return startTeamDao.create(startTeam);
    }

    @Override
    public StarTeam store(StarTeam startTeam) throws Exception {
       return startTeamDao.merge(startTeam);
    }

    @Override
    public void remove(StarTeam startTeam) throws Exception {
        startTeamDao.remove(startTeam, startTeam.getStarTeamId());
    }

    @Override
    public List<StarTeam> findAll() throws Exception {
        return startTeamDao.findAll();
    }

    @Override
    public List<StarTeam> findAllActive() throws Exception {
        return startTeamDao.findAllActive();
    }

    @Override
    public List<StarTeamDTO> findPaginate(int first, int max, String order) throws Exception {
        return startTeamDao.findPaginate(first, max, order);
    }

    @Override
    public List<StarTeam> findByStartTeam(StarTeam startTeam) throws Exception {
        return startTeamDao.findByStartTeam(startTeam);
    }

    @Override
    public List<StarTeam> findByFiltro(StarTeam startTeam) throws Exception {
        return startTeamDao.findByFiltro(startTeam);
    }

    @Override
    public StarTeam findBySupervisor(Integer supervisorUserId) throws Exception {
          return startTeamDao.findBySupervisor(supervisorUserId);
    }

}