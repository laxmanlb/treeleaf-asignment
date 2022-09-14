/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.treeleaf.assistment.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Laxman Baniya
 */
public class StudentMarksDetailsDto implements Serializable {

    private String userName;
    private double percentage;
    private List<StudentMarksDto> marksDetails;
    private boolean responseStaus;
    private String responseMsg;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public List<StudentMarksDto> getMarksDetails() {
        return marksDetails;
    }

    public void setMarksDetails(List<StudentMarksDto> marksDetails) {
        this.marksDetails = marksDetails;
    }

    public boolean isResponseStaus() {
        return responseStaus;
    }

    public void setResponseStaus(boolean responseStaus) {
        this.responseStaus = responseStaus;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
