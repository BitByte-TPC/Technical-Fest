package com.priyanshnama.technical_fest.ui.events;

public class Event {
    private String name, image_url;

    public Event(){}

    public Event(String name, String image_url){
        this.image_url = image_url;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
