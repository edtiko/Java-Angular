/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.user;

import co.expertic.base.util.DateUtil;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.UserFittingVideoDTO;
import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.UserFitting;
import co.expertic.training.model.entities.UserFittingHistory;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.StorageService;
import co.expertic.training.service.user.UserFittingService;
import co.expertic.training.web.enums.StatusResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Edwin G
 */
@RestController
@RequestMapping("/userFitting")
public class UserFittingController {

    private static final Logger LOGGER = Logger.getLogger(UserFittingController.class);
    private static final String ROOT = "e:/upload-training/fitting/";

    @Autowired
    private UserFittingService userFittingService;

    private final StorageService storageService;

    @Autowired
    public UserFittingController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/upload/{userFittingId}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ResponseService> uploadVideo(@RequestParam("file") MultipartFile file, @PathVariable Integer userFittingId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {

                String ext = file.getOriginalFilename().split("\\.")[1];
                String fileName = DateUtil.getCurrentDate("ddMMyyyyHHmm") + "_" + userFittingId+"."+ext;
                String name =  DateUtil.getCurrentDate("ddMMyyyyHHmm") + "_" + userFittingId;
                File directory = new File(ROOT);
                File archivo = new File(ROOT + fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                }

                UserFittingHistory userFittingHistory = userFittingService.getByVideoName(name);
                if (userFittingHistory == null) {
                    UserFittingHistory video = new UserFittingHistory();
                    video.setUserFittingId(new UserFitting(userFittingId));
                    video.setCreationDate(Calendar.getInstance().getTime());
                    video.setVideoName(name);
                    video.setStateId(new State(StateEnum.PENDING.getId()));
                    userFittingService.createVideo(video);
                }

                responseService.setStatus(StatusResponse.SUCCESS.getName());
                responseService.setOutput("Video Cargado Correctamente.");
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                responseService.setDetail(e.getMessage());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
        } else {
            strResponse.append("Video cargado esta vacio.");
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/video/{userFittingId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getVideo(@PathVariable Integer userFittingId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            UserFittingVideoDTO userFittingHistory = userFittingService.getLastVideo(userFittingId);
            responseService.setOutput(userFittingHistory);
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

    @RequestMapping(value = "/get/history/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getUserFittingHistory(@PathVariable Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<UserFittingVideoDTO> userFittingHistory = userFittingService.getUserFittingHistory(userId);
            responseService.setOutput(userFittingHistory);
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

    @RequestMapping(value = "/change/state/{stateId}/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getUserFittingHistory(@PathVariable Integer stateId, @PathVariable Integer id) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            userFittingService.changeState(stateId, id);
            responseService.setOutput("Estado actualizado con éxito.");
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

    @RequestMapping(value = "/files/{videoPath}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String videoPath) throws IOException {

        Resource file = storageService.loadAsResource("fitting/" + videoPath);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, file.getURL().openConnection().getContentType())
                .body(file);
    }

}
