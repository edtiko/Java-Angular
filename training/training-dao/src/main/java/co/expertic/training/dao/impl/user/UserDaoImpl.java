package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.UserDTO;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.expertic.training.model.entities.User;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.model.dto.NotificationDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;

/**
 *
 * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
 * @project training-dao
 * @class UsuarioDaoImpl
 * @since 11/07/2016
 *
 */
@Repository
public class UserDaoImpl extends BaseDAOImpl<User> implements UserDao {

    /**
     * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
     * @param userId
     * @return
     * @since 11/07/2016
     * @see co.expertic.training.dao.UserDao#findById(long)
     */
    @Override
    public User findById(Integer userId) {

        try {
            String qlString = "SELECT u FROM User u WHERE u.userId = :userId";
            setParameter("userId", userId);
            List<User> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> findAllUsers() {
        try {
            String qlString = "SELECT u FROM User u ";
            List<User> query = createQuery(qlString);
            return query;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User saveUser(User user) throws Exception {
        return create(user);
    }

    @Override
    public Integer updateUser(User user) {
        try {
            return merge(user).getUserId();
        } catch (DAOException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteUser(User user) {
        try {
            remove(user, user.getUserId());
        } catch (DAOException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isUser(String username, String password) {
        try {
            String qlString = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password";
            setParameter("login", username);
            setParameter("password", password);
            List<User> query = createQuery(qlString);
            return (query.get(0) != null);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findUserByUsername(String userName) {
        try {
            String qlString = "SELECT u FROM User u WHERE lower(u.login) = lower(:login) ";
            setParameter("login", userName);
            List<User> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveProfilePhoto(byte[] bytes, Integer userId) {
        try {
            User user = findById(userId);
            user.setProfilePhoto(bytes);
            merge(user);
        } catch (DAOException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<UserDTO> findAllUsersWithDiscipline() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.UserDTO(u.userId, u.login, u.name, u.secondName, u.lastName, ");
        sql.append("u.email,u.sex,u.phone,du.discipline.disciplineId,u.stateId, r.roleId.roleId, u.countryId.countryId, u.profilePhoto, ");
        sql.append(" (SELECT v.url FROM VideoUser v WHERE v.userId.userId = u.userId), (SELECT p.aboutMe FROM UserProfile p WHERE p.userId.userId =u.userId)  )");
        sql.append("FROM User u, DisciplineUser du, RoleUser r ");
        sql.append("WHERE u.userId = du.userId.userId ");
        sql.append("AND u.userId = r.userId.userId ");
        sql.append("ORDER BY u.name ASC ");
        Query query = getEntityManager().createQuery(sql.toString());
        List<UserDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<UserDTO> findUserWithDisciplineById(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.UserDTO(u.userId, u.login, u.name, u.secondName, u.lastName, ");
        sql.append("u.email,u.sex,u.phone,du.discipline.disciplineId, du.discipline.disciplineIdExt,u.stateId, r.roleId.roleId, u.countryId.countryId, u.profilePhoto, ");
        sql.append(" (SELECT v.url FROM VideoUser v WHERE v.userId.userId = u.userId), (SELECT p.aboutMe FROM UserProfile p WHERE p.userId.userId =u.userId)  )");
        sql.append("FROM User u, DisciplineUser du, RoleUser r ");
        sql.append("WHERE u.userId = du.userId.userId ");
        sql.append("AND u.userId = r.userId.userId ");
        sql.append("AND u.userId = :userId ");
        sql.append("ORDER BY u.name ASC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<UserDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<User> findUserByRole(Integer roleId) throws Exception {
        String qlString = "SELECT u FROM User u, RoleUser ru WHERE ru.userId.userId = u.userId AND ru.roleId.roleId = :roleId ";
        setParameter("roleId", roleId);
        List<User> query = createQuery(qlString);
        return query;
    }

    @Override
    public List<UserDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        if (order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new co.expertic.training.model.dto.UserDTO(u.userId, u.login, u.name, u.secondName, u.lastName, ");
        builder.append("u.email,u.sex,u.phone,du.discipline.disciplineId,u.stateId, r.roleId.roleId, u.countryId.countryId, u.profilePhoto, ");
        builder.append(" (SELECT MAX(v.url) FROM VideoUser v WHERE v.userId.userId = u.userId ), ");
        builder.append(" (SELECT MAX(p.aboutMe) FROM UserProfile p WHERE p.userId.userId = u.userId ), ");
        builder.append(" u.creationDate, u.lastUpdate, ");
        builder.append("(select a.login FROM User a WHERE u.userCreate = a.userId),  ");
        builder.append("(select a.login FROM User a WHERE u.userUpdate = a.userId),  ");
        builder.append("(select a.userId FROM User a WHERE u.userCreate = a.userId), ");
        builder.append("(select a.userId FROM User a WHERE u.userUpdate = a.userId),  ");
        builder.append(" r.roleId  ");
        builder.append(") from User u, DisciplineUser du, RoleUser r ");
        builder.append("WHERE u.userId = du.userId.userId ");
        builder.append("AND u.userId = r.userId.userId ");
        if (order.contains("roleId.name")) {
            builder.append("order by r.");
        } else {
            builder.append("order by u.");
        }

        builder.append(order);
        int count = findAllUsers().size();

        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<UserDTO> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }

        return list;
    }

    @Override
    public User getStarFromAtlethe(Integer userId) throws Exception {
        StringBuilder string = new StringBuilder();
        string.append("SELECT s.starUserId FROM CoachAssignedPlan c, TrainingPlanUser tp, StarTeam s ");
        string.append("WHERE c.trainingPlanUserId.trainingPlanUserId = tp.trainingPlanUserId ");
        string.append("AND c.starTeamId.starTeamId = s.starTeamId ");
        string.append("AND tp.userId.userId = :userId ");
        string.append("AND tp.stateId = :active ");
        setParameter("userId", userId);
        setParameter("active", 1);
        List<User> list = createQuery(string.toString());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public Integer getCountNotification(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" select sum(count) as notification from( ");
        sql.append(" select count(plan_message_id) count");
        sql.append(" from   plan_message ");
        sql.append(" where receiving_user_id = ").append(userId);
        sql.append(" and readed = false ");
        sql.append(" and coach_assigned_plan_id is null ");
        sql.append(" and coach_ext_athlete_id is null ");

        sql.append(" union ");

        sql.append(" select count(mail_communication_id) count");
        sql.append(" from   mail_communication");
        sql.append(" where receiving_user = ").append(userId);
        sql.append(" and read = false");
        sql.append(" and coach_assigned_plan_id is null ");
        sql.append(" and coach_ext_athlete_id is null ) n ");

        /* sql.append(" union ");

        sql.append(" select count(plan_audio_id) count");
        sql.append(" from plan_audio ");
        sql.append(" where to_user_id = ").append(userId);
        sql.append(" and readed = false");
         sql.append(" and coach_assigned_plan_id is null ");
        sql.append(" and coach_ext_athlete_id is null ");

        sql.append(" union");

        sql.append(" select count(plan_video_id) count");
        sql.append(" from plan_video ");
        sql.append(" where to_user_id = ").append(userId);
        sql.append(" and readed = false ");
        sql.append(" and coach_assigned_plan_id is null ");
        sql.append(" and coach_ext_athlete_id is null ) n");*/
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public List<UserDTO> findDefaultSupervisors() throws Exception {
        try {
            String qlString = " SELECT new co.expertic.training.model.dto.UserDTO(u.userId, u.name, u.secondName, u.lastName) FROM User u, StarTeam st WHERE u.userId = st.coachUserId.userId AND st.starUserId is null ";
            Query query = this.getEntityManager().createQuery(qlString);
            List<UserDTO> list = query.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Integer> getUserAges() throws DAOException {

        String sql = "select a.age from(\n"
                + "select DISTINCT EXTRACT(YEAR from AGE(birth_date)) as \"age\"\n"
                + "from user_training\n"
                + "where state_id = 1\n"
                + "and birth_date is not null) a\n"
                + "where a.age > 0\n"
                + "order by a.age asc";

        Query query = getEntityManager().createNativeQuery(sql);
        List<Integer> list = (List<Integer>) (List<?>) query.getResultList();
        return list;
    }

    @Override
    public List<NotificationDTO> getUserNotification(Integer userSessionId, Integer planId, String tipoPlan) throws DAOException {
        String sql = "";
        String strPlan = "";
        if (planId != -1) {
            if ("IN".equals(tipoPlan)) {
                strPlan += " and coach_assigned_plan_id = " + planId + " \n";
            } else{
                strPlan += " and coach_ext_athlete_id = " + planId + " \n";
            }
        }
        sql += " select r.role_id,  notification.*  from(\n"
                + "select plan_audio_id as id,\n"
                + "       from_user_id as from_user_id, \n"
                + "       'audio' as module, \n"
                + "        CASE WHEN to_star = true THEN 5 ELSE 4 END as roleId,\n"
                + "        creation_date,\n"
                + "        coach_assigned_plan_id \n"
                + "from plan_audio\n"
                + "where to_user_id = " + userSessionId + " \n"
                +strPlan
                + "and readed = false\n"
                + "\n"
                + "union\n"
                + "\n"
                + "select plan_video_id as id,\n"
                + "       from_user_id as from_user_id, \n"
                + "       'video' as module, \n"
                + "       CASE WHEN to_star = true THEN 5 ELSE 4 END as roleId,\n"
                + "       creation_date,\n"
                + "       coach_assigned_plan_id \n"
                + "from plan_video\n"
                + "where to_user_id = " + userSessionId + " \n"
                +strPlan
                +" and readed = false\n"
                + "\n"
                + "union\n"
                + "\n"
                + "select mail_communication_id as id,\n"
                + "       sending_user as from_user_id, \n"
                + "       'mail' as module, \n"
                + "       CASE WHEN to_star = true THEN 5 ELSE 4 END as roleId,\n"
                + "       creation_date,\n"
                + "       coach_assigned_plan_id \n"
                + "from mail_communication\n"
                + "where receiving_user = " + userSessionId + " \n"
                + strPlan
                + "and read = false\n"
                + "\n"
                + "union \n"
                + "\n"
                + "select plan_message_id as id,\n"
                + "       message_user_id as from_user_id, \n"
                + "      'chat' as module, \n"
                + "      CASE WHEN to_star = true THEN 5 ELSE 4 END as roleId,\n"
                + "      creation_date,\n"
                + "      coach_assigned_plan_id \n"
                + "from plan_message\n"
                + "where receiving_user_id = " + userSessionId + " \n"
                +strPlan
                +" and readed = false) notification, role_user ru, role r \n"
                + " where ru.user_id = notification.from_user_id \n"
                + " and ru.role_id = r.role_id \n"
                + " order by notification.creation_date desc ";
        Query query = this.getEntityManager().createNativeQuery(sql);
        List<Object[]> list = query.getResultList();
        List<NotificationDTO> res = new ArrayList<>();
        list.stream().forEach((result) -> {
            res.add(new NotificationDTO((Integer) result[0], (Integer) result[1], (Integer) result[2],(String) result[3], (Integer) result[4], (Date) result[5], (Integer) result[6]));
        });
        return res;
    }

    @Override
    public List<NotificationDTO> getUserCountNotification(Integer userId, Integer toUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" select notification.*  from(\n"
                + "                select  count(plan_audio_id),\n"
                + "                       'audio' as module\n"
                + "                from plan_audio\n"
                + "                where to_user_id = " + toUserId + " \n"
                + "                and from_user_id = "+userId+" \n"
                + "                and readed = false\n"
                + "                group by to_user_id\n"
                + "                union\n"
                + "                \n"
                + "                select count(plan_video_id),\n"
                + "                       'video' as module\n"
                + "                from plan_video\n"
                + "                where to_user_id = " + toUserId + " \n"
                + "                and from_user_id = "+userId+" \n"
                + "                and readed = false\n"
                + "                group by to_user_id\n"
                + "                \n"
                + "                union\n"
                + "                \n"
                + "                select count(mail_communication_id), \n"
                + "                       'mail' as module\n"
                + "                from mail_communication\n"
                + "                where receiving_user = " + toUserId + " \n"
                + "                and sending_user = "+userId+" \n"
                + "                and read = false\n"
                + "                group by receiving_user\n"
                + "                \n"
                + "                union \n"
                + "                \n"
                + "                select count(plan_message_id),\n"
                + "                      'chat' as module\n"
                + "                from plan_message\n"
                + "                where receiving_user_id = " + toUserId + " \n"
                + "                and message_user_id = "+userId+" \n"
                + "                and readed = false\n"
                + "                group by receiving_user_id ) notification ");
        Query query = this.getEntityManager().createNativeQuery(sql.toString());
        List<Object[]> list = query.getResultList();
        List<NotificationDTO> res = new ArrayList<>();
        list.stream().forEach((result) -> {
            res.add(new NotificationDTO((Long) result[0], (String) result[1]));
        });
        return res;
    }

    @Override
    public List<NotificationDTO> getUserCountNotificationStar(Integer athleteId, Integer coachUserId, Integer starUserId) throws DAOException {
         StringBuilder sql = new StringBuilder();
        sql.append(" select notification.*  from(\n"
                + "                select  count(plan_audio_id),\n"
                + "                       'audio' as module\n"
                + "                from plan_audio\n"
                + "                where to_user_id = " + starUserId + " \n"
                + "                and from_user_id = "+coachUserId+" \n"
                + "                and readed = false\n"
                + "                group by to_user_id\n"
                + "                union\n"
                + "                \n"
                + "                select count(plan_video_id),\n"
                + "                       'video' as module\n"
                + "                from plan_video\n"
                + "                where to_user_id = " + starUserId + " \n"
                + "                and from_user_id = "+coachUserId+" \n"
                + "                and readed = false\n"
                + "                group by to_user_id\n"
                + "                \n"
                + "                union\n"
                + "                \n"
                + "                select count(mail_communication_id), \n"
                + "                       'mail' as module\n"
                + "                from mail_communication\n"
                + "                where receiving_user = " + starUserId + " \n"
                + "                and sending_user = "+athleteId+" \n"
                + "                and read = false\n"
                + "                group by receiving_user\n"
                + "                \n"
                + "                union \n"
                + "                \n"
                + "                select count(plan_message_id),\n"
                + "                      'chat' as module\n"
                + "                from plan_message\n"
                + "                where receiving_user_id = " + starUserId + " \n"
                + "                and message_user_id = "+athleteId+" \n"
                + "                and readed = false\n"
                + "                group by receiving_user_id ) notification ");
        Query query = this.getEntityManager().createNativeQuery(sql.toString());
        List<Object[]> list = query.getResultList();
        List<NotificationDTO> res = new ArrayList<>();
        list.stream().forEach((result) -> {
            res.add(new NotificationDTO((Long) result[0], (String) result[1]));
        });
        return res;
    }
}
