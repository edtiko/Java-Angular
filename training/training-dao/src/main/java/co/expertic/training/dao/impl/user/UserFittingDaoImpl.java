/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.user.UserFittingDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.UserFitting;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class UserFittingDaoImpl extends BaseDAOImpl<UserFitting> implements UserFittingDao {

    @Override
    public UserFitting findByUser(Integer userId) throws DAOException {

        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserFitting a ");
        builder.append("WHERE a.userId.userId = :userId ");
        builder.append("AND a.stateId = :stateId ");
        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", userId);
        query.setParameter("stateId", StateEnum.ACTIVE.getId().shortValue());

        List<UserFitting> list = query.getResultList();

        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public UserFitting getFittingActiveByUser(Integer userId) throws DAOException {

        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserFitting a JOIN a.userFittingHistoryCollection b ");
        builder.append("WHERE a.userId.userId = :userId ");
        builder.append("AND a.stateId = :stateId ");
        builder.append("AND b.stateId != :stateFitting ");
        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", userId);
        query.setParameter("stateId", StateEnum.ACTIVE.getId().shortValue());
        query.setParameter("stateFitting", StateEnum.APPROVED.getId().shortValue());

        List<UserFitting> list = query.getResultList();

        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public List<UserResumeDTO> findAthletesByUserPaginate(Integer userId, PaginateDto paginateDto) throws DAOException {
        StringBuilder sql = new StringBuilder();
        String order = paginateDto.getOrder();
        int first = paginateDto.getPage();
        int max = paginateDto.getLimit();
        if (order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        sql.append("select u.user_id, u.name||' '||u.second_name||' '||u.last_name as fullName,\n"
                + "       (select s.description\n"
                + "        from user_fitting_history h, state s\n"
                + "        where user_fitting_id = uf.user_fitting_id \n"
                + "        and s.state_id = h.state_id\n"
                + "        order by creation_date desc \n"
                + "        limit 1) as state\n"
                + "from user_fitting uf, \n"
                + "     user_training u\n"
                + "where uf.user_id = u.user_id\n"
                + "and uf.state_id = ").append(StateEnum.ACTIVE.getId());
        sql.append(" order by ");
        sql.append(order);

        Query query = this.getEntityManager().createNativeQuery(sql.toString());
        List<Object[]> list = query.getResultList();
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<UserResumeDTO> res = new ArrayList<>();
        list.stream().forEach((result) -> {
            res.add(new UserResumeDTO((Integer)result[0], (String) result[1], (String) result[2]));
        });
        return res;
    }

    @Override
    public List<UserResumeDTO> getAthletes() throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.UserResumeDTO(m.userId)  ");
        sql.append("FROM UserFitting m ");
        sql.append("Where 1=1");
        sql.append(" And m.stateId = :stateId ");
        sql.append(" Order by m.creationDate desc");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("stateId", StateEnum.ACTIVE.getId());

        return query.getResultList();
    }

}
