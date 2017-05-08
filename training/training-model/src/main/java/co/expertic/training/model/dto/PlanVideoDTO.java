package co.expertic.training.model.dto;

import co.expertic.training.constant.UrlProperties;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.CoachExtAthlete;
import co.expertic.training.model.entities.PlanVideo;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.CustomerDateAndTimeDeserialize;
import co.expertic.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class PlanVideoDTO {
    
    private Integer id;
    private Integer toUserId;
    private UserDTO toUser;
    private String name;
    private Integer fromUserId;
    private UserDTO fromUser;
    private String  videoPath;
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    @JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
    private Date    createDate;
    private Integer sesionId;
    private Integer coachAssignedPlanId;
    private Integer coachExtAthleteId;
    private String readableTime;
    private Double hours;
    private Integer indRejected;
    private Boolean toStar;
    private Integer roleSelected;
    private boolean mobile;
    private String guion;
    
    public PlanVideoDTO(){
        
    }
    
    // getVideosByUser
    public PlanVideoDTO(Integer planVideoId, String name, User fromUserId,
            User toUserId, Date createDate, Integer indRejected, Boolean toStar){
        this.id = planVideoId;
        this.name = name;
        this.fromUser = UserDTO.mapFromUserShortEntity(fromUserId);
        this.toUser =   UserDTO.mapFromUserShortEntity(toUserId);
        this.createDate = createDate;
        this.indRejected = indRejected;
        this.toStar = toStar;
    }
    
    public PlanVideoDTO(Integer planVideoId, String name, Integer fromUserId, Integer toUserId, Date createDate){
        this.id = planVideoId;
        this.name = name;
        this.fromUserId = fromUserId;
        this.toUserId =  toUserId;
        this.createDate = createDate;
    }
    
    public PlanVideoDTO(Integer planVideoId, String name, User fromUserId, User toUserId, Date createDate, CoachAssignedPlan coachAssignedPlanId, CoachExtAthlete coachExtAthleteId){
        this.id = planVideoId;
        this.name = name;
        this.fromUser = UserDTO.mapFromUserShortEntity(fromUserId);
        this.toUser =   UserDTO.mapFromUserShortEntity(toUserId);
        this.createDate = createDate;
        if(coachAssignedPlanId != null){
            this.coachAssignedPlanId = coachAssignedPlanId.getCoachAssignedPlanId();
        }
        else if(coachExtAthleteId != null){
            this.coachExtAthleteId = coachExtAthleteId.getCoachExtAthleteId();
        }
        
    }
    
    public PlanVideoDTO(Integer planVideoId, String name, Integer fromUserId, Integer toUserId, Date createDate, String guion) {
        this.id = planVideoId;
        this.name = name;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.createDate = createDate;
        this.guion = guion;
    }
    
    public static PlanVideoDTO mapFromPlanVideoEntityShort(PlanVideo video) {
        if (video != null) {
            return new PlanVideoDTO(video.getPlanVideoId(), video.getName(), video.getFromUserId().getUserId(), video.getToUserId().getUserId(), video.getCreationDate());
        }
        return null;
    }
    
    public static PlanVideoDTO mapFromPlanVideoEntity(PlanVideo video) {
        if (video != null) {
            return new PlanVideoDTO(video.getPlanVideoId(), video.getName(), video.getFromUserId(), 
                    video.getToUserId(), video.getCreationDate(), video.getCoachAssignedPlanId(), video.getCoachExtAthleteId());
        }
        return null;
    }

    public Integer getIndRejected() {
        return indRejected;
    }

    public void setIndRejected(Integer indRejected) {
        this.indRejected = indRejected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getVideoPath() {
        return UrlProperties.URL_VIDEO_FILES+name;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSesionId() {
        return sesionId;
    }

    public void setSesionId(Integer sesionId) {
        this.sesionId = sesionId;
    }

    public Integer getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(Integer coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public UserDTO getToUser() {
        return toUser;
    }

    public void setToUser(UserDTO toUser) {
        this.toUser = toUser;
    }

    public UserDTO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDTO fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(Integer coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }
    

    public String getReadableTime() {
        return readableTime;
    }

    public void setReadableTime(String readableTime) {
        this.readableTime = readableTime;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Boolean getToStar() {
        return toStar;
    }

    public void setToStar(Boolean toStar) {
        this.toStar = toStar;
    }

    public Integer getRoleSelected() {
        return roleSelected;
    }

    public void setRoleSelected(Integer roleSelected) {
        this.roleSelected = roleSelected;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public String getGuion() {
        return guion;
    }

    public void setGuion(String guion) {
        this.guion = guion;
    }

      
}
