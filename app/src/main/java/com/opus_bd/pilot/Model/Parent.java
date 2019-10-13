package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

    private Integer titleid;

    private PilotCheckBodyM pilotCheckBodyM;


    public Integer getTitleid() {
        return titleid;
    }

    public void setTitleid(Integer titleid) {
        this.titleid = titleid;
    }

    public PilotCheckBodyM getPilotCheckBodyM() {
        return pilotCheckBodyM;
    }

    public void setPilotCheckBodyM(PilotCheckBodyM pilotCheckBodyM) {
        this.pilotCheckBodyM = pilotCheckBodyM;
    }
}
