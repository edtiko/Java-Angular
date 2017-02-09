package co.expertic.training.model.util;

/**
* Response of the Rest Service  <br>
* Creation Info:
* date 20/08/2015
* @author Andres Felipe Lopez Rodriguez
**/
public class ResponseService {
    private String status;
    private Object output;
    private String detail;
    private String message;

    public ResponseService() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}