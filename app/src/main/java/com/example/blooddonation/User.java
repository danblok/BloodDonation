package com.example.blooddonation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String sex;
    private String city;
    private String bloodType;
    private String seriesAndNumber;
    private List<String> bloodComponents;
    private String role;

    public User() {
    }

    public User(String firstName, String lastName, String middleName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
    }

    public User(String id, String firstName, String lastName, String middleName, String email, String password, Date dateOfBirth, String sex, String city, String bloodType, String seriesAndNumber, List<String> bloodComponents, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.city = city;
        this.bloodType = bloodType;
        this.seriesAndNumber = seriesAndNumber;
        this.bloodComponents = bloodComponents;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getSeriesAndNumber() {
        return seriesAndNumber;
    }

    public void setSeriesAndNumber(String seriesAndNumber) {
        this.seriesAndNumber = seriesAndNumber;
    }

    public List<String> getBloodComponents() {
        return bloodComponents;
    }

    public void setBloodComponents(List<String> bloodComponents) {
        this.bloodComponents = bloodComponents;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
