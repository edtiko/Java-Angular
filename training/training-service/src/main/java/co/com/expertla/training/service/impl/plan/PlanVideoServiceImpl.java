package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.PlanVideoDao;
import co.com.expertla.training.model.dto.ChartReportDTO;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.service.plan.MailCommunicationService;
import co.com.expertla.training.service.plan.PlanVideoService;
import java.util.ArrayList;
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
public class PlanVideoServiceImpl implements PlanVideoService {

    @Autowired
    PlanVideoDao planVideoDao;
    
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
    public List<PlanVideoDTO> getVideosByUser(Integer coachAssignedPlanId, Integer userId, String fromto, String tipoPlan) throws Exception {
        return planVideoDao.getVideosByUser(coachAssignedPlanId, userId, fromto, tipoPlan);
    }

    @Override
    public PlanVideoDTO getVideoById(Integer id) throws Exception {
        return PlanVideoDTO.mapFromPlanVideoEntity(planVideoDao.getVideoById(id));
    }

    @Override
    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId) throws Exception {
        return planVideoDao.getCountVideoByPlan(coachAssignedPlanId, userId);
    }

    @Override
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer userId) throws Exception {
        return planVideoDao.getCountVideosReceived(coachAssignedPlanId, userId);
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

}
