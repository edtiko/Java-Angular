package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.CharacteristicDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.CharacteristicDTO;
import co.com.expertla.training.model.entities.Characteristic;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Characteristic Dao Impl <br>
* Info. Creación: <br>
* fecha 8/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class CharacteristicDaoImpl extends BaseDAOImpl<Characteristic> implements CharacteristicDao {

    public CharacteristicDaoImpl() {
    }
    
    @Override
    public List<Characteristic> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Characteristic a ");
        builder.append("order by a.characteristicId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Characteristic> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Characteristic a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<CharacteristicDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.CharacteristicDTO(a.characteristicId,");
        builder.append("a.name,(CASE WHEN (a.valueType = 1) THEN 'CHECK' WHEN (a.valueType = 2) THEN 'NUMERO' ELSE 'PORCENTAJE' END),a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Characteristic a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR (CASE WHEN (a.valueType = 1) THEN 'CHECK' WHEN (a.valueType = 2) THEN 'NUMERO' ELSE 'PORCENTAJE' END) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.name FROM State u WHERE u.stateId = a.stateId) like '%");
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
        List<CharacteristicDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Characteristic> findByCharacteristic(Characteristic characteristic) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Characteristic a ");
        builder.append("WHERE a.characteristicId = :id ");
        setParameter("id", characteristic.getCharacteristicId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Characteristic> findByFiltro(Characteristic characteristic) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Characteristic a ");
        builder.append("WHERE 1=1 ");
        
        if(characteristic.getName() != null && !characteristic.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + characteristic.getName() + "%");
        }


        if(characteristic.getValueType() != null && !characteristic.getValueType().trim().isEmpty()) {
            builder.append("AND lower(a.valueType) like lower(:valueType) ");
            setParameter("valueType", "%" + characteristic.getValueType() + "%");
        }




        if(characteristic.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", characteristic.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<Characteristic> findByName(Characteristic characteristic) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Characteristic a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", characteristic.getName().trim());
        return createQuery(builder.toString());
    }
}