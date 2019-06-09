package com.opus_bd.salestracking.Model;

public class MessageEvent {
    public MessageEvent(boolean update) {
        this.update = update;
    }

    private boolean update;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
