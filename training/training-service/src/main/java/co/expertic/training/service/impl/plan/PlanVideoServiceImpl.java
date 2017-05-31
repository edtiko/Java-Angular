package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.PlanVideoDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.PlanVideo;
import co.expertic.training.model.entities.ScriptVideo;
import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.User;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanVideoService;
import co.expertic.training.service.plan.ScriptVideoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class PlanVideoServiceImpl implements PlanVideoService {

    @Autowired
    PlanVideoDao planVideoDao;
    
     @Autowired
    UserDao userDao;
    
    @Autowired
    private ScriptVideoService scriptVideoService;
    
    @Autowired
    private MailCommunicationService mailCommunicationService;

    @Override
    public PlanVideoDTO create(PlanVideo video) throws Exception {
        return PlanVideoDTO.mapFromPlanVideoEntity(planVideoDao.create(video));
    }

    @Override
    public PlanVideoDTO getByVideoPath(String fileName) throws Exception {
        return PlanVideoDTO.mapFromPlanVideoEntity(planVideoDao.getByVideoPath(fileName));
    }

    @Override
    public List<PlanVideoDTO> getVideosByUser(Map parameters) throws Exception {
        return planVideoDao.getVideosByUser(parameters);
    }

    @Override
    public PlanVideoDTO getVideoById(Integer id) throws Exception {
        return PlanVideoDTO.mapFromPlanVideoEntity(planVideoDao.getVideoById(id));
    }

    @Override
    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected) throws Exception {
        return planVideoDao.getCountVideoByPlan(coachAssignedPlanId, userId, toUserId, roleSelected);
    }

    @Override
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer fromUserId,  Integer toUserId, Integer roleSelected) throws Exception {
        return planVideoDao.getCountVideosReceived(coachAssignedPlanId, fromUserId, toUserId, roleSelected);
    }

    @Override
    public void readVideos(Integer coachAssignedPlanId, Integer userId) throws Exception {
        planVideoDao.readVideos(coachAssignedPlanId, userId);
    }

    @Override
    public void readVideo(Integer planMessageId) throws Exception {
        planVideoDao.readVideo(planMessageId);
    }

    @Override
    public Integer getCountVideoByPlanExt(Integer planId, Integer userId) throws Exception {
        return planVideoDao.getCountVideoByPlanExt(planId, userId);
    }

    @Override
    public Integer getCountVideosReceivedExt(Integer planId, Integer userId) throws Exception {
        return planVideoDao.getCountVideosReceivedExt(planId, userId);
    }

    @Override
    public void readVideosExt(Integer planId, Integer userId) throws Exception {
        planVideoDao.readVideosExt(planId, userId);
    }

    public List<PlanVideo> getPlanVideoStarByCoach(Integer userId) throws Exception {
        return planVideoDao.getPlanVideoStarByCoach(userId);
    }
    
    public List<PlanVideo> getPlanVideoStarByStar(Integer userId) throws Exception {
        return planVideoDao.getPlanVideoStarByStar(userId);
    }

    @Override
    public List<ChartReportDTO> getResponseCountVideo(Integer userId,Integer roleId) throws Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = mailCommunicationService.getAllRecipientsByStarId(userId);
        } else {
           users = mailCommunicationService.getAllRecipientsByCoachId(userId);           
        }
//        List<PlanVideoDTO> planVideoList = planVideoDao.getResponseTimeVideos(userId, users);
        List<PlanVideoDTO> planVideoList = planVideoDao.getResponseCountVideo(userId,users);
        List<ChartReportDTO> charList = new ArrayList<>();
        ChartReportDTO chartReportDTO = null;
        Integer redCount = 0;
        Integer yellowCount = 0;
        Integer greenCount = 0;
        String colour = "";
        for (PlanVideoDTO planVideo : planVideoList) {
            colour = getColour(planVideo);
            if(colour.equals("red")) {
                redCount++;
            } else if (colour.equals("yellow")) {
                yellowCount++;
            } else {
                greenCount++;
            }
        }
        
            chartReportDTO = new ChartReportDTO();
            chartReportDTO.setName("Rojo");
            chartReportDTO.setValue(redCount);
            chartReportDTO.setStyle("red");
            charList.add(chartReportDTO);
            
            chartReportDTO = new ChartReportDTO();
            chartReportDTO.setName("Amarillo");
            chartReportDTO.setValue(yellowCount);
            chartReportDTO.setStyle("yellow");
            charList.add(chartReportDTO);
            
            chartReportDTO = new ChartReportDTO();
            chartReportDTO.setName("Verde");
            chartReportDTO.setValue(greenCount);
            chartReportDTO.setStyle("green");
            charList.add(chartReportDTO);
        return charList;
    }
    
    private String getColour(PlanVideoDTO planVideo) {
        if(planVideo.getHours() <= 8) {
            return "green";
        } else if (planVideo.getHours() > 16) {
            return "red";
        } else {
            return "yellow";
        }
    }
    
    @Override
    public List<PlanVideoDTO> getResponseTimeVideos(Integer userId, Integer roleId)throws  Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = mailCommunicationService.getAllRecipientsByStarId(userId);
        } else {
           users = mailCommunicationService.getAllRecipientsByCoachId(userId);           
        }
        return planVideoDao.getResponseTimeVideos(userId, users);
    }
    
    @Override
    public void rejectedVideo(Integer planVideoId) throws Exception {
        planVideoDao.rejectedVideo(planVideoId);
    }
    
    @Override
    public PlanVideo getPlanVideoById(Integer id) throws Exception {
        return planVideoDao.getVideoById(id);
    }
    
    @Override
    public PlanVideo store(PlanVideo video) throws Exception {
        return planVideoDao.merge(video);
    }

    @Override
    public int getCountVideoEmergencyIn(Integer planId, Integer fromUserId, Integer toUserId, Integer roleSelected) throws Exception {
        return planVideoDao.getCountVideoEmergencyIn(planId, fromUserId, toUserId, roleSelected);
    }

    @Override
    public int getCountVideoEmergencyExt(Integer planId, Integer fromUserId) throws Exception {
        return planVideoDao.getCountVideoEmergencyExt(planId, fromUserId);
    }

    @Override
    public void approveVideo(Integer planVideoId, Integer userId, String guion) throws Exception {
        PlanVideo video = planVideoDao.getVideoById(planVideoId);
        User starId = userDao.getStarFromAtlethe(userId);
        video.setFromUserId(video.getToUserId());
        video.setToUserId(starId);
        video.setIndRejected(0);
        planVideoDao.merge(video);
        ScriptVideo script = new ScriptVideo();
        script.setGuion(guion);
        script.setCreationDate(new Date());
        script.setPlanVideoId(video);
        script.setFromPlanVideoId(new PlanVideo(planVideoId));
        script.setStateId(new State(StateEnum.PENDING.getId()));
        scriptVideoService.create(script);
    }
}
