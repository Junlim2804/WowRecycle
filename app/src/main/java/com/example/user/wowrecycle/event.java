package com.example.user.wowrecycle;

public class event {
    int background;
    String eventDesc;
    String view;

    public event() {
    }

    public event(int background, String eventDesc, String view) {
        this.background = background;
        this.eventDesc = eventDesc;
        this.view = view;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
