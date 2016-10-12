package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.PlanAudioDTO;
import co.com.expertla.training.model.entities.PlanAudio;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanAudioDao extends BaseDAO<PlanAudio> {

    public PlanAudio getByAudioPath(String fileName) throws DAOException;

    public List<PlanAudioDTO> getAudiosByUser(Integer planId, Integer userId, String fromto, String tipoPlan) throws DAOException;

    public PlanAudio getAudioById(Integer id) throws DAOException;

    public Integer getCountAudioByPlan(Integer planId, Integer userId) throws DAOException;
    
    public Integer getCountAudioByPlanExt(Integer planId, Integer userId) throws DAOException;
    
    public Integer getCountAudiosReceived(Integer planId, Integer userId) throws DAOException;
    
    public Integer getCountAudiosReceivedExt(Integer planId, Integer userId) throws DAOException;

    public void readAudios(Integer planId, Integer userId) throws DAOException;
    
    public void readAudiosExt(Integer planId, Integer userId) throws DAOException;

    public void readAudio(Integer planAudioId) throws DAOException;

}
