/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.user;

import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.user.UserService;
import co.expertic.training.web.enums.StatusResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edwin G
 */
@RestController
@RequestMapping("account")
public class AccountController {

    private static final Logger LOGGER = Logger.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "get/subscriptions/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getSubscriptions(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            String json = null;
            String jsonResponse = userService.getSubscriptions(userId);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();
                if (statusRes.equals("success")) {
                    if (jo.get("output") != null && !jo.get("output").isJsonNull()) {
                        //JsonElement jelem =  gson.fromJson(jo.get("output"), JsonElement.class);
                        json = jo.get("output").getAsJsonPrimitive().toString();
                    }
                }

            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(json);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "cancel/subscription/{subscriptionId}/{status}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> cancelSubscription(@PathVariable("subscriptionId") Integer subscriptionId, @PathVariable("status") String status) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            String jsonResponse = userService.cancelSubscription(subscriptionId, status);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();
                if (statusRes.equals("success")) {
                    responseService.setOutput("Suscripción cancelada con éxito.");
                }

            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
    
    @RequestMapping(value = "get/address/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getInfoAddress(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            String json = null;
            String jsonResponse = userService.getInfoAddressUser(userId);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();
                if (statusRes.equals("success")) {
                    if (jo.get("output") != null && !jo.get("output").isJsonNull()) {
                        json = jo.get("output").getAsJsonPrimitive().toString();
                    }
                }

            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(json);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
    
    
      @RequestMapping(value = "edit/address", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ResponseService> editUserAddress(@RequestBody String addressUserJson) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            String jsonResponse = userService.editAddressUser(addressUserJson);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();
                 if (statusRes.equals("success")) {
                    responseService.setMessage("Dirección actualizada con éxito.");
                }else{
                    responseService.setMessage(jo.get("output").getAsString());
                 }
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

}
