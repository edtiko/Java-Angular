package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.DisciplineDao;
import co.expertic.training.model.dto.DisciplineDTO;
import co.expertic.training.model.entities.Discipline;
import co.expertic.training.service.configuration.DisciplineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Discipline Service Impl <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("disciplineService")
@Transactional
public class DisciplineServiceImpl implements DisciplineService {

    @Autowired
    private DisciplineDao disciplineDao;

    @Override
    public Discipline create(Discipline discipline) throws Exception {
        return disciplineDao.create(discipline);
    }

    @Override
    public Discipline store(Discipline discipline) throws Exception {
       return disciplineDao.merge(discipline);
    }

    @Override
    public void remove(Discipline discipline) throws Exception {
        disciplineDao.remove(discipline, discipline.getDisciplineId());
    }

    @Override
    public List<DisciplineDTO> findAll() throws Exception {
        return disciplineDao.findAll();
    }

    @Override
    public List<Discipline> findAllActive() throws Exception {
        return disciplineDao.findAllActive();
    }

    @Override
    public List<DisciplineDTO> findPaginate(int first, int max, String order) throws Exception {
        return disciplineDao.findPaginate(first, max, order);
    }

    @Override
    public List<Discipline> findByDiscipline(Discipline discipline) throws Exception {
        return disciplineDao.findByDiscipline(discipline);
    }

    @Override
    public List<Discipline> findByFiltro(Discipline discipline) throws Exception {
        return disciplineDao.findByFiltro(discipline);
    }

    @Override
    public DisciplineDTO findByUserId(Integer userId) throws Exception {
        List<DisciplineDTO> list = disciplineDao.findByUserId(userId);
        
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}