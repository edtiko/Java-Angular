/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import java.util.List;
import org.springframework.stereotype.Repository;
import co.expertic.training.dao.configuration.FederalStateDao;
import co.expertic.training.model.entities.FederalState;

/**
 *
 * @author Edwin G
 */
@Repository
public class FederalStateDaoImpl extends BaseDAOImpl<FederalState> implements FederalStateDao {

    @Override
    public FederalState findById(Integer federalStateId) {

        try {
            String qlString = "SELECT s FROM FederalState s WHERE s.federalStateId = :federalStateId";
            setParameter("federalStateId", federalStateId);
            List<FederalState> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<FederalState> findStatesByCountryId(Integer countryId) {
        try {
            String qlString = "SELECT u FROM FederalState u WHERE u.countryId.countryId = :countryId";
            setParameter("countryId", countryId);
            List<FederalState> query = createQuery(qlString);
            return query;
        } catch (Exception e) {
            return null;
        }
    }

}
