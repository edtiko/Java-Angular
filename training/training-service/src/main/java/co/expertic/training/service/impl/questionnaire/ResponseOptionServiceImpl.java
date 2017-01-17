package co.expertic.training.service.impl.questionnaire;

import co.expertic.training.dao.questionnaire.ResponseOptionDao;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireResponse;
import co.expertic.training.model.entities.ResponseOption;
import co.expertic.training.service.questionnaire.ResponseOptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseOptionServiceImpl implements ResponseOptionService{

    @Autowired
    private ResponseOptionDao responseOptionDao;

    @Override
    public ResponseOption create(ResponseOption responseOption) throws Exception {
        return responseOptionDao.create(responseOption);
    }

    @Override
    public ResponseOption merge(ResponseOption responseOption) throws Exception {
        return responseOptionDao.merge(responseOption);
    }

    @Override
    public void remove(ResponseOption responseOption) throws Exception {
        responseOptionDao.remove(responseOption);
    }

    @Override
    public List<ResponseOption> findAll(SePaginator sePaginator) throws Exception {
        return responseOptionDao.findAll(sePaginator);
    }

    @Override
    public List<ResponseOption> findByResponseOptionId(ResponseOption responseOption) throws Exception {
        return responseOptionDao.findByResponseOptionId(responseOption);
    }
    
    @Override
    public ResponseOption findByQuestionnaireResponseId(QuestionnaireResponse questionnaireResponseId) throws Exception {
        List<ResponseOption> resultList = responseOptionDao.findByQuestionnaireResponseId(questionnaireResponseId);
        ResponseOption responseOption = null;
        if(resultList != null && !resultList.isEmpty()) {
            responseOption = resultList.get(0);
        }
        return responseOption;
    }
}