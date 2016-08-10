/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

/**
 *
 * @author Edwin G
 */
public class OpenTokDTO {
    
    private String apiKey;
    private String sessionId;
    private String token;
    
    public OpenTokDTO(String apiKey, String sessionId, String token){
        this.apiKey = apiKey;
        this.sessionId = sessionId;
        this.token = token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
    
    
}
