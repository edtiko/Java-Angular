package co.com.expertla.training.dao.impl.user;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.entities.VideoUser;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.dao.user.VideoUserDao;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
* VideoUser Dao Impl <br>
* Creation Date: <br>
* date 24/08/2016 <br>
* @author Angela Ramirez
**/
@Repository
public class VideoUserDaoImpl extends BaseDAOImpl<VideoUser> implements VideoUserDao {
    
    public VideoUserDaoImpl() {
    }
    
    @Override
    public List<VideoUser> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from VideoUser a ");
        builder.append("order by a.VideoUserId desc ");
        return createQuery(builder.toString());
    }


    @Override
    public List<VideoUser> getByUser(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u FROM VideoUser u ");
        sql.append("WHERE u.userId.userId = :userId ");
        sql.append("AND u.stateId.stateId = :stateId ");
        setParameter("userId", userId);
        setParameter("stateId", StateEnum.ACTIVE.getId());
        List<VideoUser> list = createQuery(sql.toString());
        return list;
    }

}