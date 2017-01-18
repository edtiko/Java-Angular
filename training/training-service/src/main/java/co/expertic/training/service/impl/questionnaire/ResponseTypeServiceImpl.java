package co.expertic.training.service.impl.questionnaire;

import co.expertic.training.dao.questionnaire.ResponseTypeDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.ResponseType;
import co.expertic.training.service.questionnaire.ResponseTypeService;
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