/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.user;

import co.expertic.base.util.DateUtil;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.user.UserFittingService;
import co.expertic.training.web.enums.StatusResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String ROOT = "e:/upload-training/";

    @Autowired
    private UserFittingService userFittingService;

    @RequestMapping(value = "/upload/{toUserId}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ResponseService> uploadVideo(@RequestParam("fileToUpload") MultipartFile file,
            @PathVariable Integer toUserId,
            @PathVariable Integer fromUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {

                String fileName = DateUtil.getCurrentDate("ddMMyyyyHHmm") + "_" + fromUserId + "_" + toUserId;
                File directory = new File(ROOT);
                File archivo = new File(ROOT + fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                        //storageService.store(file);
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                    //storageService.store(file);
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

}
