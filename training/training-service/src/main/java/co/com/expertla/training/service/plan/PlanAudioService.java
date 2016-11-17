/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.dto.PlanAudioDTO;
import co.com.expertla.training.model.entities.PlanAudio;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanAudioService {

    public PlanAudioDTO create(PlanAudio video) throws Exception;

    public PlanAudioDTO getByAudioPath(String fileName) throws Exception;

    public List<PlanAudioDTO> getAudiosByUser(Integer coachAssignedPlanId, Integer userId, String fromto, String tipoPlan, Integer roleSelected) throws Exception;

    public PlanAudioDTO getAudioById(Integer id) throws Exception;

    public Integer getCountAudioByPlan(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception;

    public Integer getCountAudiosReceived(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception;

    public void readAudios(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception;

    public void readAudio(Integer planMessageId) throws Exception;

    public Integer getCountAudioByPlanExt(Integer planId, Integer userId) throws Exception;

    public Integer getCountAudiosReceivedExt(Integer planId, Integer userId) throws Exception;

    public void readAudiosExt(Integer planId, Integer userId) throws Exception;

    public int getCountAudioEmergencyByPlan(Integer planId, Integer fromUserId, Integer roleSelected)throws Exception;

    public int getCountAudioByEmergencyPlanExt(Integer planId, Integer fromUserId)throws Exception;

}
