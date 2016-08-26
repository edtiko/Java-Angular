package co.com.expertla.training.model.dto;

import java.io.Serializable;

/**
 * DayDto
 *
 * @author Angela Ramírez
 */
public class DayDto implements Serializable {

    private boolean selected;

    // Establece el dia a no selecccionado
    public DayDto() {
        setSelected(false);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
