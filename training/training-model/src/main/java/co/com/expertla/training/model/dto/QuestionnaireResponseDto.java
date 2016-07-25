package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.QuestionnaireResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Creation Info: <br>
 * date 13/10/2015 <br>
 * @author Angela Ramirez
 */
public class QuestionnaireResponseDto extends QuestionnaireResponse {
    private List<ResponseOptionDto> responseOptionList;
    
    public QuestionnaireResponseDto() {
        responseOptionList = new ArrayList<>();
    }
    public List<ResponseOptionDto> getResponseOptionList() {
        return responseOptionList;
    }

    public void setResponseOptionList(List<ResponseOptionDto> responseOptionList) {
        this.responseOptionList = responseOptionList;
    }
    
}
