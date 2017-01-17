/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class MarketingDTO {
    
    private String order;
    private int limit;
    private int page;
    private String filter;
    private int count;
    private String sportEquipment;
    private String brand;
    private String model;
    private Integer sportEquipmentCount;
    private Date initDate;
    private Date endDate;
    private Integer countryId;
    private Integer potentiometer;
    private Integer potentiometerModel;
    private Integer pulsometer;
    private Integer pulsometerModel;
    private Integer bike;
    private Integer bikeModel;
    private Integer shoe;
    private String sex;
    private Integer discipline;
    private Integer age;
    private Integer role;
    private Integer sportEquipmentType;
    
    public MarketingDTO(){
        
    }
    
    
    public MarketingDTO(String sportEquipment, String brand, String model, Long sportEquipmentCount){
        this.sportEquipment = sportEquipment;
        this.brand = brand;
        this.model = model;
        this.sportEquipmentCount = sportEquipmentCount.intValue();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSportEquipment() {
        return sportEquipment;
    }

    public void setSportEquipment(String sportEquipment) {
        this.sportEquipment = sportEquipment;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getSportEquipmentCount() {
        return sportEquipmentCount;
    }

    public void setSportEquipmentCount(Integer sportEquipmentCount) {
        this.sportEquipmentCount = sportEquipmentCount;
    }


    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getPotentiometer() {
        return potentiometer;
    }

    public void setPotentiometer(Integer potentiometer) {
        this.potentiometer = potentiometer;
    }

    public Integer getPotentiometerModel() {
        return potentiometerModel;
    }

    public void setPotentiometerModel(Integer potentiometerModel) {
        this.potentiometerModel = potentiometerModel;
    }

    public Integer getPulsometer() {
        return pulsometer;
    }

    public void setPulsometer(Integer pulsometer) {
        this.pulsometer = pulsometer;
    }

    public Integer getPulsometerModel() {
        return pulsometerModel;
    }

    public void setPulsometerModel(Integer pulsometerModel) {
        this.pulsometerModel = pulsometerModel;
    }

    public Integer getBike() {
        return bike;
    }

    public void setBike(Integer bike) {
        this.bike = bike;
    }

    public Integer getBikeModel() {
        return bikeModel;
    }

    public void setBikeModel(Integer bikeModel) {
        this.bikeModel = bikeModel;
    }

    public Integer getShoe() {
        return shoe;
    }

    public void setShoe(Integer shoe) {
        this.shoe = shoe;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Integer discipline) {
        this.discipline = discipline;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getSportEquipmentType() {
        return sportEquipmentType;
    }

    public void setSportEquipmentType(int sportEquipmentType) {
        this.sportEquipmentType = sportEquipmentType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
       
    
}
