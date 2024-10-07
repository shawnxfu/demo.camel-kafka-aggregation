package dev.sfu.demo.camel_kafka.model;

public class Event {
    private String type;
    private String message;

    public Event() {
    }

    public Event(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
