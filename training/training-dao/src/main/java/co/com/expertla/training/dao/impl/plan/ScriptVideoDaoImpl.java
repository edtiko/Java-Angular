package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.plan.ScriptVideoDao;
import co.com.expertla.training.dao.user.UserDao;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.ScriptVideo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
* ScriptVideo Dao Impl <br>
* Info. Creación: <br>
* fecha 11/10/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ScriptVideoDaoImpl extends BaseDAOImpl<ScriptVideo> implements ScriptVideoDao {
    
    @Autowired
    private UserDao userDao;

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
    
    @Override
    public List<PlanMessageDTO> getResponseTimeScripts(Integer userId, List<UserDTO> users)throws  Exception {
        
            HashMap<Integer,UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(),user);
        }
        
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from p.creation_date - lead(p.creation_date) over (order by p.creation_date))  )as seconds ");
        builder.append("from plan_video  p join script_video s on s.plan_video_id = p.plan_video_id where ");
        builder.append("p.from_user_id = ").append(userId).append("  or p.to_user_id = ").append(userId);
        builder.append("and exists (");
        builder.append("select 'x' from plan_video pp where pp.to_user_id = p.from_user_id and pp.from_user_id = p.to_user_id  )");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanMessageDTO> messageList = new ArrayList<>();
        PlanMessageDTO obj = new PlanMessageDTO();
        for (Object[] result : list) {
            obj = new PlanMessageDTO();
            obj.setMessageUserId(mapUsers.get((Integer) result[4]));
            obj.setReceivingUserId(mapUsers.get((Integer) result[5]));
            obj.setCreationDate((Date)result[6]);
            Double seconds = (Double)result[14];
            obj.setReadableTime(getTime(seconds,obj.getCreationDate()));
            messageList.add(obj);
        }
        return messageList;
    
    }
    
    private String getTime(Double seconds, Date creationDate ){
        Double time;
        if(seconds == null) {
            Date now = new Date();
            Long diff = now.getTime() - creationDate.getTime();
            diff = diff /1000;
            time = diff.doubleValue();
        } else {
            time = seconds;
        }
         if(time > 60) {
             int mins = time.intValue() / 60;
             if(mins > 60) {
                 mins = mins /60;
                 return mins + " hrs";
             } else {
                 return mins + " mins";
             }
         } else {
             return seconds + " segs";
         }
    }
    
    @Override
    public List<PlanMessageDTO> getResponseCountScripts(Integer userId,List<UserDTO> users) throws Exception {
        HashMap<Integer,UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(),user);
        }
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from creation_date - lead(creation_date) over (order by creation_date)) / 3600 )as hours ");
        builder.append("from mail_communication  p where (p.sending_user = ").append(userId).append(" or p.receiving_user = ").append(userId).append(") ");
        builder.append("and exists (");
        builder.append("select 'x' from mail_communication pp where pp.receiving_user = p.sending_user and pp.sending_user = p.receiving_user ");
        builder.append(")");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanMessageDTO> messageList = new ArrayList<>();
        PlanMessageDTO obj = new PlanMessageDTO();
        for (Object[] result : list) {
            obj = new PlanMessageDTO();
            obj.setMessageUserId(mapUsers.get((Integer) result[1]));
            obj.setReceivingUserId(mapUsers.get((Integer) result[2]));
            obj.setCreationDate((Date)result[6]);
            obj.setHours( result[9] == null ? getHours(obj.getCreationDate()) : (Double) result[9]);
            messageList.add(obj);
        }
        return messageList;
    }
    
    private Double getHours(Date creationDate ){
          Date now = new Date();
            Long diff = now.getTime() - creationDate.getTime();
            diff = diff /1000;
            diff = diff / 3600;
            return diff.doubleValue();
    }

}