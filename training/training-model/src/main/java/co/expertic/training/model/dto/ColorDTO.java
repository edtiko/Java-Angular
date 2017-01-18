package co.expertic.training.model.dto;

/**
 * Creation Info: <br>
 * date 28/10/2015 <br>
 * @author Angela Ramirez
 */
public class ColorDTO {
    private String color;
    private String referenceMin;
    private String referenceMax;

    public ColorDTO() {
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getReferenceMin() {
        return referenceMin;
    }

    public void setReferenceMin(String referenceMin) {
        this.referenceMin = referenceMin;
    }

    public String getReferenceMax() {
        return referenceMax;
    }

    public void setReferenceMax(String referenceMax) {
        this.referenceMax = referenceMax;
    }
    
}
