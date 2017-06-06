package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.CoachAssignedPlanDao;
import co.expertic.training.dao.plan.PlanAudioDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.PlanAudioDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.PlanAudio;
import co.expertic.training.model.entities.User;
import co.expertic.training.service.plan.PlanAudioService;
import java.util.Calendar;
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
    @Autowired
    CoachAssignedPlanDao coachAssignedPlanDao;
    
    @Override
    public PlanAudioDTO create(PlanAudio video) throws Exception {
        return PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.create(video));
    }
    
    @Override
    public PlanAudioDTO getByAudioPath(String fileName) throws Exception {
        return PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.getByAudioPath(fileName));
    }
    
    @Override
    public List<PlanAudioDTO> getAudiosByUser(Integer coachAssignedPlanId, Integer userId, Integer receivingUserId, String fromto, String tipoPlan, Integer roleSelected) throws Exception {
        return planAudioDao.getAudiosByUser(coachAssignedPlanId, userId, receivingUserId, fromto, tipoPlan, roleSelected);
    }
    
    @Override
    public PlanAudioDTO getAudioById(Integer id) throws Exception {
        return PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.getAudioById(id));
    }
    
    @Override
    public Integer getCountAudioByPlan(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected) throws Exception {
        return planAudioDao.getCountAudioByPlan(coachAssignedPlanId, userId, toUserId, roleSelected);
    }
    
    @Override
    public Integer getCountAudiosReceived(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected) throws Exception {
        return planAudioDao.getCountAudiosReceived(coachAssignedPlanId, userId, toUserId, roleSelected);
    }
    
    @Override
    public void readAudios(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception {
        planAudioDao.readAudios(coachAssignedPlanId, userId, roleSelected);
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
        return planAudioDao.getCountAudioEmergencyByPlan(planId, fromUserId, roleSelected);
    }
    
    @Override
    public int getCountAudioByEmergencyPlanExt(Integer planId, Integer fromUserId) throws Exception {
        return planAudioDao.getCountAudioByEmergencyPlanExt(planId, fromUserId);
    }
    
    @Override
    public PlanAudioDTO approveAudio(Integer planAudioId, Integer planId) throws Exception {
        PlanAudio audioAthlete = planAudioDao.getAudioById(planAudioId);
        CoachAssignedPlan plan = coachAssignedPlanDao.findById(planId);
        User starUser = null;
        User coachUser = null;
        
        if (plan.getStarTeamId() != null && plan.getStarTeamId().getStarUserId() != null) {
            starUser = plan.getStarTeamId().getStarUserId();
            coachUser = plan.getStarTeamId().getCoachUserId();
        } else {
            throw new Exception("No existe una estrella asignada, comuniquese con el administrador.");
        }
        
        audioAthlete.setStateId(StateEnum.APPROVED.getId().shortValue());
        audioAthlete.setReaded(Boolean.TRUE);
        planAudioDao.merge(audioAthlete);
           
        PlanAudio audio = new PlanAudio();
        audio.setFromUserId(coachUser);
        audio.setToUserId(starUser);
        audio.setName(audioAthlete.getName());
        audio.setToStar(Boolean.TRUE);
        audio.setCoachAssignedPlanId(plan);
        audio.setReaded(Boolean.FALSE);
        audio.setStateId(StateEnum.APPROVED.getId().shortValue());
        audio.setCreationDate(Calendar.getInstance().getTime());
        PlanAudioDTO dto = PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.create(audio));
        
        return dto;
        
    }
    
    @Override
    public void rejectAudio(Integer planAudioId) throws Exception {
        PlanAudio audio = planAudioDao.getAudioById(planAudioId);
        audio.setStateId(StateEnum.REJECTED.getId().shortValue());
        audio.setCreationDate(Calendar.getInstance().getTime());
        planAudioDao.merge(audio);
    }
    
    @Override
    public PlanAudio findById(Integer planAudioId) throws Exception {
        return planAudioDao.findById(planAudioId);
    }
    
    @Override
    public PlanAudioDTO sendAudioStarToAThlete(Integer planAudioId) throws Exception {
        PlanAudio audioStar = planAudioDao.findById(planAudioId);
        if (audioStar.getCoachAssignedPlanId() != null) {
            
            CoachAssignedPlan plan = coachAssignedPlanDao.findById(audioStar.getCoachAssignedPlanId().getCoachAssignedPlanId());
            User Athlete = plan.getTrainingPlanUserId().getUserId();
            User Asesor = audioStar.getToUserId();
            audioStar.setStateId(StateEnum.APPROVED.getId().shortValue());
            planAudioDao.merge(audioStar);
            
            PlanAudio audio = new PlanAudio();
            audio.setFromUserId(Asesor);
            audio.setToUserId(Athlete);
            audio.setStateId(StateEnum.RESPONDIDO.getId().shortValue());
            audio.setName(audioStar.getName());
            audio.setCreationDate(Calendar.getInstance().getTime());
            audio.setCoachAssignedPlanId(plan);
            audio.setReaded(Boolean.FALSE);
            audio.setToStar(Boolean.TRUE);
            return PlanAudioDTO.mapFromPlanAudioEntity(planAudioDao.create(audio));
            
        } else {
            throw new Exception("No existe un plan asociado, comuniquese con el administrador.");
        }
        
    }
    
}
