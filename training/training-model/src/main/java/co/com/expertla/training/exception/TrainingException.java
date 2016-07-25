/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.exception;

import java.util.List;

/**
 *
 * @author Edwin G
 */
public class TrainingException extends Exception{
      private List<String> errorList;
    
    /**
     * Constructs a new exception with null as its detail message <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     */
    public TrainingException() {
    }
    
    /**
     * Constructs a new exception with the specified detail message <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param message 
     */
    public TrainingException(String message) {
        super(message);
    }
    /**
     * Constructs a new exception with the specified list detail message <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param messages 
     */
    public TrainingException(List<String> messages) {
        this.errorList = messages;
    }
    
    /** 
     * @return the errorList
     */
    public List<String> getErrorList() {
        return errorList;
    }
    
    /**
     * @param errorList the errorList to set
     */
    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    /**
     * 
     * @return 
     */
    public Object getError() {
        if(getErrorList() != null && !getErrorList().isEmpty()) {
            return getErrorList();
        }
        
        return getMessage();
    }
}
