package co.expertic.training.web.controller.questionnaire;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.constant.MessageBundle;
import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.DataType;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.questionnaire.DataTypeService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Web Service of DataType <br>
 * Creation Info:  <br>
 * date 15/08/2015 <br>
 * @author Angela Ramirez
 */
@RequestMapping("/dataType")
@RestController
public class DataTypeController {

    @Autowired
    private DataTypeService dataTypeService;


    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public @ResponseBody Response create(@RequestBody DataType dataType) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (dataType == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            dataTypeService.create(dataType);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.DATA_TYPE_PROPERTIES, "dataTypeCreated"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(DataTypeController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(DataTypeController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        } 
    }


    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public @ResponseBody Response updateDataType(@RequestBody DataType dataType) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (dataType == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            List<DataType> dataTypeList = dataTypeService.findByDataTypeId(dataType);
            if (dataTypeList != null && !dataTypeList.isEmpty()) {
                dataTypeService.merge(dataType);
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.DATA_TYPE_PROPERTIES, "dataTypeUpdated"));
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.DATA_TYPE_PROPERTIES, "dataTypeDoesNotExist"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (TrainingException error) {
            Logger.getLogger(DataTypeController.class.getName()).log(Level.SEVERE, null, error);
            responseService.setOutput(error.getErrorList());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(error.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(DataTypeController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    

    @RequestMapping(value = "/get/by/id/", method = RequestMethod.POST)
    public @ResponseBody Response getDataTypeById(@RequestBody DataType dataType) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (dataType == null) {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            List<DataType> dataTypeList = dataTypeService.findByDataTypeId(dataType);
            if(dataTypeList != null && !dataTypeList.isEmpty()) {
                DataType dataTypeObj = dataTypeList.get(0);
                responseService.setOutput(dataTypeObj);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
            } else {
                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.DATA_TYPE_PROPERTIES, "dataTypeNotFound"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(DataTypeController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
    

    @RequestMapping(value = "/get/all/", method = RequestMethod.POST)
    public @ResponseBody Response getAllDataType(@RequestBody SePaginator sePaginator) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<DataType> dataTypeList = dataTypeService.findAll(sePaginator);
            responseService.setOutput(dataTypeList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(DataTypeController.class.getName()).log(Level.SEVERE, null, e);
            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(strResponse).build();
        }
    }
}
