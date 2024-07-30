package com.chirag.weatherapp;

class Sys {
    private String country;
    private long sunrise;
    private long sunset;
    private long id;
    private long type;

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long value) {
        this.sunrise = value;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long value) {
        this.sunset = value;
    }

    public long getid() {
        return id;
    }

    public void setid(long value) {
        this.id = value;
    }

    public long getType() {
        return type;
    }

    public void setType(long value) {
        this.type = value;
    }
}
