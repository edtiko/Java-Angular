package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.PlanAudioDTO;
import co.expertic.training.model.entities.PlanAudio;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanAudioDao extends BaseDAO<PlanAudio> {

    public PlanAudio getByAudioPath(String fileName) throws DAOException;

    public List<PlanAudioDTO> getAudiosByUser(Integer planId, Integer userId, Integer receivingUserId,String fromto, String tipoPlan, Integer roleSelected) throws DAOException;

    public PlanAudio getAudioById(Integer id) throws DAOException;

    public Integer getCountAudioByPlan(Integer planId, Integer userId, Integer toUserId, Integer roleSelected) throws DAOException;
    
    public Integer getCountAudioByPlanExt(Integer planId, Integer userId) throws DAOException;
    
    public Integer getCountAudiosReceived(Integer planId, Integer userId, Integer roleSelected) throws DAOException;
    
    public Integer getCountAudiosReceivedExt(Integer planId, Integer userId) throws DAOException;

    public void readAudios(Integer planId, Integer userId, Integer roleSelected) throws DAOException;
    
    public void readAudiosExt(Integer planId, Integer userId) throws DAOException;

    public void readAudio(Integer planAudioId) throws DAOException;

    public int getCountAudioEmergencyByPlan(Integer planId, Integer fromUserId, Integer roleSelected)throws DAOException;

    public int getCountAudioByEmergencyPlanExt(Integer planId, Integer fromUserId)throws DAOException;

}
