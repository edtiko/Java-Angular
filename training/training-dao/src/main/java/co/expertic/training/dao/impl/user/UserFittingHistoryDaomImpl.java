/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.user.UserFittingHistoryDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.UserFittingVideoDTO;
import co.expertic.training.model.entities.UserFittingHistory;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class UserFittingHistoryDaomImpl extends BaseDAOImpl<UserFittingHistory> implements UserFittingHistoryDao {

    @Override
    public UserFittingHistory getByVideoName(String fileName) throws DAOException {
        try {
            String qlString = "SELECT v FROM UserFittingHistory v WHERE v.videoName = :videoName ";
            setParameter("videoName", fileName);
            List<UserFittingHistory> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserFittingVideoDTO getLastVideo(Integer userFittingId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.UserFittingVideoDTO(u.userFittingHistoryId, u.videoName, u.stateId, u.creationDate, u.userFittingId.userId )");
        sql.append("FROM UserFittingHistory u ");
        sql.append("WHERE u.userFittingId.userFittingId = :userFittingId ");
        sql.append("AND u.userFittingId.stateId = :active ");
        sql.append("ORDER BY u.creationDate DESC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userFittingId", userFittingId);
        query.setParameter("active", StateEnum.ACTIVE.getId().shortValue());
        List<UserFittingVideoDTO> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

}
