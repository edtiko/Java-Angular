/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class UserDTO {
    
    private Integer userId;

    
    private String login;

    private String password;

    private String name;
 
    private String lastName;
  
    private String email;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "CET")
    private Date birthDate;
 
    private String sex;

    private BigInteger weight;
  
    private String phone;
   
    private String cellphone;
 
    private String address;

    private String postalCode;

    private byte[] profilePhoto;
 
    private String facebookPage;

    private String indMetricSys;
   
    private Date creationDate;

    private String cityId;

    private String stateId;

    private String starId;
    
}
