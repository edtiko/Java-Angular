package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.UserTrainingOrderDao;
import co.com.expertla.training.model.entities.UserTrainingOrder;
import co.com.expertla.training.service.plan.UserTrainingOrderService;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserTrainingOrder Service Impl <br>
* Info. Creación: <br>
* fecha 12/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("userTrainingOrderService")
@Transactional
public class UserTrainingOrderServiceImpl implements UserTrainingOrderService {

    @Autowired
    private UserTrainingOrderDao userTrainingOrderDao;

    @Override
    public UserTrainingOrder create(UserTrainingOrder userTrainingOrder) throws Exception {
        return userTrainingOrderDao.create(userTrainingOrder);
    }

    @Override
    public UserTrainingOrder store(UserTrainingOrder userTrainingOrder) throws Exception {
       return userTrainingOrderDao.merge(userTrainingOrder);
    }

    @Override
    public void remove(UserTrainingOrder userTrainingOrder) throws Exception {
        userTrainingOrderDao.remove(userTrainingOrder, userTrainingOrder.getUserTrainingOrderId());
    }

    @Override
    public List<UserTrainingOrder> findAll() throws Exception {
        return userTrainingOrderDao.findAll();
    }

    @Override
    public List<UserTrainingOrder> findAllActive() throws Exception {
        return userTrainingOrderDao.findAllActive();
    }

    @Override
    public List<UserTrainingOrder> findByUserTrainingOrder(UserTrainingOrder userTrainingOrder) throws Exception {
        return userTrainingOrderDao.findByUserTrainingOrder(userTrainingOrder);
    }

    @Override
    public List<UserTrainingOrder> findByFiltro(UserTrainingOrder userTrainingOrder) throws Exception {
        return userTrainingOrderDao.findByFiltro(userTrainingOrder);
    }
    
    @Override
    public String getPlanIdByOrder(UserTrainingOrder userTrainingOrder) throws Exception {
        URL url = new URL("http://181.143.227.220:8081/cpt/get_plan_order.php");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setInstanceFollowRedirects(true);
        String postData = "user_id="+userTrainingOrder.getUserId()+"&order_id="+userTrainingOrder.getOrderId()+
                "order_item_id" + userTrainingOrder.getOrderItemId();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Content-length", String.valueOf(postData.length()));
        con.setDoOutput(true);
        con.setDoInput(true);
        try (DataOutputStream output = new DataOutputStream(con.getOutputStream())) {
            output.writeBytes(postData);
        }
        int code = con.getResponseCode(); // 200 = HTTP_OK

        // read the response
        DataInputStream input = new DataInputStream(con.getInputStream());
        int c;
        StringBuilder resultBuf = new StringBuilder();
        while ((c = input.read()) != -1) {
            resultBuf.append((char) c);
        }
        input.close();

        return resultBuf.toString();
    }

}