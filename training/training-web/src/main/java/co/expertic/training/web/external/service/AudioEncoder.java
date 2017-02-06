/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.external.service;

import co.expertic.training.model.dto.PlanAudioDTO;
import com.google.gson.Gson;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Edwin G
 */
public class AudioEncoder implements Encoder.Text<PlanAudioDTO> {
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
   public String encode(final PlanAudioDTO audio) throws EncodeException {
       return gson.toJson(audio); 
   }
}
