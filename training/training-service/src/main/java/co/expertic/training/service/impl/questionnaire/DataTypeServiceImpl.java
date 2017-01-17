package co.expertic.training.service.impl.questionnaire;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.constant.MessageBundle;
import co.expertic.training.dao.questionnaire.DataTypeDao;
import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.DataType;
import co.expertic.training.model.util.UtilValidation;
import co.expertic.training.service.questionnaire.DataTypeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataTypeServiceImpl implements DataTypeService{
    
    @Autowired
    private DataTypeDao dataTypeDao;
    private final UtilValidation util = new UtilValidation();
    private List<String> errorList;
    
    @Override
    public DataType create(DataType dataType) throws Exception {
        errorList = validateMandatoryFields(dataType);
        if(errorList == null || errorList.isEmpty()){
            List<DataType> listDataType = findByName(dataType.getName());
            
            if(listDataType != null && !listDataType.isEmpty()) {
                throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.DATA_TYPE_PROPERTIES,"dataTypeExists"));
            }
           return dataTypeDao.create(dataType);
        } else {
            throw new TrainingException(errorList);
        }
    }

    @Override
    public DataType merge(DataType dataType) throws Exception {
        errorList = validateMandatoryFields(dataType);
        if (errorList == null || errorList.isEmpty()) {

            List<DataType> dataTypeList = findByDataTypeId(dataType);
            if (dataTypeList != null && !dataTypeList.isEmpty()) {
                DataType objDataType = dataTypeList.get(0);
                if (!objDataType.getName().equals(dataType.getName())) {
                    List<DataType> listDataType = findByName(dataType.getName());

                    if (listDataType != null && !listDataType.isEmpty()) {
                        throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.DATA_TYPE_PROPERTIES, "dataTypeExists"));
                    }
                }
                return dataTypeDao.merge(dataType);

            } else {
                throw new TrainingException(MessageUtil.getMessageFromBundle(MessageBundle.DATA_TYPE_PROPERTIES, "dataTypeDoesNotExist"));
            }

        } else {
            throw new TrainingException(errorList);
        }
    }

    @Override
    public void remove(DataType dataType) throws Exception {
        dataTypeDao.remove(dataType);
    }

    @Override
    public List<DataType> findAll(SePaginator sePaginator) throws Exception {
        return dataTypeDao.findAll(sePaginator);
    }

    @Override
    public List<DataType> findByDataTypeId(DataType dataType) throws Exception {
        return dataTypeDao.findByDataTypeId(dataType);
    }
    
    /**
     * Creation Info: 
     * date 15/08/2015
     * @author Angela Ramirez
     *
     * Specify and validate mandatory fields
     * @param dataType
     * @return a list of errors
     */
    private List<String> validateMandatoryFields(DataType dataType) {
        errorList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        map.put("name", dataType.getName());
        return errorList = util.validateFields(map);
    }
    
    @Override
    public List<DataType> findByName(String name) throws Exception {
        return dataTypeDao.findByName(name);
    }
}