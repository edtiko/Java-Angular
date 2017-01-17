/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.ActivityCalendarDTO;
import co.expertic.training.model.entities.ManualActivity;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface ManualActivityDao extends BaseDAO<ManualActivity>{

    public List<ActivityCalendarDTO> findByUserId(Integer userId) throws DAOException;

    public ActivityCalendarDTO findByManualActivityId(Integer manualActivityId) throws DAOException;

    public boolean existManualActivity(ActivityCalendarDTO activity) throws DAOException;

    
}
