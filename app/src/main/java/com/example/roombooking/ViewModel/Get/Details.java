package com.example.roombooking.ViewModel.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {
    @SerializedName("FACILITY NAME")
    @Expose
    private String fACILITYNAME;
    @SerializedName("ROOM TYPE")
    @Expose
    private String rOOMTYPE;
    @SerializedName("EQUIPMENTS")
    @Expose
    private String eQUIPMENTS;
    @SerializedName("GATHERING")
    @Expose
    private String gATHERING;
    @SerializedName("VANUE DATE")
    @Expose
    private String vANUEDATE;
    @SerializedName("PURPOSE OF VANUE")
    @Expose
    private String pURPOSEOFVANUE;
    @SerializedName("EXTRA NOTES")
    @Expose
    private String eXTRANOTES;
    @SerializedName("START TIME")
    @Expose
    private String sTARTTIME;
    @SerializedName("END TIME")
    @Expose
    private String eNDTIME;

    public String getFACILITYNAME() {
        return fACILITYNAME;
    }

    public void setFACILITYNAME(String fACILITYNAME) {
        this.fACILITYNAME = fACILITYNAME;
    }

    public String getROOMTYPE() {
        return rOOMTYPE;
    }

    public void setROOMTYPE(String rOOMTYPE) {
        this.rOOMTYPE = rOOMTYPE;
    }

    public String getEQUIPMENTS() {
        return eQUIPMENTS;
    }

    public void setEQUIPMENTS(String eQUIPMENTS) {
        this.eQUIPMENTS = eQUIPMENTS;
    }

    public String getGATHERING() {
        return gATHERING;
    }

    public void setGATHERING(String gATHERING) {
        this.gATHERING = gATHERING;
    }

    public String getVANUEDATE() {
        return vANUEDATE;
    }

    public void setVANUEDATE(String vANUEDATE) {
        this.vANUEDATE = vANUEDATE;
    }

    public String getPURPOSEOFVANUE() {
        return pURPOSEOFVANUE;
    }

    public void setPURPOSEOFVANUE(String pURPOSEOFVANUE) {
        this.pURPOSEOFVANUE = pURPOSEOFVANUE;
    }

    public String getEXTRANOTES() {
        return eXTRANOTES;
    }

    public void setEXTRANOTES(String eXTRANOTES) {
        this.eXTRANOTES = eXTRANOTES;
    }

    public String getSTARTTIME() {
        return sTARTTIME;
    }

    public void setSTARTTIME(String sTARTTIME) {
        this.sTARTTIME = sTARTTIME;
    }

    public String getENDTIME() {
        return eNDTIME;
    }

    public void setENDTIME(String eNDTIME) {
        this.eNDTIME = eNDTIME;
    }
}
