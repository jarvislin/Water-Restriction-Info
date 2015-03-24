package com.jarvislin.waterrestrictioninfo.model;

/**
 * Created by Jarvis Lin on 2015/3/22.
 */
public class Reservoir {
    private String name;
    private float capacity;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
