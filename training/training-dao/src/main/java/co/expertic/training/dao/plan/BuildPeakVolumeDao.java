/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.entities.BuildPeakVolume;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface BuildPeakVolumeDao extends BaseDAO<BuildPeakVolume>{
    
    
    public List<BuildPeakVolume> findByLevelId(Integer trainingLevelId) throws DAOException;
    
}
