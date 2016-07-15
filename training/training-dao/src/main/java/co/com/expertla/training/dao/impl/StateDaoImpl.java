/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.StateDao;
import co.com.expertla.training.model.entities.State;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class StateDaoImpl extends BaseDAOImpl<State> implements StateDao {

    @Override
    public State findById(Integer stateId) {

        try {
            String qlString = "SELECT s FROM State s WHERE s.stateId = :stateId";
            setParameter("stateId", stateId);
            List<State> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
