/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.user.UserFittingDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.entities.UserFitting;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class UserFittingDaoImpl extends BaseDAOImpl<UserFitting> implements UserFittingDao {

    @Override
    public UserFitting findByUser(Integer userId) throws DAOException {
        
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserFitting a ");
        builder.append("WHERE a.userId.userId = :userId ");
        builder.append("AND a.stateId = :stateId ");
        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", userId);
        query.setParameter("stateId",  StateEnum.ACTIVE.getId().shortValue());

        List<UserFitting> list = query.getResultList();

        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public UserFitting getFittingActiveByUser(Integer userId) throws DAOException {
        
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserFitting a JOIN a.userFittingHistoryCollection b ");
        builder.append("WHERE a.userId.userId = :userId ");
        builder.append("AND a.stateId = :stateId ");
        builder.append("AND b.stateId != :stateFitting ");
        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", userId);
        query.setParameter("stateId", StateEnum.ACTIVE.getId().shortValue());
        query.setParameter("stateFitting", StateEnum.APPROVED.getId().shortValue());

        List<UserFitting> list = query.getResultList();

        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

}
