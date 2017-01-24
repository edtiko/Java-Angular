package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.user.DisciplineUserDao;
import co.expertic.training.model.entities.DisciplineUser;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Discipline <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class DisciplineUserDaoImpl extends BaseDAOImpl<DisciplineUser> implements DisciplineUserDao {

    @Override
    public DisciplineUser findByUserId(Integer id) throws Exception {     
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d ");
        sql.append("FROM DisciplineUser d ");
        sql.append("WHERE d.userId.userId = :id");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id",id);
        List<DisciplineUser> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

}
