package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.PlanAudioDao;
import co.expertic.training.model.dto.PlanAudioDTO;
import co.expertic.training.model.entities.PlanAudio;
import co.expertic.training.service.plan.PlanAudioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class PlanAudioServiceImpl implements PlanAudioService {

    @Autowired
    PlanAudioDao planAudioDao;

    @Override
    public PlanAudioDTO create(PlanAudio video) throws Exception {
        return PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.create(video));
    }

    @Override
    public PlanAudioDTO getByAudioPath(String fileName) throws Exception {
        return PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.getByAudioPath(fileName));
    }

    @Override
    public List<PlanAudioDTO> getAudiosByUser(Integer coachAssignedPlanId, Integer userId, String fromto, String tipoPlan, Integer roleSelected) throws Exception {
        return planAudioDao.getAudiosByUser(coachAssignedPlanId, userId, fromto, tipoPlan,roleSelected);
    }

    @Override
    public PlanAudioDTO getAudioById(Integer id) throws Exception {
        return PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.getAudioById(id));
    }

    @Override
    public Integer getCountAudioByPlan(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception {
        return planAudioDao.getCountAudioByPlan(coachAssignedPlanId, userId,roleSelected);
    }

    @Override
    public Integer getCountAudiosReceived(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception {
        return planAudioDao.getCountAudiosReceived(coachAssignedPlanId, userId,roleSelected);
    }

    @Override
    public void readAudios(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception {
        planAudioDao.readAudios(coachAssignedPlanId, userId,roleSelected);
    }

    @Override
    public void readAudio(Integer planMessageId) throws Exception {
        planAudioDao.readAudio(planMessageId);
    }

    @Override
    public Integer getCountAudioByPlanExt(Integer planId, Integer userId) throws Exception {
        return planAudioDao.getCountAudioByPlanExt(planId, userId);
    }

    @Override
    public Integer getCountAudiosReceivedExt(Integer planId, Integer userId) throws Exception {
        return planAudioDao.getCountAudiosReceivedExt(planId, userId);
    }

    @Override
    public void readAudiosExt(Integer planId, Integer userId) throws Exception {
        planAudioDao.readAudiosExt(planId, userId);
    }

    @Override
    public int getCountAudioEmergencyByPlan(Integer planId, Integer fromUserId, Integer roleSelected) throws Exception {
        return planAudioDao.getCountAudioEmergencyByPlan(planId, fromUserId,roleSelected);
    }

    @Override
    public int getCountAudioByEmergencyPlanExt(Integer planId, Integer fromUserId) throws Exception {
         return planAudioDao.getCountAudioByEmergencyPlanExt(planId, fromUserId);
    }

}
