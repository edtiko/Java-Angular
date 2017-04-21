/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.plan;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.plan.BuildPeakVolumeDao;
import co.expertic.training.model.entities.BuildPeakVolume;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class BuildPeakVolumeDaoImpl extends BaseDAOImpl<BuildPeakVolume> implements BuildPeakVolumeDao{

    @Override
    public List<BuildPeakVolume> findByLevelId(Integer trainingLevelId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT e ");
        sql.append("FROM BuildPeakVolume e ");
        sql.append("WHERE e.trainingLevelId.trainingLevelId = :trainingLevelId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingLevelId", trainingLevelId);
        List<BuildPeakVolume> list = query.getResultList();
        
        return list;
    }
    
}
