package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.ScriptVideoDao;
import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.ScriptVideo;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.ScriptVideoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ScriptVideo Service Impl <br>
* Info. Creación: <br>
* fecha 11/10/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("scriptVideoService")
@Transactional
public class ScriptVideoServiceImpl implements ScriptVideoService {

    @Autowired
    private ScriptVideoDao scriptVideoDao;
    @Autowired
    private MailCommunicationService mailCommunicationService;

    @Override
    public ScriptVideo create(ScriptVideo scriptVideo) throws Exception {
        return scriptVideoDao.create(scriptVideo);
    }

    @Override
    public ScriptVideo store(ScriptVideo scriptVideo) throws Exception {
       return scriptVideoDao.merge(scriptVideo);
    }

    @Override
    public void remove(ScriptVideo scriptVideo) throws Exception {
        scriptVideoDao.remove(scriptVideo, scriptVideo.getScriptVideoId());
    }

    @Override
    public List<ScriptVideo> findAll() throws Exception {
        return scriptVideoDao.findAll();
    }

    @Override
    public List<ScriptVideo> findByScriptVideo(ScriptVideo scriptVideo) throws Exception {
        return scriptVideoDao.findByScriptVideo(scriptVideo);
    }

    @Override
    public List<ScriptVideo> findByFiltro(ScriptVideo scriptVideo) throws Exception {
        return scriptVideoDao.findByFiltro(scriptVideo);
    }
    
    @Override
    public List<ScriptVideo> getScriptVideoToStarId(Integer userId) throws Exception {
        return scriptVideoDao.getScriptVideoToStarId(userId);
    }
    
    @Override
    public List<ScriptVideo> getScriptVideoByStarId(Integer userId) throws Exception {
        return scriptVideoDao.getScriptVideoByStarId(userId);
    }
    
    @Override
    public List<PlanMessageDTO> getResponseTimeScripts(Integer userId, Integer roleId) throws Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = mailCommunicationService.getAllRecipientsByStarId(userId);
        } else {
           users = mailCommunicationService.getAllRecipientsByCoachId(userId);           
        }   
        return scriptVideoDao.getResponseTimeScripts(userId, users);
    }
    
    @Override
    public List<ChartReportDTO> getResponseCountScripts(Integer userId,Integer roleId) throws Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = mailCommunicationService.getAllRecipientsByStarId(userId);
        } else {
           users = mailCommunicationService.getAllRecipientsByCoachId(userId);           
        }
        List<PlanMessageDTO> planMessageList = scriptVideoDao.getResponseCountScripts(userId,users);
        List<ChartReportDTO> charList = new ArrayList<>();
        ChartReportDTO chartReportDTO = null;
        Integer redCount = 0;
        Integer yellowCount = 0;
        Integer greenCount = 0;
        String colour = "";
        for (PlanMessageDTO msg : planMessageList) {
            colour = getColour(msg);
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
    
    private String getColour(PlanMessageDTO msg) {
        if(msg.getHours() <= 8) {
            return "green";
        } else if (msg.getHours() > 16) {
            return "red";
        } else {
            return "yellow";
        }
    }

    @Override
    public List<PlanVideoDTO> getByPlan(Integer planId) throws Exception {
        return scriptVideoDao.getByPlan(planId);
    }

    @Override
    public ScriptVideo findByPlanVideoId(Integer id) throws Exception {
       
        return scriptVideoDao.findByPlanVideoId(id);
    }

    @Override
    public void update(ScriptVideo script) throws Exception {
       scriptVideoDao.merge(script);
    }

}