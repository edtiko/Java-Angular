/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.external.service;

import co.expertic.training.model.dto.PlanAudioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Edwin G
 */
public class AudioDecoder implements Decoder.Text<PlanAudioDTO> {

    private Gson gson;

    @Override
    public void init(EndpointConfig config) {
        gson = new Gson();
    }

    @Override
    public PlanAudioDTO decode(String textMessage) throws DecodeException {
        
        ObjectMapper mapper = new ObjectMapper();
         PlanAudioDTO msgdto = null;
        try {
            msgdto = mapper.readValue(textMessage, PlanAudioDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(MessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
       return msgdto;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
