package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.PlanVideoDao;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.service.plan.PlanVideoService;
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

}
