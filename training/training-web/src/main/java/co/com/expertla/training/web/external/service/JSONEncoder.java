/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Edwin G
 */
public class JSONEncoder implements Encoder.Text<Object> {
   private Gson gson;
   @Override
   public void init(EndpointConfig config) {
       gson = new Gson(); 
   }
   @Override
   public void destroy() {
       // do nothing
   }
   @Override
   public String encode(Object object) throws EncodeException {
       return gson.toJson(object); 
   }
}
