package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.plan.ScriptVideoDao;
import co.com.expertla.training.model.entities.ScriptVideo;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* ScriptVideo Dao Impl <br>
* Info. Creación: <br>
* fecha 11/10/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ScriptVideoDaoImpl extends BaseDAOImpl<ScriptVideo> implements ScriptVideoDao {

    public ScriptVideoDaoImpl() {
    }
    
    @Override
    public List<ScriptVideo> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ScriptVideo a ");
        builder.append("order by a.scriptVideoId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<ScriptVideo> findByScriptVideo(ScriptVideo scriptVideo) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ScriptVideo a ");
        builder.append("WHERE a.scriptVideoId = :id ");
        setParameter("id", scriptVideo.getScriptVideoId());
        return createQuery(builder.toString());
    }

    @Override
    public List<ScriptVideo> findByFiltro(ScriptVideo scriptVideo) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ScriptVideo a ");
        builder.append("WHERE 1=1 ");
        

        return createQuery(builder.toString());
    }
    
    @Override
    public List<ScriptVideo> getScriptVideoToStarId(Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select p from ScriptVideo p ");
        builder.append("WHERE p.planVideoId.toUserId.userId = :userId ");
        setParameter("userId", userId);

        return createQuery(builder.toString());
    }
    
    @Override
    public List<ScriptVideo> getScriptVideoByStarId(Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select p from ScriptVideo p ");
        builder.append("WHERE p.planVideoId.fromUserId.userId = :userId ");
        setParameter("userId", userId);

        return createQuery(builder.toString());
    }

}