/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonFormat(pattern = "dd/MM/yyyy")
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

    private Integer cityId;

    private Short stateId;
    
    
    public UserDTO() {
    }

   public UserDTO(Integer userId, String name, String lastName, String email, Date birthDate, String address,
                   String sex, BigInteger weight, String phone, String cellphone, Integer cityId, 
                   Short stateId, String login, String facebookPage, String postalCode) {
        this.userId = userId;
        this.login = login;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.sex = sex;
        this.weight = weight;
        this.phone = phone;
        this.cellphone = cellphone;
        this.cityId = cityId;
        this.stateId = stateId;
        this.facebookPage = facebookPage;
        this.postalCode = postalCode;
        
    }


    public static UserDTO mapFromUserEntity(User user) {
        return new UserDTO(user.getUserId(), user.getName(), user.getLastName(),user.getEmail(), user.getBirthDate(), user.getAddress(), 
                           user.getSex(), user.getWeight(), user.getPhone(), user.getCellphone(), (user.getCityId()!=null?user.getCityId().getCityId():null), 
                           user.getStateId(), user.getLogin(), user.getFacebookPage(), user.getPostalCode());
    }
    
      public static List<UserDTO> mapFromUsersEntities(List<User> users) {
        return users.stream().map((user) -> mapFromUserEntity(user)).collect(Collectors.toList());
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BigInteger getWeight() {
        return weight;
    }

    public void setWeight(BigInteger weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getIndMetricSys() {
        return indMetricSys;
    }

    public void setIndMetricSys(String indMetricSys) {
        this.indMetricSys = indMetricSys;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    
}
