package com.chirag.weatherapp;

class Weather {
    private String icon;
    private String description;
    private String main;
    private long id;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String value) {
        this.icon = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String value) {
        this.main = value;
    }

    public long getid() {
        return id;
    }

    public void setid(long value) {
        this.id = value;
    }
}
