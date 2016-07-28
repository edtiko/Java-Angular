package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.QuestionnaireResponse;
import java.util.ArrayList;
import java.util.List;


public class QuestionnaireResponseDTO extends QuestionnaireResponse {
    private List<ResponseOptionDTO> responseOptionList;
    
    public QuestionnaireResponseDTO() {
        responseOptionList = new ArrayList<>();
    }
    
    
    public List<ResponseOptionDTO> getResponseOptionList() {
        return responseOptionList;
    }

    public void setResponseOptionList(List<ResponseOptionDTO> responseOptionList) {
        this.responseOptionList = responseOptionList;
    }
    
}
