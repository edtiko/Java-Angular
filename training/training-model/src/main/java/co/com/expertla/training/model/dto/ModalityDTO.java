package co.com.expertla.training.model.dto;

import java.io.Serializable;

public class ModalityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer modalityId;
    private String name;
    
    public ModalityDTO() {
    }

    public ModalityDTO(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public ModalityDTO(Integer modalityId, String name) {
        this.modalityId = modalityId;
        this.name = name;
    }

    public Integer getModalityId() {
        return modalityId;
    }

    public void setModalityId(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
