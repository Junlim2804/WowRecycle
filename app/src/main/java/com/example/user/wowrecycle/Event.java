package com.example.user.wowrecycle;

public class Event {
    String background;
    String eventDesc;
    String view;
    String url;

    public Event() {
    }

    public Event(String background, String eventDesc, String view, String url) {
        this.background = background;
        this.eventDesc = eventDesc;
        this.view = view;
        this.url = url;
    }

    public Event(String background, String eventDesc, String url) {
        this.background = background;
        this.eventDesc = eventDesc;
        this.url = url;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
