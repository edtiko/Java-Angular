/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.external.service;

import co.expertic.training.model.dto.PlanVideoDTO;
import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Edwin G
 */
public class VideoEncoder implements Encoder.Text<PlanVideoDTO> {
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
   public String encode(final PlanVideoDTO message) throws EncodeException {
       return gson.toJson(message); 
   }
}