/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class MarketingDTO {
    
    private int count;
    private String sportEquipment;
    private String brand;
    private String model;
    private int sportEquipmentCount;
    private Date initDate;
    private Date endDate;
    private int countryId;
    private int potentiometer;
    private int potentiometerModel;
    private int pulsometer;
    private int pulsometerModel;
    private int bike;
    private int bikeModel;
    private int shoe;
    private String sex;
    private int discipline;
    private int age;
    private int role;
    private int sportEquipmentType;
    
    
    public MarketingDTO(String sportEquipment, String brand, String model, int sportEquipmentCount){
        this.sportEquipment = sportEquipment;
        this.brand = brand;
        this.model = model;
        this.sportEquipmentCount = sportEquipmentCount;
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

    public int getSportEquipmentCount() {
        return sportEquipmentCount;
    }

    public void setSportEquipmentCount(int sportEquipmentCount) {
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getPotentiometer() {
        return potentiometer;
    }

    public void setPotentiometer(int potentiometer) {
        this.potentiometer = potentiometer;
    }

    public int getPotentiometerModel() {
        return potentiometerModel;
    }

    public void setPotentiometerModel(int potentiometerModel) {
        this.potentiometerModel = potentiometerModel;
    }

    public int getPulsometer() {
        return pulsometer;
    }

    public void setPulsometer(int pulsometer) {
        this.pulsometer = pulsometer;
    }

    public int getPulsometerModel() {
        return pulsometerModel;
    }

    public void setPulsometerModel(int pulsometerModel) {
        this.pulsometerModel = pulsometerModel;
    }

    public int getBike() {
        return bike;
    }

    public void setBike(int bike) {
        this.bike = bike;
    }

    public int getBikeModel() {
        return bikeModel;
    }

    public void setBikeModel(int bikeModel) {
        this.bikeModel = bikeModel;
    }

    public int getShoe() {
        return shoe;
    }

    public void setShoe(int shoe) {
        this.shoe = shoe;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getDiscipline() {
        return discipline;
    }

    public void setDiscipline(int discipline) {
        this.discipline = discipline;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getSportEquipmentType() {
        return sportEquipmentType;
    }

    public void setSportEquipmentType(int sportEquipmentType) {
        this.sportEquipmentType = sportEquipmentType;
    }
    
    
    
    
    
}
