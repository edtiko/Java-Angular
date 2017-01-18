package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.DisciplineDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.DisciplineDTO;
import co.expertic.training.model.entities.Discipline;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Discipline Dao Impl <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class DisciplineDaoImpl extends BaseDAOImpl<Discipline> implements DisciplineDao {

    public DisciplineDaoImpl() {
    }
    
    @Override
    public List<DisciplineDTO> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.DisciplineDTO(d.disciplineId, d.name, d.description) ");
        sql.append("FROM Discipline d ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }


    @Override
    public List<Discipline> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Discipline a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<DisciplineDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.DisciplineDTO(a.disciplineId,");
        builder.append("a.name,a.description,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Discipline a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<DisciplineDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Discipline> findByDiscipline(Discipline discipline) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Discipline a ");
        builder.append("WHERE a.disciplineId = :id ");
        setParameter("id", discipline.getDisciplineId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Discipline> findByFiltro(Discipline discipline) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Discipline a ");
        builder.append("WHERE 1=1 ");
        
        if(discipline.getName() != null && !discipline.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + discipline.getName() + "%");
        }


        if(discipline.getDescription() != null && !discipline.getDescription().trim().isEmpty()) {
            builder.append("AND lower(a.description) like lower(:description) ");
            setParameter("description", "%" + discipline.getDescription() + "%");
        }




        if(discipline.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", discipline.getStateId());
        }

        return createQuery(builder.toString());
    }

    @Override
    public List<DisciplineDTO> findByUserId(Integer userId) throws Exception {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT new co.expertic.training.model.dto.DisciplineDTO(d.discipline.disciplineId, d.discipline.name, d.discipline.description) ");
            sql.append("FROM DisciplineUser d ");
            sql.append("WHERE d.userId.userId = ");
            sql.append(userId);
            Query query = getEntityManager().createQuery(sql.toString());
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}