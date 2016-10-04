/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.entities.PlanMessage;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public interface PlanMessageDao extends BaseDAO<PlanMessage> {

    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId, String tipoPlan) throws DAOException;

    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId) throws DAOException;

    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId) throws DAOException;

    public void readMessages(Integer coachAssignedPlanId, Integer userId) throws DAOException;

    public void readMessage(Integer planMessageId) throws DAOException;

    public Integer getCountMessagesByPlanExt(Integer planId, Integer userId) throws DAOException;

    public Integer getCountMessagesReceivedExt(Integer planId, Integer userId) throws DAOException;

    public void readMessagesExt(Integer planId, Integer userId) throws DAOException;

}
