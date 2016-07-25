package co.com.expertla.training.service.impl.questionnaire;

import co.com.expertla.training.dao.questionnaire.ResponseTypeDao;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.ResponseType;
import co.com.expertla.training.service.questionnaire.ResponseTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResponseTypeServiceImpl implements ResponseTypeService {

    @Autowired
    private ResponseTypeDao responseTypeDao;

    @Override
    public ResponseType create(ResponseType responseType) throws Exception {
        return responseTypeDao.create(responseType);
    }

    @Override
    public ResponseType merge(ResponseType responseType) throws Exception {
        return responseTypeDao.merge(responseType);
    }

    @Override
    public void remove(ResponseType responseType) throws Exception {
        responseTypeDao.remove(responseType);
    }

    @Override
    public List<ResponseType> findAll(SePaginator sePaginator) throws Exception {
        return responseTypeDao.findAll(sePaginator);
    }

    @Override
    public List<ResponseType> findByResponseTypeId(ResponseType responseType) throws Exception {
        return responseTypeDao.findByResponseTypeId(responseType);
    }
}