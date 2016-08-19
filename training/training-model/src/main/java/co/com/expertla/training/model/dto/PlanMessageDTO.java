/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

/**
 *
 * @author Edwin G
 */
public class PlanMessageDTO {

    private Integer id;
    private String message;
    private Integer coachAssignedPlanId;
    private Integer athleteUserId;
    private Integer coachUserId;

    public PlanMessageDTO() {

    }

    public PlanMessageDTO(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(Integer coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public Integer getAthleteUserId() {
        return athleteUserId;
    }

    public void setAthleteUserId(Integer athleteUserId) {
        this.athleteUserId = athleteUserId;
    }

    public Integer getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(Integer coachUserId) {
        this.coachUserId = coachUserId;
    }
    
    

}
