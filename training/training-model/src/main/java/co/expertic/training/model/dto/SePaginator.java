package co.expertic.training.model.dto;

/**
* Model <br>
* Creation Info:
* date 20/08/2015
* @author Andres Felipe Lopez Rodriguez
**/
public class SePaginator {
    private int initialRow;
    private int maxRow;

    public SePaginator() {
    }

    public int getInitialRow() {
        return initialRow;
    }

    public void setInitialRow(int initialRow) {
        this.initialRow = initialRow;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }
    
    
}
