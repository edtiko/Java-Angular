package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.TrainingPlanDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.ReportDTO;
import co.expertic.training.model.dto.TrainingPlanDTO;
import co.expertic.training.model.entities.TrainingPlan;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* TrainingPlan Dao Impl <br>
* Info. Creación: <br>
* fecha 23/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class TrainingPlanDaoImpl extends BaseDAOImpl<TrainingPlan> implements TrainingPlanDao {

    public TrainingPlanDaoImpl() {
    }
    
    @Override
    public List<TrainingPlan> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        builder.append("order by a.trainingPlanId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<TrainingPlan> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.stateId = :active AND a.price > 0 ");
        builder.append("AND a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        builder.append("order by a.price asc ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlanDTO> findPaginate(int first, int max, String order, String filter, String typePlan) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.TrainingPlanDTO(a.trainingPlanId,");
        builder.append("a.name,a.description,a.endDate, a.stateId, a.price, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from TrainingPlan a ");
        builder.append("WHERE a.typePlan = '");builder.append(typePlan);builder.append("'");
        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("AND ( 1=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.description) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.duration) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.price) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userCreate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userUpdate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append(")");
        }

        builder.append("order by a.");
        builder.append(order);
        int count = this.getEntityManager().createQuery(builder.toString()).getResultList().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<TrainingPlanDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<TrainingPlan> findByTrainingPlan(TrainingPlan trainingPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.trainingPlanId = :id ");
        setParameter("id", trainingPlan.getTrainingPlanId());
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlan> findByFiltro(TrainingPlan trainingPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE 1=1 ");
        
        if(trainingPlan.getName() != null && !trainingPlan.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + trainingPlan.getName() + "%");
        }

        if(trainingPlan.getDescription() != null && !trainingPlan.getDescription().trim().isEmpty()) {
            builder.append("AND lower(a.description) like lower(:description) ");
            setParameter("description", "%" + trainingPlan.getDescription() + "%");
        }
        builder.append("AND a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlan> findByName(TrainingPlan trainingPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        builder.append("AND a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        setParameter("name", trainingPlan.getName().trim());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<TrainingPlan> findPlaformAllActive(String typePlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.stateId = :active AND a.price > 0 ");
        
        if(typePlan != null && !typePlan.isEmpty()) {
            builder.append("AND a.typePlan = :typePlan ");
        } else {
            builder.append("AND a.typePlan != :typePlan ");
        }
        
        
        builder.append("order by a.price asc ");
        
        if(typePlan != null && !typePlan.isEmpty()) {
            setParameter("typePlan", typePlan);
        } else {
            setParameter("typePlan", "1");
        }
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<List<ReportCountDTO>> findSaleReport(ReportDTO paginateDto) throws Exception {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //SQL Cantidad de usuarios
        StringBuilder sqlUser = new StringBuilder();
        sqlUser.append("select u.creation_date, count(u.user_id) \n");
        sqlUser.append("from user_training u, \n");
        sqlUser.append("discipline_user du, \n");
        sqlUser.append("training_plan_user pu\n");
        sqlUser.append("where u.user_id = pu.user_id\n");
        sqlUser.append("and u.user_id = du.user_id\n");

        //SQL cantidad de usuarios por plan
        StringBuilder sqlPlan = new StringBuilder();
        sqlPlan.append(" select tp.name, count(u.user_id) \n");
        sqlPlan.append("from user_training u, \n");
        sqlPlan.append("discipline_user du, \n");
        sqlPlan.append("training_plan_user pu,\n");
        sqlPlan.append("training_plan tp\n");
        sqlPlan.append("where u.user_id = pu.user_id\n");
        sqlPlan.append("and u.user_id = du.user_id\n");
        sqlPlan.append("and   tp.training_plan_id = pu.training_plan_id\n");

         //SQL cantidad de renovaciones por plan
        StringBuilder sqlRenovation = new StringBuilder();
        sqlRenovation.append("select p.name, count(r.training_plan_renovation_id) \n");
        sqlRenovation.append("from  training_plan_user pu,\n");
        sqlRenovation.append("training_plan p, \n");
        sqlRenovation.append("user_training u, \n");
        sqlRenovation.append("discipline_user du, \n");
        sqlRenovation.append("training_plan_renovation r \n");
        sqlRenovation.append("where pu.training_plan_user_id = r.training_plan_user_id \n");
        sqlRenovation.append("and u.user_id = pu.user_id \n");
        sqlRenovation.append("and u.user_id = du.user_id\n");
        sqlRenovation.append("and p.training_plan_id = pu.training_plan_id\n");

        if (!"".equals(paginateDto.getInitDate())) {
            Date initDate = dateFormat.parse(paginateDto.getInitDate());
            sqlUser.append(" and u.creation_date >= '").append(initDate).append("'");
            sqlPlan.append(" and u.creation_date >= '").append(initDate).append("'");
            sqlRenovation.append(" and r.creation_date >= '").append(initDate).append("'");
        }

        if (!"".equals(paginateDto.getEndDate())) {
            Date endDate = dateFormat.parse(paginateDto.getEndDate());
            sqlUser.append(" and u.creation_date <= '").append(endDate).append("'");
            sqlPlan.append(" and u.creation_date <= '").append(endDate).append("'");
            sqlRenovation.append(" and r.creation_date <= '").append(endDate).append("'");
        }

        if (paginateDto.getAge() != null) {
            sqlUser.append(" and EXTRACT(YEAR from AGE(u.birth_date)) = ").append(paginateDto.getAge());
            sqlPlan.append(" and EXTRACT(YEAR from AGE(u.birth_date)) = ").append(paginateDto.getAge());
            sqlRenovation.append(" and EXTRACT(YEAR from AGE(u.birth_date)) = ").append(paginateDto.getAge());
        }
        if (paginateDto.getCountryId() != null && paginateDto.getCountryId() != -1) {
            sqlUser.append(" and u.country_id = ").append(paginateDto.getCountryId());
            sqlPlan.append(" and u.country_id = ").append(paginateDto.getCountryId());
            sqlRenovation.append(" and u.country_id = ").append(paginateDto.getCountryId());
        }

        if (!"".equals(paginateDto.getSex())) {
            sqlUser.append(" and u.sex = '").append(paginateDto.getSex()).append("'");
            sqlPlan.append(" and u.sex = '").append(paginateDto.getSex()).append("'");
            sqlRenovation.append(" and u.sex = '").append(paginateDto.getSex()).append("'");
        }
        
        if (paginateDto.getTrainingPlanId() != null && paginateDto.getTrainingPlanId() != -1) {
            sqlUser.append(" and pu.training_plan_id = ").append(paginateDto.getTrainingPlanId());
            sqlPlan.append(" and tp.training_plan_id = ").append(paginateDto.getTrainingPlanId());
            sqlRenovation.append(" and p.training_plan_id = ").append(paginateDto.getTrainingPlanId());
        }

        if (paginateDto.getDiscipline() != null && paginateDto.getDiscipline() != -1) {
            sqlUser.append(" and du.discipline = ").append(paginateDto.getDiscipline());
            sqlPlan.append(" and du.discipline = ").append(paginateDto.getDiscipline());
            sqlRenovation.append(" and du.discipline = ").append(paginateDto.getDiscipline());
        }

        sqlUser.append(" group by u.creation_date");

        Query query = getEntityManager().createNativeQuery(sqlUser.toString());
        List<Object[]> list1 = query.getResultList();
        List<ReportCountDTO> listUser = new ArrayList<>();

        list1.stream().forEach((r) -> {
            listUser.add(new ReportCountDTO((Date) r[0], (Long) r[1]));
        });

   
        sqlPlan.append(" group by tp.name");

        Query query2 = getEntityManager().createNativeQuery(sqlPlan.toString());
        List<Object[]> list2 = query2.getResultList();
        List<ReportCountDTO> listPlan = new ArrayList<>();

        list2.stream().forEach((r) -> {
            listPlan.add(new ReportCountDTO((String) r[0], (Long) r[1]));
        });


        sqlRenovation.append(" group by p.name");

        Query query3 = getEntityManager().createNativeQuery(sqlRenovation.toString());
        List<Object[]> list3 = query3.getResultList();
        List<ReportCountDTO> listRenovation = new ArrayList<>();

        list3.stream().forEach((r) -> {
            listRenovation.add(new ReportCountDTO((String) r[0], (Long) r[1]));
        });

        List<List<ReportCountDTO>> res = new ArrayList<>();
        res.add(listUser);
        res.add(listPlan);
        res.add(listRenovation);

        return res;
    }
}