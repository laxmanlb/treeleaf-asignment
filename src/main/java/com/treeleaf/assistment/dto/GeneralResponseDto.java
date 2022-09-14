package com.treeleaf.assistment.dto;

import java.io.Serializable;

public class GeneralResponseDto implements Serializable {

    private String responseMsg;
    private boolean responseStatus;


    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public boolean isResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(boolean responseStatus) {
        this.responseStatus = responseStatus;
    }
}
