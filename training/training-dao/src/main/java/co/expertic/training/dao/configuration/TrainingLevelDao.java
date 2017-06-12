/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.TrainingLevelDTO;
import co.expertic.training.model.entities.TrainingLevel;
import co.expertic.training.model.entities.TrainingLevelType;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface TrainingLevelDao extends BaseDAO<TrainingLevel> {
    
    public TrainingLevelDTO findById(Integer trainingLevelId) throws Exception;
    
    public TrainingLevel getById(Integer trainingLevelId) throws Exception;
    
     public List<TrainingLevelDTO> findByModality(Integer modalityId) throws Exception;

    public List<TrainingLevelDTO> findAllActive() throws Exception;
    
     public List<TrainingLevelType> getTrainingLevelTypeActive() throws DAOException;
    
}
