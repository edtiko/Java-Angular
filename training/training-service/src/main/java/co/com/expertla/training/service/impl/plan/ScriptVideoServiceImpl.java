package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.ScriptVideoDao;
import co.com.expertla.training.model.entities.ScriptVideo;
import co.com.expertla.training.service.plan.ScriptVideoService;
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

}