/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.configuration.InjuryDao;
import co.expertic.training.model.entities.Injury;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class InjuryDaoImpl extends BaseDAOImpl<Injury> implements InjuryDao {

    @Override
    public Injury findById(Integer id) throws DAOException {
        try {
            String qlString = "SELECT c FROM Injury c WHERE c.injuryId = :injuryId";
            setParameter("injuryId", id);
            List<Injury> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Injury> findAll() throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Injury a ");
        builder.append("order by a.name  ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }

}
