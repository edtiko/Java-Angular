package co.com.expertla.training.model.util;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.constant.MessageBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Util used for business validations <br>
 * Creation Info: 
 * date 15/08/2015
 * @author Angela Ramirez
 */
public class UtilValidation {

    /**
     * Validates the mandatory fields of an object <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     *
     * @param fields
     * @return a list of errors in a collection
     */
    public List<String> validateFields(HashMap<String, Object> fields) {
        List<String> errorList = new ArrayList<>();
        fields.entrySet().stream().forEach((entry) -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                errorList.add(String.format(MessageUtil.getMessageFromBundle(MessageBundle.UTIL_VALIDATION_PROPERTIES, "mandatoryField"),key));
            } else {
                if(value instanceof String) {
                    String strValue = (String) value;
                    if(strValue.trim().isEmpty())
                    errorList.add(String.format(MessageUtil.getMessageFromBundle(MessageBundle.UTIL_VALIDATION_PROPERTIES, "mandatoryField"),key));
                }
            }
        });
        return errorList;
    }
}
